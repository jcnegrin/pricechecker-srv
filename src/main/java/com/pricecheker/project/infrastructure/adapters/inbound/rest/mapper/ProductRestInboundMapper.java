package com.pricecheker.project.infrastructure.adapters.inbound.rest.mapper;

import com.pricecheker.project.domain.entity.ProductDomainEntity;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.response.product.ProductViewResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductRestInboundMapper {

  ProductViewResponse mapToView(ProductDomainEntity product);
}
