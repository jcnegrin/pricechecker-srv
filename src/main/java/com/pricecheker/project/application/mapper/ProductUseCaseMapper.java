package com.pricecheker.project.application.mapper;

/*
    Author: juannegrin
    Date: 20/10/24
    Time: 22:01
*/

import com.pricecheker.project.domain.entity.ProductDomainEntity;
import com.pricecheker.project.domain.view.ProductView;
import org.mapstruct.Mapper;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface ProductUseCaseMapper {

  ProductView toView(ProductDomainEntity productDomainEntity);

  ProductView toView(ProductDomainEntity productDomainEntity, BigDecimal price);
}
