package com.pricecheker.project.application.utils;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyConverterUtilsTest {

  @Test
  void testConvertStringToBigDecimal_withEuroSymbol() {
    // Caso: Convertir "100€" a BigDecimal
    BigDecimal result = CurrencyConverterUtils.convertStringToBigDecimal("100€");
    assertEquals(new BigDecimal("100"), result);
  }

  @Test
  void testConvertStringToBigDecimal_withCommaAsDecimal() {
    // Caso: Convertir "100,50" a BigDecimal
    BigDecimal result = CurrencyConverterUtils.convertStringToBigDecimal("100,50€");
    assertEquals(new BigDecimal("100.50"), result);
  }

  @Test
  void testConvertStringToBigDecimal_withoutSymbol() {
    // Caso: Convertir "200" a BigDecimal
    BigDecimal result = CurrencyConverterUtils.convertStringToBigDecimal("200");
    assertEquals(new BigDecimal("200"), result);
  }

  @Test
  void testConvertStringToBigDecimal_emptyString() {
    // Caso: Cadena vacía
    BigDecimal result = CurrencyConverterUtils.convertStringToBigDecimal("");
    assertEquals(BigDecimal.ZERO, result);
  }

  @Test
  void testConvertStringToBigDecimal_invalidString() {
    // Caso: Cadena inválida
    BigDecimal result = CurrencyConverterUtils.convertStringToBigDecimal("abc€");
    assertEquals(BigDecimal.ZERO, result);
  }

  @Test
  void testConvertStringToBigDecimal_withSpacesAndSymbol() {
    // Caso: Cadena con espacios y símbolo "€"
    BigDecimal result = CurrencyConverterUtils.convertStringToBigDecimal(" 250,75 € ");
    assertEquals(new BigDecimal("250.75"), result);
  }

  @Test
  void testConvertStringToBigDecimal_withNegativeValue() {
    // Caso: Cadena con un valor negativo
    BigDecimal result = CurrencyConverterUtils.convertStringToBigDecimal("-123,45€");
    assertEquals(new BigDecimal("-123.45"), result);
  }
}
