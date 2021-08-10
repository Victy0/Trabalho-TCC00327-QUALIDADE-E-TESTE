package com.uff.sem_barreiras.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.uff.sem_barreiras.exceptions.IdNullException;
import com.uff.sem_barreiras.exceptions.NotFoundException;
import com.uff.sem_barreiras.filter.DefaultFilter;
import com.uff.sem_barreiras.model.Escolaridade;
import com.uff.sem_barreiras.service.EscolaridadeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EscolaridadeController {

    // mapeamento Get para listar todos as escolaridades
    @GetMapping("/escolaridade")
    public Page<Escolaridade> listarEscolaridades( @PageableDefault( size = 30, sort = "descricao" ) final Pageable page, final HttpServletRequest request )
    { 
        Specification<Escolaridade> spec =  new DefaultFilter<Escolaridade>( new HashMap<String, String[]>(request.getParameterMap() ) );

        return this.escolaridadeService.listarEscolaridades(spec, page);
    } 
    
    //mapeamento Get para recuperar 1 estado informando o id do mesmo
    @GetMapping("/escolaridade/{id}")
    public Escolaridade encontrarEscolaridade(@PathVariable( value = "id" ) final Integer id) throws NotFoundException , IdNullException 
    {
        return this.escolaridadeService.encontrarEscolaridade(id);
    }
    
    @Autowired
    private EscolaridadeService escolaridadeService ;
}
