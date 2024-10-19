package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "product")
@Entity
public class ProductEntity {

  @Id
  @Column(name = "id", nullable = false, length = 36)
  private String id;

  @Column(name = "name", nullable = false, length = 255)
  private String name;

  @Column(name = "brand", length = 50)
  private String brand;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "image_url", length = 255)
  private String imageUrl;

  @ManyToOne
  @JoinColumn(name = "shop_id", foreignKey = @ForeignKey(name = "fk_product_shop"))
  private ShopEntity shop;

  @ManyToOne
  @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_product_category"))
  private CategoryEntity category;
}
