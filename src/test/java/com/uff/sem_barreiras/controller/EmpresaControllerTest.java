package com.uff.sem_barreiras.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uff.sem_barreiras.model.Empresa;
import com.uff.sem_barreiras.service.EmpresaService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest(EmpresaController.class)
public class EmpresaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpresaService empresaService;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void verificarEndpointRecuperacaoEmpresaPorId() throws Exception {
        this.mockMvc.perform(get("/empresa/1")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void verificarEndpointRecuperacaoCidadeLista() throws Exception {
        this.mockMvc.perform(get("/empresa")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void VerificarEndpointCriacaoCidade() throws Exception {
        this.mockMvc.perform(post("/empresa")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(new Empresa())))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void VerificarEndpointAlteracaoCidade() throws Exception {
        this.mockMvc.perform(put("/empresa/alterar")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(new Empresa())))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void verificarEndpointDeleteCidadePorId() throws Exception {
        this.mockMvc.perform(delete("/empresa/1")).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
