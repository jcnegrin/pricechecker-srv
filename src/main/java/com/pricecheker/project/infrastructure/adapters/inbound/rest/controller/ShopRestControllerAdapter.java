package com.pricecheker.project.infrastructure.adapters.inbound.rest.controller;

import com.pricecheker.project.infrastructure.adapters.inbound.rest.response.GenericResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Shop Rest Controller APIs")
public interface ShopRestControllerAdapter {

  @Operation(
      description = "Get a shop details by ID",
      responses = {
        @ApiResponse(responseCode = "200", description = "Shop found"),
        @ApiResponse(responseCode = "404", description = "Shop not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{shopId}")
  ResponseEntity<GenericResponse> getShop(@PathVariable String shopId);

  @Operation(
      description = "Get all shops",
      responses = {
        @ApiResponse(responseCode = "200", description = "Shops found"),
        @ApiResponse(responseCode = "404", description = "Shops not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<GenericResponse> getAllShops();
}
