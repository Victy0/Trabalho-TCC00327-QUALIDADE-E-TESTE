package com.uff.sem_barreiras.service;

import java.util.Optional;

import com.uff.sem_barreiras.dao.DeficienciaDao;
import com.uff.sem_barreiras.exceptions.IdNullException;
import com.uff.sem_barreiras.exceptions.InsertException;
import com.uff.sem_barreiras.exceptions.NotFoundException;
import com.uff.sem_barreiras.model.Deficiencia;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class DeficienciaService {

    // listar todos os deficiencias
    public Page<Deficiencia> listarDeficiencias(Specification<Deficiencia> spec, final Pageable page) {
        return this.deficienciaDao.findAll( spec, page);
    }

    // encontrar deficiencia pelo id
    public Deficiencia encontrarDeficiencia(Integer id) throws NotFoundException {
        Optional<Deficiencia> optionalDeficiencia = null;

        if(id != null) {
            try{
                optionalDeficiencia = this.deficienciaDao.findById(id);
                if(optionalDeficiencia.isPresent()){
                    return optionalDeficiencia.get();
                } else {
                    throw new NotFoundException("Id não encontrado", id);
                }
            }catch(final Exception e ){
                throw new NotFoundException("Deficiência", id);
            }
        } else {
            throw new NotFoundException("Id informado é nulo", id);
        }
    }

    public boolean camposPreenchidos(Deficiencia def) {
        return def.getId() != null && !def.getDescricao().isEmpty();
    }

    // salvar deficiencia
    public Deficiencia criarDeficiencia(Deficiencia deficiencia) throws InsertException {
        if (deficiencia != null) {
            if(camposPreenchidos(deficiencia)){
                return this.deficienciaDao.save(deficiencia);
            } else {
                throw new InsertException("a Deficiencia");
            }
        } else {
            throw new InsertException("a Deficiencia");
        }
    }

    // deletar deficiencia
    public void deletarDeficiencia(Integer id) throws NotFoundException {
        if (id != null) {
            Deficiencia deficiencia = encontrarDeficiencia(id);
            if (deficiencia != null){
                this.deficienciaDao.deleteById(id);
            } else {
                throw new NotFoundException("Deficiência não encontrada com id: ", id);
            }
        } else {
            throw new NotFoundException("Deficinência, id não informado", id);
        }
    }

    // alterar deficiencia
    public Deficiencia alterarDeficiencia(Deficiencia deficiencia) throws IdNullException, InsertException{
        if(deficiencia != null) {
            if(camposPreenchidos(deficiencia)) {
                this.deficienciaDao.save(deficiencia);
                return this.deficienciaDao.findById(deficiencia.getId()).get();
            } else {
                throw new InsertException("Deficiência");
            }
        } else {
            throw new InsertException("Deficiência");
        }
    }

    @Autowired
    private DeficienciaDao deficienciaDao;
}
