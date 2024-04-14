package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.mapper;

import com.pricecheker.project.domain.entity.CategoryDomainEntity;
import com.pricecheker.project.domain.entity.PriceDomainEntity;
import com.pricecheker.project.domain.entity.ProductDomainEntity;
import com.pricecheker.project.domain.entity.StoreDomainEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity.CategoryEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity.PriceEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity.ProductEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity.StoreEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductOutboundMapper {

    ProductDomainEntity toDomain(ProductEntity entity);
    ProductEntity toEntity(ProductDomainEntity domain);

    StoreDomainEntity toDomain(StoreEntity entity);
    CategoryDomainEntity toDomain(CategoryEntity entity);
    PriceDomainEntity toDomain(PriceEntity entity);

    StoreEntity toEntity(StoreDomainEntity domain);
    CategoryEntity toEntity(CategoryDomainEntity domain);
    PriceEntity toEntity(PriceDomainEntity domain);
}
