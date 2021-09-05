package com.uff.sem_barreiras.service;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.uff.sem_barreiras.dao.EmpresaDao;
import com.uff.sem_barreiras.exceptions.NotFoundException;
import com.uff.sem_barreiras.model.Empresa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmpresaServiceTest {

    @Mock
    private EmpresaDao empresaDao;

    @InjectMocks
    private EmpresaService empresaService;

    //teste encontrar empresa
    @Test
    public void testeEncontrarEmpresaComSucesso() throws NotFoundException
    {
        Empresa mockEmpresa = mock(Empresa.class);
        when(mockEmpresa.getId()).thenReturn(1);
        Optional<Empresa> optionalEmpresa = Optional.of(mockEmpresa);

        when(empresaDao.findById(1)).thenReturn(optionalEmpresa);

        Empresa empresa = this.empresaService.encontrarEmpresa( 1 );
        Assertions.assertNotNull(empresa.getId());
        Assertions.assertEquals(Integer.valueOf(1), empresa.getId());
    }

    @Test
    public void testeEncontrarEmpresaComIdNull() throws NotFoundException 
    {
        Assertions.assertThrows(NotFoundException.class, () -> {
            this.empresaService.encontrarEmpresa(null);
        });
    }

    @Test
    public void testeEncontrarEmpresaInexistente() throws NotFoundException 
    {
        when(empresaDao.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> {
            this.empresaService.encontrarEmpresa(0);
        });
    }

    //teste


    
    
}
