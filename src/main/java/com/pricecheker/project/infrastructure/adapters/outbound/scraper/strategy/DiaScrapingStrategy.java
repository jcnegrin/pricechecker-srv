package com.pricecheker.project.infrastructure.adapters.outbound.scraper.strategy;

import com.pricecheker.project.infrastructure.adapters.outbound.scraper.dto.StoreProductDto;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiaScrapingStrategy implements ScrapingStrategy {

  private final WebDriver webDriver;

  @Autowired
  public DiaScrapingStrategy(WebDriver webDriver) {
    this.webDriver = webDriver;
  }

  @Override
  public Map<String, List<StoreProductDto>> extractProducts() {
    return Map.of();
  }
}
