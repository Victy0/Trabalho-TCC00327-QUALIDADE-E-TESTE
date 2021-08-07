package com.uff.sem_barreiras.service;

import java.util.Optional;

import com.uff.sem_barreiras.dao.CidadeDao;
import com.uff.sem_barreiras.exceptions.IdNullException;
import com.uff.sem_barreiras.exceptions.InsertException;
import com.uff.sem_barreiras.exceptions.NotFoundException;
import com.uff.sem_barreiras.model.Cidade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CidadeService {

    // listar todos os cidades
    public Page<Cidade> listarCidades( Specification<Cidade> spec, final Pageable page ) {
        return this.cidadeDao.findAll(spec, page);
    }

    // encontrar cidade pelo id
    public Cidade encontrarCidade(Integer id) throws NotFoundException {
        Cidade cidade = null;

        if (id == null) {
            throw new NotFoundException("Cidade", id);
        } else {
            Optional<Cidade> cidadeOptional;
            try{
                cidadeOptional = this.cidadeDao.findById(id);
            }catch(final Exception e ){
                throw new NotFoundException("Cidade", id);
            }

            if (!cidadeOptional.isPresent()) {
                throw new NotFoundException("Cidade", id);
            }

            cidade = cidadeOptional.get();
        }
        return cidade;
    }

    //deletar  cidades por id
    public void deletarCidade(Integer id) throws NotFoundException{  
        if (id == null) {
            throw new NotFoundException("Cidade", id);
        } else {
            try{
                this.cidadeDao.deleteById(id);
            }catch(final Exception e ){
                throw new NotFoundException("Cidade", id);
            }
        }
    }

    // salvar cidade
    public Cidade criarCidade(Cidade cidade) throws InsertException {
        Cidade CidadeSalva = null;
        if (cidade == null) {
            throw new InsertException("a Cidade");
        } else {
            try{
                CidadeSalva = this.cidadeDao.save(cidade);
            }catch(final Exception e){
                throw new InsertException("a Cidade");
            }
        }
        return CidadeSalva;
    }

    // alterar cidade
    public Cidade alterarCidade(Cidade cidade) throws IdNullException{
        Cidade cidadeSalva = null;
        if(cidade.getId() == null){
            throw new IdNullException("Cidade");
        } else {
            cidadeSalva = this.cidadeDao.save(cidade);
        }

        return cidadeSalva;
    }

    @Autowired
    private CidadeDao cidadeDao;
}
