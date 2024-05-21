package com.pricecheker.project.application.services;

import com.pricecheker.project.application.ports.inbound.ShopUseCaseServicePort;
import com.pricecheker.project.application.ports.outbound.StoreRepositoryPort;
import com.pricecheker.project.domain.dto.CreateStoreDto;

import java.util.List;
import java.util.UUID;

import com.pricecheker.project.domain.entity.ShopDomainEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopUseCaseService implements ShopUseCaseServicePort {

  private final StoreRepositoryPort storeRepositoryPort;

  @Override
  public List<ShopDomainEntity> getStores() {
    return storeRepositoryPort.findAll();
  }

  @Override
  public ShopDomainEntity createStore(CreateStoreDto store) {

    ShopDomainEntity storeDomainEntity =
        ShopDomainEntity.builder()
            .id(UUID.randomUUID().toString())
            .name(store.getName())
            .url(store.getUrl())
            .build();

    return storeRepositoryPort.save(storeDomainEntity);
  }
}
