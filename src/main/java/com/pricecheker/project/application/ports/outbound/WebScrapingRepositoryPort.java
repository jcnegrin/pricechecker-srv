package com.pricecheker.project.application.ports.outbound;

import java.util.List;
import java.util.Map;

public interface WebScrapingRepositoryPort {

  Map<String, List<ShopProductDomainEntity>> extractProducts();
}
