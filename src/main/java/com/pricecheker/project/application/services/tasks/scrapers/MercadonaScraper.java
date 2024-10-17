package com.pricecheker.project.application.services.tasks.scrapers;

import com.pricecheker.project.application.services.tasks.interfaces.ScraperStrategy;
import com.pricecheker.project.application.services.tasks.model.ScrapedProduct;
import com.pricecheker.project.application.services.tasks.model.ShopDetails;
import java.util.List;

/*
    Author: juannegrin
    Date: 17/10/24
    Time: 20:56
*/
public class MercadonaScraper implements ScraperStrategy {

  private final ShopDetails shopDetails;

  public MercadonaScraper(ShopDetails shopDetails) {
    this.shopDetails = shopDetails;
  }

  @Override
  public List<ScrapedProduct> scrapeProducts() {
    return List.of();
  }
}
