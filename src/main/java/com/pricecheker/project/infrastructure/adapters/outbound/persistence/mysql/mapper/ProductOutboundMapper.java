package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.mapper;

import com.pricecheker.project.domain.entity.CategoryDomainEntity;
import com.pricecheker.project.domain.entity.PriceDomainEntity;
import com.pricecheker.project.domain.entity.ProductDomainEntity;
import com.pricecheker.project.domain.entity.ShopDomainEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity.CategoryEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity.PriceEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity.ProductEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity.ShopEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductOutboundMapper {

    ProductDomainEntity toDomain(ProductEntity entity);
    ProductEntity toEntity(ProductDomainEntity domain);

    ShopDomainEntity toDomain(ShopEntity entity);
    CategoryDomainEntity toDomain(CategoryEntity entity);
    PriceDomainEntity toDomain(PriceEntity entity);

    ShopEntity toEntity(ShopDomainEntity domain);
    CategoryEntity toEntity(CategoryDomainEntity domain);
    PriceEntity toEntity(PriceDomainEntity domain);
}
