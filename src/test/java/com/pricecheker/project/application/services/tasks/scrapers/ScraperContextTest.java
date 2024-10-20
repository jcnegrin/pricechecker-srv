package com.pricecheker.project.application.services.tasks.scrapers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.pricecheker.project.application.services.tasks.interfaces.ScraperStrategy;
import com.pricecheker.project.application.services.tasks.model.ScrapedProduct;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ScraperContextTest {

  @Mock private ScraperStrategy scraperStrategy;

  @InjectMocks private ScraperContext scraperContext;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void scrapeProducts_returnsEmptyMapWhenNoProducts() {
    when(scraperStrategy.scrapeProducts()).thenReturn(Collections.emptyMap());

    Map<String, List<ScrapedProduct>> result = scraperContext.scrapeProducts();

    assertTrue(result.isEmpty());
  }

  @Test
  void scrapeProducts_handlesNullReturn() {
    when(scraperStrategy.scrapeProducts()).thenReturn(null);

    Map<String, List<ScrapedProduct>> result = scraperContext.scrapeProducts();

    assertNull(result);
  }
}
