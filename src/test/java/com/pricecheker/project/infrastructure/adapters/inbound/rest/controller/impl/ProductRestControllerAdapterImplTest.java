package com.pricecheker.project.infrastructure.adapters.inbound.rest.controller.impl;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pricecheker.project.application.ports.inbound.ProductUseCaseServicePort;
import com.pricecheker.project.domain.view.ProductView;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductRestControllerAdapterImpl.class)
class ProductRestControllerAdapterImplTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private ProductUseCaseServicePort productUseCaseServicePort;

  @Test
  void testGetProductsByCategoryAndShop_Success() throws Exception {
    // Arrange
    String categoryId = "category1";
    String shopId = "shop1";
    List<ProductView> mockProducts = Collections.singletonList(new ProductView());
    when(productUseCaseServicePort.getProductsByShopIdAndCategory(shopId, categoryId))
        .thenReturn(mockProducts);

    // Act & Assert
    mockMvc
        .perform(
            get("/products/{shopId}/{categoryId}", shopId, categoryId) // Path params
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.products").isArray())
        .andExpect(jsonPath("$.products.length()").value(1))
        .andExpect(jsonPath("$.id").exists());
  }

  @Test
  void testGetProductsByCategoryAndShop_NoProductsFound() throws Exception {
    // Arrange
    String categoryId = "category1";
    String shopId = "shop1";
    when(productUseCaseServicePort.getProductsByShopIdAndCategory(shopId, categoryId))
        .thenReturn(Collections.emptyList());

    // Act & Assert
    mockMvc
        .perform(
            get("/products/{shopId}/{categoryId}", shopId, categoryId) // Path params
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.products").isArray())
        .andExpect(jsonPath("$.products.length()").value(0))
        .andExpect(jsonPath("$.id").exists());
  }
}
