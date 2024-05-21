package com.pricecheker.project.application.ports.inbound;

import com.pricecheker.project.domain.dto.CreateStoreDto;
import java.util.List;

import com.pricecheker.project.domain.entity.ShopDomainEntity;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ShopUseCaseServicePort {

  List<ShopDomainEntity> getStores();

  ShopDomainEntity createStore(CreateStoreDto store);
}
