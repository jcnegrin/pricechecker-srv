package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "Price", schema = "PriceChecker")
@Entity
public class PriceEntity {

  @Id private String id;

  @Column(precision = 10, scale = 2)
  private BigDecimal amount;

  @Column(name = "update_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime updateDate;

  @ManyToOne
  @JoinColumn(name = "product_id", referencedColumnName = "id")
  private ProductEntity product;

  @ManyToOne
  @JoinColumn(name = "shop_id", referencedColumnName = "id")
  private ShopEntity shop;
}
