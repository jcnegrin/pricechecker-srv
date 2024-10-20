package com.pricecheker.project.application.ports.inbound;

/*
    Author: juannegrin
    Date: 20/10/24
    Time: 21:46
*/

import com.pricecheker.project.domain.entity.PriceDomainEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

@Validated
public interface PriceUseCaseServicePort {
//  List<PriceDomainEntity> getPricesByProductId(@Valid @NotEmpty String productId);

  PriceDomainEntity getLatestPriceByProductId(@Valid @NotEmpty String productId);
}
