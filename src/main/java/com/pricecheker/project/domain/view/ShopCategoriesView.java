package com.pricecheker.project.domain.view;

/*
    Author: juannegrin
    Date: 19/10/24
    Time: 17:48
*/

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShopCategoriesView {
  private String shopId;
  private String shop;
  private List<CategoryView> categories;
}
