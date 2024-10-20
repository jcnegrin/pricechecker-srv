package com.pricecheker.project.application.utils;

import java.math.BigDecimal;

/*
    Author: juannegrin
    Date: 18/10/24
    Time: 23:22
*/
public class CurrencyConverterUtils {

  public static BigDecimal convertStringToBigDecimal(String amount) {
    try {
      String cleanedAmount = amount.replace("€", "").trim();
      cleanedAmount = cleanedAmount.replace(",", ".");
      return new BigDecimal(cleanedAmount);
    } catch (Exception e) {
      return BigDecimal.ZERO;
    }
  }
}
