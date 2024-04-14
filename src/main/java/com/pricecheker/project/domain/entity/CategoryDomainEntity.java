package com.pricecheker.project.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDomainEntity {

    private String id;
    private String name;
    private String categoryCode;
    private List<ProductDomainEntity> products;
    private CategoryDomainEntity parentCategory;
    private List<CategoryDomainEntity> subcategories;
}
