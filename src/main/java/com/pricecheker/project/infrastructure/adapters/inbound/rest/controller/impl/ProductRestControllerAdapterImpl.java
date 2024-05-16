package com.pricecheker.project.infrastructure.adapters.inbound.rest.controller.impl;

import com.pricecheker.project.application.ports.inbound.ProductUseCaseServicePort;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.controller.ProductRestControllerAdapter;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.mapper.ProductRestInboundMapper;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.response.product.GetProductsResponse;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.response.product.ProductViewResponse;
import java.util.List;
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
  private final ProductRestInboundMapper mapper;

  @Override
  public ResponseEntity<GetProductsResponse> getProducts() {

    log.info("[Start]  ProductRestControllerAdapterImpl.getProducts - Getting all products");

    List<ProductViewResponse> products =
        productUseCaseServicePort.findAllProducts().stream().map(mapper::mapToView).toList();

    log.info("[End]  ProductRestControllerAdapterImpl.getProducts - Getting all products");
    return ResponseEntity.ok().body(GetProductsResponse.builder().products(products).build());
  }
}
