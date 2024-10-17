package com.pricecheker.project.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDomainEntity {
  private String id;
  private String name;
  private String brand;
  private String description;
  private String imageUrl;
  private ShopDomainEntity shop;
  private CategoryDomainEntity category;
}
