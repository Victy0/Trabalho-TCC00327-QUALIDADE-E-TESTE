package com.uff.sem_barreiras.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.uff.sem_barreiras.service.EmpresaService;
import com.uff.sem_barreiras.service.EscolaridadeService;

@WebMvcTest(EscolaridadeController.class)
public class EscolaridadeControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
    private EscolaridadeService escolaridadeService;

    @Test
    public void verificarEndpointRecuperacaoEscolaridadePorId() throws Exception {
        this.mockMvc.perform(get("/escolaridade/1")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void verificarEndpointRecuperacaoEscolaridadeLista() throws Exception {
        this.mockMvc.perform(get("/escolaridade")).andExpect(MockMvcResultMatchers.status().isOk());
    }

}
