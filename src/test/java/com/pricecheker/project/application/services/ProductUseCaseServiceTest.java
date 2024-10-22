package com.pricecheker.project.application.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.pricecheker.project.application.mapper.ProductUseCaseMapper;
import com.pricecheker.project.application.ports.inbound.CategoryUseCaseServicePort;
import com.pricecheker.project.application.ports.inbound.PriceUseCaseServicePort;
import com.pricecheker.project.application.ports.inbound.ShopUseCaseServicePort;
import com.pricecheker.project.application.ports.outbound.ProductRepositoryPort;
import com.pricecheker.project.domain.entity.CategoryDomainEntity;
import com.pricecheker.project.domain.entity.PriceDomainEntity;
import com.pricecheker.project.domain.entity.ProductDomainEntity;
import com.pricecheker.project.domain.entity.ShopDomainEntity;
import com.pricecheker.project.domain.exception.ProductNotFoundException;
import com.pricecheker.project.domain.view.ProductView;
import com.pricecheker.project.domain.view.ShopView;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ProductUseCaseServiceTest {

  @InjectMocks private ProductUseCaseService productUseCaseService;

  @Mock private ProductRepositoryPort productRepositoryPort;

  @Mock private ShopUseCaseServicePort shopUseCaseServicePort;

  @Mock private CategoryUseCaseServicePort categoryUseCaseServicePort;

  @Mock private PriceUseCaseServicePort priceUseCaseServicePort;

  @Mock private ProductUseCaseMapper mapper;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getProductsByShopIdAndCategory_ValidInputs_ReturnsProductList() {
    // Arrange
    String shopId = "shopId";
    String categoryId = "categoryId";

    ShopDomainEntity mockShop =
        ShopDomainEntity.builder()
            .id(shopId)
            .name("Mercadona")
            .url("https://mercadona.com")
            .build();

    CategoryDomainEntity mockCategory =
        CategoryDomainEntity.builder().id(categoryId).name("Frutas").build();

    ProductDomainEntity product1 =
        ProductDomainEntity.builder()
            .id("productId1")
            .name("Apple")
            .shop(mockShop)
            .category(mockCategory)
            .build();

    ProductDomainEntity product2 =
        ProductDomainEntity.builder()
            .id("productId2")
            .name("Banana")
            .shop(mockShop)
            .category(mockCategory)
            .build();

    PriceDomainEntity price1 =
        PriceDomainEntity.builder()
            .id("priceId1")
            .amount(BigDecimal.valueOf(1.50))
            .updateDate(LocalDateTime.now())
            .build();

    PriceDomainEntity price2 =
        PriceDomainEntity.builder()
            .id("priceId2")
            .amount(BigDecimal.valueOf(0.80))
            .updateDate(LocalDateTime.now())
            .build();

    ShopView shopView = ShopView.builder().id("shopId").name("Mercadona").build();

    when(shopUseCaseServicePort.getShopById(shopId)).thenReturn(mockShop);
    when(categoryUseCaseServicePort.getCategoryById(categoryId)).thenReturn(mockCategory);
    when(productRepositoryPort.findProductByShopAndCategory(shopId, categoryId))
        .thenReturn(List.of(product1, product2));
    when(priceUseCaseServicePort.getLatestPriceByProductId("productId1")).thenReturn(price1);
    when(priceUseCaseServicePort.getLatestPriceByProductId("productId2")).thenReturn(price2);

    ProductView productView1 =
        ProductView.builder()
            .id("productId1")
            .name("Apple")
            .price(BigDecimal.valueOf(1.50))
            .shop(shopView)
            .build();

    ProductView productView2 =
        ProductView.builder()
            .id("productId2")
            .name("Banana")
            .price(BigDecimal.valueOf(0.80))
            .shop(shopView)
            .build();

    when(mapper.toView(product1)).thenReturn(productView1);
    when(mapper.toView(product2)).thenReturn(productView2);

    // Act
    List<ProductView> result =
        productUseCaseService.getProductsByShopIdAndCategory(shopId, categoryId);

    // Assert
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("Apple", result.get(0).getName());
    assertEquals("Banana", result.get(1).getName());
    verify(shopUseCaseServicePort).getShopById(shopId);
    verify(categoryUseCaseServicePort).getCategoryById(categoryId);
    verify(productRepositoryPort).findProductByShopAndCategory(shopId, categoryId);
    verify(priceUseCaseServicePort).getLatestPriceByProductId("productId1");
    verify(priceUseCaseServicePort).getLatestPriceByProductId("productId2");
  }

  @Test
  void getProductById_ValidProductId_ReturnsProductView() {
    // Arrange
    String productId = "productId";
    ShopView shopView = ShopView.builder().id("shopId").name("Mercadona").build();

    ProductDomainEntity mockProduct =
        ProductDomainEntity.builder().id(productId).name("Apple").build();

    ProductView mockProductView =
        ProductView.builder()
            .id(productId)
            .name("Apple")
            .price(BigDecimal.valueOf(1.50))
            .shop(shopView)
            .build();

    when(productRepositoryPort.findProductById(productId)).thenReturn(Optional.of(mockProduct));
    when(mapper.toView(mockProduct)).thenReturn(mockProductView);

    // Act
    ProductView result = productUseCaseService.getProductById(productId);

    // Assert
    assertNotNull(result);
    assertEquals("Apple", result.getName());
    verify(productRepositoryPort).findProductById(productId);
    verify(mapper).toView(mockProduct);
  }

  @Test
  void getProductById_ProductNotFound_ThrowsException() {
    // Arrange
    String productId = "productId";
    when(productRepositoryPort.findProductById(productId)).thenReturn(Optional.empty());

    // Act & Assert
    ProductNotFoundException thrown =
        assertThrows(
            ProductNotFoundException.class,
            () -> productUseCaseService.getProductById(productId),
            "Expected exception to throw, but it didn't");
    assertEquals("Product with ID: " + productId + " not found", thrown.getMessage());
    verify(productRepositoryPort).findProductById(productId);
  }

  @Test
  void getSimilarProducts_ValidProductId_ReturnsSimilarProductViews() {
    // Arrange
    String productId = "productId";

    // Producto original: Manzana
    CategoryDomainEntity mockCategory =
        CategoryDomainEntity.builder().id("categoryId").name("Frutas").build();

    ProductDomainEntity mockProduct =
        ProductDomainEntity.builder().id(productId).name("Manzana").category(mockCategory).build();

    // Producto similar: Malla de Manzanas
    ProductDomainEntity similarProduct =
        ProductDomainEntity.builder()
            .id("productId2")
            .name("Manzanas")
            .category(mockCategory)
            .build();

    // Simular la búsqueda del producto original
    when(productRepositoryPort.findProductById(productId)).thenReturn(Optional.of(mockProduct));
    when(productRepositoryPort.findProductsByCategory(mockCategory.getId()))
        .thenReturn(List.of(similarProduct)); // productRepositoryPort::findProductsByCategory
    when(categoryUseCaseServicePort.getAllCategories()).thenReturn(List.of(mockCategory));

    // Producto similar mapeado a ProductView
    ShopView shopView = ShopView.builder().id("shopId").name("Mercadona").build();

    ProductView similarProductView =
        ProductView.builder()
            .id("productId2")
            .name("Manzanas")
            .price(BigDecimal.valueOf(3.50))
            .shop(shopView)
            .build();

    when(priceUseCaseServicePort.getLatestPriceByProductId(similarProductView.getId()))
        .thenReturn(PriceDomainEntity.builder().amount(BigDecimal.valueOf(3.50)).build());

    // Simular el mapeo de ProductDomainEntity a ProductView
    when(mapper.toView(similarProduct)).thenReturn(similarProductView);
    // Act
    List<ProductView> result = productUseCaseService.getSimilarProducts(productId);

    // Assert
    assertNotNull(result, "The result should not be null");
    assertEquals(1, result.size(), "The list should contain one similar product");

    // Verificar que los métodos hayan sido llamados
    verify(productRepositoryPort).findProductById(productId);
    verify(productRepositoryPort).findProductsByCategory(mockCategory.getId());
    verify(mapper).toView(similarProduct);
  }
}
