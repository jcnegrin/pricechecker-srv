package com.pricecheker.project.application.services.tasks;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.pricecheker.project.application.ports.outbound.ProductCategoryRepositoryPort;
import com.pricecheker.project.application.ports.outbound.ProductPriceRepositoryPort;
import com.pricecheker.project.application.ports.outbound.ProductRepositoryPort;
import com.pricecheker.project.application.ports.outbound.ShopRepositoryPort;
import com.pricecheker.project.application.services.tasks.factory.ScraperFactory;
import com.pricecheker.project.application.services.tasks.interfaces.ScraperStrategy;
import com.pricecheker.project.application.services.tasks.model.ScrapedProduct;
import com.pricecheker.project.domain.entity.CategoryDomainEntity;
import com.pricecheker.project.domain.entity.PriceDomainEntity;
import com.pricecheker.project.domain.entity.ProductDomainEntity;
import com.pricecheker.project.domain.entity.ShopDomainEntity;
import java.math.BigDecimal;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DailyProductExtractTaskUseCaseAdapterTest {

  @Mock private ShopRepositoryPort shopRepositoryPort;

  @Mock private ProductRepositoryPort productRepositoryPort;

  @Mock private ProductCategoryRepositoryPort productCategoryRepositoryPort;

  @Mock private ProductPriceRepositoryPort productPriceRepositoryPort;

  @InjectMocks private DailyProductExtractTaskUseCaseAdapter dailyProductExtractTaskUseCaseAdapter;

  @Test
  void executeDailyTask_shouldDoNothingWhenNoShopsFound() {
    // Setup mock behavior: no shops found
    when(shopRepositoryPort.findAll()).thenReturn(Collections.emptyList());

    // Call the method
    dailyProductExtractTaskUseCaseAdapter.executeDailyTask();

    // Verify that no further interactions happen when no shops are found
    verify(shopRepositoryPort).findAll();
    verifyNoMoreInteractions(
        shopRepositoryPort,
        productRepositoryPort,
        productCategoryRepositoryPort,
        productPriceRepositoryPort);
  }

  void executeDailyTask_shouldProcessAndSaveNewProduct() {
    // Setup mock shop
    ShopDomainEntity shop =
        ShopDomainEntity.builder()
            .id(UUID.randomUUID().toString())
            .name("Mercadona")
            .url("http://mercadona.es")
            .build();

    List<ShopDomainEntity> shops = List.of(shop);
    when(shopRepositoryPort.findAll()).thenReturn(shops);

    // Setup scraped products
    ScrapedProduct scrapedProduct =
        ScrapedProduct.builder()
            .id(UUID.randomUUID())
            .name("New Product")
            .price("10.99")
            .imgUrl("http://image-url.com")
            .description("A new product")
            .build();

    Map<String, List<ScrapedProduct>> products = Map.of("Category 1", List.of(scrapedProduct));

    // Mock ScraperStrategy instead of ScraperContext
    ScraperStrategy scraperStrategy = mock(ScraperStrategy.class);
    when(scraperStrategy.scrapeProducts()).thenReturn(products);

    // Mock ScraperFactory to return the mocked scraper strategy
    // when(ScraperFactory.getScraper(shop.getName(), shop.getUrl())).thenReturn(scraperStrategy);

    // Setup category behavior
    CategoryDomainEntity category =
        CategoryDomainEntity.builder().id(UUID.randomUUID().toString()).name("Category 1").build();

    when(productCategoryRepositoryPort.findCategoryByName("CATEGORY 1"))
        .thenReturn(Optional.of(category));

    // Mock new product creation
    when(productRepositoryPort.findProductByNameAndShop(scrapedProduct.getName(), shop))
        .thenReturn(Optional.empty());

    // Call the method
    dailyProductExtractTaskUseCaseAdapter.executeDailyTask();

    // Verify the product and price are saved
    verify(productRepositoryPort).saveProduct(any(ProductDomainEntity.class));
    verify(productPriceRepositoryPort).saveProductPrice(any(PriceDomainEntity.class));
  }

  void executeDailyTask_shouldNotSavePriceIfNoPriceChange() {
    // Setup mock shop
    ShopDomainEntity shop =
        ShopDomainEntity.builder()
            .id(UUID.randomUUID().toString())
            .name("Mercadona")
            .url("http://testshop.com")
            .build();

    List<ShopDomainEntity> shops = List.of(shop);
    when(shopRepositoryPort.findAll()).thenReturn(shops);

    // Setup scraped products
    ScrapedProduct scrapedProduct =
        ScrapedProduct.builder()
            .id(UUID.randomUUID())
            .name("Existing Product")
            .price("10.99")
            .imgUrl("http://image-url.com")
            .description("An existing product")
            .build();

    Map<String, List<ScrapedProduct>> products = Map.of("Category 1", List.of(scrapedProduct));

    // Mock ScraperStrategy instead of ScraperContext
    ScraperStrategy scraperStrategy = mock(ScraperStrategy.class);
    when(scraperStrategy.scrapeProducts()).thenReturn(products);

    // Mock ScraperFactory to return the mocked scraper strategy
    when(ScraperFactory.getScraper(shop.getName(), shop.getUrl())).thenReturn(scraperStrategy);

    // Mock category behavior
    CategoryDomainEntity category =
        CategoryDomainEntity.builder().id(UUID.randomUUID().toString()).name("Category 1").build();

    when(productCategoryRepositoryPort.findCategoryByName("CATEGORY 1"))
        .thenReturn(Optional.of(category));

    // Mock existing product and price behavior
    ProductDomainEntity existingProduct =
        ProductDomainEntity.builder()
            .id(UUID.randomUUID().toString())
            .name("Existing Product")
            .category(category)
            .shop(shop)
            .build();

    when(productRepositoryPort.findProductByNameAndShop(scrapedProduct.getName(), shop))
        .thenReturn(Optional.of(existingProduct));

    PriceDomainEntity latestPrice =
        PriceDomainEntity.builder()
            .id(UUID.randomUUID().toString())
            .amount(new BigDecimal("10.99"))
            .build();

    when(productPriceRepositoryPort.findLatestPriceByProduct(existingProduct))
        .thenReturn(Optional.of(latestPrice));

    // Call the method
    dailyProductExtractTaskUseCaseAdapter.executeDailyTask();

    // Verify product price is not saved because there's no price change
    verify(productRepositoryPort, never()).saveProduct(any(ProductDomainEntity.class));
    verify(productPriceRepositoryPort, never()).saveProductPrice(any(PriceDomainEntity.class));
  }

  @Test
  void executeDailyTask_shouldHandleStoreExtractionErrorGracefully() {
    // Setup mock shop with an error during product extraction
    ShopDomainEntity shop =
        ShopDomainEntity.builder()
            .id(UUID.randomUUID().toString())
            .name("Failing Shop")
            .url("http://failing-shop.com")
            .build();

    when(shopRepositoryPort.findAll()).thenReturn(List.of(shop));

    // Call the method
    dailyProductExtractTaskUseCaseAdapter.executeDailyTask();

    // Verify the log or the system behavior after the error, no further calls
    verifyNoInteractions(
        productRepositoryPort, productCategoryRepositoryPort, productPriceRepositoryPort);
  }
}
