package com.pricecheker.project.application.ports.outbound;

/*
    Author: juannegrin
    Date: 18/10/24
    Time: 23:02
*/

import com.pricecheker.project.domain.entity.PriceDomainEntity;
import com.pricecheker.project.domain.entity.ProductDomainEntity;
import java.util.List;
import java.util.Optional;

public interface ProductPriceRepositoryPort {
  void saveProductPrice(PriceDomainEntity price);

  Optional<PriceDomainEntity> findLatestPriceByProduct(ProductDomainEntity product);

  List<PriceDomainEntity> findPricesByProductId(String productId);

  Optional<PriceDomainEntity> findLatestPriceByProductId(String productId);
}
