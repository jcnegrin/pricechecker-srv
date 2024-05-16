package com.pricecheker.project.infrastructure.adapters.inbound.rest.controller;

import com.pricecheker.project.infrastructure.adapters.inbound.rest.request.CreateShopRequest;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.response.GenericResponse;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.response.shop.ShopViewResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "Shop Rest Controller API")
public interface ShopRestControllerAdapter {

  @Operation(
      description = "Get all shops",
      responses = {
        @ApiResponse(responseCode = "200", description = "Shops found"),
        @ApiResponse(responseCode = "404", description = "Shops not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<GenericResponse<ShopViewResponse>> getAllShops();

  @Operation(
      description = "Create a shop",
      responses = {
        @ApiResponse(responseCode = "201", description = "Shop created"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @PostMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<GenericResponse<ShopViewResponse>> createShop(CreateShopRequest createShopRequest);
}
