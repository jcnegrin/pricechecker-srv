package com.pricecheker.project.infrastructure.adapters.inbound.rest.controller.impl;

import com.pricecheker.project.application.ports.inbound.QueryCategoriesByShopUseCasePort;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.controller.CategoryRestControllerAdapter;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.response.category.QueryCategoriesByShopResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    Author: juannegrin
    Date: 19/10/24
    Time: 21:29
*/

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryRestControllerAdapterImpl implements CategoryRestControllerAdapter {

  private final QueryCategoriesByShopUseCasePort service;

  @Override
  public ResponseEntity<QueryCategoriesByShopResponse> getCategoriesByShop() {

    log.info(
        "[Start]  CategoryRestControllerAdapterImpl.getCategoriesByShop - Getting all categories by shop");

    var response = service.getCategoriesByShop();

    log.info(
        "[End]  CategoryRestControllerAdapterImpl.getCategoriesByShop - Getting all categories by shop");

    return ResponseEntity.ok(QueryCategoriesByShopResponse.builder().data(response).build());
  }
}
