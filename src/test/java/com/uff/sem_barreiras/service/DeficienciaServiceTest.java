package com.uff.sem_barreiras.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.uff.sem_barreiras.dao.DeficienciaDao;
import com.uff.sem_barreiras.exceptions.AlredyExistsException;
import com.uff.sem_barreiras.exceptions.IdNullException;
import com.uff.sem_barreiras.exceptions.InsertException;
import com.uff.sem_barreiras.exceptions.InsertWithAttributeException;
import com.uff.sem_barreiras.exceptions.NotFoundException;
import com.uff.sem_barreiras.model.Cidade;
import com.uff.sem_barreiras.model.Deficiencia;

@SpringBootTest
class DeficienciaServiceTest {
	@Mock
	private DeficienciaDao deficienciaDao;
	
	@InjectMocks
	private DeficienciaService deficienciaService;

	 // ************************************************************************************************************** TESTE ENCONTRAR EMPRESA
    @Test
    public void testeEncontrarDeficienciaComSucesso() throws NotFoundException
    {
        Deficiencia mockDeficiencia = mock(Deficiencia.class);
        when(mockDeficiencia.getId()).thenReturn(1);
        Optional<Deficiencia> optionalEmpresa = Optional.of(mockDeficiencia);
        when(deficienciaDao.findById(1)).thenReturn(optionalEmpresa);

        Deficiencia def = this.deficienciaService.encontrarDeficiencia(1);
        Assertions.assertNotNull(def.getId());
        Assertions.assertEquals(Integer.valueOf(1), def.getId());
    }

    @Test
    public void testeEncontrarDeficienciaComIdNull() throws NotFoundException 
    {
        Assertions.assertThrows(NotFoundException.class, () -> {
            this.deficienciaService.encontrarDeficiencia(null);
        });
    }

    @Test
    public void testeEncontrarEmpresaInexistente() throws NotFoundException 
    {
        when(deficienciaDao.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> {
            this.deficienciaService.encontrarDeficiencia(0);
        });
    }

    // ************************************************************************************************************** TESTE CRIAR EMPRESA
    @Test
    public void testeCriarDeficienciaComSucesso() throws InsertException, AlredyExistsException, InsertWithAttributeException
    {
        Deficiencia def = new Deficiencia();
       def.setId(1); 
       def.setDescricao("deficiencia auditiva");
       def.setNecessidadePisoTatil(true);
       def.setNecessidadeRampa(false);
       def.setNecessidadeSonora(true);
       when(deficienciaDao.save(any(Deficiencia.class))).thenReturn(def);
       
       Assertions.assertNotNull( this.deficienciaService.criarDeficiencia(def) );
    }

    @Test
    public void testeCriarDeficienciaNula() throws InsertException, AlredyExistsException, InsertWithAttributeException
    {
        Assertions.assertThrows(InsertException.class, () -> {
            this.deficienciaService.criarDeficiencia(null);
        });
    }

   

    
    // ************************************************************************************************************** TESTE DELETE DE EMPRESA
    @Test
    public void testeDeletarDeficienciaComSucesso() throws NotFoundException 
    {
        Deficiencia mockDeficiencia = mock(Deficiencia.class);
        when(deficienciaDao.findById(anyInt())).thenReturn(Optional.of(mockDeficiencia));

        Assertions.assertEquals("Empresa removida com sucesso", this.deficienciaService.deletarDeficiencia(anyInt()).getMensagem());
    }

    @Test
    public void testeDeletarDeficienciaComIdNull() throws NotFoundException 
    {
        Assertions.assertThrows(NotFoundException.class, () -> {
           this.deficienciaService.deletarDeficiencia(null);
        });
    }
    
    // ************************************************************************************************************** TESTE ALTERAÇÃO DE EMPRESA
    @Test
    public void testeAlterarDeficienciaComSucesso() throws InsertException, InsertWithAttributeException, IdNullException
    {
      Deficiencia def = new Deficiencia();
        
        def.setDescricao("deficiencia de fala");
        def.setNecessidadePisoTatil(false);
        def.setNecessidadeRampa(false);
        def.setNecessidadeSonora(false);
       
        
        Assertions.assertNotNull( this.deficienciaService.alterarDeficiencia(def) );
    }

    @Test
    public void testeAlterarDeficienciaSemId() throws InsertException, InsertWithAttributeException, IdNullException
    {
        Deficiencia def = new Deficiencia();
        def.setId(null);

        Assertions.assertThrows(IdNullException.class, () -> {
            this.deficienciaService.alterarDeficiencia(def);
        });
    }

    @Test
    public void testeAlterarDeficienciaNula() throws InsertException, AlredyExistsException, InsertWithAttributeException
    {
        Assertions.assertThrows(IdNullException.class, () -> {
            this.deficienciaService.alterarDeficiencia(null);
        });
    }

    // OBSERVAÇÃO - teste dos campos obrigatórios mesmo que para criação

	}


