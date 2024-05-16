package com.pricecheker.project.application.ports.inbound;

import com.pricecheker.project.domain.entity.ProductDomainEntity;
import java.util.List;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ProductUseCaseServicePort {

  List<ProductDomainEntity> findAllProducts();

  List<ProductDomainEntity> getProductsByStore(String store);

  List<ProductDomainEntity> getProductsByCategory(String category);
}
