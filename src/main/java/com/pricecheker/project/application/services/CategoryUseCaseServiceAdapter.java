package com.pricecheker.project.application.services;

import com.pricecheker.project.application.ports.inbound.CategoryUseCaseServicePort;
import com.pricecheker.project.application.ports.outbound.ProductCategoryRepositoryPort;
import com.pricecheker.project.domain.entity.CategoryDomainEntity;
import com.pricecheker.project.domain.exception.ShopDoesNotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/*
    Author: juannegrin
    Date: 20/10/24
    Time: 21:39
*/

@Service
@RequiredArgsConstructor
public class CategoryUseCaseServiceAdapter implements CategoryUseCaseServicePort {

  private final ProductCategoryRepositoryPort repositoryPort;

  @Override
  public CategoryDomainEntity getCategoryById(String id) {
    return repositoryPort
        .findCategoryById(id)
        .orElseThrow(() -> new ShopDoesNotExistsException(id));
  }
}
