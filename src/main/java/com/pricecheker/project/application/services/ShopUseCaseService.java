package com.pricecheker.project.application.services;

import com.pricecheker.project.application.ports.inbound.ShopUseCaseServicePort;
import com.pricecheker.project.application.ports.outbound.ShopRepositoryPort;
import com.pricecheker.project.domain.dto.CreateStoreDto;
import com.pricecheker.project.domain.entity.ShopDomainEntity;
import com.pricecheker.project.domain.exception.ShopDoesNotExistsException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopUseCaseService implements ShopUseCaseServicePort {

  private final ShopRepositoryPort shopRepositoryPort;

  @Override
  public List<ShopDomainEntity> getShop() {
    return shopRepositoryPort.findAll();
  }

  @Override
  public ShopDomainEntity getShopById(String id) {

    ShopDomainEntity store =
        shopRepositoryPort.findById(id).orElseThrow(() -> new ShopDoesNotExistsException(id));

    return store;
  }

  @Override
  public ShopDomainEntity createStore(CreateStoreDto store) {

    ShopDomainEntity storeDomainEntity =
        ShopDomainEntity.builder()
            .id(UUID.randomUUID().toString())
            .name(store.getName())
            .url(store.getUrl())
            .build();

    return shopRepositoryPort.save(storeDomainEntity);
  }
}
