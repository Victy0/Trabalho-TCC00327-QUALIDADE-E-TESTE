package com.uff.sem_barreiras.service;

import java.util.Optional;

import com.uff.sem_barreiras.dao.AreaAtuacaoDao;
import com.uff.sem_barreiras.exceptions.IdNullException;
import com.uff.sem_barreiras.exceptions.InsertException;
import com.uff.sem_barreiras.exceptions.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.uff.sem_barreiras.model.AreaAtuacao;

@Service
public class AreaAtuacaoService {

    public Page<AreaAtuacao> listarAreaAtuacao(Specification<AreaAtuacao> spec, final Pageable page){
        return this.areaAtuacaoDao.findAll(spec, page);
    }

    public AreaAtuacao encontrarAreaAtuacao(Integer id) throws NotFoundException{
        AreaAtuacao areaAtuacao = null;

        if (id == null) {
            throw new NotFoundException("Área de atuação", id);
        } else {
            Optional<AreaAtuacao> areaOptional;
            try{
                areaOptional = this.areaAtuacaoDao.findById(id);
            }catch(final Exception e ){
                throw new NotFoundException("Área de atuação", id);
            }

            if (!areaOptional.isPresent()) {
                throw new NotFoundException("Área de atuação", id);
            }

            areaAtuacao = areaOptional.get();
        }
        return areaAtuacao;
    }

    public AreaAtuacao criarAreaAtuacao(AreaAtuacao area) throws InsertException{
        AreaAtuacao areaSalva = null;
        if (area == null) {
            throw new InsertException("a Área de atuação");
        } else {
            try{
                areaSalva = this.areaAtuacaoDao.save(area);
            }catch(final Exception e){
                throw new InsertException("a Área de atuação");
            }
        }
        return areaSalva;
    }

    public void deletarAreaAtuacao(Integer id) throws NotFoundException{
        if (id == null) {
            throw new NotFoundException("Área de atuação", id);
        } else {
            try{
                this.areaAtuacaoDao.deleteById(id);
            }catch(final Exception e ){
                throw new NotFoundException("Área de atuação", id);
            }
        }
    }

    // alterar area
    public AreaAtuacao alterarAreaAtuacao(AreaAtuacao area) throws IdNullException{
        AreaAtuacao areaSalva = null;
        if(area.getId() == null){
            throw new IdNullException("Área de atuação");
        } else {
            areaSalva = this.areaAtuacaoDao.save(area);
        }

        return areaSalva;
    }

    @Autowired
    private AreaAtuacaoDao areaAtuacaoDao;
}
