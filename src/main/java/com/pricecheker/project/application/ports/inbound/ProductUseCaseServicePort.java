package com.pricecheker.project.application.ports.inbound;

import com.pricecheker.project.domain.view.ProductView;
import java.util.List;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ProductUseCaseServicePort {
  List<ProductView> getProductsByShopIdAndCategory(String shopId, String categoryId);

  ProductView getProductById(String productId);

  List<ProductView> getSimilarProducts(String productId);
}
