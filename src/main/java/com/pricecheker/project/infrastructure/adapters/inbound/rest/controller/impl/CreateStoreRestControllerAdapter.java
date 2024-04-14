package com.pricecheker.project.infrastructure.adapters.inbound.rest.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pricecheker.project.application.ports.inbound.CreateStoreUseCasePort;
import com.pricecheker.project.domain.dto.CreateStoreDto;
import com.pricecheker.project.domain.entity.StoreDomainEntity;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.controller.CreateStoreRestControllerPort;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.mapper.CreateStoreInboundMapper;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.request.CreateStoreRequest;
import com.pricecheker.project.infrastructure.adapters.inbound.rest.response.CreateStoreResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
@Slf4j
public class CreateStoreRestControllerAdapter implements CreateStoreRestControllerPort {

    @Autowired
    private final CreateStoreUseCasePort createStoreUseCasePort;
    @Autowired
    private final CreateStoreInboundMapper mapper;
    @Autowired
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public ResponseEntity<CreateStoreResponse> createStore(CreateStoreRequest request) {

        log.info("[start] CreateStoreRestControllerAdapter.createStore - request: {}",
               objectMapper.writeValueAsString(request));

        StoreDomainEntity createdStore = createStoreUseCasePort.createStore(mapper.toDto(request));

        return ResponseEntity.ok(this.mapper.toResponse(createdStore));
    }
}
