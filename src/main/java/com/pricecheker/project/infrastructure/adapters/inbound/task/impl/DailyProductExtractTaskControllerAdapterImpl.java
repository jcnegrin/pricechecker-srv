package com.pricecheker.project.infrastructure.adapters.inbound.task.impl;

import com.pricecheker.project.application.ports.inbound.DailyProductExtractTaskUseCasePort;
import com.pricecheker.project.infrastructure.adapters.inbound.task.DailyProductExtractTaskControllerAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*
    Author: juannegrin
    Date: 17/10/24
    Time: 20:28
*/

@Component
@RequiredArgsConstructor
@Slf4j
public class DailyProductExtractTaskControllerAdapterImpl
    implements DailyProductExtractTaskControllerAdapter {

  private final DailyProductExtractTaskUseCasePort service;

  @Override
  @Scheduled(cron = "${scheduling.cron.dailyProductExtract}", zone = "${scheduling.zone}")
  public void execute() {}
}
