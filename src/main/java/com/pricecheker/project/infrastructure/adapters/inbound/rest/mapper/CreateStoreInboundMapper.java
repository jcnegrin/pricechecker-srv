package com.pricecheker.project.infrastructure.adapters.inbound.rest.mapper;

import com.pricecheker.project.domain.dto.CreateStoreDto;
import com.pricecheker.project.domain.entity.StoreDomainEntity;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.request.CreateStoreRequest;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.response.CreateStoreResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateStoreInboundMapper {
    CreateStoreDto toDto(CreateStoreRequest request);
    CreateStoreResponse toResponse(StoreDomainEntity dto);
}
