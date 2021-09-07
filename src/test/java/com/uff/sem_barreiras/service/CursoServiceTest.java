package com.uff.sem_barreiras.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.uff.sem_barreiras.dao.CursoDao;
import com.uff.sem_barreiras.exceptions.IdNullException;
import com.uff.sem_barreiras.exceptions.InsertException;
import com.uff.sem_barreiras.exceptions.NotFoundException;
import com.uff.sem_barreiras.model.Curso;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CursoServiceTest {

    @Mock
    private CursoDao cursoDao;

    @InjectMocks
    private CursoService cursoService;

    @Test
    public void criarCursoComSucesso() throws InsertException {
        Curso novoCurso = new Curso();
        doAnswer(new Answer<Curso>() {

            @Override
            public Curso answer(InvocationOnMock arg0) throws Throwable {
                Curso cursoSendoSalvo = arg0.getArgument(0);
                cursoSendoSalvo.setId(1);
                return cursoSendoSalvo;
            }
        }).when(cursoDao).save(any(Curso.class));

        Curso cursoSalvo = this.cursoService.criarCurso(novoCurso);
        Assertions.assertNotNull(cursoSalvo);
        Assertions.assertNotNull(cursoSalvo.getId());
    }

    @Test
    public void criarCursoComArgumentoNull() throws InsertException {
        Assertions.assertThrows(InsertException.class, () -> {
            this.cursoService.criarCurso(null);
        });
    }

    @Test
    public void criarCursoComDescricaoVazia() throws InsertException {
        Curso mockCurso = mock(Curso.class);
        when(mockCurso.getDescricao()).thenReturn(StringUtils.EMPTY);
        Assertions.assertThrows(InsertException.class, () -> {
            this.cursoService.criarCurso(mockCurso);
        });
    }

    @Test
    public void criarCursoComHiperlinkVazio() throws InsertException {
        Curso mockCurso = mock(Curso.class);
        when(mockCurso.getHyperLink()).thenReturn(StringUtils.EMPTY);
        Assertions.assertThrows(InsertException.class, () -> {
            this.cursoService.criarCurso(mockCurso);
        });
    }

    @Test
    public void criarCursoComErroDeBanco() throws InsertException {
        Curso mockCurso = mock(Curso.class);
        when(mockCurso.getHyperLink()).thenReturn(StringUtils.SPACE);
        when(mockCurso.getDescricao()).thenReturn(StringUtils.SPACE);
        when(cursoDao.save(any(Curso.class))).thenThrow(RuntimeException.class);
        Assertions.assertThrows(InsertException.class, () -> {
            this.cursoService.criarCurso(mockCurso);
        });
    }

    @Test
    public void encontrarCursoComSucesso() throws NotFoundException {
        Curso mockCurso = mock(Curso.class);
        when(mockCurso.getId()).thenReturn(1);
        Optional<Curso> optionalCurso = Optional.of(mockCurso);

        when(cursoDao.findById(1)).thenReturn(optionalCurso);
        Integer idTest = 1;
        Curso curso = this.cursoService.encontrarCurso(idTest);
        Assertions.assertNotNull(curso.getId());
        Assertions.assertEquals(Integer.valueOf(1), curso.getId());
    }

    @Test
    public void encontrarCursoComIdNull() throws NotFoundException {
        Assertions.assertThrows(NotFoundException.class, () -> {
            this.cursoService.encontrarCurso(null);
        });
    }

    @Test
    public void encontrarCursoInexistente() throws NotFoundException {
        when(cursoDao.findById(anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> {
            this.cursoService.encontrarCurso(1);
        });
    }

    @Test
    public void encontrarCursoComErroDeBanco() throws NotFoundException {
        when(cursoDao.findById(anyInt())).thenThrow(RuntimeException.class);
        Assertions.assertThrows(NotFoundException.class, () -> {
            this.cursoService.encontrarCurso(1);
        });
    }

    @Test
    public void vincularVagasComSucesso() {
        Integer cursoId = 1;
        List<Integer> idVagaList = new ArrayList<Integer>();
        idVagaList.add(1);

        doNothing().when(cursoDao).vinculaCursoVaga(anyInt(), anyInt());

        Assertions.assertTrue(cursoService.vingularVagas(cursoId, idVagaList));
    }

    @Test
    public void vincularVagasComFracasso() {
        Integer cursoId = 1;
        List<Integer> idVagaList = new ArrayList<Integer>();

        doNothing().when(cursoDao).vinculaCursoVaga(anyInt(), anyInt());

        Assertions.assertFalse(cursoService.vingularVagas(cursoId, idVagaList));
    }

    @Test
    public void vincularVagasComFracasso02() {
        Integer cursoId = null;
        List<Integer> idVagaList = new ArrayList<Integer>();
        idVagaList.add(1);

        doNothing().when(cursoDao).vinculaCursoVaga(anyInt(), anyInt());

        Assertions.assertFalse(cursoService.vingularVagas(cursoId, idVagaList));
    }

    @Test
    public void deletarCursoComSucesso() throws NotFoundException {
        doNothing().when(cursoDao).deleteById(anyInt());
        cursoService.deletarCurso(1);
        verify(cursoDao).deleteById(anyInt());
    }

    @Test
    public void deletarCursoComIdNull() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            this.cursoService.deletarCurso(null);
        });
    }

    @Test
    public void deletarCursoComErroDeBanco() throws NotFoundException {
        doThrow(RuntimeException.class).when(cursoDao).deleteById(anyInt());
        Assertions.assertThrows(NotFoundException.class, () -> {
            this.cursoService.deletarCurso(1);
        });
    }

    @Test
    public void alterarCursoComSucesso() throws InsertException, IdNullException {
        Curso curso = mock(Curso.class);
        when(curso.getId()).thenReturn(1);
        when(curso.getDescricao()).thenReturn(StringUtils.SPACE);
        when(curso.getHyperLink()).thenReturn(StringUtils.SPACE);

        when(cursoDao.findById(anyInt())).thenReturn(Optional.of(curso));

        Curso cursoSalvo = this.cursoService.alterarCurso(curso);
        Assertions.assertNotNull(cursoSalvo);
        Assertions.assertNotNull(cursoSalvo.getId());
        verify(cursoDao).save(any(Curso.class));
    }

    @Test
    public void alterarCursoComBuscaVazia() throws InsertException, IdNullException {
        Curso curso = mock(Curso.class);
        when(curso.getId()).thenReturn(1);
        when(curso.getDescricao()).thenReturn(StringUtils.SPACE);
        when(curso.getHyperLink()).thenReturn(StringUtils.SPACE);

        when(cursoDao.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(IdNullException.class, () -> {
            this.cursoService.alterarCurso(curso);
        });
        verify(cursoDao).save(any(Curso.class));
    }

    @Test
    public void alterarCursoComIdNull() throws IdNullException {
        Curso curso = new Curso();
        Assertions.assertThrows(IdNullException.class, () -> {
            this.cursoService.alterarCurso(curso);
        });
    }

    @Test
    public void alterarCursoComDescricaoVazia() throws InsertException {
        Curso mockCurso = mock(Curso.class);
        when(mockCurso.getDescricao()).thenReturn(StringUtils.EMPTY);
        Assertions.assertThrows(InsertException.class, () -> {
            this.cursoService.alterarCurso(mockCurso);
        });
    }

    @Test
    public void alterarCursoComHiperlinkVazio() throws InsertException {
        Curso mockCurso = mock(Curso.class);
        when(mockCurso.getHyperLink()).thenReturn(StringUtils.EMPTY);
        Assertions.assertThrows(InsertException.class, () -> {
            this.cursoService.alterarCurso(mockCurso);
        });
    }

    @Test
    public void alterarCursoComErroDeBanco() throws IdNullException {
        Curso mockCurso = mock(Curso.class);
        when(mockCurso.getHyperLink()).thenReturn(StringUtils.SPACE);
        when(mockCurso.getDescricao()).thenReturn(StringUtils.SPACE);
        when(cursoDao.findById(anyInt())).thenThrow(RuntimeException.class);
        Assertions.assertThrows(IdNullException.class, () -> {
            this.cursoService.alterarCurso(mockCurso);
        });
    }
}
