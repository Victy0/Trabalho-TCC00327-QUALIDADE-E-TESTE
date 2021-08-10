package com.uff.sem_barreiras.service;

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
        if(id != null) {
            Cidade cidade = this.cidadeDao.findById(id).get();
            if(cidade != null){
                return this.cidadeDao.findById(id).get();
            } else {
                throw new NotFoundException("Cidade", id);
            }
        } else {
            throw new NotFoundException("Cidade", id);
        }
    }
    //deletar  cidades por id
    public void deletarCidade(Integer id) throws NotFoundException{ 
        if(id != null) {
            this.cidadeDao.deleteById(id);
        } else {
            throw new NotFoundException("Curso", id);
        }
    }

    public boolean camposPreenchidos(Cidade cidade){
        return cidade.getId() != null && !cidade.getNome().isEmpty() && cidade.getEstado() != null;
    }

    // salvar cidade
    public Cidade criarCidade(Cidade cidade) throws InsertException {
        if(cidade != null){
            if(camposPreenchidos(cidade)){
                this.cidadeDao.save(cidade);
                return this.cidadeDao.findById(cidade.getId()).get();
            } else {
                throw new InsertException("a Cidade");
            }
        } else {
            throw new InsertException("a Cidade");
        }
    }

    // alterar cidade
    public Cidade alterarCidade(Cidade cidade) throws IdNullException, InsertException{
        if(camposPreenchidos(cidade)){
            this.cidadeDao.save(cidade);
            return this.cidadeDao.findById(cidade.getId()).get();
        } else {
            throw new InsertException("Cidade");
        }
    }

    @Autowired
    private CidadeDao cidadeDao;
}
