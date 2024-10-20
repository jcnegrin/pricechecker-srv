package com.pricecheker.project.application.services.tasks.scrapers;

import com.microsoft.playwright.*;
import com.pricecheker.project.application.services.tasks.interfaces.ScraperStrategy;
import com.pricecheker.project.application.services.tasks.model.ScrapedProduct;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DiaScraper implements ScraperStrategy {

  private final String url;

  public DiaScraper(String url) {
    this.url = url;
  }

  @Override
  public Map<String, List<ScrapedProduct>> scrapeProducts() {
    log.info("Starting DiaScraper for URL: {}", url);
    try (Playwright playwright = Playwright.create()) {
      Browser browser = createBrowser(playwright);
      BrowserContext context =
          browser.newContext(
              new Browser.NewContextOptions()
                  .setExtraHTTPHeaders(
                      Map.of(
                          "User-Agent",
                          "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36")));
      Page page = navigateToPage(context);

      acceptCookies(page);
      openProductMenu(page);

      List<Locator> categories = getCategories(page);
      log.info("Found {} categories to process.", categories.size());

      // TODO: Remove, just for testing purposes .sublIST(0, 1)

      Map<String, List<ScrapedProduct>> productMap =
          processCategories(page, categories.subList(0, 1));

      browser.close();
      log.info("DiaScraper finished successfully.");

      return productMap;

    } catch (Exception e) {
      log.error("Error during DiaScraper execution: {}", e.getMessage(), e);
      return Map.of();
    }
  }

  private Browser createBrowser(Playwright playwright) {
    log.info("Creating browser instance...");
    return playwright
        .chromium()
        .launch(
            new BrowserType.LaunchOptions()
                .setHeadless(true)
                .setArgs(List.of("--disable-blink-features=AutomationControlled"))
                .setSlowMo(200));
  }

  private Page navigateToPage(BrowserContext context) {
    log.info("Navigating to the main page: {}", url);
    Page page = context.newPage();
    page.navigate(url);
    return page;
  }

  private void acceptCookies(Page page) {
    log.info("Accepting cookies...");
    page.locator("#onetrust-accept-btn-handler").click();
  }

  private void openProductMenu(Page page) {
    log.info("Opening product menu...");
    page.locator("[data-test-id=\"desktop-category-button\"]").click();
  }

  private List<Locator> getCategories(Page page) {
    log.info("Retrieving categories...");
    page.waitForSelector("ul[data-test-id=\"categories-list\"]");
    return page.locator("li[data-test-id=\"categories-list-element\"]").all();
  }

  private Map<String, List<ScrapedProduct>> processCategories(Page page, List<Locator> categories) {
    Map<String, List<ScrapedProduct>> productMap = new HashMap<>();

    for (Locator category : categories) {

      try {
        String categoryName = category.textContent();
        log.info("Processing category: {}", categoryName);

        String categoryURL =
            category.locator("a[data-test-id=\"category-item-link\"]").getAttribute("href");
        category.locator("a[data-test-id=\"category-item-link\"]").click();
        page.waitForURL(Pattern.compile(".*" + categoryURL + ".*"));

        List<Locator> subCategories = getSubCategories(page);
        log.info("Found {} subcategories in category: {}", subCategories.size(), categoryName);

        processSubCategories(
            page, categoryName, subCategories.subList(1, subCategories.size()), productMap);
      } catch (Exception e) {
        log.error("Error processing category: {}", e.getMessage(), e);
      }
    }
    return productMap;
  }

  private List<Locator> getSubCategories(Page page) {
    log.info("Retrieving subcategories...");
    try {
      page.waitForSelector("ul[data-test-id=\"sub-categories-list\"]");
      return page.locator("div[data-test-id=\"sub-category-item\"]").all();
    } catch (Exception e) {
      log.warn("No subcategories found, skipping...");
      return List.of(); // Return an empty list if no subcategories found
    }
  }

  private void processSubCategories(
      Page page,
      String categoryName,
      List<Locator> subCategories,
      Map<String, List<ScrapedProduct>> productMap) {
    for (Locator subCategory : subCategories) {
      try {
        String subCategoryName = subCategory.textContent();
        log.info("Processing subcategory: {}", subCategoryName);

        subCategory.click();
        page.waitForTimeout(4000); // Wait for products to load
        scrollToEndOfPage(page);

        List<Locator> products = getProducts(page);
        log.info("Found {} products in subcategory: {}", products.size(), subCategoryName);

        List<ScrapedProduct> productList = extractProducts(products, categoryName, subCategoryName);
        productMap.put(categoryName, productList);

      } catch (Exception e) {
        log.error("Error processing subcategory: {}, {}", subCategory, categoryName);
      }
    }
  }

  private void scrollToEndOfPage(Page page) {
    log.info("Scrolling to the bottom of the page...");
    double previousHeight = -1;

    while (true) {
      int currentHeight = (int) page.evaluate("document.body.scrollHeight");
      if (previousHeight >= currentHeight) {
        log.info("Reached the end of the page.");
        break;
      }
      page.evaluate("window.scrollTo(0," + previousHeight + ");");
      page.waitForTimeout(5000);
      previousHeight += 1000;
    }
  }

  private List<Locator> getProducts(Page page) {
    return page.locator("li[data-test-id=\"product-card-list-item\"]").all();
  }

  private List<ScrapedProduct> extractProducts(
      List<Locator> productLocators, String category, String subCategory) {
    return productLocators.stream()
        .map(
            productLocator -> {
              try {
                String productName =
                    getTextFromLocator(
                        productLocator, "a[data-test-id=\"search-product-card-name\"]", "N/A");
                String productPrice =
                    getTextFromLocator(
                        productLocator,
                        "p[data-test-id=\"search-product-card-unit-price\"]",
                        "N/A");
                String productImgUrl =
                    productLocator
                        .locator("img[data-test-id=\"search-product-card-image\"]")
                        .getAttribute("src");

                log.info("Product found - Name: {}, Price: {}", productName, productPrice);

                return ScrapedProduct.builder()
                    .id(UUID.randomUUID())
                    .name(productName)
                    .price(productPrice)
                    .imgUrl(productImgUrl)
                    .description("N/A")
                    .category(category)
                    .subCategory(subCategory)
                    .build();
              } catch (Exception e) {
                log.error("Error extracting product: {}", e.getMessage());
                return null;
              }
            })
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  private String getTextFromLocator(Locator locator, String selector, String defaultValue) {
    try {
      return locator.locator(selector).textContent().trim();
    } catch (Exception e) {
      log.warn(
          "Failed to get text for selector: {}, returning default value: {}",
          selector,
          defaultValue);
      return defaultValue;
    }
  }
}
