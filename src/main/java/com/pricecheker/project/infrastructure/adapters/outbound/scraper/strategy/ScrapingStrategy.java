package com.pricecheker.project.infrastructure.adapters.outbound.scraper.strategy;

import com.pricecheker.project.infrastructure.adapters.outbound.scraper.dto.StoreProductDto;
import java.util.List;
import java.util.Map;

public interface ScrapingStrategy {
    Map<String, List<StoreProductDto>> extractProducts();
}
