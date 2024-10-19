package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.mapper;

import com.pricecheker.project.domain.entity.ProductDomainEntity;
import com.pricecheker.project.domain.entity.ShopDomainEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {ShopRepositoryMapper.class})
public interface ProductRepositoryMapper {

  ProductDomainEntity toDomain(ProductEntity entity);

  ProductEntity toEntity(ProductDomainEntity domain);

  @Mapping(target = "id", source = "domain.id")
  @Mapping(target = "name", source = "domain.name")
  ProductEntity toEntity(ProductDomainEntity domain, ShopDomainEntity shop);
}
