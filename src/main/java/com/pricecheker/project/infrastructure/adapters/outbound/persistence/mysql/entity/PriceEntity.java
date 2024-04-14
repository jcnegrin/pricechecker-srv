package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Table(name = "Precio", schema = "PriceChecker")
@Entity
public class PriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, name = "monto")
    private BigDecimal amount;

    @Column(nullable = false, name = "medida")
    private Integer measure;

    @Column(nullable = false, name = "unidad")
    private String unit;

    @ManyToOne
    @JoinColumn(name = "codigoProducto", referencedColumnName = "id")
    private ProductEntity product;
}
