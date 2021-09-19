package com.uff.sem_barreiras.service;

import com.uff.sem_barreiras.dao.EmpresaDao;
import com.uff.sem_barreiras.dto.ResponseObject;
import com.uff.sem_barreiras.exceptions.AlredyExistsException;
import com.uff.sem_barreiras.exceptions.IdNullException;
import com.uff.sem_barreiras.exceptions.InsertException;
import com.uff.sem_barreiras.exceptions.InsertWithAttributeException;
import com.uff.sem_barreiras.exceptions.NotFoundException;
import com.uff.sem_barreiras.model.Empresa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class EmpresaService {

    // listar todos os empresas
    public Page<Empresa> listarEmpresas( Specification<Empresa> spec, final Pageable page ) 
    {
        return this.empresaDao.findAll(spec, page);
    }

    // encontrar empresa pelo id
    public Empresa encontrarEmpresa(Integer id) throws NotFoundException 
    {
        Empresa empresa = null;
        if(id == null)
        {
            throw new NotFoundException("Empresa", id);
        }
        else
        {
            Optional <Empresa> empresaOptional;
            try
            {
                empresaOptional = this.empresaDao.findById(id);
            }
            catch(final Exception e)
            {
                throw new NotFoundException("Empresa", id);
            }

            if(!empresaOptional.isPresent())
            {
                throw new NotFoundException("Empresa", id);
            }
            empresa = empresaOptional.get();
        }
        return empresa;
    }

    // salvar empresa
    public Empresa criarEmpresa(Empresa empresa) throws InsertException, AlredyExistsException, InsertWithAttributeException 
    {   
        if(empresa == null || empresa.getEmail() == null)
        {
            throw new InsertException( "a Empresa" );
        }
        else
        {
            Integer id = this.empresaDao.getIdByEmail(empresa.getEmail());
            if(id != null )
            {
                throw new AlredyExistsException("Empresa com e-mail " + empresa.getEmail() + "já se encontra cadastrado no sistema!");
            }

            String campoObrigatoriofaltando = this.campoObrigatorioFaltando(empresa, false);
            if( campoObrigatoriofaltando != null)
            {
                throw new InsertWithAttributeException( "a Empresa", campoObrigatoriofaltando );
            }
            else
            {
                try
                {
                    this.empresaDao.save(empresa);
                }
                catch(Exception e)
                {
                    throw new InsertException( "a Empresa" );
                }
            }
        }
        return empresa;
    }

    // deletar empresa
    @Transactional
    public ResponseObject deletarEmpresa(Integer id) throws NotFoundException 
    {
        if(id == null || !empresaDao.findById(id).isPresent())
        {
            throw new NotFoundException("Empresa", id);
        }
        else
        {
            try
            {
                this.empresaDao.deleteVagasDaEmpresa(id);
                this.empresaDao.deleteById(id);
            }
            catch(final Exception e)
            {
                throw new NotFoundException("Empresa", id);
            }
        }
        return new ResponseObject(true, "Empresa removida com sucesso");
    }

    // alterar empresa
    public Empresa alterarEmpresa(Empresa empresa) throws IdNullException, InsertWithAttributeException, InsertException
    { 
        //verificação dupla de troca de e-mail no FRONT-END
        if(empresa == null || empresa.getId() == null)
        {
            throw new IdNullException( "a Empresa" );
        }
        else
        {
            String campoObrigatoriofaltando = this.campoObrigatorioFaltando(empresa, true);
            if( campoObrigatoriofaltando != null)
            {
                throw new InsertWithAttributeException( "a Empresa", campoObrigatoriofaltando );
            }
            else
            {
                try
                {
                    this.empresaDao.save(empresa);
                }
                catch(Exception e)
                {
                    throw new InsertException( "a Empresa" );
                }  
            }
        }       
        return empresa;
    }

    // validar campos obrigatórios de empresa
    public String campoObrigatorioFaltando(Empresa empresa, boolean alteracao)
    {
        if( alteracao && empresa.getId() == null )
        {
            return "id";
        }

        if( empresa.getEmail() == "" || empresa.getEmail() == null )
        {
            return "email";
        }
        
        if( empresa.getNome() == "" || empresa.getNome() == null )
        {
            return "nome";
        }

        if( empresa.getCnpj() == "" || empresa.getCnpj() == null )
        {
            return "cnpj";
        }

        if( empresa.getEndereco() == "" || empresa.getEndereco() == null )
        {
            return "endereço";
        }

        if( empresa.getTelefone() == "" || empresa.getTelefone() == null )
        {
            return "telefone";
        }

        if( empresa.getCidade() == null )
        {
            return "cidade";
        }

        return null;
    }

    @Autowired
    private EmpresaDao empresaDao;
}
