package com.uff.sem_barreiras.service;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.uff.sem_barreiras.dao.VagaDao;
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

    @Mock
    private EmailService emailService;

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
        when(vagaDao.findById(anyInt())).thenReturn(Optional.empty());

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
        //new Date() pega a data que inicia o teste por isso precisa ser maior
        Assertions.assertNotNull( this.vagaService.criarVaga( vaga ).getDataCriacao().getTime() > new Date().getTime() );
        Assertions.assertTrue( this.vagaService.criarVaga( vaga ).getDuracaoVaga() == 30 );
    }

    @Test
    public void testeCriarVagaNula() throws InsertException, InsertWithAttributeException
    {
        Assertions.assertThrows(InsertException.class, () -> {
            this.vagaService.criarVaga(null);
        });
    }

    @Test
    public void testeCriarVagaErroJPA() throws InsertException, InsertWithAttributeException
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

        vaga.setResumo( "" );

        Assertions.assertThrows(InsertWithAttributeException.class, () -> {
            this.vagaService.criarVaga( vaga );
        });

        vaga.setResumo( "resumo" );

        Assertions.assertThrows(InsertWithAttributeException.class, () -> {
            this.vagaService.criarVaga( vaga );
        });

        vaga.setDescricao( "" );

        Assertions.assertThrows(InsertWithAttributeException.class, () -> {
            this.vagaService.criarVaga( vaga );
        });

        vaga.setDescricao( "descricao" );

        Assertions.assertThrows(InsertWithAttributeException.class, () -> {
            this.vagaService.criarVaga( vaga );
        });

        vaga.setFuncao( "" );

        Assertions.assertThrows(InsertWithAttributeException.class, () -> {
            this.vagaService.criarVaga( vaga );
        });

        vaga.setFuncao( "funcao" );

        Assertions.assertThrows(InsertWithAttributeException.class, () -> {
            this.vagaService.criarVaga( vaga );
        });

        vaga.setNivel( "" );

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

        //new Date() pega a data que inicia o teste por isso precisa ser maior
        Assertions.assertNotNull( this.vagaService.criarVaga( vaga ).getDataCriacao().getTime() > new Date().getTime() );
        Assertions.assertTrue( this.vagaService.criarVaga( vaga ).getDuracaoVaga() == 30 );
    }

    // ************************************************************************************************************** TESTE DELETE DE VAGA
    @Test
    public void testeDeletarVagaComSucesso() throws NotFoundException 
    {
        Vaga mockVaga = mock(Vaga.class);
        when(vagaDao.findById(anyInt())).thenReturn(Optional.of(mockVaga));

        Assertions.assertEquals("Vaga removida com sucesso", this.vagaService.deletarVaga(anyInt()).getMensagem());
    }

    @Test
    public void testeDeletarVagaComIdNull() throws NotFoundException 
    {
        Assertions.assertThrows(NotFoundException.class, () -> {
            this.vagaService.deletarVaga(null);
        });
    }

    @Test
    public void testeDeletarVagaInexistente()
    {
        when(vagaDao.findById(anyInt())).thenReturn(Optional.empty());
    	
        Assertions.assertThrows(NotFoundException.class, () -> {
            this.vagaService.deletarVaga(0);
        });
    }

    // ************************************************************************************************************** TESTE ALTERA????O DE VAGA
    @Test
    public void testeAlterarVagaComSucesso() throws IdNullException, InsertException, InsertWithAttributeException
    {
        Vaga vaga = new Vaga();
        vaga.setId(1);
        vaga.setEmpresa( new Empresa() );
        vaga.setResumo( "resumo" );
        vaga.setDescricao( "descricao" );
        vaga.setArea( new AreaAtuacao() );
        vaga.setFuncao( "funcao" );
        vaga.setNivel( "nivel" );
        vaga.setDuracaoVaga( 26 );
        vaga.setEscolaridade( new Escolaridade() );

        Assertions.assertNotNull( this.vagaService.alterarVaga( vaga ) );
        //new Date() pega a data que inicia o teste por isso precisa ser maior
        Assertions.assertNotNull( this.vagaService.alterarVaga( vaga ).getDataCriacao().getTime() > new Date().getTime() );
        Assertions.assertTrue( this.vagaService.alterarVaga( vaga ).getDuracaoVaga() == 26 );
    }

    @Test
    public void testeAlterarVagaSemId() throws IdNullException, InsertException, InsertWithAttributeException
    {
        Vaga vaga = new Vaga();
        vaga.setId(null);

        Assertions.assertThrows(IdNullException.class, () -> {
            this.vagaService.alterarVaga(vaga);
        });
    }

    @Test
    public void testeAlterarVagaNula() throws IdNullException, InsertException, InsertWithAttributeException
    {
        Assertions.assertThrows(IdNullException.class, () -> {
            this.vagaService.alterarVaga(null);
        });
    }

    @Test
    public void testeAlterarVagaErroJPA() throws InsertException, InsertWithAttributeException
    {
        Vaga vaga = new Vaga();
        vaga.setId(1);
        vaga.setEmpresa( new Empresa() );
        vaga.setResumo( "resumo" );
        vaga.setDescricao( "descricao" );
        vaga.setArea( new AreaAtuacao() );
        vaga.setFuncao( "funcao" );
        vaga.setNivel( "nivel" );
        vaga.setEscolaridade( new Escolaridade() );

        when(vagaDao.save(vaga)).thenThrow(RuntimeException.class);

        Assertions.assertThrows(InsertException.class, () -> {
            this.vagaService.alterarVaga(vaga);
        });
    }

    @Test
    public void testeAlterarVagaSemCampoObrigatorio() throws InsertException, InsertWithAttributeException, IdNullException
    {
        Vaga vaga = new Vaga();
        vaga.setId(1);

        Assertions.assertThrows(InsertWithAttributeException.class, () -> {
            this.vagaService.alterarVaga( vaga );
        });

        vaga.setEmpresa( new Empresa() );

        Assertions.assertThrows(InsertWithAttributeException.class, () -> {
            this.vagaService.alterarVaga( vaga );
        });

        vaga.setResumo( "" );

        Assertions.assertThrows(InsertWithAttributeException.class, () -> {
            this.vagaService.alterarVaga( vaga );
        });

        vaga.setResumo( "resumo" );

        Assertions.assertThrows(InsertWithAttributeException.class, () -> {
            this.vagaService.alterarVaga( vaga );
        });

        vaga.setDescricao( "" );

        Assertions.assertThrows(InsertWithAttributeException.class, () -> {
            this.vagaService.alterarVaga( vaga );
        });

        vaga.setDescricao( "descricao" );

        Assertions.assertThrows(InsertWithAttributeException.class, () -> {
            this.vagaService.alterarVaga( vaga );
        });

        vaga.setFuncao( "" );

        Assertions.assertThrows(InsertWithAttributeException.class, () -> {
            this.vagaService.alterarVaga( vaga );
        });

        vaga.setFuncao( "funcao" );

        Assertions.assertThrows(InsertWithAttributeException.class, () -> {
            this.vagaService.alterarVaga( vaga );
        });

        vaga.setNivel( "" );

        Assertions.assertThrows(InsertWithAttributeException.class, () -> {
            this.vagaService.alterarVaga( vaga );
        });

        vaga.setNivel( "nivel" );

        Assertions.assertThrows(InsertWithAttributeException.class, () -> {
            this.vagaService.alterarVaga( vaga );
        });

        vaga.setArea( new AreaAtuacao() );

        Assertions.assertThrows(InsertWithAttributeException.class, () -> {
            this.vagaService.alterarVaga( vaga );
        });

        vaga.setEscolaridade( new Escolaridade() );

        Assertions.assertNotNull( this.vagaService.alterarVaga(vaga) );

        //new Date() pega a data que inicia o teste por isso precisa ser maior
        Assertions.assertNotNull( this.vagaService.alterarVaga( vaga ).getDataCriacao().getTime() > new Date().getTime() );
        Assertions.assertTrue( this.vagaService.alterarVaga( vaga ).getDuracaoVaga() == 30 );
    }

    // ************************************************************************************************************** TESTE REALIZAR CANDIDATURA
    @Test
    public void testeRealizarCandidaturaComSucesso() throws NotFoundException
    {
        Empresa mockEmpresa = mock(Empresa.class);
        when(mockEmpresa.getEmail()).thenReturn("email");

        Vaga mockvaga = mock(Vaga.class);
        when(mockvaga.getId()).thenReturn(1);
        when(mockvaga.getEmpresa()).thenReturn( mockEmpresa );
        when(mockvaga.getResumo()).thenReturn("resumo");

        Optional<Vaga> optionalvaga = Optional.of(mockvaga);
        when(vagaDao.findById(anyInt())).thenReturn(optionalvaga);

        Assertions.assertEquals("Candidatura realizada com sucesso", this.vagaService.realizarCandidatura("nome", "email", "telefone", 1).getMensagem());
    }

    @Test
    public void testeRealizarCandidaturaSemIndicarCamposNecessarios() throws NotFoundException
    {
        Assertions.assertEquals("Candidatura n??o pode ser realizada!", this.vagaService.realizarCandidatura(null, "email", "telefone", 1).getMensagem().substring(0, 35));

        Assertions.assertEquals("Candidatura n??o pode ser realizada!", this.vagaService.realizarCandidatura("nome", null, null, 1).getMensagem().substring(0, 35));

        Assertions.assertEquals("Candidatura n??o pode ser realizada!", this.vagaService.realizarCandidatura("", "email", "telefone", 1).getMensagem().substring(0, 35));
    }

    @Test
    public void testeRealizarCandidaturaComSucessoSemInformarTodosCamposDeIdentificacao() throws NotFoundException
    {
        Empresa mockEmpresa = mock(Empresa.class);
        when(mockEmpresa.getEmail()).thenReturn("email");

        Vaga mockvaga = mock(Vaga.class);
        when(mockvaga.getId()).thenReturn(1);
        when(mockvaga.getEmpresa()).thenReturn( mockEmpresa );
        when(mockvaga.getResumo()).thenReturn("resumo");

        Optional<Vaga> optionalvaga = Optional.of(mockvaga);
        when(vagaDao.findById(anyInt())).thenReturn(optionalvaga);

        Assertions.assertEquals("Candidatura realizada com sucesso", this.vagaService.realizarCandidatura("nome", null, "telefone", 1).getMensagem());

        Assertions.assertEquals("Candidatura realizada com sucesso", this.vagaService.realizarCandidatura("nome", "email", null, 1).getMensagem());
    }

    @Test
    public void testeRealizarCandidaturaComFalha() throws NotFoundException
    {
        Empresa mockEmpresa = mock(Empresa.class);
        when(mockEmpresa.getEmail()).thenReturn("email");

        Vaga mockvaga = mock(Vaga.class);
        when(mockvaga.getId()).thenReturn(1);
        when(mockvaga.getEmpresa()).thenReturn( mockEmpresa );
        when(mockvaga.getResumo()).thenReturn("resumo");

        Optional<Vaga> optionalvaga = Optional.of(mockvaga);
        when(vagaDao.findById(anyInt())).thenReturn(optionalvaga);

        doThrow( RuntimeException.class ).when(emailService).enviar(anyString(), anyString(), anyString());

        Assertions.assertEquals("Candidatura n??o realizada. Tente novamente", this.vagaService.realizarCandidatura("nome", "email", "telefone", 1).getMensagem());
    }

    // ************************************************************************************************************** TESTE CRON DELE????O E NOTIFICA????O
    @Test
    public void testeDeletar30DiasPassados() throws NotFoundException
    {
        List<Integer> mockList = new ArrayList<>();   
        mockList.add(1);
        mockList.add(3);
        mockList.add(7);

        when(vagaDao.recuperaVagaPassado30Dias()).thenReturn(mockList);

        doNothing().when(vagaDao).deleteById(anyInt());

        Vaga mockVaga = mock(Vaga.class);
        when(vagaDao.findById(anyInt())).thenReturn(Optional.of(mockVaga));

        vagaService.deletarVagaPassado30Dias();

        verify(vagaDao, times(3)).deleteById(anyInt());
    }

    @Test
    public void testeNotificacaoVagasQuaseExpirando() throws NotFoundException
    {
        List<Integer> mockList = new ArrayList<>();   
        mockList.add(1);
        mockList.add(3);
        mockList.add(7);

        when(vagaDao.recuperaVagaPassado27Dias()).thenReturn(mockList);

        Empresa mockEmpresa = mock(Empresa.class);
        when(mockEmpresa.getEmail()).thenReturn("email");

        Vaga mockvaga = mock(Vaga.class);
        when(mockvaga.getId()).thenReturn(1);
        when(mockvaga.getEmpresa()).thenReturn( mockEmpresa );
        when(mockvaga.getResumo()).thenReturn("resumo");

        when(vagaDao.getOne(anyInt())).thenReturn(mockvaga);

        doNothing().when(vagaDao).deleteById(anyInt());

        vagaService.notificarVagaQueIraExperiar();

        verify(emailService, times(3)).enviar(anyString(), anyString(), anyString());
    }
    
}
