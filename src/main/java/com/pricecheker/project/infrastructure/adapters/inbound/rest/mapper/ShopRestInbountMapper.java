package com.pricecheker.project.infrastructure.adapters.inbound.rest.mapper;

import com.pricecheker.project.domain.dto.CreateStoreDto;
import com.pricecheker.project.domain.entity.StoreDomainEntity;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.request.CreateShopRequest;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.response.shop.ShopViewResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShopRestInbountMapper {

  ShopViewResponse toShopViewResponse(StoreDomainEntity storeDomainEntity);

  CreateStoreDto toCreateStoreDto(CreateShopRequest createShopRequest);
}
