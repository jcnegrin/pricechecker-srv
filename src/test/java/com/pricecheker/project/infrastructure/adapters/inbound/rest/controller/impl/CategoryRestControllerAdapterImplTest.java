package com.pricecheker.project.infrastructure.adapters.inbound.rest.controller.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pricecheker.project.application.ports.inbound.QueryCategoriesByShopUseCasePort;
import com.pricecheker.project.domain.view.CategoryView;
import com.pricecheker.project.domain.view.ShopCategoriesView;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CategoryRestControllerAdapterImpl.class)
class CategoryRestControllerAdapterImplTest {

  @MockBean private QueryCategoriesByShopUseCasePort service;

  @Autowired private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetCategoriesByShop() throws Exception {
    // Simulamos la estructura de ShopCategoriesView
    List<CategoryView> mockCategories1 =
        List.of(new CategoryView("Electronics"), new CategoryView("Groceries"));
    List<CategoryView> mockCategories2 =
        List.of(new CategoryView("Clothing"), new CategoryView("Books"));
    List<ShopCategoriesView> shopCategoriesViews =
        List.of(
            new ShopCategoriesView("","Shop1", mockCategories1),
            new ShopCategoriesView("","Shop2", mockCategories2));

    // Simulamos el comportamiento del servicio
    when(service.getCategoriesByShop()).thenReturn(shopCategoriesViews);

    // Realizamos una solicitud GET al endpoint del controlador
    mockMvc
        .perform(get("/categories"))
        .andExpect(status().isOk()) // Verificamos que el estado HTTP sea 200 OK
        .andExpect(jsonPath("$.data").isArray()) // Verificamos que 'data' es un array
        .andExpect(jsonPath("$.data.length()").value(2)) // Verificamos que el array tiene 2 tiendas
        .andExpect(jsonPath("$.data[0].shop").value("Shop1")) // Verificamos la primera tienda
        .andExpect(
            jsonPath("$.data[0].categories[0].name")
                .value("Electronics")) // Verificamos la primera categoría de la primera tienda
        .andExpect(
            jsonPath("$.data[0].categories[1].name")
                .value("Groceries")) // Verificamos la segunda categoría de la primera tienda
        .andExpect(jsonPath("$.data[1].shop").value("Shop2")) // Verificamos la segunda tienda
        .andExpect(
            jsonPath("$.data[1].categories[0].name")
                .value("Clothing")) // Verificamos la primera categoría de la segunda tienda
        .andExpect(
            jsonPath("$.data[1].categories[1].name")
                .value("Books")); // Verificamos la segunda categoría de la segunda tienda

    // Verificamos que el servicio fue llamado exactamente una vez
    verify(service, times(1)).getCategoriesByShop();
  }
}
