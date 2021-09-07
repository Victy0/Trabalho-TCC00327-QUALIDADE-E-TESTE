package com.uff.sem_barreiras.service;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.uff.sem_barreiras.dao.EmpresaDao;
import com.uff.sem_barreiras.exceptions.AlredyExistsException;
import com.uff.sem_barreiras.exceptions.IdNullException;
import com.uff.sem_barreiras.exceptions.InsertException;
import com.uff.sem_barreiras.exceptions.InsertWithAttributeException;
import com.uff.sem_barreiras.exceptions.NotFoundException;
import com.uff.sem_barreiras.model.Cidade;
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

    // ************************************************************************************************************** TESTE ENCONTRAR EMPRESA
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

    // ************************************************************************************************************** TESTE CRIAR EMPRESA
    @Test
    public void testeCriarEmpresaComSucesso() throws InsertException, AlredyExistsException, InsertWithAttributeException
    {
        Empresa empresa = new Empresa();
        empresa.setCidade( new Cidade());
        empresa.setCnpj( "0000000000" );
        empresa.setEmail( "email@email.com" );
        empresa.setEndereco( "endereco" );
        empresa.setNome( "nome" );
        empresa.setTelefone( "0000000000" );

        when(empresaDao.getIdByEmail(anyString())).thenReturn(null);

        Assertions.assertNotNull( this.empresaService.criarEmpresa(empresa) );
    }

    @Test
    public void testeCriarEmpresaComEmailJaCadastrado() throws InsertException, AlredyExistsException, InsertWithAttributeException
    {
        Empresa empresa = new Empresa();
        empresa.setEmail( "email@email.com" );

        when(empresaDao.getIdByEmail(anyString())).thenReturn( 1 );

        Assertions.assertThrows(AlredyExistsException.class, () -> {
            this.empresaService.criarEmpresa(empresa);
        });
    }

    @Test
    public void testeCriarEmpresaNula() throws InsertException, AlredyExistsException, InsertWithAttributeException
    {
        Assertions.assertThrows(InsertException.class, () -> {
            this.empresaService.criarEmpresa(null);
        });
    }

    @Test
    public void testeCriarEmpresaErroJPA() throws InsertException, AlredyExistsException, InsertWithAttributeException
    {
        Empresa empresa = new Empresa();
        empresa.setCidade( new Cidade());
        empresa.setCnpj( "0000000000" );
        empresa.setEmail( "email@email.com" );
        empresa.setEndereco( "endereco" );
        empresa.setNome( "nome" );
        empresa.setTelefone( "0000000000" );

        when(empresaDao.getIdByEmail(anyString())).thenReturn(null);

        when(empresaDao.save(empresa)).thenThrow(RuntimeException.class);

        Assertions.assertThrows(InsertException.class, () -> {
            this.empresaService.criarEmpresa(empresa);
        });
    }

    @Test
    public void testeCriarEmpresaSemCampoObrigatorio() throws InsertException, AlredyExistsException, InsertWithAttributeException
    {
        Empresa empresa = new Empresa();

        when(empresaDao.getIdByEmail(anyString())).thenReturn(null);

        Assertions.assertThrows(InsertException.class, () -> {
            this.empresaService.criarEmpresa( empresa );
        });

        empresa.setEmail( "email@email.com" );
        Assertions.assertThrows(InsertWithAttributeException.class, () -> {
            this.empresaService.criarEmpresa( empresa );
        });

        empresa.setNome( "nome");
        Assertions.assertThrows(InsertWithAttributeException.class, () -> {
            this.empresaService.criarEmpresa( empresa );
        });

        empresa.setCnpj( "0000000000" );
        Assertions.assertThrows(InsertWithAttributeException.class, () -> {
            this.empresaService.criarEmpresa( empresa );
        });

        empresa.setCidade( new Cidade());
        Assertions.assertThrows(InsertWithAttributeException.class, () -> {
            this.empresaService.criarEmpresa( empresa );
        });
        
        empresa.setEndereco( "endereco" );
        Assertions.assertThrows(InsertWithAttributeException.class, () -> {
            this.empresaService.criarEmpresa( empresa );
        });
        
        empresa.setTelefone( "0000000000" );
        Assertions.assertNotNull( this.empresaService.criarEmpresa(empresa) );
    }

    // ************************************************************************************************************** TESTE DELETE DE EMPRESA
    @Test
    public void testeDeletarEmpresaComSucesso() throws NotFoundException 
    {
        Empresa mockEmpresa = mock(Empresa.class);
        when(empresaDao.findById(anyInt())).thenReturn(Optional.of(mockEmpresa));

        Assertions.assertEquals("Empresa removida com sucesso", this.empresaService.deletarEmpresa(anyInt()).getMensagem());
    }

    @Test
    public void testeDeletarEmpresaComIdNull() throws NotFoundException 
    {
        Assertions.assertThrows(NotFoundException.class, () -> {
            this.empresaService.deletarEmpresa(null);
        });
    }
    
    // ************************************************************************************************************** TESTE ALTERAÇÃO DE EMPRESA
    @Test
    public void testeAlterarEmpresaComSucesso() throws InsertException, InsertWithAttributeException, IdNullException
    {
        Empresa empresa = new Empresa();
        empresa.setId( 1 );
        empresa.setCidade( new Cidade());
        empresa.setCnpj( "0000000000" );
        empresa.setEmail( "email@email.com" );
        empresa.setEndereco( "endereco" );
        empresa.setNome( "nome" );
        empresa.setTelefone( "0000000000" );

        Assertions.assertNotNull( this.empresaService.alterarEmpresa(empresa) );
    }

    @Test
    public void testeAlterarEmpresaSemId() throws InsertException, InsertWithAttributeException, IdNullException
    {
        Empresa empresa = new Empresa();
        empresa.setId(null);

        Assertions.assertThrows(IdNullException.class, () -> {
            this.empresaService.alterarEmpresa(empresa);
        });
    }

    @Test
    public void testeAlterarEmpresaNula() throws InsertException, AlredyExistsException, InsertWithAttributeException
    {
        Assertions.assertThrows(IdNullException.class, () -> {
            this.empresaService.alterarEmpresa(null);
        });
    }

    // OBSERVAÇÃO - teste dos campos obrigatórios mesmo que para criação
}
