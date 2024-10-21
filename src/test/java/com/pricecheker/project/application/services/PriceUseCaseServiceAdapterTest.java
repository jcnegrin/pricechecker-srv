package com.pricecheker.project.application.services;

import com.pricecheker.project.application.ports.outbound.ProductPriceRepositoryPort;
import com.pricecheker.project.domain.entity.PriceDomainEntity;
import com.pricecheker.project.domain.exception.PriceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class PriceUseCaseServiceAdapterTest {

  @Mock private ProductPriceRepositoryPort repositoryPort;

  @InjectMocks private PriceUseCaseServiceAdapter priceService;

  private PriceDomainEntity latestPrice;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    latestPrice =
        PriceDomainEntity.builder()
            .id("1")
            .amount(BigDecimal.valueOf(15.99))
            .updateDate(LocalDateTime.now())
            .build();
  }

  @Test
  void testGetLatestPriceByProductId_Success() {
    // Arrange
    when(repositoryPort.findLatestPriceByProductId(anyString()))
        .thenReturn(Optional.of(latestPrice));

    // Act
    PriceDomainEntity result = priceService.getLatestPriceByProductId("productId");

    // Assert
    assertNotNull(result);
    assertEquals(BigDecimal.valueOf(15.99), result.getAmount());
  }

  @Test
  void testGetLatestPriceByProductId_PriceNotFound() {
    // Arrange
    when(repositoryPort.findLatestPriceByProductId(anyString())).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(
        PriceNotFoundException.class, () -> priceService.getLatestPriceByProductId("productId"));
  }
}
