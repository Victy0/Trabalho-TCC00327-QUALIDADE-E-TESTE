package com.uff.sem_barreiras.service;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.uff.sem_barreiras.dao.CursoDao;
import com.uff.sem_barreiras.exceptions.NotFoundException;
import com.uff.sem_barreiras.model.Curso;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CursoServiceTest {

    @Mock
    private CursoDao cursoDao;

    @InjectMocks
    private CursoService cursoService;

    @Test
    public void encontrarCursoComSucesso() throws NotFoundException {
        Curso mockCurso = mock(Curso.class);
        when(mockCurso.getId()).thenReturn(1);
        Optional<Curso> optionalCurso = Optional.of(mockCurso);

        when(cursoDao.findById(1)).thenReturn(optionalCurso);
        Integer idTest = 1;
        Curso curso = this.cursoService.encontrarCurso( idTest );
        Assertions.assertNotNull(curso.getId());
        Assertions.assertEquals(Integer.valueOf(1), curso.getId());
    }

    @Test
    public void encontrarCursoComIdNull() throws NotFoundException {
        Assertions.assertThrows(NotFoundException.class, () -> {this.cursoService.encontrarCurso(null);});
    }

    @Test
    public void encontrarCursoInexistente() throws NotFoundException {
        when(cursoDao.findById(anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> {this.cursoService.encontrarCurso(1);});
    }

    
    
}
