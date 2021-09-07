package com.uff.sem_barreiras.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.uff.sem_barreiras.dao.AreaAtuacaoDao;
import com.uff.sem_barreiras.exceptions.IdNullException;
import com.uff.sem_barreiras.exceptions.InsertException;
import com.uff.sem_barreiras.exceptions.NotFoundException;
import com.uff.sem_barreiras.model.AreaAtuacao;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AreaAtuacaoServiceTest {

	@Mock
    private AreaAtuacaoDao areaAtuacaoDao;

    @InjectMocks
    private AreaAtuacaoService areaAtuacaoService;

	
    @Test
    public void criarAreaAtuacaoComSucesso() throws InsertException {
        AreaAtuacao novoAreaAtuacao = new AreaAtuacao();
        doAnswer(new Answer<AreaAtuacao>() {

            @Override
            public AreaAtuacao answer(InvocationOnMock arg0) throws Throwable {
                AreaAtuacao areaAtuacaoSendoSalvo = arg0.getArgument(0);
                areaAtuacaoSendoSalvo.setId(1);
                return areaAtuacaoSendoSalvo;
            }
        }).when(areaAtuacaoDao).save(any(AreaAtuacao.class));

        AreaAtuacao areaAtuacaoSalvo = this.areaAtuacaoService.criarAreaAtuacao(novoAreaAtuacao);
        Assertions.assertNotNull(areaAtuacaoSalvo);
        Assertions.assertNotNull(areaAtuacaoSalvo.getId());
    }

    @Test
    public void criarAreaAtuacaoComArgumentoNull() throws InsertException {
        Assertions.assertThrows(InsertException.class, () -> {
            this.areaAtuacaoService.criarAreaAtuacao(null);
        });
    }

    @Test
    public void criarAreaAtuacaoComErroDeBanco() throws InsertException {
        AreaAtuacao mockAreaAtuacao = mock(AreaAtuacao.class);
        when(areaAtuacaoDao.save(any(AreaAtuacao.class))).thenThrow(RuntimeException.class);
        Assertions.assertThrows(InsertException.class, () -> {
            this.areaAtuacaoService.criarAreaAtuacao(mockAreaAtuacao);
        });
    }
	
    @Test
    public void encontrarAreaAtuacaoComSucesso() throws NotFoundException {
        AreaAtuacao mockAreaAtuacao = mock(AreaAtuacao.class);
        when(mockAreaAtuacao.getId()).thenReturn(1);
        Optional<AreaAtuacao> optionalAreaAtuacao = Optional.of(mockAreaAtuacao);

        when(areaAtuacaoDao.findById(anyInt())).thenReturn(optionalAreaAtuacao);
        Integer idTest = 1;
        AreaAtuacao areaAtuacao = this.areaAtuacaoService.encontrarAreaAtuacao(idTest);
        Assertions.assertNotNull(areaAtuacao.getId());
        Assertions.assertEquals(Integer.valueOf(1), areaAtuacao.getId());
    }

    @Test
    public void encontrarAreaAtuacaoComIdNull() throws NotFoundException {
        Assertions.assertThrows(NotFoundException.class, () -> {
            this.areaAtuacaoService.encontrarAreaAtuacao(null);
        });
    }

    @Test
    public void encontrarAreaAtuacaoInexistente() throws NotFoundException {
        when(areaAtuacaoDao.findById(anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> {
            this.areaAtuacaoService.encontrarAreaAtuacao(1);
        });
    }

    @Test
    public void encontrarAreaAtuacaoComErroDeBanco() throws NotFoundException {
        when(areaAtuacaoDao.findById(anyInt())).thenThrow(RuntimeException.class);
        Assertions.assertThrows(NotFoundException.class, () -> {
            this.areaAtuacaoService.encontrarAreaAtuacao(1);
        });
    }

	
    @Test
    public void deletarAreaAtuacaoComSucesso() throws NotFoundException {
        doNothing().when(areaAtuacaoDao).deleteById(anyInt());
        areaAtuacaoService.deletarAreaAtuacao(1);
        verify(areaAtuacaoDao).deleteById(anyInt());
    }

    @Test
    public void deletarAreaAtuacaoComIdNull() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            this.areaAtuacaoService.deletarAreaAtuacao(null);
        });
    }

    @Test
    public void deletarAreaAtuacaoComErroDeBanco() throws NotFoundException {
        doThrow(RuntimeException.class).when(areaAtuacaoDao).deleteById(anyInt());
        Assertions.assertThrows(NotFoundException.class, () -> {
            this.areaAtuacaoService.deletarAreaAtuacao(1);
        });
    }

	
    @Test
    public void alterarAreaAtuacaoComSucesso() throws IdNullException {
        AreaAtuacao areaAtuacao = mock(AreaAtuacao.class);
        when(areaAtuacao.getId()).thenReturn(1);

        when(areaAtuacaoDao.save(any(AreaAtuacao.class))).thenReturn(areaAtuacao);

        AreaAtuacao areaAtuacaoSalvo = this.areaAtuacaoService.alterarAreaAtuacao(areaAtuacao);
        Assertions.assertNotNull(areaAtuacaoSalvo);
        Assertions.assertNotNull(areaAtuacaoSalvo.getId());
    }

    @Test
    public void alterarAreaAtuacaoComIdNull() throws IdNullException {
        AreaAtuacao areaAtuacao = new AreaAtuacao();
        Assertions.assertThrows(IdNullException.class, () -> {
            this.areaAtuacaoService.alterarAreaAtuacao(areaAtuacao);
        });
    }

}
