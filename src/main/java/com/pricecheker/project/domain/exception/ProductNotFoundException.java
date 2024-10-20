package com.pricecheker.project.domain.exception;

/*
    Author: juannegrin
    Date: 20/10/24
    Time: 21:53
*/
public class ProductNotFoundException extends RuntimeException {
  public ProductNotFoundException(String id) {
    super("Product with ID: " + id + " not found");
  }
}
