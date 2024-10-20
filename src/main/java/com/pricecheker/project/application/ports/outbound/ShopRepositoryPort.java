package com.pricecheker.project.application.ports.outbound;

import com.pricecheker.project.domain.entity.ShopDomainEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ShopRepositoryPort {
  ShopDomainEntity save(@NotNull ShopDomainEntity StoreDomainEntity);

  Optional<ShopDomainEntity> findById(@NotEmpty String id);

  ShopDomainEntity findByName(@NotEmpty String name);

  List<ShopDomainEntity> findAll();
}
