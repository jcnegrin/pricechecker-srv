package com.pricecheker.project.application.utils;

import com.pricecheker.project.domain.entity.CategoryDomainEntity;
import com.pricecheker.project.domain.entity.ProductDomainEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.text.similarity.LevenshteinDistance;

/*
    Author: juannegrin
    Date: 20/10/24
    Time: 23:31
*/

public class SimilarityUtils {

  private static final LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

  // Método para buscar categorías similares
  public static List<CategoryDomainEntity> findSimilarCategories(
      CategoryDomainEntity targetCategory, List<CategoryDomainEntity> otherCategories) {
    return otherCategories.stream()
        .map(
            category -> {
              int distance =
                  levenshteinDistance.apply(targetCategory.getName(), category.getName());
              return new SimilarCategory(category, distance);
            })
        .filter(
            similarCategory -> similarCategory.getDistance() <= 10) // Umbral de 5 para categorías
        .sorted(
            (c1, c2) ->
                Integer.compare(c1.getDistance(), c2.getDistance())) // Ordenar por similitud
        .map(SimilarCategory::getCategory)
        .collect(Collectors.toList());
  }

  // Método para buscar productos similares
  public static List<ProductDomainEntity> findSimilarProducts(
      ProductDomainEntity targetProduct, List<ProductDomainEntity> otherProducts) {
    return otherProducts.stream()
        .map(
            product -> {
              int distance = levenshteinDistance.apply(targetProduct.getName(), product.getName());
              return new SimilarProduct(product, distance);
            })
        .filter(similarProduct -> similarProduct.getDistance() <= 10) // Umbral de 10 para productos
        .sorted(
            (p1, p2) ->
                Integer.compare(p1.getDistance(), p2.getDistance())) // Ordenar por similitud
        .map(SimilarProduct::getProduct)
        .collect(Collectors.toList());
  }

  // Clases de soporte para categorías y productos similares
  private static class SimilarCategory {
    private final CategoryDomainEntity category;
    private final int distance;

    public SimilarCategory(CategoryDomainEntity category, int distance) {
      this.category = category;
      this.distance = distance;
    }

    public CategoryDomainEntity getCategory() {
      return category;
    }

    public int getDistance() {
      return distance;
    }
  }

  private static class SimilarProduct {
    private final ProductDomainEntity product;
    private final int distance;

    public SimilarProduct(ProductDomainEntity product, int distance) {
      this.product = product;
      this.distance = distance;
    }

    public ProductDomainEntity getProduct() {
      return product;
    }

    public int getDistance() {
      return distance;
    }
  }
}
