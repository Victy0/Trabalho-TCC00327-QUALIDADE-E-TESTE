package com.uff.sem_barreiras.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.springframework.boot.test.context.SpringBootTest;

import com.uff.sem_barreiras.dao.EscolaridadeDao;
import com.uff.sem_barreiras.exceptions.IdNullException;
import com.uff.sem_barreiras.exceptions.NotFoundException;
import com.uff.sem_barreiras.model.Escolaridade;

@SpringBootTest
public class EscolaridadeServiceTest {

	@Mock
    private EscolaridadeDao escolaridadeDao;
	
	@InjectMocks
	private EscolaridadeService escolaridadeService;
	// ************************************************************************************************************** TESTE ENCONTRAR ESCOLARIDADE
	@Test
	public void TesteEncontrarEscolaridadeComSucesso() throws NotFoundException, IdNullException  {
		Escolaridade mockEscolaridade = mock(Escolaridade.class);
		when(mockEscolaridade.getId()).thenReturn(1);
		Optional<Escolaridade> optionalEmpresa = Optional.of(mockEscolaridade);
        when(escolaridadeDao.findById(1)).thenReturn(optionalEmpresa);

        Escolaridade escolaridade = this.escolaridadeService.encontrarEscolaridade(1);
        Assertions.assertNotNull(escolaridade.getId());
        Assertions.assertEquals(Integer.valueOf(1), escolaridade.getId());
	}
	
	@Test
	public void TesteEncontrarEscolaridadeComIdNulo() throws NotFoundException, IdNullException  {
		
      
        Assertions.assertThrows(IdNullException.class, ()->{
        	this.escolaridadeService.encontrarEscolaridade(null);
        	});
        }
	
	@Test
    public void testeEncontrarEmpresaInexistente() throws NotFoundException 
    {
        when(escolaridadeDao.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> {
            this.escolaridadeService.encontrarEscolaridade(0);
        });
    }

}
