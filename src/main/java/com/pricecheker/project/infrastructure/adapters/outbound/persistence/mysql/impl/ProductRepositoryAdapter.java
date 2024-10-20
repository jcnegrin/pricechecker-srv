package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.impl;

import com.pricecheker.project.application.ports.outbound.ProductRepositoryPort;
import com.pricecheker.project.domain.entity.ProductDomainEntity;
import com.pricecheker.project.domain.entity.ShopDomainEntity;
import com.pricecheker.project.domain.query.FindAllProductsQuery;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity.ShopEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.mapper.CategoryRepositoryMapper;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.mapper.ProductRepositoryMapper;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.mapper.ShopRepositoryMapper;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepositoryPort {

  private final ProductRepository repository;
  private final ProductRepositoryMapper productRepositoryMapper;
  private final ShopRepositoryMapper shopRepositoryMapper;
  private final CategoryRepositoryMapper categoryRepositoryMapper;

  @Override
  public List<ProductDomainEntity> findAllProducts(FindAllProductsQuery query) {
    return repository.findAll().stream().map(productRepositoryMapper::toDomain).toList();
  }

  @Override
  public Optional<ProductDomainEntity> findProductByNameAndShop(
      String name, ShopDomainEntity shop) {
    ShopEntity shopEntity = shopRepositoryMapper.toEntity(shop);
    return repository.findByNameAndShop(name, shopEntity).map(productRepositoryMapper::toDomain);
  }

  @Override
  public List<String> findCategoriesByShop(String shopId) {
    return repository.findCategoriesByShopId(shopId);
  }

  @Override
  public List<ProductDomainEntity> findProductByShopAndCategory(String shopId, String categoryId) {
    return repository.findByShopIdAndCategoryId(shopId, categoryId).stream()
        .map(productRepositoryMapper::toDomain)
        .toList();
  }

  @Override
  public Optional<ProductDomainEntity> findProductById(String productId) {
    return repository.findById(productId).map(productRepositoryMapper::toDomain);
  }

  @Override
  public ProductDomainEntity saveProduct(ProductDomainEntity product) {
    return this.productRepositoryMapper.toDomain(
        repository.save(this.productRepositoryMapper.toEntity(product)));
  }
}
