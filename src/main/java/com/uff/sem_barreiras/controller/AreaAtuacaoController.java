package com.uff.sem_barreiras.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.uff.sem_barreiras.dto.ResponseObject;
import com.uff.sem_barreiras.exceptions.IdNullException;
import com.uff.sem_barreiras.exceptions.InsertException;
import com.uff.sem_barreiras.exceptions.NotFoundException;
import com.uff.sem_barreiras.filter.DefaultFilter;
import com.uff.sem_barreiras.model.AreaAtuacao;
import com.uff.sem_barreiras.service.AreaAtuacaoService;

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
public class AreaAtuacaoController {

    // mapeamento get para listar todas as areas de atuação
    @GetMapping("/area")
    public Page<AreaAtuacao> listarAreas( @PageableDefault( size = 50, sort = "descricao" ) final Pageable page, final HttpServletRequest request ){ 
        Specification<AreaAtuacao> spec = new DefaultFilter<AreaAtuacao>( new HashMap<String, String[]>(request.getParameterMap() ) );

        return this.areaAtuacaoService.listarAreaAtuacao(spec, page);
    }

    // mapeamento get para encontrar a area de atuação pelo id
    @GetMapping("/area/{id}")
    public AreaAtuacao encontrarArea(@PathVariable(value = "id")Integer id) throws NotFoundException{ 
        return this.areaAtuacaoService.encontrarAreaAtuacao(id);
    }

    // mapeamento post para criar nova area deatuação
    @PostMapping(value = "/area")
    public AreaAtuacao cadastrarAreaAtuacao(@RequestBody final AreaAtuacao area) throws InsertException{
        return this.areaAtuacaoService.criarAreaAtuacao(area);
    }
    
    // mapeamento post para criar nova area de atuação
    @DeleteMapping("/area/{id}")
    public ResponseObject deletarAreaAtuacao(@PathVariable(value = "id")Integer id) throws NotFoundException{
        this.areaAtuacaoService.deletarAreaAtuacao(id);
        return new ResponseObject(true, "Área de atuação removida com sucesso");
    }

     // mapeamento Put para alterar area de atuação
     @PutMapping("/area/alterar")
     public AreaAtuacao alterarAreaAtuacao(@RequestBody final AreaAtuacao area) throws NotFoundException, IdNullException {
         return this.areaAtuacaoService.alterarAreaAtuacao(area);
     }

    @Autowired
    private AreaAtuacaoService areaAtuacaoService ;
}
