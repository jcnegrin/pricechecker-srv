package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.repository;

import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity.PriceEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity.ProductEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<PriceEntity, String> {
  Optional<PriceEntity> findFirstByProductOrderByUpdateDateDesc(ProductEntity product);
}
