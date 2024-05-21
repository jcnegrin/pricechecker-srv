package com.pricecheker.project.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopDomainEntity {

    private String id;
    private String name;
    private String url;
    private List<ProductDomainEntity> products = new ArrayList<>();
}
