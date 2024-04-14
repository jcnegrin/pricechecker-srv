package com.pricecheker.project.infrastructure.adapters.inbound.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateStoreResponse {

    private String id;
    private String name;
    private String url;
}
