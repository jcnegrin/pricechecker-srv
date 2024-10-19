package com.pricecheker.project.infrastructure.adapters.inbound.rest.controller;

/*
    Author: juannegrin
    Date: 18/10/24
    Time: 12:13
*/

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Daily Extraction Task Rest Controller API")
public interface DailyExtractionTaskRestControllerAdapter {

  @Operation(
      description = "Execute daily extraction task",
      summary = "Execute daily extraction task")
  @GetMapping
  void execute();
}
