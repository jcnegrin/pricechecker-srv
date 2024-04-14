package com.pricecheker.project.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceDomainEntity {

    private String id;
    private BigDecimal amount;
    private Integer measure;
    private String unit;
    private ProductDomainEntity product;
}
