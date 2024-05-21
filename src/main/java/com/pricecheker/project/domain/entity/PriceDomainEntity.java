package com.pricecheker.project.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceDomainEntity {

    private String id;
    private BigDecimal amount;
    private LocalDateTime updateDate;
    private ProductDomainEntity product;
}
