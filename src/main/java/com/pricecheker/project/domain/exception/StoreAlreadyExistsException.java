package com.pricecheker.project.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;

public class StoreAlreadyExistsException extends GenericRuntimeException {

    public StoreAlreadyExistsException(String name) {
        setErrorCode(HttpStatus.CONFLICT);
        setDescription("Store already exists");
        setDetail("Store with name " + name + " already exists");
    }
}
