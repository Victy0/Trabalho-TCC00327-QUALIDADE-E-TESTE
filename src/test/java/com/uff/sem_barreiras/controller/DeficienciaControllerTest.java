package com.uff.sem_barreiras.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.uff.sem_barreiras.service.DeficienciaService;


@WebMvcTest(DeficienciaController.class)
class DeficienciaControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
    private DeficienciaService   deficienciaService;

    @Test
    public void verificarEndpointRecuperacaoDeficienciaPorId() throws Exception {
        this.mockMvc.perform(get("/deficiencia/1")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void verificarEndpointRecuperacaoEscolaridadeLista() throws Exception {
        this.mockMvc.perform(get("/deficiencia")).andExpect(MockMvcResultMatchers.status().isOk());
    }

}
