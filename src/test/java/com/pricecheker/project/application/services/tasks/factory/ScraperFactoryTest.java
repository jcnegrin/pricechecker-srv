package com.pricecheker.project.application.services.tasks.factory;

import static org.junit.jupiter.api.Assertions.*;

import com.pricecheker.project.application.services.tasks.interfaces.ScraperStrategy;
import com.pricecheker.project.application.services.tasks.scrapers.DiaScraper;
import com.pricecheker.project.application.services.tasks.scrapers.MercadonaScraper;
import org.junit.jupiter.api.Test;

class ScraperFactoryTest {

  @Test
  void getScraper_returnsMercadonaScraper() {
    ScraperStrategy scraper = ScraperFactory.getScraper("MERCADONA", "http://mercadona.com");
    assertTrue(scraper instanceof MercadonaScraper);
  }

  @Test
  void getScraper_returnsDiaScraper() {
    ScraperStrategy scraper = ScraperFactory.getScraper("DIA", "http://dia.com");
    assertTrue(scraper instanceof DiaScraper);
  }

  @Test
  void getScraper_throwsExceptionForInvalidStore() {
    assertThrows(
        IllegalArgumentException.class,
        () -> ScraperFactory.getScraper("INVALID", "http://invalid.com"));
  }

  @Test
  void getScraper_throwsExceptionForNullStore() {
    assertThrows(
        NullPointerException.class, () -> ScraperFactory.getScraper(null, "http://null.com"));
  }

  @Test
  void getScraper_throwsExceptionForEmptyStore() {
    assertThrows(
        IllegalArgumentException.class, () -> ScraperFactory.getScraper("", "http://empty.com"));
  }
}
