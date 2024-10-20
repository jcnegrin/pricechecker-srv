package com.pricecheker.project.application.ports.inbound;

/*
    Author: juannegrin
    Date: 19/10/24
    Time: 17:47
*/

import com.pricecheker.project.domain.view.ShopCategoriesView;
import java.util.List;
import org.springframework.validation.annotation.Validated;

@Validated
public interface QueryCategoriesByShopUseCasePort {
  List<ShopCategoriesView> getCategoriesByShop();
}
