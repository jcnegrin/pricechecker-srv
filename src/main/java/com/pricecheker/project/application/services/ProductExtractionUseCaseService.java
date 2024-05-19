package com.pricecheker.project.application.services;

import com.pricecheker.project.application.ports.inbound.ProductExtractionUseCasePort;
import com.pricecheker.project.application.ports.outbound.StoreRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductExtractionUseCaseService implements ProductExtractionUseCasePort {

  private StoreRepositoryPort storeRepositoryPort;

  @Autowired
  public ProductExtractionUseCaseService(StoreRepositoryPort storeRepositoryPort) {
    this.storeRepositoryPort = storeRepositoryPort;
  }

  @Override
  public void extractProducts() {

    log.info("[Start]  ProductExtractionUseCaseService.extractProducts - Extracting products");

    storeRepositoryPort
        .findAll()
        .forEach(
            store -> {
              log.info("Extracting products from store: {}", store.getName());
            });
  }
}
