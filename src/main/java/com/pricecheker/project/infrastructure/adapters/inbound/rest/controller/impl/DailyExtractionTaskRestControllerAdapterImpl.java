package com.pricecheker.project.infrastructure.adapters.inbound.rest.controller.impl;

import com.pricecheker.project.application.ports.inbound.DailyProductExtractTaskUseCasePort;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.controller.DailyExtractionTaskRestControllerAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    Author: juannegrin
    Date: 18/10/24
    Time: 12:18
*/

@RestController
@RequestMapping("/daily-extraction-task")
@RequiredArgsConstructor
@Slf4j
public class DailyExtractionTaskRestControllerAdapterImpl
    implements DailyExtractionTaskRestControllerAdapter {

  private final DailyProductExtractTaskUseCasePort service;

  @Override
  public void execute() {
    log.info("[DailyExtractionTaskRestControllerAdapterImpl] - Task started");
    service.executeDailyTask();
    log.info("[DailyExtractionTaskRestControllerAdapterImpl] - Task finished");
  }
}
