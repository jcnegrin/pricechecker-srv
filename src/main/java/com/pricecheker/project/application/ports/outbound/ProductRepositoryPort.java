package com.pricecheker.project.application.ports.outbound;

import com.pricecheker.project.domain.entity.ProductDomainEntity;
import com.pricecheker.project.domain.query.FindAllProductsQuery;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface ProductRepositoryPort {
    List<ProductDomainEntity> findAllProducts(@NotNull FindAllProductsQuery query);
}
