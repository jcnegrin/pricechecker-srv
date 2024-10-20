package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.repository;

import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity.ProductEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity.ShopEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {

  Optional<ProductEntity> findByNameAndShop(String name, ShopEntity shop);

  @Query("SELECT DISTINCT p.category.id FROM ProductEntity p WHERE p.shop.id = :shopId")
  List<String> findCategoriesByShopId(@Param(value = "shopId") String shopId);
}
