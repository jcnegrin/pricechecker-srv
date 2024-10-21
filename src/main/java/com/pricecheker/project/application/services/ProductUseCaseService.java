package com.pricecheker.project.application.services;

import com.pricecheker.project.application.mapper.ProductUseCaseMapper;
import com.pricecheker.project.application.ports.inbound.CategoryUseCaseServicePort;
import com.pricecheker.project.application.ports.inbound.PriceUseCaseServicePort;
import com.pricecheker.project.application.ports.inbound.ProductUseCaseServicePort;
import com.pricecheker.project.application.ports.inbound.ShopUseCaseServicePort;
import com.pricecheker.project.application.ports.outbound.ProductRepositoryPort;
import com.pricecheker.project.application.utils.SimilarityUtils;
import com.pricecheker.project.domain.entity.CategoryDomainEntity;
import com.pricecheker.project.domain.entity.PriceDomainEntity;
import com.pricecheker.project.domain.entity.ProductDomainEntity;
import com.pricecheker.project.domain.entity.ShopDomainEntity;
import com.pricecheker.project.domain.exception.ProductNotFoundException;
import com.pricecheker.project.domain.view.ProductView;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductUseCaseService implements ProductUseCaseServicePort {

  private final ProductRepositoryPort productRepositoryPort;
  private final ShopUseCaseServicePort shopUseCaseServicePort;
  private final CategoryUseCaseServicePort categoryUseCaseServicePort;
  private final PriceUseCaseServicePort priceUseCaseServicePort;
  private final ProductUseCaseMapper mapper;

  @Override
  public List<ProductView> getProductsByShopIdAndCategory(String shopId, String categoryId) {
    if (!StringUtils.hasText(shopId) || !StringUtils.hasText(categoryId)) {
      log.error("Shop ID or Category ID cannot be null or empty");
      throw new IllegalArgumentException("Shop ID and Category ID must be provided");
    }

    log.info("Fetching products for shopId: {} and categoryId: {}", shopId, categoryId);

    // Fetching the shop entity by ID
    ShopDomainEntity shop = shopUseCaseServicePort.getShopById(shopId);
    log.debug(String.format("Fetched shop: %s with ID: %s", shop.getName(), shop.getId()));

    // Fetching the category entity by ID
    CategoryDomainEntity category = categoryUseCaseServicePort.getCategoryById(categoryId);
    log.debug(
        String.format("Fetched category: %s with ID: %s", category.getName(), category.getId()));

    // Fetching and mapping products
    List<ProductView> products =
        productRepositoryPort.findProductByShopAndCategory(shopId, categoryId).stream()
            .map(mapper::toView)
            .map(
                product -> {
                  PriceDomainEntity price =
                      priceUseCaseServicePort.getLatestPriceByProductId(product.getId());
                  product.setPrice(price.getAmount());
                  return product;
                })
            .toList();

    log.info(
        "Found {} products for shop: {} and category: {}",
        products.size(),
        shop.getName(),
        category.getName());

    return products;
  }

  @Override
  public ProductView getProductById(String productId) {
    if (!StringUtils.hasText(productId)) {
      log.error("Product ID cannot be null or empty");
      throw new IllegalArgumentException("Product ID must be provided");
    }

    log.info("Fetching product by ID: {}", productId);

    ProductDomainEntity product =
        productRepositoryPort
            .findProductById(productId)
            .orElseThrow(
                () -> {
                  log.error("Product with ID {} not found", productId);
                  return new ProductNotFoundException(productId);
                });

    ProductView productView = mapper.toView(product);

    log.info("Fetched product: {} with ID: {}", productView.getName(), productId);
    return productView;
  }

  @Override
  public List<ProductView> getSimilarProducts(String productId) {

    ProductDomainEntity product =
        productRepositoryPort
            .findProductById(productId)
            .orElseThrow(
                () -> {
                  log.error("Product with ID {} not found", productId);
                  return new ProductNotFoundException(productId);
                });

    CategoryDomainEntity category = product.getCategory();
    List<CategoryDomainEntity> categories = categoryUseCaseServicePort.getAllCategories();

    List<CategoryDomainEntity> similarCategories =
        SimilarityUtils.findSimilarCategories(category, categories);

    List<ProductDomainEntity> products =
        similarCategories.stream()
            .map(CategoryDomainEntity::getId)
            .map(productRepositoryPort::findProductsByCategory)
            .flatMap(List::stream)
            .toList();

    return products.stream().map(mapper::toView).toList();
  }
}
