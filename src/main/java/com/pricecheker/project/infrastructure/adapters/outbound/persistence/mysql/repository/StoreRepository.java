package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.repository;


import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<ShopEntity, String> {

    ShopEntity findByName(String name);

}
