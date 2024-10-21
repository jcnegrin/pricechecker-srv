package com.pricecheker.project.infrastructure.adapters.inbound.rest.controller.impl;

import com.pricecheker.project.application.ports.inbound.ProductUseCaseServicePort;
import com.pricecheker.project.domain.view.ProductView;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.controller.ProductRestControllerAdapter;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.response.product.GetProductsResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductRestControllerAdapterImpl implements ProductRestControllerAdapter {

  private final ProductUseCaseServicePort productUseCaseServicePort;

  @Override
  public ResponseEntity<GetProductsResponse> getProductsByCategoryAndShop(
      String categoryId, String shopId) {

    log.info("[Start]  ProductRestControllerAdapterImpl.getProducts - Getting all products");

    List<ProductView> products =
        productUseCaseServicePort.getProductsByShopIdAndCategory(shopId, categoryId);

    log.info("[End]  ProductRestControllerAdapterImpl.getProducts - Getting all products");

    return ResponseEntity.ok()
        .body(
            GetProductsResponse.builder()
                .id(UUID.randomUUID().toString())
                .products(products)
                .build());
  }

  @Override
  public ResponseEntity<GetProductsResponse> getSimilarProducts(String productId) {

    log.info(
        "[Start]  ProductRestControllerAdapterImpl.getSimilarProducts - Getting similar products");

    List<ProductView> products = productUseCaseServicePort.getSimilarProducts(productId);

    log.info(
        "[End]  ProductRestControllerAdapterImpl.getSimilarProducts - Getting similar products");

    return ResponseEntity.ok()
        .body(
            GetProductsResponse.builder()
                .id(UUID.randomUUID().toString())
                .products(products)
                .build());
  }
}
