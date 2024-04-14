package com.pricecheker.project.domain.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindAllProductsQuery {
    private String categoryId;
    private String storeId;
}
