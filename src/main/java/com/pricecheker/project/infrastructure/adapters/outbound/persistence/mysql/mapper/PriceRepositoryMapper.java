package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.mapper;

/*
    Author: juannegrin
    Date: 19/10/24
    Time: 00:04
*/

import com.pricecheker.project.domain.entity.PriceDomainEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity.PriceEntity;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {ProductRepositoryMapper.class})
public interface PriceRepositoryMapper {

  PriceDomainEntity toDomainEntity(PriceEntity entity);

  PriceEntity toEntity(PriceDomainEntity domain);
}
