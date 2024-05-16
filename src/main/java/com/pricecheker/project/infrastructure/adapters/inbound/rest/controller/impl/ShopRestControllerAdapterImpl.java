package com.pricecheker.project.infrastructure.adapters.inbound.rest.controller.impl;

import com.pricecheker.project.application.ports.inbound.ShopUseCaseServicePort;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.controller.ShopRestControllerAdapter;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.mapper.ShopRestInbountMapper;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.request.CreateShopRequest;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.response.GenericResponse;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.response.shop.ShopViewResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/shops")
@RequiredArgsConstructor
@Slf4j
public class ShopRestControllerAdapterImpl implements ShopRestControllerAdapter {

  private final ShopRestInbountMapper mapper;
  private final ShopUseCaseServicePort shopUseCaseServicePort;

  @Override
  public ResponseEntity<GenericResponse<ShopViewResponse>> getAllShops() {
    log.info("[Start]  ShopRestControllerAdapterImpl.getAllShops - Getting all shops");

    var shops =
        shopUseCaseServicePort.getStores().stream().map(mapper::toShopViewResponse).toList();

    log.info("[End]  ShopRestControllerAdapterImpl.getAllShops - Getting all shops");
    return ResponseEntity.ok(
        GenericResponse.<ShopViewResponse>builder().id(UUID.randomUUID()).data(shops).build());
  }

  @Override
  public ResponseEntity<GenericResponse<ShopViewResponse>> createShop(
      CreateShopRequest createShopRequest) {
    log.info(
        "[Start]  ShopRestControllerAdapterImpl.createShop - Creating shop: {}", createShopRequest);

    log.info(
        "[End]  ShopRestControllerAdapterImpl.createShop - Creating shop: {}", createShopRequest);
    return null;
  }
}
