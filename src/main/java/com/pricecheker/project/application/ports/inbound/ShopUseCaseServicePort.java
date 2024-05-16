package com.pricecheker.project.application.ports.inbound;

import com.pricecheker.project.domain.entity.StoreDomainEntity;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface ShopUseCaseServicePort {

  List<StoreDomainEntity> getStores();

  StoreDomainEntity createStore(StoreDomainEntity store);
}
