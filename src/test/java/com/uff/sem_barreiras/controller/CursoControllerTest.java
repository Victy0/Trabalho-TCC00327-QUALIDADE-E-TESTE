package com.uff.sem_barreiras.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uff.sem_barreiras.model.Curso;
import com.uff.sem_barreiras.service.CursoService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(CursoController.class)
public class CursoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CursoService cursoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void encontrarCursoComSucesso() throws Exception {
        when(cursoService.encontrarCurso(anyInt())).thenReturn(new Curso());
        this.mockMvc.perform(get("/curso/1")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void alterarcursoComSucesso() throws Exception {
        when(cursoService.criarCurso(any(Curso.class))).thenReturn(new Curso());
        this.mockMvc.perform(put("/curso/alterar")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(new Curso())))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void cadastrarCursoComSucesso() throws Exception {
        when(cursoService.criarCurso(any(Curso.class))).thenReturn(new Curso());
        this.mockMvc.perform(post("/curso")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(new Curso())))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deletarCursoComSucesso() throws Exception {
        when(cursoService.encontrarCurso(anyInt())).thenReturn(new Curso());
        this.mockMvc.perform(delete("/curso/1")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void vincularVagaCursoComSucesso() throws Exception {
        when(cursoService.vingularVagas(anyInt(), anyList())).thenReturn(true);
        this.mockMvc.perform(post("/vingularVaga/1")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(new ArrayList<Integer>())))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
