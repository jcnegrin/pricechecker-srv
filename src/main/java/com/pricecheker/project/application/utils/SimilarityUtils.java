package com.pricecheker.project.application.utils;

import com.pricecheker.project.domain.entity.CategoryDomainEntity;
import com.pricecheker.project.domain.entity.ProductDomainEntity;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.apache.commons.text.similarity.FuzzyScore;

/*
    Author: juannegrin
    Date: 20/10/24
    Time: 23:31
*/

public class SimilarityUtils {

  private static final FuzzyScore fuzzyScore = new FuzzyScore(Locale.ENGLISH);

  // Método para buscar categorías similares
  public static List<CategoryDomainEntity> findSimilarCategories(
      CategoryDomainEntity targetCategory, List<CategoryDomainEntity> otherCategories) {
    return otherCategories.stream()
        .map(
            category -> {
              int score = fuzzyScore.fuzzyScore(targetCategory.getName(), category.getName());
              return new SimilarCategory(category, score);
            })
        .filter(
            similarCategory -> similarCategory.getScore() >= 5) // Umbral ajustado para FuzzyScore
        .sorted(
            (c1, c2) ->
                Integer.compare(
                    c2.getScore(), c1.getScore())) // Ordenar por puntuación (mayor es mejor)
        .map(SimilarCategory::getCategory)
        .collect(Collectors.toList());
  }

  // Método para buscar productos similares
  public static List<ProductDomainEntity> findSimilarProducts(
      ProductDomainEntity targetProduct, List<ProductDomainEntity> otherProducts) {
    return otherProducts.stream()
        .map(
            product -> {
              int score = fuzzyScore.fuzzyScore(targetProduct.getName(), product.getName());
              return new SimilarProduct(product, score);
            })
        .filter(
            similarProduct -> similarProduct.getScore() >= 20) // Umbral ajustado para FuzzyScore
        .sorted(
            (p1, p2) ->
                Integer.compare(
                    p2.getScore(), p1.getScore())) // Ordenar por puntuación (mayor es mejor)
        .map(SimilarProduct::getProduct)
        .collect(Collectors.toList());
  }

  // Clases de soporte para categorías y productos similares
  private static class SimilarCategory {
    private final CategoryDomainEntity category;
    private final int score;

    public SimilarCategory(CategoryDomainEntity category, int score) {
      this.category = category;
      this.score = score;
    }

    public CategoryDomainEntity getCategory() {
      return category;
    }

    public int getScore() {
      return score;
    }
  }

  private static class SimilarProduct {
    private final ProductDomainEntity product;
    private final int score;

    public SimilarProduct(ProductDomainEntity product, int score) {
      this.product = product;
      this.score = score;
    }

    public ProductDomainEntity getProduct() {
      return product;
    }

    public int getScore() {
      return score;
    }
  }
}
