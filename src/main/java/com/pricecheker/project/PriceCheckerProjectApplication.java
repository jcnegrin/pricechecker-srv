package com.pricecheker.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PriceCheckerProjectApplication {

  public static void main(String[] args) {
    SpringApplication.run(PriceCheckerProjectApplication.class, args);
  }
}
