package com.pricecheker.project.application.services;

import com.pricecheker.project.application.ports.inbound.CreateStoreUseCasePort;
import com.pricecheker.project.application.ports.outbound.StoreRepositoryPort;
import com.pricecheker.project.domain.dto.CreateStoreDto;
import com.pricecheker.project.domain.entity.StoreDomainEntity;
import com.pricecheker.project.domain.exception.StoreAlreadyExistsException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateStoreUseCaseAdapter implements CreateStoreUseCasePort {

    @Autowired
    private final StoreRepositoryPort storeRepositoryPort;

    @Override
    public StoreDomainEntity createStore(CreateStoreDto createStoreDto) {

        StoreDomainEntity storeDomainEntity = storeRepositoryPort.findByName(createStoreDto.getName());
        boolean storeExists = storeDomainEntity != null;

        if (!storeExists) {
            StoreDomainEntity createdStore =
                    storeRepositoryPort.save(StoreDomainEntity
                        .builder()
                        .name(createStoreDto.getName())
                        .url(createStoreDto.getUrl())
                        .build());
            return createdStore;

        }

        throw new StoreAlreadyExistsException(createStoreDto.getName());
    }
}
