package com.pricecheker.project.infrastructure.configuration.webscraping;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeleniumConfig {

  @Bean
  public WebDriver webDriver() {
    return new ChromeDriver();
  }
}
