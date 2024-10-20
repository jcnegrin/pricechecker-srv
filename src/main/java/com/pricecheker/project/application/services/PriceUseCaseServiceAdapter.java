package com.pricecheker.project.application.services;

import com.pricecheker.project.application.ports.inbound.PriceUseCaseServicePort;
import com.pricecheker.project.application.ports.inbound.ProductUseCaseServicePort;
import com.pricecheker.project.application.ports.outbound.ProductPriceRepositoryPort;
import com.pricecheker.project.domain.entity.PriceDomainEntity;
import com.pricecheker.project.domain.exception.PriceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/*
    Author: juannegrin
    Date: 20/10/24
    Time: 21:47
*/

@Service
@RequiredArgsConstructor
@Slf4j
public class PriceUseCaseServiceAdapter implements PriceUseCaseServicePort {

  private final ProductPriceRepositoryPort repositoryPort;

  // private final ProductUseCaseServicePort productUseCaseServicePort;

  //  @Override
  //  public List<PriceDomainEntity> getPricesByProductId(String productId) {
  //    log.info("Fetching prices for product with ID: {}", productId);
  //
  //    ProductView product = productUseCaseServicePort.getProductById(productId);
  //
  //    List<PriceDomainEntity> prices = repositoryPort.findPricesByProductId(product.getId());
  //    log.info("Found {} prices for product with ID: {}", prices.size(), productId);
  //
  //    return prices;
  //  }

  @Override
  public PriceDomainEntity getLatestPriceByProductId(String productId) {
    return repositoryPort
        .findLatestPriceByProductId(productId)
        .orElseThrow(() -> new PriceNotFoundException(productId));
  }
}
