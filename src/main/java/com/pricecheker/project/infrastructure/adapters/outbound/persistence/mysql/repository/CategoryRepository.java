package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.repository;

import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity.CategoryEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {
  Optional<CategoryEntity> findByName(String name);
}
