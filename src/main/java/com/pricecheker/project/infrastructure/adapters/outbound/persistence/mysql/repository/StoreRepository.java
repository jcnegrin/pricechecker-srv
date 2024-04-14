package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.repository;

import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, String> {

    StoreEntity findByName(String name);

}
