package com.pricecheker.project.application.ports.outbound;

import com.pricecheker.project.domain.entity.ShopProductDomainEntity;
import java.util.List;
import java.util.Map;

public interface WebScrapingRepositoryPort {

  Map<String, List<ShopProductDomainEntity>> extractProducts();
}
