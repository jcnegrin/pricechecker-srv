package com.pricecheker.project.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteDomainEntity {
  private String id;
  private UserDomainEntity user;
  private ProductDomainEntity product;
}
