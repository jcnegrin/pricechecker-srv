package com.pricecheker.project.infrastructure.adapters.outbound.scraper.impl;

import com.pricecheker.project.application.ports.outbound.WebScrapingRepositoryPort;
import com.pricecheker.project.infrastructure.adapters.outbound.scraper.factory.ScraperFactory;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebScrapingRepository implements WebScrapingRepositoryPort {

  private final ScraperFactory scraperFactory;

  @Autowired
  public WebScrapingRepository(ScraperFactory scraperFactory) {
    this.scraperFactory = scraperFactory;
  }

  @Override
  public Map<String, List<ShopProductDomainEntity>> extractProducts() {

    return Map.of();
  }
}
