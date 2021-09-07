
package com.uff.sem_barreiras.service;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.uff.sem_barreiras.dao.CidadeDao;
import com.uff.sem_barreiras.dao.EmpresaDao;
import com.uff.sem_barreiras.exceptions.AlredyExistsException;
import com.uff.sem_barreiras.exceptions.IdNullException;
import com.uff.sem_barreiras.exceptions.InsertException;
import com.uff.sem_barreiras.exceptions.InsertWithAttributeException;
import com.uff.sem_barreiras.exceptions.NotFoundException;
import com.uff.sem_barreiras.model.Cidade;
import com.uff.sem_barreiras.model.Estado;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CidadeServiceTest {

    @Mock
    private CidadeDao cidadeDao;

    @InjectMocks
    private CidadeService cidadeService;

    // ************************************************************************************************************** TESTE ENCONTRAR CIDADE
    @Test
    public void testeEncontrarCidadeComSucesso() throws NotFoundException
    {
        Cidade mockCidade = mock(Cidade.class);
        when(mockCidade.getId()).thenReturn(1);
        Optional<Cidade> optionalCidade = Optional.of(mockCidade);
        when(cidadeDao.findById(1)).thenReturn(optionalCidade);

        Cidade cidade = this.cidadeService.encontrarCidade( 1 );
        Assertions.assertNotNull(cidade.getId());
        Assertions.assertEquals(Integer.valueOf(1), cidade.getId());

    }

    @Test
    public void testeEncontrarCidadeComIdNull() throws NotFoundException 
    {
        Assertions.assertThrows(NotFoundException.class, () -> {
            this.cidadeService.encontrarCidade(null);
        });
    }

    // ************************************************************************************************************** TESTE CRIAR CIDADE
    @Test
    public void testeCriarCidadeComSucesso() throws InsertException 
    {
        Cidade cidade = new Cidade();
        cidade.setEstado( new Estado());
        cidade.setId( 1 );
        cidade.setNome( "nome" );

        when(cidadeDao.save(cidade)).thenReturn(cidade);

        Assertions.assertNotNull( this.cidadeService.criarCidade( cidade) );
    }

    @Test
    public void testeCriarEmpresaNula() throws InsertException, AlredyExistsException, InsertWithAttributeException
    {
        Assertions.assertThrows(InsertException.class, () -> {
            this.cidadeService.criarCidade(null);
        });
    }

}
