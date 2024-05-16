package com.pricecheker.project.infrastructure.adapters.inbound.rest.response.product;

import com.pricecheker.project.domain.entity.PriceDomainEntity;
import java.util.List;
import lombok.Data;

@Data
public class ProductViewResponse {
  private String id;
  private String name;
  private String description;
  private String store;
  private String category;
  private List<PriceDomainEntity> prices;
}
