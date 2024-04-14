package com.pricecheker.project.domain.exception;

import org.springframework.http.HttpStatus;

public interface ErrorDataException {

    HttpStatus getErrorCode();
    void setErrorCode(HttpStatus errorCode);
    String getDescription();
    void setDescription(String description);
    String getDetail();
    void setDetail(String detail);
}
