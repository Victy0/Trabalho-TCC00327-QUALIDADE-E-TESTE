package com.uff.sem_barreiras.controller;

import com.uff.sem_barreiras.service.EmpresaService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(EmpresaController.class)
public class EmpresaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpresaService empresaService;

    @Test
    public void verificarEndpointRecuperacaoEmpresaPorId() throws Exception {
        this.mockMvc.perform(get("/empresa/1")).andExpect(MockMvcResultMatchers.status().isOk());
    }

}
