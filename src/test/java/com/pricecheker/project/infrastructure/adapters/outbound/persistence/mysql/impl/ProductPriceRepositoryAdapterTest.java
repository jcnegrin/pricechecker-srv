package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.pricecheker.project.domain.entity.PriceDomainEntity;
import com.pricecheker.project.domain.entity.ProductDomainEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity.PriceEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity.ProductEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.mapper.PriceRepositoryMapper;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.mapper.ProductRepositoryMapper;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.repository.PriceRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ProductPriceRepositoryAdapterTest {

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private PriceRepositoryMapper priceMapper;

    @Mock
    private ProductRepositoryMapper productMapper;

    @InjectMocks
    private ProductPriceRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializar los mocks antes de cada test
    }

    @Test
    void testSaveProductPrice() {
        // Arrange
        PriceDomainEntity priceDomainEntity = new PriceDomainEntity();
        PriceEntity priceEntity = new PriceEntity();

        when(priceMapper.toEntity(priceDomainEntity)).thenReturn(priceEntity);

        // Act
        adapter.saveProductPrice(priceDomainEntity);

        // Assert
        verify(priceRepository).save(priceEntity);  // Verifica que el repositorio haya sido invocado correctamente
    }

    @Test
    void testFindLatestPriceByProduct() {
        // Arrange
        ProductDomainEntity productDomainEntity = new ProductDomainEntity();
        ProductEntity productEntity = new ProductEntity();
        PriceEntity priceEntity = new PriceEntity();
        PriceDomainEntity priceDomainEntity = new PriceDomainEntity();

        when(productMapper.toEntity(productDomainEntity)).thenReturn(productEntity);
        when(priceRepository.findFirstByProductOrderByUpdateDateDesc(productEntity)).thenReturn(Optional.of(priceEntity));
        when(priceMapper.toDomainEntity(priceEntity)).thenReturn(priceDomainEntity);

        // Act
        Optional<PriceDomainEntity> result = adapter.findLatestPriceByProduct(productDomainEntity);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(priceDomainEntity, result.get());
        verify(priceRepository).findFirstByProductOrderByUpdateDateDesc(productEntity);
        verify(priceMapper).toDomainEntity(priceEntity);
    }

    @Test
    void testFindLatestPriceByProduct_NotFound() {
        // Arrange
        ProductDomainEntity productDomainEntity = new ProductDomainEntity();
        ProductEntity productEntity = new ProductEntity();

        when(productMapper.toEntity(productDomainEntity)).thenReturn(productEntity);
        when(priceRepository.findFirstByProductOrderByUpdateDateDesc(productEntity)).thenReturn(Optional.empty());

        // Act
        Optional<PriceDomainEntity> result = adapter.findLatestPriceByProduct(productDomainEntity);

        // Assert
        assertFalse(result.isPresent());  // El resultado debe estar vacío si no se encuentra ningún precio
        verify(priceRepository).findFirstByProductOrderByUpdateDateDesc(productEntity);
    }
}