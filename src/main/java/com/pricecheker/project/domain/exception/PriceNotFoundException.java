package com.pricecheker.project.domain.exception;

/*
    Author: juannegrin
    Date: 20/10/24
    Time: 22:24
*/
public class PriceNotFoundException extends RuntimeException {
  public PriceNotFoundException(String productId) {
    super("Price not found for product with ID: " + productId);
  }
}
