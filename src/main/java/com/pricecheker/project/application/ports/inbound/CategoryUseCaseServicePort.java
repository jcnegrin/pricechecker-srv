package com.pricecheker.project.application.ports.inbound;

/*
    Author: juannegrin
    Date: 20/10/24
    Time: 21:38
*/

import com.pricecheker.project.domain.entity.CategoryDomainEntity;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CategoryUseCaseServicePort {
  CategoryDomainEntity getCategoryById(@NotEmpty String id);
}
