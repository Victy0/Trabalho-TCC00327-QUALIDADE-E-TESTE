package com.uff.sem_barreiras.service;

import java.util.Optional;

import com.uff.sem_barreiras.dao.EscolaridadeDao;
import com.uff.sem_barreiras.exceptions.NotFoundException;
import com.uff.sem_barreiras.exceptions.IdNullException;
import com.uff.sem_barreiras.model.Escolaridade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class EscolaridadeService {

    public Page<Escolaridade> listarEscolaridades( Specification<Escolaridade> spec, final Pageable page ){
        return this.escolaridadeDao.findAll( spec, page);
    }

    public Escolaridade encontrarEscolaridade(Integer id) throws NotFoundException , IdNullException{
        Escolaridade escolaridade = null;
        if(id == null){
            throw new IdNullException("Escolaridade");
        }else{
            Optional <Escolaridade> escolaridadeOptional;
        try{
           escolaridadeOptional = this.escolaridadeDao.findById(id);
        }catch(final Exception e ){
            throw new NotFoundException("Escolaridade", id);
        }
        if(!escolaridadeOptional.isPresent()){
            throw new NotFoundException("Empresa", id);
        }
        escolaridade = escolaridadeOptional.get();
        return escolaridade;
      }
    }

    @Autowired
    private EscolaridadeDao escolaridadeDao;
}
