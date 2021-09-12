package com.uff.sem_barreiras.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uff.sem_barreiras.dto.LoginObject;
import com.uff.sem_barreiras.dto.ResponseObject;
import com.uff.sem_barreiras.exceptions.IdNullException;
import com.uff.sem_barreiras.model.Cidade;
import com.uff.sem_barreiras.service.LoginService;
@WebMvcTest(LoginController.class)
class LoginControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    public void VerificarEndpointLogin() throws Exception {
    	LoginObject login = new LoginObject();
    	
    	login.setEmail("teste@teste.com");
    	
        this.mockMvc.perform(post("/empresa/login")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(new Cidade())))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void VerificarEndpointCriacaoCidade() throws Exception {
    	LoginObject login = new LoginObject();
    	
    	login.setEmail("teste@teste.com");
    	login.setCodigo("1414");
    	
        this.mockMvc.perform(post("/empresa/login-confirma")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(new Cidade())))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void VerificarEndpointLoginSeesion() throws Exception {
    	LoginObject login = new LoginObject();
    	
    	login.setEmail("teste@teste.com");
    	
        this.mockMvc.perform(post("/empresa/session")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(new Cidade())))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void VerificarEndpointLogout() throws Exception {
    	LoginObject login = new LoginObject();
    	
    	login.setEmail("teste@teste.com");
    	
        this.mockMvc.perform(post("/empresa/logout")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(new Cidade())))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
   
    @Test
    public void VerificarEndpointLimparControlarLogin() throws Exception {
    	LoginObject login = new LoginObject();
    	
    	login.setEmail("teste@teste.com");
    	
        this.mockMvc.perform(post("/empresa/forcar/limpar-controlar-login")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(new Cidade())))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
    
    @Test
    public void VerificarEndpointLoginManual() throws Exception {
    	LoginObject login = new LoginObject();
    	
    	login.setEmail("teste@teste.com");
    	
        this.mockMvc.perform(post("/empresa/forcar/login-manual")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(new Cidade())))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
