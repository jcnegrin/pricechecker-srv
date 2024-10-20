package com.pricecheker.project.application.services.tasks.model;

/*
    Author: juannegrin
    Date: 17/10/24
    Time: 21:42
*/

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ScrapedProduct {
  private final UUID id;
  private final String name;
  private final String price;
  private final String imgUrl;
  private final String description;
  private final String category;
  private final String subCategory;
}
