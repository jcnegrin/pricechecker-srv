package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Tienda", schema = "PriceChecker")
public class StoreEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, name = "nombre")
    private String name;

    @Column(nullable = false)
    private String url;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    private List<ProductEntity> products = new ArrayList<>();

}
