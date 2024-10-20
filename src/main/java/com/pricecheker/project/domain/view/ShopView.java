package com.pricecheker.project.domain.view;

/*
    Author: juannegrin
    Date: 20/10/24
    Time: 21:59
*/

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ShopView {
  private String id;
  private String name;
}
