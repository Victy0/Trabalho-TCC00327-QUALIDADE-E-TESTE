package com.uff.sem_barreiras.service;

import com.uff.sem_barreiras.exceptions.NotFoundException;
import com.uff.sem_barreiras.model.Empresa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmpresaServiceTest {

    @Autowired
    private EmpresaService empresaService;


    @Test
    public void encontrarEmpresa() throws NotFoundException
    {
        Integer idTest = 1;
        Empresa empresa = this.empresaService.encontrarEmpresa( idTest );
        Assertions.assertNotNull(empresa.getId());
    }

    
    
}
