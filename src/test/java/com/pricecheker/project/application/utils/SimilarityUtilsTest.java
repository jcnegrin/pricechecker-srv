package com.pricecheker.project.application.utils;

import static org.junit.jupiter.api.Assertions.*;

import com.pricecheker.project.domain.entity.CategoryDomainEntity;
import com.pricecheker.project.domain.entity.ProductDomainEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimilarityUtilsTest {

  private CategoryDomainEntity category1;
  private CategoryDomainEntity category2;
  private CategoryDomainEntity category3;

  private ProductDomainEntity product1;
  private ProductDomainEntity product2;
  private ProductDomainEntity product3;

  @BeforeEach
  void setUp() {
    // Setup for categories
    category1 = CategoryDomainEntity.builder().id("1").name("Frutas y Verduras").build();
    category2 = CategoryDomainEntity.builder().id("2").name("Verduras").build();
    category3 = CategoryDomainEntity.builder().id("3").name("Carnes").build();

    // Setup for products
    product1 = ProductDomainEntity.builder().id("1").name("Manzana Roja").build();
    product2 = ProductDomainEntity.builder().id("2").name("Manzana Verde").build();
    product3 = ProductDomainEntity.builder().id("3").name("Refresco de Naranja").build();
  }

  @Test
  void testFindSimilarCategories_ShouldReturnSimilarCategories() {
    // Arrange
    List<CategoryDomainEntity> categories = List.of(category2, category3);

    // Act
    List<CategoryDomainEntity> similarCategories =
        SimilarityUtils.findSimilarCategories(category1, categories);

    // Assert
    assertEquals(1, similarCategories.size());
    assertEquals("Verduras", similarCategories.get(0).getName());
  }

  @Test
  void testFindSimilarCategories_ShouldReturnNoSimilarCategories() {
    // Arrange
    List<CategoryDomainEntity> categories = List.of(category3);

    // Act
    List<CategoryDomainEntity> similarCategories =
        SimilarityUtils.findSimilarCategories(category1, categories);

    // Assert
    assertEquals(0, similarCategories.size());
  }

  @Test
  void testFindSimilarProducts_ShouldReturnSimilarProducts() {
    // Arrange
    List<ProductDomainEntity> products = List.of(product2, product3);

    // Act
    List<ProductDomainEntity> similarProducts =
        SimilarityUtils.findSimilarProducts(product1, products);

    // Assert
    assertEquals(1, similarProducts.size());
    assertEquals("Manzana Verde", similarProducts.get(0).getName());
  }

  @Test
  void testFindSimilarProducts_ShouldReturnNoSimilarProducts() {
    // Arrange
    List<ProductDomainEntity> products = List.of(product3);

    // Act
    List<ProductDomainEntity> similarProducts =
        SimilarityUtils.findSimilarProducts(product1, products);

    // Assert
    assertEquals(0, similarProducts.size());
  }
}
