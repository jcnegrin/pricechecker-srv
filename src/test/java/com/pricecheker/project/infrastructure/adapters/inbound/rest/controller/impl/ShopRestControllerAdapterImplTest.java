package com.pricecheker.project.infrastructure.adapters.inbound.rest.controller.impl;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pricecheker.project.application.ports.inbound.ShopUseCaseServicePort;
import com.pricecheker.project.domain.entity.ShopDomainEntity;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.mapper.ShopRestInbountMapper;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.response.shop.ShopViewResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ShopRestControllerAdapterImpl.class)
class ShopRestControllerAdapterImplTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private ShopUseCaseServicePort shopUseCaseServicePort;

  @MockBean private ShopRestInbountMapper shopMapper;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this); // Inicializar los mocks
  }

  @Test
  void testGetAllShops() throws Exception {
    // Arrange
    ShopDomainEntity shopDomainEntity = new ShopDomainEntity();
    ShopViewResponse shopViewResponse = new ShopViewResponse();
    List<ShopDomainEntity> shopList = List.of(shopDomainEntity);
    List<ShopViewResponse> shopViewList = List.of(shopViewResponse);

    when(shopUseCaseServicePort.getShop()).thenReturn(shopList);
    when(shopMapper.toShopViewResponse(shopDomainEntity)).thenReturn(shopViewResponse);

    // Act & Assert
    mockMvc
        .perform(get("/shops").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data").isArray())
        .andExpect(
            jsonPath("$.data.length()").value(1)); // Verificar que la lista contiene 1 tienda

    verify(shopUseCaseServicePort).getShop();
    verify(shopMapper).toShopViewResponse(shopDomainEntity);
  }
}
