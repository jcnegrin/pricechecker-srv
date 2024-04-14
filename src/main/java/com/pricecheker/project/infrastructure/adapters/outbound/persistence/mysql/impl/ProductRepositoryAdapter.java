package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.impl;

import com.pricecheker.project.application.ports.outbound.ProductRepositoryPort;
import com.pricecheker.project.domain.entity.ProductDomainEntity;
import com.pricecheker.project.domain.query.FindAllProductsQuery;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.mapper.ProductOutboundMapper;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    private final ProductRepository productRepository;
    private final ProductOutboundMapper productInboundMapper;

    @Override
    public List<ProductDomainEntity> findAllProducts(FindAllProductsQuery query) {
        return productRepository.findAll().stream()
                .map(productInboundMapper::toDomain)
                .toList();
    }
}
