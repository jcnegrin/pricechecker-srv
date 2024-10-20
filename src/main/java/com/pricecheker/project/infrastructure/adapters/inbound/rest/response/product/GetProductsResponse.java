package com.pricecheker.project.infrastructure.adapters.inbound.rest.response.product;

import com.pricecheker.project.domain.view.ProductView;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetProductsResponse {
  private String id;
  private List<ProductView> products;
}
