package com.pricecheker.project.application.services.tasks.model;

/*
    Author: juannegrin
    Date: 17/10/24
    Time: 23:21
*/

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShopProductModel {
    private String shopName;
    private List<ScrapedProduct> products;
}
