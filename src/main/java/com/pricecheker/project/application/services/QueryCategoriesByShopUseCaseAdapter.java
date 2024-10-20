package com.pricecheker.project.application.services;

import com.pricecheker.project.application.ports.inbound.QueryCategoriesByShopUseCasePort;
import com.pricecheker.project.application.ports.inbound.ShopUseCaseServicePort;
import com.pricecheker.project.application.ports.outbound.ProductCategoryRepositoryPort;
import com.pricecheker.project.application.ports.outbound.ProductRepositoryPort;
import com.pricecheker.project.domain.view.CategoryView;
import com.pricecheker.project.domain.view.ShopCategoriesView;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/*
    Author: juannegrin
    Date: 19/10/24
    Time: 18:08
*/

@Service
@RequiredArgsConstructor
@Slf4j
public class QueryCategoriesByShopUseCaseAdapter implements QueryCategoriesByShopUseCasePort {

  private final ShopUseCaseServicePort shopUseCaseServicePort;
  private final ProductRepositoryPort productRepositoryPort;
  private final ProductCategoryRepositoryPort productCategoryRepositoryPort;

  @Override
  public List<ShopCategoriesView> getCategoriesByShop() {
    return shopUseCaseServicePort.getShop().stream()
        .map(
            shop -> {
              List<String> categoryIds = productRepositoryPort.findCategoriesByShop(shop.getId());

              List<CategoryView> categories =
                  categoryIds.stream()
                      .map(productCategoryRepositoryPort::findCategoryById)
                      .filter(Optional::isPresent)
                      .map(Optional::get)
                      .map(category -> new CategoryView(category.getName()))
                      .toList();

              return new ShopCategoriesView(shop.getId(), shop.getName(), categories);
            })
        .toList();
  }
}
