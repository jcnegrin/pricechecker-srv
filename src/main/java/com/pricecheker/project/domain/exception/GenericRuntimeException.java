package com.pricecheker.project.domain.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class GenericRuntimeException extends RuntimeException implements ErrorDataException {

    private HttpStatus errorCode;
    private String description;
    private String detail;

    public GenericRuntimeException() {}

    public GenericRuntimeException(Throwable cause) {
        super(cause);
    }

    public String getMessage() {
        return this.getDetail();
    }
}
