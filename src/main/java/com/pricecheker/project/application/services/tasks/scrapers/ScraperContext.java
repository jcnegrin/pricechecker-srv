package com.pricecheker.project.application.services.tasks.scrapers;

import com.pricecheker.project.application.services.tasks.interfaces.ScraperStrategy;
import com.pricecheker.project.application.services.tasks.model.ScrapedProduct;
import java.util.List;
import java.util.Map;

/*
    Author: juannegrin
    Date: 17/10/24
    Time: 20:57
*/
public class ScraperContext {

  private final ScraperStrategy scraperStrategy;

  public ScraperContext(ScraperStrategy scraperStrategy) {
    this.scraperStrategy = scraperStrategy;
  }

  public Map<String, List<ScrapedProduct>> scrapeProducts() {
    return scraperStrategy.scrapeProducts();
  }
}
