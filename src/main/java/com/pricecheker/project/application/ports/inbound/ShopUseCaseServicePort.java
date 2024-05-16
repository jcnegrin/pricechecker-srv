package com.pricecheker.project.application.ports.inbound;

import com.pricecheker.project.domain.dto.CreateStoreDto;
import com.pricecheker.project.domain.entity.StoreDomainEntity;
import java.util.List;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ShopUseCaseServicePort {

  List<StoreDomainEntity> getStores();

  StoreDomainEntity createStore(CreateStoreDto store);
}
