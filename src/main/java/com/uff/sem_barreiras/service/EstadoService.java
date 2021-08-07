package com.uff.sem_barreiras.service;

import com.uff.sem_barreiras.dao.EstadoDao;
import com.uff.sem_barreiras.exceptions.IdNullException;
import com.uff.sem_barreiras.exceptions.NotFoundException;
import com.uff.sem_barreiras.model.Estado;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@Service
public class EstadoService {
    
    // listar todos os estados
    public Page<Estado> listarEstados( Specification<Estado> spec, final Pageable page ){
        return this.estadoDao.findAll(spec, page);
    }

    // encontrar estado pelo id
    public Estado encontrarEstado(Integer id) throws NotFoundException , IdNullException
    {
        Estado estado = null;
        if(id == null){
            throw new IdNullException("Estado");
        }else{
            Optional <Estado> estadoOptional;
        try{
           estadoOptional = this.estadoDao.findById(id);
        }catch(final Exception e ){
            throw new NotFoundException("Escolaridade", id);
        }
        if(!estadoOptional.isPresent()){
            throw new NotFoundException("Empresa", id);
        }
        estado = estadoOptional.get();
        return estado;
      }
    }

    @Autowired
    private EstadoDao estadoDao;
}
