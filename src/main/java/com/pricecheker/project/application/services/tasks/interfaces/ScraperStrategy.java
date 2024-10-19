package com.pricecheker.project.application.services.tasks.interfaces;

/*
    Author: juannegrin
    Date: 17/10/24
    Time: 20:56
*/

import com.pricecheker.project.application.services.tasks.model.ScrapedProduct;
import java.util.List;
import java.util.Map;

public interface ScraperStrategy {
  Map<String, List<ScrapedProduct>> scrapeProducts();
}
