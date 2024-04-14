package com.pricecheker.project.infrastructure.adapters.inbound.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericExceptionResponse {
    private int errorCode;
    private String description;
    private String detail;
}
