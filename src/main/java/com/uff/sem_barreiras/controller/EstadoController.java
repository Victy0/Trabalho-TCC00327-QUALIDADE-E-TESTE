package com.uff.sem_barreiras.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.uff.sem_barreiras.exceptions.IdNullException;
import com.uff.sem_barreiras.exceptions.NotFoundException;
import com.uff.sem_barreiras.filter.DefaultFilter;
import com.uff.sem_barreiras.model.Estado;
import com.uff.sem_barreiras.service.EstadoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;

@RestController
public class EstadoController {
    
    // mapeamento Get para listar todos os estados
    @GetMapping("/estado")
    public Page<Estado> listarEstados( @PageableDefault( size = 26, sort = "uf" ) final Pageable page, final HttpServletRequest request )
    {
        Specification<Estado> spec = new DefaultFilter<Estado>( new HashMap<String, String[]>(request.getParameterMap() ) );

        return this.estadoService.listarEstados(spec, page);
    }

    //mapeamento Get para recuperar 1 estado informando o id do mesmo
    @GetMapping("/estado/{id}")
    public Estado encontrarEstado(@PathVariable( value = "id" ) final Integer id) throws NotFoundException, IdNullException
    {
        return this.estadoService.encontrarEstado(id);
    }

    @Autowired
    private EstadoService estadoService;
}
