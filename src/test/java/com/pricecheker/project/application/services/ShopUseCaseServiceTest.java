package com.pricecheker.project.application.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.pricecheker.project.application.ports.outbound.ShopRepositoryPort;
import com.pricecheker.project.domain.dto.CreateStoreDto;
import com.pricecheker.project.domain.entity.ShopDomainEntity;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ShopUseCaseServiceTest {

  @Mock private ShopRepositoryPort shopRepositoryPort;

  @InjectMocks private ShopUseCaseService shopUseCaseService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetShop() {
    // Simulamos una lista de tiendas
    List<ShopDomainEntity> mockStores =
        List.of(
            ShopDomainEntity.builder().id("1").name("Store 1").url("http://store1.com").build(),
            ShopDomainEntity.builder().id("2").name("Store 2").url("http://store2.com").build());

    // Simulamos el comportamiento del puerto
    when(shopRepositoryPort.findAll()).thenReturn(mockStores);

    // Llamamos al método del servicio
    List<ShopDomainEntity> result = shopUseCaseService.getShop();

    // Verificamos que el resultado es el esperado
    assertEquals(2, result.size());
    assertEquals("Store 1", result.get(0).getName());
    assertEquals("Store 2", result.get(1).getName());

    // Verificamos que el puerto fue llamado una vez
    verify(shopRepositoryPort, times(1)).findAll();
  }

  @Test
  void testCreateStore() {
    // Simulamos los datos de la tienda a crear
    CreateStoreDto storeDto = new CreateStoreDto("New Store", "http://newstore.com");

    // Simulamos el comportamiento del puerto
    ShopDomainEntity savedStore =
        ShopDomainEntity.builder()
            .id(UUID.randomUUID().toString())
            .name(storeDto.getName())
            .url(storeDto.getUrl())
            .build();
    when(shopRepositoryPort.save(any(ShopDomainEntity.class))).thenReturn(savedStore);

    // Llamamos al método del servicio
    ShopDomainEntity result = shopUseCaseService.createStore(storeDto);

    // Verificamos que la tienda se creó correctamente con los datos proporcionados
    assertNotNull(result.getId()); // Verificamos que el ID fue generado
    assertEquals(storeDto.getName(), result.getName());
    assertEquals(storeDto.getUrl(), result.getUrl());

    // Verificamos que el puerto fue llamado para guardar la tienda
    verify(shopRepositoryPort, times(1)).save(any(ShopDomainEntity.class));
  }
}
