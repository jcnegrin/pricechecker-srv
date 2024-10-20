package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.pricecheker.project.domain.entity.CategoryDomainEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity.CategoryEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.mapper.CategoryRepositoryMapper;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ProductCategoryRepositoryAdapterTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryRepositoryMapper categoryMapper;

    @InjectMocks
    private ProductCategoryRepositoryAdapter categoryRepositoryAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializar los mocks antes de cada test
    }

    @Test
    void testFindAllCategories() {
        // Arrange
        CategoryEntity categoryEntity = new CategoryEntity();
        CategoryDomainEntity categoryDomainEntity = new CategoryDomainEntity();

        when(categoryRepository.findAll()).thenReturn(List.of(categoryEntity));
        when(categoryMapper.toDomain(categoryEntity)).thenReturn(categoryDomainEntity);

        // Act
        List<CategoryDomainEntity> result = categoryRepositoryAdapter.findAllCategories();

        // Assert
        assertEquals(1, result.size());
        assertEquals(categoryDomainEntity, result.get(0));
        verify(categoryRepository).findAll();
        verify(categoryMapper).toDomain(categoryEntity);
    }

    @Test
    void testFindCategoryById() {
        // Arrange
        String categoryId = "123";
        CategoryEntity categoryEntity = new CategoryEntity();
        CategoryDomainEntity categoryDomainEntity = new CategoryDomainEntity();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity));
        when(categoryMapper.toDomain(categoryEntity)).thenReturn(categoryDomainEntity);

        // Act
        Optional<CategoryDomainEntity> result = categoryRepositoryAdapter.findCategoryById(categoryId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(categoryDomainEntity, result.get());
        verify(categoryRepository).findById(categoryId);
        verify(categoryMapper).toDomain(categoryEntity);
    }

    @Test
    void testFindCategoryByName() {
        // Arrange
        String categoryName = "Electronics";
        CategoryEntity categoryEntity = new CategoryEntity();
        CategoryDomainEntity categoryDomainEntity = new CategoryDomainEntity();

        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.of(categoryEntity));
        when(categoryMapper.toDomain(categoryEntity)).thenReturn(categoryDomainEntity);

        // Act
        Optional<CategoryDomainEntity> result = categoryRepositoryAdapter.findCategoryByName(categoryName);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(categoryDomainEntity, result.get());
        verify(categoryRepository).findByName(categoryName);
        verify(categoryMapper).toDomain(categoryEntity);
    }

    @Test
    void testSaveCategory() {
        // Arrange
        CategoryDomainEntity categoryDomainEntity = new CategoryDomainEntity();
        CategoryEntity categoryEntity = new CategoryEntity();

        when(categoryMapper.toEntity(categoryDomainEntity)).thenReturn(categoryEntity);
        when(categoryRepository.save(categoryEntity)).thenReturn(categoryEntity);
        when(categoryMapper.toDomain(categoryEntity)).thenReturn(categoryDomainEntity);

        // Act
        CategoryDomainEntity result = categoryRepositoryAdapter.saveCategory(categoryDomainEntity);

        // Assert
        assertEquals(categoryDomainEntity, result);
        verify(categoryMapper).toEntity(categoryDomainEntity);
        verify(categoryRepository).save(categoryEntity);
        verify(categoryMapper).toDomain(categoryEntity);
    }
}