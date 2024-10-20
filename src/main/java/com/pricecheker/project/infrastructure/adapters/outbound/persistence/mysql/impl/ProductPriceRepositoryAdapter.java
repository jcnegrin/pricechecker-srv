package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.impl;

import com.pricecheker.project.application.ports.outbound.ProductPriceRepositoryPort;
import com.pricecheker.project.domain.entity.PriceDomainEntity;
import com.pricecheker.project.domain.entity.ProductDomainEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.mapper.PriceRepositoryMapper;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.mapper.ProductRepositoryMapper;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.repository.PriceRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/*
    Author: juannegrin
    Date: 19/10/24
    Time: 00:03
*/

@Component
@RequiredArgsConstructor
public class ProductPriceRepositoryAdapter implements ProductPriceRepositoryPort {

  private final PriceRepository repository;
  private final PriceRepositoryMapper mapper;
  private final ProductRepositoryMapper productMapper;

  @Override
  public void saveProductPrice(PriceDomainEntity price) {
    repository.save(mapper.toEntity(price));
  }

  @Override
  public Optional<PriceDomainEntity> findLatestPriceByProduct(ProductDomainEntity product) {
    return repository
        .findFirstByProductOrderByUpdateDateDesc(productMapper.toEntity(product))
        .map(mapper::toDomainEntity);
  }

  @Override
  public List<PriceDomainEntity> findPricesByProductId(String id) {
    return repository.findByProductId(id).stream().map(mapper::toDomainEntity).toList();
  }

  @Override
  public Optional<PriceDomainEntity> findLatestPriceByProductId(String productId) {
    return repository
        .findFirstByProductIdOrderByUpdateDateDesc(productId)
        .map(mapper::toDomainEntity);
  }
}
