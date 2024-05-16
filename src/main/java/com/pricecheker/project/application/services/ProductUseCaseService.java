package com.pricecheker.project.application.services;

import com.pricecheker.project.application.ports.inbound.ProductUseCaseServicePort;
import com.pricecheker.project.application.ports.outbound.ProductRepositoryPort;
import com.pricecheker.project.domain.entity.ProductDomainEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductUseCaseService implements ProductUseCaseServicePort {

    private final ProductRepositoryPort productRepositoryPort;

    @Override
    public List<ProductDomainEntity> findAllProducts() {
        return List.of();
    }

    @Override
    public List<ProductDomainEntity> getProductsByStore(String store) {
        return List.of();
    }

    @Override
    public List<ProductDomainEntity> getProductsByCategory(String category) {
        return List.of();
    }
}
