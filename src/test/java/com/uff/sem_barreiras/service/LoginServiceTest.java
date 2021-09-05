package com.uff.sem_barreiras.service;

import com.uff.sem_barreiras.dao.EmpresaDao;
import com.uff.sem_barreiras.dto.LoginObject;
import com.uff.sem_barreiras.exceptions.IdNullException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LoginServiceTest {

    @Mock
    private EmpresaDao empresaDao;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private LoginService loginService;

    // ************************************************************************************************************** TESTE ENVIAR CÓDIGO
    @Test
    public void testeEnviarCodigoComSucesso() throws IdNullException
    {
        LoginObject mockLogin = mock(LoginObject.class);
        when(mockLogin.getEmail()).thenReturn("teste");
        when(empresaDao.getIdByEmail(anyString())).thenReturn( 1 );

        Assertions.assertEquals( "Código de verificação enviado por e-mail", this.loginService.enviarCodigoVerificacao(mockLogin).getMensagem().substring(0, 40));
    }

    @Test
    public void testeEnviarCodigoEmpresaNaoCadastrada() throws IdNullException
    {
        LoginObject mockLogin = mock(LoginObject.class);
        when(mockLogin.getEmail()).thenReturn("teste");
        when(empresaDao.getIdByEmail(anyString())).thenReturn( null );

        Assertions.assertEquals( "Empresa não cadastrada", this.loginService.enviarCodigoVerificacao(mockLogin).getMensagem());
    }

    // ************************************************************************************************************** TESTE VALIDAÇÃO DO CÓDIGO
    @Test
    public void testeValidacaoCodigoComSucesso()
    {
        HttpSession mockSession = mock(HttpSession.class);
        when(empresaDao.getIdByEmail(anyString())).thenReturn( 1 );

        this.loginService.setCodigoControleLogin( 1, Long.parseLong("1630880531090"));

        Assertions.assertEquals( "Autentificação concluída com sucesso", this.loginService.confirmarCodigoVerificacao( "email", "1090", mockSession).getMensagem());
    }

    @Test
    public void testeValidacaoCodigoErrado()
    {
        HttpSession mockSession = mock(HttpSession.class);
        when(empresaDao.getIdByEmail(anyString())).thenReturn( 1 );

        Assertions.assertEquals( "Erro de autentificação", this.loginService.confirmarCodigoVerificacao( "email", "1090", mockSession).getMensagem());
    }

    @Test
    public void testeValidacaoidNull()
    {
        HttpSession mockSession = mock(HttpSession.class);
        when(empresaDao.getIdByEmail(anyString())).thenReturn( null );

        Assertions.assertEquals( "Erro de autentificação", this.loginService.confirmarCodigoVerificacao( "email", "1090", mockSession).getMensagem());
    }

    
}
