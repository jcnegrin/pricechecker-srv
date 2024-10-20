package com.pricecheker.project.application.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.pricecheker.project.application.ports.inbound.ShopUseCaseServicePort;
import com.pricecheker.project.application.ports.outbound.ProductCategoryRepositoryPort;
import com.pricecheker.project.application.ports.outbound.ProductRepositoryPort;
import com.pricecheker.project.domain.entity.CategoryDomainEntity;
import com.pricecheker.project.domain.entity.ShopDomainEntity;
import com.pricecheker.project.domain.view.ShopCategoriesView;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class QueryCategoriesByShopUseCaseAdapterTest {

  @Mock private ShopUseCaseServicePort shopUseCaseServicePort;

  @Mock private ProductRepositoryPort productRepositoryPort;

  @Mock private ProductCategoryRepositoryPort productCategoryRepositoryPort;

  @InjectMocks private QueryCategoriesByShopUseCaseAdapter queryCategoriesByShopUseCaseAdapter;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetCategoriesByShop_withValidData() {
    // Simulamos dos tiendas
    ShopDomainEntity shop1 =
        ShopDomainEntity.builder().id("1").name("Shop 1").url("http://shop1.com").build();
    ShopDomainEntity shop2 =
        ShopDomainEntity.builder().id("2").name("Shop 2").url("http://shop2.com").build();
    when(shopUseCaseServicePort.getShop()).thenReturn(List.of(shop1, shop2));

    // Simulamos las categorías asociadas a las tiendas
    when(productRepositoryPort.findCategoriesByShop("1")).thenReturn(List.of("cat1", "cat2"));
    when(productRepositoryPort.findCategoriesByShop("2")).thenReturn(List.of("cat3"));

    // Simulamos los detalles de las categorías
    when(productCategoryRepositoryPort.findCategoryById("cat1"))
        .thenReturn(Optional.of(new CategoryDomainEntity("cat1", "Electronics")));
    when(productCategoryRepositoryPort.findCategoryById("cat2"))
        .thenReturn(Optional.of(new CategoryDomainEntity("cat2", "Groceries")));
    when(productCategoryRepositoryPort.findCategoryById("cat3"))
        .thenReturn(Optional.of(new CategoryDomainEntity("cat3", "Clothing")));

    // Llamamos al método del servicio
    List<ShopCategoriesView> result = queryCategoriesByShopUseCaseAdapter.getCategoriesByShop();

    // Verificamos que hay dos tiendas en la respuesta
    assertEquals(2, result.size());

    // Verificamos los detalles de la primera tienda
    assertEquals("Shop 1", result.get(0).getShop());
    assertEquals(2, result.get(0).getCategories().size());
    assertEquals("Electronics", result.get(0).getCategories().get(0).getName());
    assertEquals("Groceries", result.get(0).getCategories().get(1).getName());

    // Verificamos los detalles de la segunda tienda
    assertEquals("Shop 2", result.get(1).getShop());
    assertEquals(1, result.get(1).getCategories().size());
    assertEquals("Clothing", result.get(1).getCategories().get(0).getName());

    // Verificamos que los puertos fueron llamados correctamente
    verify(shopUseCaseServicePort, times(1)).getShop();
    verify(productRepositoryPort, times(1)).findCategoriesByShop("1");
    verify(productRepositoryPort, times(1)).findCategoriesByShop("2");
    verify(productCategoryRepositoryPort, times(1)).findCategoryById("cat1");
    verify(productCategoryRepositoryPort, times(1)).findCategoryById("cat2");
    verify(productCategoryRepositoryPort, times(1)).findCategoryById("cat3");
  }

  @Test
  void testGetCategoriesByShop_withNoCategories() {
    // Simulamos una tienda sin categorías
    ShopDomainEntity shop =
        ShopDomainEntity.builder().id("1").name("Shop 1").url("http://shop1.com").build();
    when(shopUseCaseServicePort.getShop()).thenReturn(List.of(shop));

    // Simulamos que no hay categorías asociadas a la tienda
    when(productRepositoryPort.findCategoriesByShop("1")).thenReturn(List.of());

    // Llamamos al método del servicio
    List<ShopCategoriesView> result = queryCategoriesByShopUseCaseAdapter.getCategoriesByShop();

    // Verificamos que la tienda está presente pero sin categorías
    assertEquals(1, result.size());
    assertEquals("Shop 1", result.get(0).getShop());
    assertEquals(0, result.get(0).getCategories().size());

    // Verificamos que los puertos fueron llamados correctamente
    verify(shopUseCaseServicePort, times(1)).getShop();
    verify(productRepositoryPort, times(1)).findCategoriesByShop("1");
  }

  @Test
  void testGetCategoriesByShop_withInvalidCategoryId() {
    // Simulamos una tienda
    ShopDomainEntity shop =
        ShopDomainEntity.builder().id("1").name("Shop 1").url("http://shop1.com").build();
    when(shopUseCaseServicePort.getShop()).thenReturn(List.of(shop));

    // Simulamos una categoría que no existe
    when(productRepositoryPort.findCategoriesByShop("1")).thenReturn(List.of("cat1"));
    when(productCategoryRepositoryPort.findCategoryById("cat1")).thenReturn(Optional.empty());

    // Llamamos al método del servicio
    List<ShopCategoriesView> result = queryCategoriesByShopUseCaseAdapter.getCategoriesByShop();

    // Verificamos que la tienda está presente pero sin categorías válidas
    assertEquals(1, result.size());
    assertEquals("Shop 1", result.get(0).getShop());
    assertEquals(0, result.get(0).getCategories().size());

    // Verificamos que los puertos fueron llamados correctamente
    verify(shopUseCaseServicePort, times(1)).getShop();
    verify(productRepositoryPort, times(1)).findCategoriesByShop("1");
    verify(productCategoryRepositoryPort, times(1)).findCategoryById("cat1");
  }
}
