package com.pricecheker.project.application.services.tasks.scrapers;

import com.microsoft.playwright.*;
import com.pricecheker.project.application.services.tasks.interfaces.ScraperStrategy;
import com.pricecheker.project.application.services.tasks.model.ScrapedProduct;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MercadonaScraper implements ScraperStrategy {

  private static final String POSTAL_CODE = "28006";
  private final String url;

  public MercadonaScraper(String url) {
    this.url = url;
  }

  @Override
  public Map<String, List<ScrapedProduct>> scrapeProducts() {
    log.info("Starting MercadonaScraper for URL: {}", url);
    try (Playwright playwright = Playwright.create()) {
      Browser browser = createBrowser(playwright);
      Page page = navigateToPage(browser);

      acceptCookies(page);
      enterPostalCode(page);
      navigateToMainPage(page);

      Map<String, List<ScrapedProduct>> productMap = extractProductData(page);
      printProductMap(productMap);

      browser.close();
      log.info("Scraping completed for Mercadona");
      return productMap;
    } catch (Exception e) {
      log.error("Error occurred during Mercadona scraping: {}", e.getMessage(), e);
      return Map.of();
    }
  }

  private Browser createBrowser(Playwright playwright) {
    log.info("Creating browser instance...");
    return playwright
        .chromium()
        .launch(new BrowserType.LaunchOptions().setHeadless(true).setSlowMo(200));
  }

  private Page navigateToPage(Browser browser) {
    log.info("Navigating to URL: {}", this.url);
    Page page = browser.newPage();
    page.navigate(this.url);
    return page;
  }

  private void acceptCookies(Page page) {
    log.info("Accepting cookies...");
    Locator cookiesButton =
        page.locator("#root > div.cookie-banner > div > div > button:nth-child(3)");
    cookiesButton.click();
  }

  private void enterPostalCode(Page page) {
    log.info("Entering postal code: {}", POSTAL_CODE);
    page.locator("input[name='postalCode']").fill(POSTAL_CODE);
    page.locator("button[data-testid='postal-code-checker-button']").click();
  }

  private void navigateToMainPage(Page page) {
    log.info("Navigating to the main page...");
    page.locator("#root > header > div.header__left > nav > a:nth-child(1)").click();
  }

  private Map<String, List<ScrapedProduct>> extractProductData(Page page) {
    log.info("Retrieving product categories...");
    List<Locator> productCategories = page.locator("//html/body/div[1]/div[2]/div[1]/ul/li").all();
    log.info("Found {} product categories", productCategories.size());

    // TODO: Remove, just for testing purposes .sublIST(0, 1)
    // productCategories = productCategories.subList(0, 1);

    return productCategories.stream()
        .flatMap(category -> processCategory(page, category))
        .collect(Collectors.groupingBy(ScrapedProduct::getCategory));
  }

  private Stream<ScrapedProduct> processCategory(Page page, Locator category) {
    String categoryName = getLocatorText(category.locator("div > button"));
    log.info("Processing category: {}", categoryName);

    category.locator("div > button").click();
    List<Locator> subCategories = getSubCategories(category);
    log.info("Found {} subcategories in category {}", subCategories.size(), categoryName);

    return subCategories.stream()
        .flatMap(subCategory -> processSubCategory(page, categoryName, subCategory));
  }

  private Stream<ScrapedProduct> processSubCategory(
      Page page, String categoryName, Locator subCategory) {
    String subCategoryName = getLocatorText(subCategory.locator("button"));
    log.info("Processing subcategory: {}", subCategoryName);

    subCategory.locator("button").click();
    page.waitForTimeout(5000);
    List<Locator> products = getProducts(page);
    log.info("Found {} products in subcategory {}", products.size(), subCategoryName);

    List<ScrapedProduct> productList =
        products.stream()
            .flatMap(product -> processProduct(categoryName, subCategoryName, product))
            .collect(Collectors.toList());

    log.info("Finished processing subcategory: {}", subCategoryName);
    return productList.stream();
  }

  private Stream<ScrapedProduct> processProduct(
      String categoryName, String subCategoryName, Locator product) {
    List<Locator> productContainer =
        product.locator("div[data-testid='product-cell'].product-cell--actionable").all();

    return productContainer.stream()
        .map(
            productCell -> {
              Locator data =
                  productCell.locator(".product-cell__content-link > .product-cell__info");

              Locator imgData =
                  productCell.locator(
                      ".product-cell__content-link > .product-cell__image-wrapper > img");

              String name = getLocatorText(data.locator(".product-cell__description-name"));
              String price =
                  getLocatorText(data.locator(".product-price > div > .product-price__unit-price"));
              String format = getLocatorText(data.locator(".product-format"));
              String img = imgData.getAttribute("src");

              log.info("Product found - Name: {}, Price: {}, Format: {}", name, price, format);

              return ScrapedProduct.builder()
                  .id(UUID.randomUUID())
                  .name(name + " " + format)
                  .price(price)
                  .imgUrl(img)
                  .description(format)
                  .category(categoryName)
                  .subCategory(subCategoryName)
                  .build();
            });
  }

  private List<Locator> getSubCategories(Locator category) {
    return category.locator("div > ul > li").all();
  }

  private List<Locator> getProducts(Page page) {
    return page.locator("section[data-testid='section']").all();
  }

  private String getLocatorText(Locator locator) {
    return locator.innerText().trim();
  }

  private void printProductMap(Map<String, List<ScrapedProduct>> productMap) {
    log.info("Product map created:");
    productMap.forEach(
        (category, products) ->
            log.info("Category: {}, Number of products: {}", category, products.size()));
  }
}
