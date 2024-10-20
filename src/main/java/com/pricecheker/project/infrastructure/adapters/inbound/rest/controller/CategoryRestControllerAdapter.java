package com.pricecheker.project.infrastructure.adapters.inbound.rest.controller;

/*
    Author: juannegrin
    Date: 19/10/24
    Time: 21:25
*/

import com.pricecheker.project.infrastructure.adapters.inbound.rest.response.category.QueryCategoriesByShopResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Category Rest Controller API")
public interface CategoryRestControllerAdapter {

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<QueryCategoriesByShopResponse> getCategoriesByShop();
}
