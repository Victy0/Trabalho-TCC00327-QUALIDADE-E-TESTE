package com.uff.sem_barreiras.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.uff.sem_barreiras.dao.EstadoDao;
import com.uff.sem_barreiras.exceptions.IdNullException;
import com.uff.sem_barreiras.exceptions.NotFoundException;
import com.uff.sem_barreiras.model.Estado;

@SpringBootTest
public class EstadoServiceTest {
	
	@Mock
    private EstadoDao estadoDao;

    @InjectMocks
    private EstadoService estadoService;

	@Test
	public void encontrarEstadoTest() throws NotFoundException, IdNullException {
		Estado mockEstado = mock(Estado.class);
		when(mockEstado.getId()).thenReturn(1);
		Optional<Estado> optionalEstado = Optional.of(mockEstado);
		when(estadoDao.findById(1)).thenReturn(optionalEstado);
		
		Estado estado = this.estadoService.encontrarEstado(1);
		Assertions.assertNotNull(estado.getId());
		Assertions.assertEquals(Integer.valueOf(1), estado.getId());
	}

}
