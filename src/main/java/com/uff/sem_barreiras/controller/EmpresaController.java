package com.uff.sem_barreiras.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.uff.sem_barreiras.dto.LoginObject;
import com.uff.sem_barreiras.dto.ResponseObject;
import com.uff.sem_barreiras.exceptions.AlredyExistsException;
import com.uff.sem_barreiras.exceptions.IdNullException;
import com.uff.sem_barreiras.exceptions.InsertException;
import com.uff.sem_barreiras.exceptions.InsertWithAttributeException;
import com.uff.sem_barreiras.exceptions.NotFoundException;
import com.uff.sem_barreiras.filter.DefaultFilter;
import com.uff.sem_barreiras.model.Empresa;
import com.uff.sem_barreiras.service.EmpresaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmpresaController {

    // mapeamento Get para listar todos os empresas
    @GetMapping("/empresa")
    public Page<Empresa> listarEmpresas( @PageableDefault( size = 50, sort = "nome" ) final Pageable page, final HttpServletRequest request ) 
    {
        Specification<Empresa> spec = new DefaultFilter<Empresa>( new HashMap<String, String[]>(request.getParameterMap() ) );

        return this.empresaService.listarEmpresas(spec, page);
    }

    // mapeamento Get para recuperar 1 empresa informando o id do mesmo
    @GetMapping("/empresa/{id}")
    public Empresa encontrarEmpresa(@PathVariable(value = "id") final Integer id) throws NotFoundException 
    {
        return this.empresaService.encontrarEmpresa(id);
    }

    // mapeamento Post para criar uma empresa
    @PostMapping("/empresa")
    public Empresa criarEmpresa(@RequestBody final Empresa empresa) throws InsertException, AlredyExistsException, InsertWithAttributeException 
    {
        return this.empresaService.criarEmpresa(empresa);
    }

    // mapeamento Delete para deletar 1 empresa informando o id do mesma
    @DeleteMapping("/empresa/{id}")
    public ResponseObject deletarEmpresa(@PathVariable(value = "id") final Integer id) throws NotFoundException 
    {
        this.empresaService.deletarEmpresa(id);
        return new ResponseObject(true, "Empresa removida com sucesso");
    }

    // mapeamento Post para login de empresa
    @PostMapping("/empresa/login")
    public ResponseObject loginEmpresa(@RequestBody final LoginObject login  )throws IdNullException
    {
        Integer id = this.empresaService.getIdByEmail(login.getEmail());

        if(id == null)
        {
            return new ResponseObject(false, "Empresa não cadastrada");
        }

        String cod = this.empresaService.enviarCodigoVerificacao(login.getEmail());

        return new ResponseObject(true, "Código de verificação enviado por e-mail | Código : " + cod );

    }

    // mapeamento Post para confirmar login de empresa por código de autentificação
    @PostMapping("/empresa/login-confirma")
    public ResponseObject loginEmpresa( @RequestBody final LoginObject login, HttpSession session  )
    {
        
        if(this.empresaService.confirmarCodigoVerificacao(login.getEmail(), login.getCodigo()))
        {
            session.setAttribute("login", this.empresaService.getIdByEmail(login.getEmail()));
            return new ResponseObject(true, "Autentificação concluída com sucesso");
        }

        return new ResponseObject(false, "Erro de autentificação");

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
    public ResponseObject logoutnEmpresa( HttpSession session  )
    {
        
        if(session.getAttribute("login") != null)
        {
            session.removeAttribute("login");
            return new ResponseObject(true, "Logout concluída com sucesso");
        }
        return new ResponseObject(false, "Nenhuma empresa com login efetuado");

    }

    // mapeamento Put para alterar empresa
    @PutMapping("/empresa/alterar")
    public Empresa alterarempresa(@RequestBody final Empresa empresa) throws NotFoundException, IdNullException 
    {
        return this.empresaService.alterarEmpresa(empresa);
    }



    @Autowired
    private EmpresaService empresaService;

}
