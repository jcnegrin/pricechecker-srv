package com.pricecheker.project.application.ports.outbound;

import com.pricecheker.project.domain.entity.CategoryDomainEntity;
import java.util.List;
import java.util.Optional;

/*
    Author: juannegrin
    Date: 17/10/24
    Time: 20:51
*/
public interface ProductCategoryRepositoryPort {
  List<CategoryDomainEntity> findAllCategories();

  Optional<CategoryDomainEntity> findCategoryByName(String name);

  CategoryDomainEntity saveCategory(CategoryDomainEntity category);
}
