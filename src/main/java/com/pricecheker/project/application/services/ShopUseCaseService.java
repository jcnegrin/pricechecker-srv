package com.pricecheker.project.application.services;

import com.pricecheker.project.application.ports.inbound.ShopUseCaseServicePort;
import com.pricecheker.project.application.ports.outbound.StoreRepositoryPort;
import com.pricecheker.project.domain.entity.StoreDomainEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopUseCaseService implements ShopUseCaseServicePort {

  private final StoreRepositoryPort storeRepositoryPort;

  @Override
  public List<StoreDomainEntity> getStores() {
    return List.of();
  }

  @Override
  public StoreDomainEntity createStore(StoreDomainEntity store) {
    return null;
  }
}
