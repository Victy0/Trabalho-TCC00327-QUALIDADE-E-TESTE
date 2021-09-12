package com.uff.sem_barreiras.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uff.sem_barreiras.model.Cidade;
import com.uff.sem_barreiras.service.CidadeService;

@WebMvcTest(CidadeController.class)
class CidadeControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private CidadeService cidadeService;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void verificarEndpointRecuperacaoCidadePorId() throws Exception {
        this.mockMvc.perform(get("/cidade/1")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void verificarEndpointRecuperacaoCidadeLista() throws Exception {
        this.mockMvc.perform(get("/cidade")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void VerificarEndpointCriacaoCidade() throws Exception {
        this.mockMvc.perform(post("/cidade")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(new Cidade())))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void VerificarEndpointAlteracaoCidade() throws Exception {
        this.mockMvc.perform(put("/cidade/alterar")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(new Cidade())))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void verificarEndpointDeleteCidadePorId() throws Exception {
        this.mockMvc.perform(delete("/cidade/1")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    

}
