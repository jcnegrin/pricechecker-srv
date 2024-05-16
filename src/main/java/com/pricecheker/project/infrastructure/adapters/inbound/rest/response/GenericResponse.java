package com.pricecheker.project.infrastructure.adapters.inbound.rest.response;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GenericResponse<T> {
  private UUID id;
  private List<T> data;
}
