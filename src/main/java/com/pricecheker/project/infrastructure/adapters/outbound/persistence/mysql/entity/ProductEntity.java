package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "Product", schema = "PriceChecker")
@Entity
public class ProductEntity {

  @Id private String id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "brand", length = 50)
  private String brand;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "image_url", length = 255)
  private String imageUrl;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "shop_id", referencedColumnName = "id")
  private ShopEntity shop;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", referencedColumnName = "id")
  private CategoryEntity category;

  @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
  private List<PriceEntity> prices;
}
