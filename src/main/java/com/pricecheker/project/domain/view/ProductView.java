package com.pricecheker.project.domain.view;

/*
    Author: juannegrin
    Date: 20/10/24
    Time: 21:58
*/

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductView {
  private String id;
  private String name;
  private String description;
  private String imageUrl;
  private BigDecimal price;
  private ShopView shop;
}
