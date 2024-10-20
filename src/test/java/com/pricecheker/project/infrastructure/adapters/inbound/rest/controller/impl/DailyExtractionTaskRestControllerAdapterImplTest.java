package com.pricecheker.project.infrastructure.adapters.inbound.rest.controller.impl;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pricecheker.project.application.ports.inbound.DailyProductExtractTaskUseCasePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DailyExtractionTaskRestControllerAdapterImpl.class)
class DailyExtractionTaskRestControllerAdapterImplTest {

  @MockBean private DailyProductExtractTaskUseCasePort service;

  @Autowired private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testExecuteDailyExtractionTask() throws Exception {
    // Simulamos el comportamiento del servicio sin realizar ninguna acción real
    doNothing().when(service).executeDailyTask();

    // Realizamos una solicitud POST al endpoint del controlador
    mockMvc
        .perform(get("/daily-extraction-task"))
        .andExpect(status().isOk()); // Verificamos que el estado HTTP es 200 OK

    // Verificamos que el método executeDailyTask del servicio fue llamado exactamente una vez
    verify(service, times(1)).executeDailyTask();
  }
}
