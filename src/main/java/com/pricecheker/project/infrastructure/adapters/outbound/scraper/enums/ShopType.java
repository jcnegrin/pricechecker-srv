package com.pricecheker.project.infrastructure.adapters.outbound.scraper.enums;

public enum ShopType {
  MERCADONA("Mercadona"),
  DIA("Dia");

  private final String storeName;

  ShopType(String storeName) {
    this.storeName = storeName;
  }

  public static ShopType fromString(String text) {
    for (ShopType b : ShopType.values()) {
      if (b.storeName.equalsIgnoreCase(text)) {
        return b;
      }
    }
    throw new IllegalArgumentException("No constant with text " + text + " found");
  }
}
