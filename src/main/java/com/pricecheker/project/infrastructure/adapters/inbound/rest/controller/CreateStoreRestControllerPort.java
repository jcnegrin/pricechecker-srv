package com.pricecheker.project.infrastructure.adapters.inbound.rest.controller;

import com.pricecheker.project.infrastructure.adapters.inbound.rest.request.CreateStoreRequest;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.response.CreateStoreResponse;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.response.GenericExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Create a new Store")
@Validated
public interface CreateStoreRestControllerPort {

    @Operation(
            description = "Create a new Store",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Store created successfully"),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Store already exists",
                            content = @Content(schema = @Schema(implementation = GenericExceptionResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CreateStoreResponse> createStore(@RequestBody @Valid CreateStoreRequest request);
}
