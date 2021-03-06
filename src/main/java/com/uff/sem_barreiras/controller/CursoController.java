package com.uff.sem_barreiras.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.uff.sem_barreiras.dto.ResponseObject;
import com.uff.sem_barreiras.exceptions.IdNullException;
import com.uff.sem_barreiras.exceptions.InsertException;
import com.uff.sem_barreiras.exceptions.NotFoundException;
import com.uff.sem_barreiras.filter.DefaultFilter;
import com.uff.sem_barreiras.model.Curso;
import com.uff.sem_barreiras.service.CursoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CursoController {
    @GetMapping("/curso")
    public Page<Curso> listarCursos( @PageableDefault( size = 50, sort = "nome" ) final Pageable page, final HttpServletRequest request )
    { 
        Specification<Curso> spec = new DefaultFilter<Curso>( new HashMap<String, String[]>(request.getParameterMap() ) );
        
        return this.cursoService.listarCursos(spec, page);
    }

    @PostMapping("/curso")
    public Curso cadastrarCurso(@RequestBody  final Curso curso)throws InsertException
    {
        return this.cursoService.criarCurso(curso);
    }

    @GetMapping("/curso/{id}")
    public Curso encontrarCurso(@PathVariable( value = "id" ) final Integer id)throws NotFoundException
    {
        return this.cursoService.encontrarCurso(id);
    }
    
    @DeleteMapping("/curso/{id}")
    public ResponseObject deletarCurso(@PathVariable(value = "id")Integer id) throws NotFoundException
    {
        this.cursoService.deletarCurso(id);
        return new ResponseObject(true, "Curso removido com sucesso");
    }

    @PutMapping("/curso/alterar")
    public Curso alterarcurso(@RequestBody final Curso curso) throws NotFoundException, IdNullException, InsertException 
    {
        return this.cursoService.alterarCurso(curso);
    }
    
    @PostMapping("/vingularVaga/{cursoId}")
    public boolean vincularVagaCurso( @PathVariable( value = "cursoId") Integer cursoId, @RequestBody final List<Integer> idVagaList) throws InsertException
    {
        return this.cursoService.vingularVagas(cursoId, idVagaList);
    }

    @Autowired
    private CursoService cursoService;
}
