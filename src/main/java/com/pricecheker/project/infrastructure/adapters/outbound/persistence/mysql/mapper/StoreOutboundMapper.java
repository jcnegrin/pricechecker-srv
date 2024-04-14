package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.mapper;

import com.pricecheker.project.domain.entity.StoreDomainEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity.StoreEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StoreOutboundMapper {

    StoreEntity toEntity(StoreDomainEntity entity);
    StoreDomainEntity toDomain(StoreEntity entity);
}
