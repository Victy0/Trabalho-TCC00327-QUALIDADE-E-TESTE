package com.uff.sem_barreiras.dao;

import com.uff.sem_barreiras.model.Empresa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaDao extends JpaRepository<Empresa, Integer>, JpaSpecificationExecutor<Empresa>  {

    @Query(value = "SELECT id FROM empresa WHERE email = :email", nativeQuery = true)
    Integer getIdByEmail(@Param( "email") String email);

    @Modifying
    @Query(value = "DELETE FROM vaga WHERE id_empresa = :id", nativeQuery = true)
    void deleteVagasDaEmpresa(@Param( "id" ) Integer id);
}