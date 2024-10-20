package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.pricecheker.project.domain.entity.ProductDomainEntity;
import com.pricecheker.project.domain.entity.ShopDomainEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity.ProductEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity.ShopEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.mapper.CategoryRepositoryMapper;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.mapper.ProductRepositoryMapper;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.mapper.ShopRepositoryMapper;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ProductRepositoryAdapterTest {

  @Mock
  private ProductRepository productRepository;

  @Mock
  private ProductRepositoryMapper productRepositoryMapper;

  @Mock
  private ShopRepositoryMapper shopRepositoryMapper;

  @Mock
  private CategoryRepositoryMapper categoryRepositoryMapper;

  @InjectMocks
  private ProductRepositoryAdapter productRepositoryAdapter;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);  // Inicializar los mocks
  }

  @Test
  void testFindAllProducts() {
    // Arrange
    ProductEntity productEntity = new ProductEntity();
    ProductDomainEntity productDomainEntity = new ProductDomainEntity();

    when(productRepository.findAll()).thenReturn(List.of(productEntity));
    when(productRepositoryMapper.toDomain(productEntity)).thenReturn(productDomainEntity);

    // Act
    List<ProductDomainEntity> result = productRepositoryAdapter.findAllProducts(null);

    // Assert
    assertEquals(1, result.size());
    verify(productRepository).findAll();
    verify(productRepositoryMapper).toDomain(productEntity);
  }

  @Test
  void testFindProductByNameAndShop() {
    // Arrange
    String name = "Product 1";
    ShopDomainEntity shopDomain = new ShopDomainEntity();
    ShopEntity shopEntity = new ShopEntity();
    ProductEntity productEntity = new ProductEntity();
    ProductDomainEntity productDomainEntity = new ProductDomainEntity();

    when(shopRepositoryMapper.toEntity(shopDomain)).thenReturn(shopEntity);
    when(productRepository.findByNameAndShop(name, shopEntity)).thenReturn(Optional.of(productEntity));
    when(productRepositoryMapper.toDomain(productEntity)).thenReturn(productDomainEntity);

    // Act
    Optional<ProductDomainEntity> result = productRepositoryAdapter.findProductByNameAndShop(name, shopDomain);

    // Assert
    assertTrue(result.isPresent());
    assertEquals(productDomainEntity, result.get());
    verify(shopRepositoryMapper).toEntity(shopDomain);
    verify(productRepository).findByNameAndShop(name, shopEntity);
    verify(productRepositoryMapper).toDomain(productEntity);
  }

  @Test
  void testFindCategoriesByShop() {
    // Arrange
    String shopId = "123";
    when(productRepository.findCategoriesByShopId(shopId)).thenReturn(List.of("Category 1", "Category 2"));

    // Act
    List<String> categories = productRepositoryAdapter.findCategoriesByShop(shopId);

    // Assert
    assertEquals(2, categories.size());
    assertEquals("Category 1", categories.get(0));
    assertEquals("Category 2", categories.get(1));
    verify(productRepository).findCategoriesByShopId(shopId);
  }

  @Test
  void testFindProductByShopAndCategory() {
    // Arrange
    String shopId = "123";
    String categoryId = "456";
    ProductEntity productEntity = new ProductEntity();
    ProductDomainEntity productDomainEntity = new ProductDomainEntity();

    when(productRepository.findByShopIdAndCategoryId(shopId, categoryId)).thenReturn(List.of(productEntity));
    when(productRepositoryMapper.toDomain(productEntity)).thenReturn(productDomainEntity);

    // Act
    List<ProductDomainEntity> result = productRepositoryAdapter.findProductByShopAndCategory(shopId, categoryId);

    // Assert
    assertEquals(1, result.size());
    assertEquals(productDomainEntity, result.get(0));
    verify(productRepository).findByShopIdAndCategoryId(shopId, categoryId);
    verify(productRepositoryMapper).toDomain(productEntity);
  }

  @Test
  void testSaveProduct() {
    // Arrange
    ProductDomainEntity productDomainEntity = new ProductDomainEntity();
    ProductEntity productEntity = new ProductEntity();

    when(productRepositoryMapper.toEntity(productDomainEntity)).thenReturn(productEntity);
    when(productRepository.save(productEntity)).thenReturn(productEntity);
    when(productRepositoryMapper.toDomain(productEntity)).thenReturn(productDomainEntity);

    // Act
    ProductDomainEntity result = productRepositoryAdapter.saveProduct(productDomainEntity);

    // Assert
    assertEquals(productDomainEntity, result);
    verify(productRepositoryMapper).toEntity(productDomainEntity);
    verify(productRepository).save(productEntity);
    verify(productRepositoryMapper).toDomain(productEntity);
  }
}