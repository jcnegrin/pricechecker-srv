package com.pricecheker.project.infrastructure.adapters.inbound.rest.response;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GenericResponse {
  private UUID id;
  private Object data;
}
