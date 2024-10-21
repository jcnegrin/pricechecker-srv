package com.pricecheker.project.application.services;

import com.pricecheker.project.application.ports.outbound.ProductCategoryRepositoryPort;
import com.pricecheker.project.domain.entity.CategoryDomainEntity;
import com.pricecheker.project.domain.exception.ShopDoesNotExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CategoryUseCaseServiceAdapterTest {

  @Mock private ProductCategoryRepositoryPort repositoryPort;

  @InjectMocks private CategoryUseCaseServiceAdapter categoryService;

  private CategoryDomainEntity category;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    category = CategoryDomainEntity.builder().id("1").name("Frutas y Verduras").build();
  }

  @Test
  void testGetCategoryById_Success() {
    // Arrange
    when(repositoryPort.findCategoryById(anyString())).thenReturn(Optional.of(category));

    // Act
    CategoryDomainEntity result = categoryService.getCategoryById("1");

    // Assert
    assertNotNull(result);
    assertEquals("Frutas y Verduras", result.getName());
  }

  @Test
  void testGetCategoryById_NotFound() {
    // Arrange
    when(repositoryPort.findCategoryById(anyString())).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(ShopDoesNotExistsException.class, () -> categoryService.getCategoryById("1"));
  }

  @Test
  void testGetAllCategories_Success() {
    // Arrange
    when(repositoryPort.findAllCategories()).thenReturn(List.of(category));

    // Act
    List<CategoryDomainEntity> result = categoryService.getAllCategories();

    // Assert
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("Frutas y Verduras", result.get(0).getName());
  }

  @Test
  void testGetAllCategories_EmptyList() {
    // Arrange
    when(repositoryPort.findAllCategories()).thenReturn(List.of());

    // Act
    List<CategoryDomainEntity> result = categoryService.getAllCategories();

    // Assert
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }
}
