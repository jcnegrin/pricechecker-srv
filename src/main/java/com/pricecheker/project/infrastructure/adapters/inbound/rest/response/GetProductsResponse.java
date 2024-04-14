package com.pricecheker.project.infrastructure.adapters.inbound.rest.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GetProductsResponse {
    private String name;
    private String description;
    private String shop;
    private BigDecimal price;
}
