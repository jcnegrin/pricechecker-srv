package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "Category", schema = "PriceChecker")
@Entity
public class CategoryEntity {

  @Id private String id;

  @Column(name = "name", nullable = false, length = 50)
  private String name;

  @OneToMany(mappedBy = "category")
  private List<ProductEntity> products;
}
