package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.impl;

import com.pricecheker.project.application.ports.outbound.StoreRepositoryPort;

import com.pricecheker.project.domain.entity.ShopDomainEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity.ShopEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.mapper.StoreOutboundMapper;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.repository.StoreRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StoreRepositoryAdapter implements StoreRepositoryPort {

  @Autowired private final StoreRepository storeRepository;
  @Autowired private final StoreOutboundMapper mapper;

  @Override
  public ShopDomainEntity save(ShopDomainEntity storeDomainEntity) {
    ShopEntity storeEntity = mapper.toEntity(storeDomainEntity);
    return this.mapper.toDomain(storeRepository.save(storeEntity));
  }

  @Override
  public ShopDomainEntity findById(String id) {
    return null;
  }

  @Override
  public ShopDomainEntity findByName(String name) {
    return this.mapper.toDomain(storeRepository.findByName(name));
  }

  @Override
  public List<ShopDomainEntity> findAll() {
    return this.storeRepository.findAll().stream().map(mapper::toDomain).toList();
  }

  @Override
  public void deleteById(String id) {}
}
