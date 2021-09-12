package com.uff.sem_barreiras.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.uff.sem_barreiras.dto.CandidatoDados;
import com.uff.sem_barreiras.dto.ResponseObject;
import com.uff.sem_barreiras.exceptions.IdNullException;
import com.uff.sem_barreiras.exceptions.InsertException;
import com.uff.sem_barreiras.exceptions.InsertWithAttributeException;
import com.uff.sem_barreiras.exceptions.NotFoundException;
import com.uff.sem_barreiras.filter.VagaFilter;
import com.uff.sem_barreiras.model.Vaga;
import com.uff.sem_barreiras.service.VagaService;

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
public class VagaController {

    @GetMapping("/vaga")
    public Page<Vaga> listarVagas( @PageableDefault( size = 50, sort = "id" ) final Pageable page, final HttpServletRequest request )
    { 
        Specification<Vaga> spec = new VagaFilter<Vaga>(new HashMap<String, String[]>(request.getParameterMap()));

        return this.vagaService.listarVagas(spec, page);
    }

    @GetMapping("/vaga/{id}")
    public Vaga encontrar(@PathVariable( value = "id" ) final Integer id)throws NotFoundException
    {
        return this.vagaService.encontrarVaga(id);
    }

    @PostMapping("/vaga") 
    public Vaga cadastrar(@RequestBody final Vaga vaga  ) throws InsertException, InsertWithAttributeException
    {
        return this.vagaService.criarVaga(vaga);
    }
    
    @DeleteMapping("/vaga/{id}")
    public ResponseObject deletar(@PathVariable(value = "id")Integer id) throws NotFoundException
    {
        return this.vagaService.deletarVaga(id);
    }

    // mapeamento Put para alterar vaga
    @PutMapping("/vaga/alterar")
    public Vaga alterarVaga(@RequestBody final Vaga vaga) throws NotFoundException, IdNullException,InsertException, InsertWithAttributeException 
    {
        return this.vagaService.alterarVaga(vaga);
    }

    @PostMapping("/vaga/candidatar/{id}")
    public ResponseObject candidatarAVaga(@RequestBody CandidatoDados candidato, @PathVariable(value = "id")Integer idVaga) throws NotFoundException
    {
        return this.vagaService.realizarCandidatura(candidato.getNome(), candidato.getEmail(), candidato.getTelefone(), idVaga);
    }

    @PostMapping("/vaga/forcar/deletar-passado")
    public void forcarDeletarPassado() throws NotFoundException
    {
        this.vagaService.deletarVagaPassado30Dias();
    }

    @PostMapping("/vaga/forcar/notificar-delete")
    public void forcarNotificacaoDelete() throws NotFoundException
    {
        this.vagaService.notificarVagaQueIraExperiar();
    }

    @Autowired
    private VagaService vagaService;
}
