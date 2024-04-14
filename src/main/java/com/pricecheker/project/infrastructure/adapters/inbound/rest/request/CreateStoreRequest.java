package com.pricecheker.project.infrastructure.adapters.inbound.rest.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@Validated
public class CreateStoreRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String url;
}
