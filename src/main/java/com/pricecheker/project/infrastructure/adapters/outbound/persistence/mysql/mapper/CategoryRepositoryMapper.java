package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.mapper;

/*
    Author: juannegrin
    Date: 19/10/24
    Time: 00:10
*/

import com.pricecheker.project.domain.entity.CategoryDomainEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryRepositoryMapper {

  CategoryDomainEntity toDomain(CategoryEntity entity);

  CategoryEntity toEntity(CategoryDomainEntity domain);
}
