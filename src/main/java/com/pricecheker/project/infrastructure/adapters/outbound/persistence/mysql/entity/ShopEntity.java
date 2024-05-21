package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Shop", schema = "PriceChecker")
public class ShopEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "url", length = 255)
  private String url;

  @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
  private List<ProductEntity> products = new ArrayList<>();
}
