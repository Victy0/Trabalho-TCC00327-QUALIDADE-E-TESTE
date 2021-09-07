package com.uff.sem_barreiras.service;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.uff.sem_barreiras.dao.VagaDao;
import com.uff.sem_barreiras.exceptions.AlredyExistsException;
import com.uff.sem_barreiras.exceptions.IdNullException;
import com.uff.sem_barreiras.exceptions.InsertException;
import com.uff.sem_barreiras.exceptions.InsertWithAttributeException;
import com.uff.sem_barreiras.exceptions.NotFoundException;
import com.uff.sem_barreiras.model.Vaga;
import com.uff.sem_barreiras.model.AreaAtuacao;
import com.uff.sem_barreiras.model.Empresa;
import com.uff.sem_barreiras.model.Escolaridade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VagaServiceTest {
    
    @Mock
    private VagaDao vagaDao;

    @InjectMocks
    private VagaService vagaService;

    // ************************************************************************************************************** TESTE ENCONTRAR vaga
    @Test
    public void testeEncontrarVagaComSucesso() throws NotFoundException
    {
        Vaga mockvaga = mock(Vaga.class);
        when(mockvaga.getId()).thenReturn(1);
        Optional<Vaga> optionalvaga = Optional.of(mockvaga);
        when(vagaDao.findById(1)).thenReturn(optionalvaga);

        Vaga vaga = this.vagaService.encontrarVaga( 1 );
        Assertions.assertNotNull(vaga.getId());
        Assertions.assertEquals(Integer.valueOf(1), vaga.getId());

    }

    @Test
    public void testeEncontrarVagaComIdNull() throws NotFoundException 
    {
        Assertions.assertThrows(NotFoundException.class, () -> {
            this.vagaService.encontrarVaga(null);
        });
    }
    
    @Test
    public void testeEncontrarVagaInexistente() throws NotFoundException
    {
    	Vaga mockvaga = mock(Vaga.class);
    	Optional<Vaga> optionalvaga = Optional.of(mockvaga);
        when(vagaDao.findById(anyInt())).thenReturn(optionalvaga.empty());

        Assertions.assertThrows(NotFoundException.class, () -> {
            this.vagaService.encontrarVaga(0);
        });

    }

    @Test
    public void testeEncontrarVagaErroJPA() throws NotFoundException
    {
        when(vagaDao.findById(anyInt())).thenThrow(RuntimeException.class);

        Assertions.assertThrows(NotFoundException.class, () -> {
            this.vagaService.encontrarVaga(0);
        });

    }

    // ************************************************************************************************************** TESTE CRIAR VAGA
    @Test
    public void testeCriarVagaComSucesso() throws InsertException, InsertWithAttributeException
    {
        Vaga vaga = new Vaga();
        vaga.setEmpresa( new Empresa() );
        vaga.setResumo( "resumo" );
        vaga.setDescricao( "descricao" );
        vaga.setArea( new AreaAtuacao() );
        vaga.setFuncao( "funcao" );
        vaga.setNivel( "nivel" );
        vaga.setEscolaridade( new Escolaridade() );

        Assertions.assertNotNull( this.vagaService.criarVaga( vaga ) );
    }

    @Test
    public void testeCriarVagaNula() throws InsertException, InsertWithAttributeException
    {
        Assertions.assertThrows(InsertException.class, () -> {
            this.vagaService.criarVaga(null);
        });
    }

    @Test
    public void testeCriarEmpresaErroJPA() throws InsertException, InsertWithAttributeException
    {
        Vaga vaga = new Vaga();
        vaga.setEmpresa( new Empresa() );
        vaga.setResumo( "resumo" );
        vaga.setDescricao( "descricao" );
        vaga.setArea( new AreaAtuacao() );
        vaga.setFuncao( "funcao" );
        vaga.setNivel( "nivel" );
        vaga.setEscolaridade( new Escolaridade() );

        when(vagaDao.save(vaga)).thenThrow(RuntimeException.class);

        Assertions.assertThrows(InsertException.class, () -> {
            this.vagaService.criarVaga(vaga);
        });
    }

    @Test
    public void testeCriarVagaSemCampoObrigatorio() throws InsertException, InsertWithAttributeException
    {
        Vaga vaga = new Vaga();

        Assertions.assertThrows(InsertWithAttributeException.class, () -> {
            this.vagaService.criarVaga( vaga );
        });

        vaga.setEmpresa( new Empresa() );

        Assertions.assertThrows(InsertWithAttributeException.class, () -> {
            this.vagaService.criarVaga( vaga );
        });

        vaga.setResumo( "resumo" );

        Assertions.assertThrows(InsertWithAttributeException.class, () -> {
            this.vagaService.criarVaga( vaga );
        });

        vaga.setDescricao( "descricao" );

        Assertions.assertThrows(InsertWithAttributeException.class, () -> {
            this.vagaService.criarVaga( vaga );
        });

        vaga.setFuncao( "funcao" );

        Assertions.assertThrows(InsertWithAttributeException.class, () -> {
            this.vagaService.criarVaga( vaga );
        });

        vaga.setNivel( "nivel" );

        Assertions.assertThrows(InsertWithAttributeException.class, () -> {
            this.vagaService.criarVaga( vaga );
        });

        vaga.setArea( new AreaAtuacao() );

        Assertions.assertThrows(InsertWithAttributeException.class, () -> {
            this.vagaService.criarVaga( vaga );
        });

        vaga.setEscolaridade( new Escolaridade() );

        Assertions.assertNotNull( this.vagaService.criarVaga(vaga) );
    }
    
}
