package com.uff.sem_barreiras.controller;

import com.uff.sem_barreiras.service.EstadoService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(EstadoController.class)
public class EstadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EstadoService estadoService;

    @Test
    public void verificarEndpointRecuperacaoEmpresaPorId() throws Exception {
        this.mockMvc.perform(get("/estado/1")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void verificarEndpointRecuperacaoEmpresaLista() throws Exception {
        this.mockMvc.perform(get("/estado")).andExpect(MockMvcResultMatchers.status().isOk());
    }

}

