package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "Producto", schema = "PriceChecker")
@Entity
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, name = "nombre")
    private String name;

    @Column(nullable = false, name = "descripcion")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigoTienda", referencedColumnName = "id")
    private StoreEntity store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigoCategoria", referencedColumnName = "id")
    private CategoryEntity category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<PriceEntity> prices;
}
