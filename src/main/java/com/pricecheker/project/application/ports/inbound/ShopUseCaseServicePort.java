package com.pricecheker.project.application.ports.inbound;

import com.pricecheker.project.domain.dto.CreateStoreDto;
import com.pricecheker.project.domain.entity.ShopDomainEntity;
import java.util.List;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ShopUseCaseServicePort {

  List<ShopDomainEntity> getShop();

  ShopDomainEntity getShopById(String id);

  ShopDomainEntity createStore(CreateStoreDto store);
}
