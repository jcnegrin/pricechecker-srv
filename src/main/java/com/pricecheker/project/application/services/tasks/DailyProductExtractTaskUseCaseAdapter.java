package com.pricecheker.project.application.services.tasks;

import com.pricecheker.project.application.ports.inbound.DailyProductExtractTaskUseCasePort;
import com.pricecheker.project.application.ports.outbound.ProductCategoryRepositoryPort;
import com.pricecheker.project.application.ports.outbound.StoreRepositoryPort;
import com.pricecheker.project.application.services.tasks.factory.ScraperFactory;
import com.pricecheker.project.application.services.tasks.interfaces.ScraperStrategy;
import com.pricecheker.project.application.services.tasks.model.*;
import com.pricecheker.project.application.services.tasks.scrapers.ScraperContext;
import com.pricecheker.project.domain.entity.ShopDomainEntity;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class DailyProductExtractTaskUseCaseAdapter implements DailyProductExtractTaskUseCasePort {

  private final ProductCategoryRepositoryPort categoryRepositoryPort;
  private final StoreRepositoryPort storeRepositoryPort;

  @Override
  public void executeDailyTask() {

    log.info(
        "DailyProductExtractTaskUseCaseAdapter.executeDailyTask - Starting daily product extraction task...");

    List<ShopDomainEntity> shops;
    try {
      shops = storeRepositoryPort.findAll();
      log.info(
          "DailyProductExtractTaskUseCaseAdapter.executeDailyTask - Found {} stores to process.",
          shops.size());
    } catch (Exception e) {
      log.error(
          "DailyProductExtractTaskUseCaseAdapter.executeDailyTask - Error retrieving stores from repository: {}",
          e.getMessage(),
          e);
      return;
    }

    List<ShopProductModel> shopProductModels = new ArrayList<>();

    for (ShopDomainEntity shop : shops) {
      try {
        log.info(
            "DailyProductExtractTaskUseCaseAdapter.executeDailyTask - Starting product extraction for store: {}",
            shop.getName());

        ShopDetails shopDetails = new ShopDetails(shop.getUrl());

        ScraperStrategy scraperStrategy = ScraperFactory.getScraper(shop.getName(), shopDetails);
        ScraperContext scraperContext = new ScraperContext(scraperStrategy);

        List<ScrapedProduct> products = scraperContext.scrapeProducts();
        log.info(
            "DailyProductExtractTaskUseCaseAdapter.executeDailyTask - Product extraction complete for store: {}. {} products found.",
            shop.getName(),
            products.size());

        ShopProductModel shopProductModel = new ShopProductModel(shop.getName(), products);
        shopProductModels.add(shopProductModel);

      } catch (Exception e) {
        log.error(
            "DailyProductExtractTaskUseCaseAdapter.executeDailyTask - Error during product extraction for store {}: {}",
            shop.getName(),
            e.getMessage(),
            e);
      }
    }

    log.info(
        "DailyProductExtractTaskUseCaseAdapter.executeDailyTask - Daily product extraction task completed. {} stores processed.",
        shopProductModels.size());
  }
}
