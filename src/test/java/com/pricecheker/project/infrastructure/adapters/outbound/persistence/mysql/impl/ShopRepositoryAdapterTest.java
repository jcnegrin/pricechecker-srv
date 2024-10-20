package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.pricecheker.project.domain.entity.ShopDomainEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity.ShopEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.mapper.ShopRepositoryMapper;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.repository.ShopRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ShopRepositoryAdapterTest {

  @Mock private ShopRepository shopRepository;

  @Mock private ShopRepositoryMapper mapper;

  @InjectMocks private ShopRepositoryAdapter shopRepositoryAdapter;

  private ShopDomainEntity shopDomainEntity;
  private ShopEntity shopEntity;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    shopDomainEntity = new ShopDomainEntity("12", "Test Shop", "https://test.com");
    shopEntity = new ShopEntity();
  }

  @Test
  void testSaveShop_Success() {
    // Arrange
    when(mapper.toEntity(any(ShopDomainEntity.class))).thenReturn(shopEntity);
    when(mapper.toDomain(any(ShopEntity.class))).thenReturn(shopDomainEntity);
    when(shopRepository.save(any(ShopEntity.class))).thenReturn(shopEntity);

    // Act
    ShopDomainEntity result = shopRepositoryAdapter.save(shopDomainEntity);

    // Assert
    assertEquals(shopDomainEntity, result);
    verify(mapper, times(1)).toEntity(any(ShopDomainEntity.class));
    verify(mapper, times(1)).toDomain(any(ShopEntity.class));
    verify(shopRepository, times(1)).save(any(ShopEntity.class));
  }

  @Test
  void testFindById_Success() {
    // Arrange
    when(shopRepository.findById("1")).thenReturn(Optional.of(shopEntity));
    when(mapper.toDomain(any(ShopEntity.class))).thenReturn(shopDomainEntity);

    // Act
    Optional<ShopDomainEntity> result = shopRepositoryAdapter.findById("1");

    // Assert
    assertEquals(Optional.of(shopDomainEntity), result);
    verify(shopRepository, times(1)).findById("1");
    verify(mapper, times(1)).toDomain(any(ShopEntity.class));
  }

  @Test
  void testFindByName_Success() {
    // Arrange
    when(shopRepository.findByName("Test Shop")).thenReturn(shopEntity);
    when(mapper.toDomain(any(ShopEntity.class))).thenReturn(shopDomainEntity);

    // Act
    ShopDomainEntity result = shopRepositoryAdapter.findByName("Test Shop");

    // Assert
    assertEquals(shopDomainEntity, result);
    verify(shopRepository, times(1)).findByName("Test Shop");
    verify(mapper, times(1)).toDomain(any(ShopEntity.class));
  }

  @Test
  void testFindAll_Success() {
    // Arrange
    when(shopRepository.findAll()).thenReturn(Collections.singletonList(shopEntity));
    when(mapper.toDomain(any(ShopEntity.class))).thenReturn(shopDomainEntity);

    // Act
    List<ShopDomainEntity> result = shopRepositoryAdapter.findAll();

    // Assert
    assertEquals(1, result.size());
    assertEquals(shopDomainEntity, result.get(0));
    verify(shopRepository, times(1)).findAll();
    verify(mapper, times(1)).toDomain(any(ShopEntity.class));
  }
}
