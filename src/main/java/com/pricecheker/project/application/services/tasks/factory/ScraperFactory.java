package com.pricecheker.project.application.services.tasks.factory;

/*
    Author: juannegrin
    Date: 17/10/24
    Time: 20:55
*/

import com.pricecheker.project.application.services.tasks.interfaces.ScraperStrategy;
import com.pricecheker.project.application.services.tasks.model.ShopDetails;
import com.pricecheker.project.application.services.tasks.scrapers.DiaScraper;
import com.pricecheker.project.application.services.tasks.scrapers.MercadonaScraper;

public class ScraperFactory {

  private ScraperFactory() {
    throw new IllegalStateException("Utility class");
  }

  public static ScraperStrategy getScraper(String store, ShopDetails shopDetails) {
    return switch (store.toUpperCase()) {
      case "MERCADONA" -> new MercadonaScraper(shopDetails);
      case "DIA" -> new DiaScraper(shopDetails);
      default -> throw new IllegalArgumentException("Invalid store");
    };
  }
}
