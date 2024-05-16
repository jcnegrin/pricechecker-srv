package com.pricecheker.project.infrastructure.adapters.inbound.rest.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateShopRequest {
  private String name;
  private String url;
}
