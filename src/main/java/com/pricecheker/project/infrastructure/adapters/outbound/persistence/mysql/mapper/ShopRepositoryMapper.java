package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.mapper;

import com.pricecheker.project.domain.entity.ShopDomainEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity.ShopEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShopRepositoryMapper {

  ShopEntity toEntity(ShopDomainEntity entity);

  ShopDomainEntity toDomain(ShopEntity entity);
}
