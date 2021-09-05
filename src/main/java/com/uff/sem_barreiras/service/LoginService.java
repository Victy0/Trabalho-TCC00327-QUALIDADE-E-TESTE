package com.uff.sem_barreiras.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.uff.sem_barreiras.dao.EmpresaDao;
import com.uff.sem_barreiras.dto.LoginObject;
import com.uff.sem_barreiras.dto.ResponseObject;
import com.uff.sem_barreiras.exceptions.IdNullException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    //enviar codigo de verificação por e-mail
    //para realização do trabalho, além de enviar por e-mail mostra na mensagem o código de acesso
    public ResponseObject enviarCodigoVerificacao(LoginObject login) throws IdNullException
    {
        Integer id = this.empresaDao.getIdByEmail( login.getEmail() );

        if( id == null )
        {
            return new ResponseObject(false, "Empresa não cadastrada");
        }

        Long milis = new Date().getTime();
        controleLogin.put(id, milis); 

        String cod = milis.toString().substring(milis.toString().length() - 4, milis.toString().length());

        String content = String.format("E-mail enviado devido a solicitação de login em Sem Barreiras\n \n Código de verificação: %s", cod );

        try
        {
            this.emailService.enviar( login.getEmail(), "Sem Barreiras - Código de verificação", content);
        }
        catch( Exception e )
        {
            return new ResponseObject(false, "Problema para identificar e-mail da empresa, contate o suporte");
        }

        return new ResponseObject(true, "Código de verificação enviado por e-mail | Código : " + cod );
    }

    //validação do código enviado por e-mail
    public ResponseObject confirmarCodigoVerificacao(String email, String codigo, HttpSession session)
    {
        Integer id = this.empresaDao.getIdByEmail( email );

        if( controleLogin.containsKey( id ) )
        {
            Long milis = controleLogin.get( id );
            if( milis.toString().substring(milis.toString().length() - 4, milis.toString().length()).equals( codigo ) ) 
            {
                controleLogin.remove( id );
                session.setAttribute( "login", id );
                return new ResponseObject( true, "Autentificação concluída com sucesso" );
            } 
        }

        return new ResponseObject(false, "Erro de autentificação");
    }

    // limpador dos códigos enviados e em espera no servido para validação
    @Scheduled( cron = "${cronSchedule.limpaControleLogin:-}", zone = "${cronSchedule.timeZone:-}" )
    private void limpaControleLogin()
    {
        List<Integer> listaDeKeyParaRemover = new ArrayList<Integer>();
        Long milis = new Date().getTime();
        for (Map.Entry<Integer, Long> entry : controleLogin.entrySet()) 
        {
            long difference = milis - entry.getValue();
            if(difference > 600000)
            {
                listaDeKeyParaRemover.add(entry.getKey());
            }
        }
        for(Integer keyParaRemover : listaDeKeyParaRemover)
        {
            controleLogin.remove(keyParaRemover);
        }
    }

    @Autowired
    private EmpresaDao empresaDao;

    @Autowired
    private EmailService emailService;

    private Map<Integer, Long> controleLogin = new HashMap<Integer, Long>();
    
}
