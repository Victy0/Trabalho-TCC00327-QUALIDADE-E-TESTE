package com.uff.sem_barreiras.dao;

import javax.transaction.Transactional;

import com.uff.sem_barreiras.model.Curso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoDao extends JpaRepository<Curso,Integer>, JpaSpecificationExecutor<Curso>  {

    @Transactional
	@Modifying
	@Query( value = "INSERT INTO vaga_x_curso( id_vaga, id_curso ) VALUES ( :vagaId, :cursoId )", nativeQuery = true )
	void vinculaCursoVaga( @Param( "cursoId" ) Integer cursoId, @Param( "vagaId" ) Integer vagaId );
   
}
