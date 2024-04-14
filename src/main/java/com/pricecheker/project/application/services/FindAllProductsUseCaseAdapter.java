package com.pricecheker.project.application.services;

import com.pricecheker.project.application.ports.inbound.FindAllProductsUseCasePort;
import com.pricecheker.project.application.ports.outbound.ProductRepositoryPort;
import com.pricecheker.project.domain.entity.ProductDomainEntity;
import com.pricecheker.project.domain.query.FindAllProductsQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllProductsUseCaseAdapter implements FindAllProductsUseCasePort {

    private final ProductRepositoryPort productRepositoryPort;

    @Override
    public List<ProductDomainEntity> findAllProducts(FindAllProductsQuery query) {
        var a = productRepositoryPort.findAllProducts(query);

        return List.of();
    }
}
