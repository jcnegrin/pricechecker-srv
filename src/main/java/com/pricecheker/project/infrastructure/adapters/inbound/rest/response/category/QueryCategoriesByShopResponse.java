package com.pricecheker.project.infrastructure.adapters.inbound.rest.response.category;

/*
    Author: juannegrin
    Date: 19/10/24
    Time: 21:26
*/

import com.pricecheker.project.domain.view.ShopCategoriesView;
import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QueryCategoriesByShopResponse implements Serializable {
  private List<ShopCategoriesView> data;
}
