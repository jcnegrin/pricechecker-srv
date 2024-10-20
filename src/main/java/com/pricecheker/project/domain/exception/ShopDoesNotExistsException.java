package com.pricecheker.project.domain.exception;

/*
    Author: juannegrin
    Date: 20/10/24
    Time: 21:36
*/
public class ShopDoesNotExistsException extends RuntimeException {
  public ShopDoesNotExistsException(String id) {
    super("Shop with id " + id + " does not exists");
  }
}
