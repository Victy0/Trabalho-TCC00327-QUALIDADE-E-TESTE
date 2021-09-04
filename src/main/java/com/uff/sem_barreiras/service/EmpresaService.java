package com.uff.sem_barreiras.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.uff.sem_barreiras.dao.EmpresaDao;
import com.uff.sem_barreiras.exceptions.AlredyExistsException;
import com.uff.sem_barreiras.exceptions.IdNullException;
import com.uff.sem_barreiras.exceptions.InsertException;
import com.uff.sem_barreiras.exceptions.NotFoundException;
import com.uff.sem_barreiras.model.Empresa;
import com.uff.sem_barreiras.model.Vaga;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmpresaService {

    // listar todos os empresas
    public Page<Empresa> listarEmpresas( Specification<Empresa> spec, final Pageable page ) {
        return this.empresaDao.findAll(spec, page);
    }

    // encontrar empresa pelo id
    public Empresa encontrarEmpresa(Integer id) throws NotFoundException {
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
            catch(final Exception e){
                throw new NotFoundException("Empresa", id);
            }
            if(!empresaOptional.isPresent())
            {
                throw new NotFoundException("Empresa", id);
            }
            empresa=empresaOptional.get();
        }
      return empresa;
    }

    // encontrar vagas da empresa pelo id
    public List<Vaga> encontrarVagas(Integer id) throws NotFoundException {
        try{
            this.empresaDao.findById(id).get();
            return this.empresaDao.getVagas(id);
        }catch(final Exception e ){
            throw new NotFoundException("Empresa", id);
        }
    }

    // salvar empresa
    public Empresa criarEmpresa(Empresa empresa) throws InsertException, AlredyExistsException {
       
        if(empresa == null){
            throw new InsertException( "a Empresa" );
        }else{
            Integer id = this.empresaDao.getIdByEmail(empresa.getEmail());
            if(id != null || empresa.getNome() == "" || empresa.getCnpj() == "" || empresa.getEndereco() == "" || empresa.getTelefone()== "" ){
                throw new AlredyExistsException("Empresa com e-mail " + empresa.getEmail() + " cadastrado!");
            }else{
                    try{
                         this.empresaDao.save(empresa);
                    }catch(Exception e){
                        throw new InsertException( "a Empresa" );
                    }
            }
        }
        return empresa;
    }

    // deletar empresa
    public void deletarEmpresa(Integer id) throws NotFoundException {
        if(id == null){
            throw new NotFoundException("Curso", id);
        }else{
            
         try{
            this.empresaDao.deleteVagasDaEmpresa(id);
            this.empresaDao.deleteById(id);
         }catch(final Exception e){
             throw new NotFoundException("Empresa", id);
         }
        
      }
       
    }

    // alterar empresa
    public Empresa alterarEmpresa(Empresa empresa) throws IdNullException{
        
        if(empresa.getId() == null || empresa.getNome() == "" || empresa.getCnpj() == "" || empresa.getEndereco() == "" || empresa.getTelefone()== ""){
            throw new IdNullException("Curso");
        }else{
        this.empresaDao.save(empresa);
        Optional <Empresa> empresaOptional;
        try{
            empresaOptional = this.empresaDao.findById(empresa.getId());
        }catch(final Exception e){
            throw new  IdNullException("Curso");
        }
        if(!empresaOptional.isPresent()){
            throw new IdNullException("Curso");
        }
        empresa = empresaOptional.get();
        
        }
        return empresa;
        
    }

    public Integer getIdByEmail(String email){
        return this.empresaDao.getIdByEmail(email);
    }

    public String enviarCodigoVerificacao(String email) throws IdNullException{

        Integer id = this.empresaDao.getIdByEmail(email);
        Long milis = new Date().getTime();
        controleLogin.put(id, milis); 
        if(id == null){
            throw new IdNullException("Email;");
        }else{
        String content = String.format("E-mail enviado devido a solicitação de login em Sem Barreiras\n \n Código de verificação: %s", milis.toString().substring(milis.toString().length() - 4, milis.toString().length()) );

        this.emailService.enviar(email, "Sem Barreiras - Código de verificação", content);

        return milis.toString().substring(milis.toString().length() - 4, milis.toString().length());
        }
    }

    public Boolean confirmarCodigoVerificacao(String email, String codigo){

        Integer id = this.empresaDao.getIdByEmail(email);

        if(!controleLogin.containsKey(id)){
            return false;
        }

        Long milis = controleLogin.get(id);
        if( !milis.toString().substring(milis.toString().length() - 4, milis.toString().length()).equals(codigo)){
            return false;
        }

        controleLogin.remove(id);
        return true;
    }

    @Scheduled( cron = "${cronSchedule.limpaControleLogin:-}", zone = "${cronSchedule.timeZone:-}" )
    private void limpaControleLogin(){
        List<Integer> listaDeKeyParaRemover = new ArrayList<Integer>();
        Long milis = new Date().getTime();
        for (Map.Entry<Integer, Long> entry : controleLogin.entrySet()) {
            long difference = milis - entry.getValue();
            if(difference > 600000){
                listaDeKeyParaRemover.add(entry.getKey());
            }
        }
        for(Integer keyParaRemover : listaDeKeyParaRemover){
            controleLogin.remove(keyParaRemover);
        }
    }

    @Autowired
    private EmpresaDao empresaDao;

    @Autowired
    private EmailService emailService;

    private Map<Integer, Long> controleLogin = new HashMap<Integer, Long>();
}
