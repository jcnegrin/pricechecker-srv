package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.impl;

import com.pricecheker.project.application.ports.outbound.ShopRepositoryPort;
import com.pricecheker.project.domain.entity.ShopDomainEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity.ShopEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.mapper.ShopRepositoryMapper;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.repository.ShopRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShopRepositoryAdapter implements ShopRepositoryPort {

  @Autowired private final ShopRepository shopRepository;
  @Autowired private final ShopRepositoryMapper mapper;

  @Override
  public ShopDomainEntity save(ShopDomainEntity storeDomainEntity) {
    ShopEntity storeEntity = mapper.toEntity(storeDomainEntity);
    return this.mapper.toDomain(shopRepository.save(storeEntity));
  }

  @Override
  public Optional<ShopDomainEntity> findById(String id) {
    return shopRepository.findById(id).map(mapper::toDomain);
  }

  @Override
  public ShopDomainEntity findByName(String name) {
    return this.mapper.toDomain(shopRepository.findByName(name));
  }

  @Override
  public List<ShopDomainEntity> findAll() {
    return this.shopRepository.findAll().stream().map(mapper::toDomain).toList();
  }
}
