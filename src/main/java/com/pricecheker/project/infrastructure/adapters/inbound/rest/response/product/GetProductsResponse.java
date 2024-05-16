package com.pricecheker.project.infrastructure.adapters.inbound.rest.response.product;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetProductsResponse {
  private String id;
  private List<ProductViewResponse> products;
}
