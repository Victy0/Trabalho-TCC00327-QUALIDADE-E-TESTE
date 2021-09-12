package com.uff.sem_barreiras.controller;

import javax.servlet.http.HttpSession;

import com.uff.sem_barreiras.dto.LoginObject;
import com.uff.sem_barreiras.dto.ResponseObject;
import com.uff.sem_barreiras.exceptions.IdNullException;
import com.uff.sem_barreiras.service.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    // mapeamento Post para login de empresa
    @PostMapping("/empresa/login")
    public ResponseObject loginEmpresa(@RequestBody final LoginObject login  ) throws IdNullException
    {
        return this.loginService.enviarCodigoVerificacao(login);
    }

    // mapeamento Post para confirmar login de empresa por código de autentificação
    @PostMapping("/empresa/login-confirma")
    public ResponseObject loginEmpresa( @RequestBody final LoginObject login, HttpSession session  )
    {
        return this.loginService.confirmarCodigoVerificacao(login.getEmail(), login.getCodigo(), session );
    }

    // mapeamento Post para recuperar empresa na sessão
    @PostMapping("/empresa/session")
    public Object sessionEmpresa( HttpSession session  )
    {
        
        if(session.getAttribute("login") != null)
        {
            return session.getAttribute("login");
        }
        return null;
    }

    // mapeamento Post para fazer logout
    @PostMapping("/empresa/logout")
    public ResponseObject logoutEmpresa( HttpSession session  )
    {
        if(session.getAttribute("login") != null)
        {
            session.removeAttribute("login");
            return new ResponseObject(true, "Logout concluída com sucesso");
        }
        return new ResponseObject(false, "Nenhuma empresa com login efetuado");
    }

    // mapeamento Post para forçar a limpeza da variável controle login
    @PostMapping("/empresa/forcar/limpar-controlar-login")
    public void forcarLimparControleLogin()
    {
        this.loginService.limpaControleLogin();
    }

    //mapeamento Post para foraçar a inclusão manual na variável controle login
    @PostMapping("/empresa/forcar/login-manual")
    public ResponseObject loginEmpresaManual(@RequestBody final LoginObject login ) throws IdNullException
    {
        return this.loginService.adicionarManualControleLogin( login.getEmail() );
    }

    @Autowired
    LoginService loginService;
    
}
