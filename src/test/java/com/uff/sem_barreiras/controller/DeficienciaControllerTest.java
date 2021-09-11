package com.uff.sem_barreiras.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uff.sem_barreiras.model.Deficiencia;
import com.uff.sem_barreiras.service.DeficienciaService;


@WebMvcTest(DeficienciaController.class)
class DeficienciaControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
    private ObjectMapper objectMapper;

	
	@MockBean
    private DeficienciaService   deficienciaService;

    @Test
    public void verificarEndpointRecuperacaoDeficienciaPorId() throws Exception {
        this.mockMvc.perform(get("/deficiencia/1")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void verificarEndpointRecuperacaoDeficienciaLista() throws Exception {
        this.mockMvc.perform(get("/deficiencia")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void verificarEndpointCriacaoDeficiencia() throws Exception {
    	String  deficiencia = "baixa visao";
    	
    	Deficiencia def =  new Deficiencia();
    	
    	def.setDescricao(deficiencia);
    	def.setNecessidadePisoTatil(true);
    	def.setNecessidadeRampa(false);
    	def.setNecessidadeSonora(true);
		
    	this.mockMvc.perform(post("/deficiencia")
    	        .contentType("application/json")
    	        .content(objectMapper.writeValueAsString(def)))
    	        .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
