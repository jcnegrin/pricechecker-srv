package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Table(name = "Categoria", schema = "PriceChecker")
@Entity
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, name = "nombre")
    private String name;

    @Column(name = "categoriaCodigo")
    private String categoryCode;

    @OneToMany(mappedBy = "category")
    private List<ProductEntity> products;

    @ManyToOne
    @JoinColumn(name = "codigoCategoria", referencedColumnName = "id")
    private CategoryEntity parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    private List<CategoryEntity> subcategories;

}
