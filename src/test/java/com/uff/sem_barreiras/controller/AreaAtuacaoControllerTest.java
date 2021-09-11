package com.uff.sem_barreiras.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uff.sem_barreiras.model.AreaAtuacao;
import com.uff.sem_barreiras.service.AreaAtuacaoService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(AreaAtuacaoController.class)
public class AreaAtuacaoControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private AreaAtuacaoService areaAtuacaoService;

    @Autowired
    private ObjectMapper objectMapper;

	@Test
	void alterarAreaAtuacaoComSucesso() throws Exception {
		when(areaAtuacaoService.criarAreaAtuacao(any(AreaAtuacao.class))).thenReturn(new AreaAtuacao());
        this.mockMvc.perform(put("/area/alterar")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(new AreaAtuacao())))
            .andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void cadastrarAreaAtuacaoComSucesso() throws Exception {
		when(areaAtuacaoService.criarAreaAtuacao(any(AreaAtuacao.class))).thenReturn(new AreaAtuacao());
        this.mockMvc.perform(post("/area")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(new AreaAtuacao())))
            .andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void deletarAreaAtuacaoComSucesso() throws Exception {
		when(areaAtuacaoService.encontrarAreaAtuacao(anyInt())).thenReturn(new AreaAtuacao());
        this.mockMvc.perform(delete("/area/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void encontrarAreaComSucesso() throws Exception {
		when(areaAtuacaoService.encontrarAreaAtuacao(anyInt())).thenReturn(new AreaAtuacao());
        this.mockMvc.perform(get("/area/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}
}
