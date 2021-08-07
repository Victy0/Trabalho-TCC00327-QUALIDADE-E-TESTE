package com.uff.sem_barreiras.service;

import java.util.Optional;

import com.uff.sem_barreiras.dao.CursoDao;
import com.uff.sem_barreiras.exceptions.IdNullException;
import com.uff.sem_barreiras.exceptions.InsertException;
import com.uff.sem_barreiras.exceptions.NotFoundException;
import com.uff.sem_barreiras.model.Curso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CursoService {

    public Page<Curso> listarCursos( Specification<Curso> spec, final Pageable page ){
        return this.cursoDao.findAll(spec, page);
    }

    public Curso criarCurso(Curso curso)throws InsertException{
        if(curso == null){
            throw new InsertException("o Curso");
        }else{
        try{

            this.cursoDao.save(curso);
        }
        catch(final Exception e){
            throw new InsertException("o Curso");
        }
        return curso;
    }
    }

    public Curso encontrarCurso(Integer id) throws NotFoundException {
       Curso curso = null;
       if(id == null){
           throw new NotFoundException("Curso", id);
       }else{
           Optional <Curso> cursoOptional;
        try{
            cursoOptional = this.cursoDao.findById(id);
        }catch(final Exception e){
            throw new NotFoundException("Curso", id);
        }
        if(!cursoOptional.isPresent()){
            throw new NotFoundException("Curso", id);
        }
        curso=cursoOptional.get();
     }
     return curso;
    }
    
    public void deletarCurso(Integer id) throws NotFoundException{  
        
       if(id == null){
           throw new NotFoundException("Curso", id);
       }else{
           
        try{
             this.cursoDao.deleteById(id);
        }catch(final Exception e){
            throw new NotFoundException("Curso", id);
        }
       
     }
    }

    // alterar curso
    public Curso alterarCurso(Curso curso) throws IdNullException{
        if(curso.getId() == null){
            throw new IdNullException("Curso");
        }else{
        this.cursoDao.save(curso);
        Optional <Curso> cursoOptional;
        try{
            cursoOptional = this.cursoDao.findById(curso.getId());
        }catch(final Exception e){
            throw new  IdNullException("Curso");
        }
        if(!cursoOptional.isPresent()){
            throw new IdNullException("Curso");
        }
        curso = cursoOptional.get();
        
        }
        return curso;
    }

    @Autowired
    private CursoDao cursoDao;
}
