package com.pricecheker.project.infrastructure.adapters.inbound.rest.controller.impl;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pricecheker.project.application.ports.inbound.ProductUseCaseServicePort;
import com.pricecheker.project.domain.view.ProductView;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductRestControllerAdapterImpl.class)
class ProductRestControllerAdapterImplTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProductUseCaseServicePort productUseCaseServicePort;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetProductsByCategoryAndShop_Success() throws Exception {
    // Arrange
    ProductView product1 = ProductView.builder()
            .id("productId1")
            .name("Apple")
            .price(BigDecimal.valueOf(1.50))
            .build();

    ProductView product2 = ProductView.builder()
            .id("productId2")
            .name("Banana")
            .price(BigDecimal.valueOf(0.80))
            .build();

    when(productUseCaseServicePort.getProductsByShopIdAndCategory(anyString(), anyString()))
            .thenReturn(List.of(product1, product2));

    // Act & Assert
    mockMvc.perform(get("/products")
                    .param("categoryId", "categoryId")
                    .param("shopId", "shopId")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.products").isArray())
            .andExpect(jsonPath("$.products.length()").value(2))
            .andExpect(jsonPath("$.products[0].name").value("Apple"))
            .andExpect(jsonPath("$.products[1].name").value("Banana"));
  }

  @Test
  void testGetProductsByCategoryAndShop_EmptyResult() throws Exception {
    // Arrange
    when(productUseCaseServicePort.getProductsByShopIdAndCategory(anyString(), anyString()))
            .thenReturn(List.of());

    // Act & Assert
    mockMvc.perform(get("/products")
                    .param("categoryId", "categoryId")
                    .param("shopId", "shopId")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.products").isArray())
            .andExpect(jsonPath("$.products.length()").value(0));
  }

  @Test
  void testGetSimilarProducts_Success() throws Exception {
    // Arrange
    ProductView product1 = ProductView.builder()
            .id("productId1")
            .name("Broccoli")
            .price(BigDecimal.valueOf(2.00))
            .build();

    when(productUseCaseServicePort.getSimilarProducts(anyString()))
            .thenReturn(List.of(product1));

    // Act & Assert
    mockMvc.perform(get("/products/similar")
                    .param("productId", "productId")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.products").isArray())
            .andExpect(jsonPath("$.products.length()").value(1))
            .andExpect(jsonPath("$.products[0].name").value("Broccoli"));
  }

  @Test
  void testGetSimilarProducts_EmptyResult() throws Exception {
    // Arrange
    when(productUseCaseServicePort.getSimilarProducts(anyString())).thenReturn(List.of());

    // Act & Assert
    mockMvc.perform(get("/products/similar")
                    .param("productId", "productId")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.products").isArray())
            .andExpect(jsonPath("$.products.length()").value(0));
  }
}