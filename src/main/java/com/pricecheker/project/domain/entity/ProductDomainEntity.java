package com.pricecheker.project.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDomainEntity {
  private String id;
  private String name;
  private String brand;
  private String description;
  private String imageUrl;
  private ShopDomainEntity shop;
  private CategoryDomainEntity category;
}
