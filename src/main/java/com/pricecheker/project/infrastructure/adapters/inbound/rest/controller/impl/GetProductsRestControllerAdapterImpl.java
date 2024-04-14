package com.pricecheker.project.infrastructure.adapters.inbound.rest.controller.impl;

import com.pricecheker.project.application.ports.inbound.FindAllProductsUseCasePort;
import com.pricecheker.project.domain.query.FindAllProductsQuery;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.controller.GetProductsRestControllerPort;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.response.GetProductsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class GetProductsRestControllerAdapterImpl implements GetProductsRestControllerPort {

    private final FindAllProductsUseCasePort findAllProductsUseCasePort;


    @Override
    public ResponseEntity<GetProductsResponse> getProducts() {

        var a = findAllProductsUseCasePort.findAllProducts(new FindAllProductsQuery());

        return null;
    }
}
