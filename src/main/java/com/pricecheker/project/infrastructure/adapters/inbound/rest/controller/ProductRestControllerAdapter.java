package com.pricecheker.project.infrastructure.adapters.inbound.rest.controller;

import com.pricecheker.project.infrastructure.adapters.inbound.rest.response.product.GetProductsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Product Rest Controller API")
public interface ProductRestControllerAdapter {

  @Operation(
      description = "Get all products by Category and shop",
      responses = {
        @ApiResponse(responseCode = "200", description = "Products found"),
        @ApiResponse(responseCode = "404", description = "Products not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<GetProductsResponse> getProductsByCategoryAndShop(
      @RequestParam String categoryId, @RequestParam String shopId);

  @Operation(
      description = "Get similar products by product ID",
      responses = {
        @ApiResponse(responseCode = "200", description = "Similar products found"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @GetMapping(value = "/similar", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<GetProductsResponse> getSimilarProducts(
      @RequestParam(value = "productId") String productId);
}
