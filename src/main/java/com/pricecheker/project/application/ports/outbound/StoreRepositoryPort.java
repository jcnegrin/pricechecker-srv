package com.pricecheker.project.application.ports.outbound;


import com.pricecheker.project.domain.entity.ShopDomainEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface StoreRepositoryPort {
    ShopDomainEntity save(@NotNull ShopDomainEntity StoreDomainEntity);
    ShopDomainEntity findById(@NotEmpty String id);
    ShopDomainEntity findByName(@NotEmpty String name);
    List<ShopDomainEntity> findAll();
    void deleteById(@NotEmpty String id);
}
