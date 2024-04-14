package com.pricecheker.project.application.ports.outbound;

import com.pricecheker.project.domain.entity.StoreDomainEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface StoreRepositoryPort {
    StoreDomainEntity save(@NotNull StoreDomainEntity StoreDomainEntity);
    StoreDomainEntity findById(@NotEmpty String id);
    StoreDomainEntity findByName(@NotEmpty String name);
    List<StoreDomainEntity> findAll();
    void deleteById(@NotEmpty String id);
}
