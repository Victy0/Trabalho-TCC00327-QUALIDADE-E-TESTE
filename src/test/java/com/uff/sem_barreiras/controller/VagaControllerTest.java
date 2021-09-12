package com.uff.sem_barreiras.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uff.sem_barreiras.model.Vaga;
import com.uff.sem_barreiras.service.VagaService;

@WebMvcTest(VagaController.class)
class VagaControllerTest {
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private VagaService vagaService;
    
    @Autowired
    private ObjectMapper objectMapper;


	 @Test
	    public void verificarEndpointRecuperacaoVagaPorId() throws Exception {
	        this.mockMvc.perform(get("/vaga/1")).andExpect(MockMvcResultMatchers.status().isOk());
	    }
	    
	    @Test
	    public void verificarEndpointRecuperacaoVagaLista() throws Exception {
	        this.mockMvc.perform(get("/vaga")).andExpect(MockMvcResultMatchers.status().isOk());
	    }
	    
	    @Test
	    public void VerificarEndpointCriacaoVaga() throws Exception {
	        this.mockMvc.perform(post("/vaga")
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(new Vaga())))
	            .andExpect(MockMvcResultMatchers.status().isOk());
	    }
	   
	    @Test
	    public void VerificarEndpointAlteracaoVaga() throws Exception {
	        this.mockMvc.perform(put("/vaga/alterar")
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(new Vaga())))
	            .andExpect(MockMvcResultMatchers.status().isOk());
	    }
	    
	    @Test
	    public void verificarEndpointDeleteVagaPorId() throws Exception {
	        this.mockMvc.perform(delete("/vaga/1")).andExpect(MockMvcResultMatchers.status().isOk());
	    }
	    
	    @Test
	    public void VerificarEndpointCandidatarVaga() throws Exception {
	        this.mockMvc.perform(post("/vaga/candidatar/1")
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(new Vaga())))
	            .andExpect(MockMvcResultMatchers.status().isOk());
	    }
	    
	    @Test
	    public void VerificarEndpointDeletarPassado() throws Exception {
	        this.mockMvc.perform(post("/vaga/forcar/deletar-passado")).andExpect(MockMvcResultMatchers.status().isOk());
	    }
	    
	    @Test
	    public void VerificarEndpointNotificarDelete() throws Exception {
	        this.mockMvc.perform(post("/vaga/forcar/notificar-delete")).andExpect(MockMvcResultMatchers.status().isOk());
	    }

}
