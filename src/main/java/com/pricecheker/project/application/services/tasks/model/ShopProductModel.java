package com.pricecheker.project.application.services.tasks.model;

/*
    Author: juannegrin
    Date: 17/10/24
    Time: 23:21
*/

import com.pricecheker.project.domain.entity.ShopDomainEntity;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShopProductModel {
  private ShopDomainEntity shop;
  private Map<String, List<ScrapedProduct>> products;
}
