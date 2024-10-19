package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.impl;

import com.pricecheker.project.application.ports.outbound.ProductCategoryRepositoryPort;
import com.pricecheker.project.domain.entity.CategoryDomainEntity;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.mapper.CategoryRepositoryMapper;
import com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/*
    Author: juannegrin
    Date: 19/10/24
    Time: 00:10
*/

@Component
@RequiredArgsConstructor
public class ProductCategoryRepositoryAdapter implements ProductCategoryRepositoryPort {

  private final CategoryRepository repository;
  private final CategoryRepositoryMapper mapper;

  @Override
  public List<CategoryDomainEntity> findAllCategories() {
    return repository.findAll().stream().map(mapper::toDomain).toList();
  }

  @Override
  public Optional<CategoryDomainEntity> findCategoryByName(String name) {
    return repository.findByName(name).map(mapper::toDomain);
  }

  @Override
  public CategoryDomainEntity saveCategory(CategoryDomainEntity category) {
    return mapper.toDomain(repository.save(mapper.toEntity(category)));
  }
}
