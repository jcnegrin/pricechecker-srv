package com.pricecheker.project.domain.exception;

/*
    Author: juannegrin
    Date: 20/10/24
    Time: 21:40
*/
public class CategoryDoesNotExistsException extends RuntimeException {
  public CategoryDoesNotExistsException(String id) {
    super("Category with id " + id + " does not exists");
  }
}
