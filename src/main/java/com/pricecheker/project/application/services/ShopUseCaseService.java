package com.pricecheker.project.application.services;

import com.pricecheker.project.application.ports.inbound.ShopUseCaseServicePort;
import com.pricecheker.project.application.ports.outbound.StoreRepositoryPort;
import com.pricecheker.project.domain.dto.CreateStoreDto;
import com.pricecheker.project.domain.entity.StoreDomainEntity;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopUseCaseService implements ShopUseCaseServicePort {

  private final StoreRepositoryPort storeRepositoryPort;

  @Override
  public List<StoreDomainEntity> getStores() {
    return storeRepositoryPort.findAll();
  }

  @Override
  public StoreDomainEntity createStore(CreateStoreDto store) {

    StoreDomainEntity storeDomainEntity =
        StoreDomainEntity.builder()
            .id(UUID.randomUUID().toString())
            .name(store.getName())
            .url(store.getUrl())
            .build();

    return storeRepositoryPort.save(storeDomainEntity);
  }
}
