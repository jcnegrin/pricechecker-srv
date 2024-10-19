package com.pricecheker.project.application.services.tasks;

import com.pricecheker.project.application.ports.inbound.DailyProductExtractTaskUseCasePort;
import com.pricecheker.project.application.ports.outbound.ProductCategoryRepositoryPort;
import com.pricecheker.project.application.ports.outbound.ProductPriceRepositoryPort;
import com.pricecheker.project.application.ports.outbound.ProductRepositoryPort;
import com.pricecheker.project.application.ports.outbound.StoreRepositoryPort;
import com.pricecheker.project.application.services.tasks.factory.ScraperFactory;
import com.pricecheker.project.application.services.tasks.model.*;
import com.pricecheker.project.application.services.tasks.scrapers.ScraperContext;
import com.pricecheker.project.application.utils.CurrencyConverterUtils;
import com.pricecheker.project.domain.entity.CategoryDomainEntity;
import com.pricecheker.project.domain.entity.PriceDomainEntity;
import com.pricecheker.project.domain.entity.ProductDomainEntity;
import com.pricecheker.project.domain.entity.ShopDomainEntity;
import java.math.BigDecimal;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class DailyProductExtractTaskUseCaseAdapter implements DailyProductExtractTaskUseCasePort {

  private final StoreRepositoryPort storeRepositoryPort;
  private final ProductRepositoryPort productRepositoryPort;
  private final ProductCategoryRepositoryPort productCategoryRepositoryPort;
  private final ProductPriceRepositoryPort productPriceRepositoryPort;

  @Override
  public void executeDailyTask() {
    log.info("Starting daily product extraction task...");

    List<ShopDomainEntity> shops = getShops();
    if (shops.isEmpty()) return;

    List<ShopProductModel> shopProductModels = extractProductsFromShops(shops);

    processAndSaveProducts(shopProductModels);

    log.info(
        "Daily product extraction task completed. {} stores processed.", shopProductModels.size());
  }

  private List<ShopDomainEntity> getShops() {
    try {
      List<ShopDomainEntity> shops = storeRepositoryPort.findAll();
      log.info("Found {} stores to process.", shops.size());
      return shops;
    } catch (Exception e) {
      log.error("Error retrieving stores from repository: {}", e.getMessage(), e);
      return Collections.emptyList();
    }
  }

  private List<ShopProductModel> extractProductsFromShops(List<ShopDomainEntity> shops) {
    List<ShopProductModel> shopProductModels = new ArrayList<>();

    for (ShopDomainEntity shop : shops) {
      try {
        log.info("Starting product extraction for store: {}", shop.getName());
        ScraperContext scraperContext =
            new ScraperContext(ScraperFactory.getScraper(shop.getName(), shop.getUrl()));
        Map<String, List<ScrapedProduct>> products = scraperContext.scrapeProducts();
        log.info(
            "Product extraction complete for store: {}. {} products found.",
            shop.getName(),
            products.size());

        shopProductModels.add(new ShopProductModel(shop, products));

      } catch (Exception e) {
        log.error(
            "Error during product extraction for store {}: {}", shop.getName(), e.getMessage(), e);
      }
    }
    return shopProductModels;
  }

  private void processAndSaveProducts(List<ShopProductModel> shopProductModels) {
    for (ShopProductModel shop : shopProductModels) {
      Map<String, List<ScrapedProduct>> productsByCategory = shop.getProducts();
      for (Map.Entry<String, List<ScrapedProduct>> entry : productsByCategory.entrySet()) {
        String categoryName = entry.getKey();
        List<ScrapedProduct> products = entry.getValue();

        CategoryDomainEntity category = getOrCreateCategory(categoryName);
        saveOrUpdateProductsForCategory(products, category, shop.getShop());
      }
    }
  }

  private CategoryDomainEntity getOrCreateCategory(String categoryName) {
    return productCategoryRepositoryPort
        .findCategoryByName(categoryName.toUpperCase())
        .orElseGet(
            () -> {
              CategoryDomainEntity newCategory =
                  CategoryDomainEntity.builder()
                      .id(UUID.randomUUID().toString())
                      .name(categoryName)
                      .build();
              return productCategoryRepositoryPort.saveCategory(newCategory);
            });
  }

  private void saveOrUpdateProductsForCategory(
      List<ScrapedProduct> products, CategoryDomainEntity category, ShopDomainEntity shop) {
    for (ScrapedProduct product : products) {
      if (product == null) continue;

      Optional<ProductDomainEntity> existingProduct =
          productRepositoryPort.findProductByNameAndShop(product.getName(), shop);

      if (existingProduct.isPresent()) {

        BigDecimal newPrice = CurrencyConverterUtils.convertStringToBigDecimal(product.getPrice());
        Optional<PriceDomainEntity> latestPrice =
            productPriceRepositoryPort.findLatestPriceByProduct(existingProduct.get());

        if (latestPrice.isEmpty() || latestPrice.get().getAmount().compareTo(newPrice) != 0) {
          saveProductPrice(existingProduct.get(), newPrice);
        }
      } else {
        ProductDomainEntity newProduct = createAndSaveProduct(product, category, shop);
        saveProductPrice(
            newProduct, CurrencyConverterUtils.convertStringToBigDecimal(product.getPrice()));
      }
    }
  }

  private ProductDomainEntity createAndSaveProduct(
      ScrapedProduct product, CategoryDomainEntity category, ShopDomainEntity shop) {
    ProductDomainEntity productDomainEntity =
        ProductDomainEntity.builder()
            .id(UUID.randomUUID().toString())
            .name(product.getName())
            .category(category)
            .shop(shop)
            .build();
    return productRepositoryPort.saveProduct(productDomainEntity);
  }

  private void saveProductPrice(ProductDomainEntity product, BigDecimal price) {
    PriceDomainEntity priceEntity =
        PriceDomainEntity.builder()
            .id(UUID.randomUUID().toString())
            .product(product)
            .amount(price)
            .build();
    productPriceRepositoryPort.saveProductPrice(priceEntity);
  }
}
