package com.pricecheker.project.infrastructure.adapters.outbound.scraper.factory;

import com.pricecheker.project.infrastructure.adapters.outbound.scraper.enums.ShopType;
import com.pricecheker.project.infrastructure.adapters.outbound.scraper.strategy.DiaScrapingStrategy;
import com.pricecheker.project.infrastructure.adapters.outbound.scraper.strategy.MercadonaScrapingStrategy;
import com.pricecheker.project.infrastructure.adapters.outbound.scraper.strategy.ScrapingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScraperFactory {
  private final MercadonaScrapingStrategy mercadonaScrapingFactory;
  private final DiaScrapingStrategy diaScrapingFactory;

  @Autowired
  public ScraperFactory(
      MercadonaScrapingStrategy mercadonaScrapingFactory, DiaScrapingStrategy diaScrapingFactory) {
    this.mercadonaScrapingFactory = mercadonaScrapingFactory;
    this.diaScrapingFactory = diaScrapingFactory;
  }

  public ScrapingStrategy getScraper(ShopType store) {
    switch (store) {
      case MERCADONA:
        return mercadonaScrapingFactory;
      case DIA:
        return diaScrapingFactory;
      default:
        throw new IllegalArgumentException("Store not supported");
    }
  }
}
