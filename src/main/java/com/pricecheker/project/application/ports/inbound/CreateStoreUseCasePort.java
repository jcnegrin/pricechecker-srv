package com.pricecheker.project.application.ports.inbound;

import com.pricecheker.project.domain.dto.CreateStoreDto;

import com.pricecheker.project.domain.entity.ShopDomainEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CreateStoreUseCasePort {

    ShopDomainEntity createStore(@NotNull @Valid CreateStoreDto createStoreDto);
}
