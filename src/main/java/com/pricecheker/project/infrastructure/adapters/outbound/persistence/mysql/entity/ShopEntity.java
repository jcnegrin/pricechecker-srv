package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Shop", schema = "PriceChecker")
public class ShopEntity implements Serializable {

  @Id private String id;

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "url", length = 255)
  private String url;

  @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
  private List<ProductEntity> products = new ArrayList<>();
}
