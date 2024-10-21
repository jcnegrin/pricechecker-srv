package com.pricecheker.project.application.ports.outbound;

import com.pricecheker.project.domain.entity.ProductDomainEntity;
import com.pricecheker.project.domain.entity.ShopDomainEntity;
import com.pricecheker.project.domain.query.FindAllProductsQuery;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ProductRepositoryPort {
  List<ProductDomainEntity> findAllProducts(@NotNull FindAllProductsQuery query);

  Optional<ProductDomainEntity> findProductByNameAndShop(
      @NotNull String name, @NotNull ShopDomainEntity shop);

  List<String> findCategoriesByShop(@NotNull String shopId);

  List<ProductDomainEntity> findProductByShopAndCategory(
      @NotNull String shopId, @NotNull String categoryId);

  List<ProductDomainEntity> findProductsByCategory(@NotNull String categoryId);

  Optional<ProductDomainEntity> findProductById(@NotNull String productId);

  ProductDomainEntity saveProduct(@NotNull ProductDomainEntity product);
}
