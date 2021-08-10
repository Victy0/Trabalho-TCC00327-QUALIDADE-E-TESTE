package com.uff.sem_barreiras.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.uff.sem_barreiras.dao.VagaDao;
import com.uff.sem_barreiras.exceptions.IdNullException;
import com.uff.sem_barreiras.exceptions.InsertException;
import com.uff.sem_barreiras.exceptions.NotFoundException;
import com.uff.sem_barreiras.model.Vaga;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class VagaService {

    //listar vagas
    public Page<Vaga> listarVagas( Specification<Vaga> spec, final Pageable page )
    {
        final Page<Vaga> base =  this.vagaDao.findAll(spec, page);
        
        return base;
    }



    // criar vaga
    public Vaga criarVaga(Vaga vaga) throws InsertException
    {
        if(vaga == null)
        {
            throw new InsertException( "a Vaga" );
        }
        else
        {
            if(vaga.getFuncao()==""||vaga.getRequisitosNecessarios()==""||vaga.getRequisitosDesejados()==""||vaga.getDuracaoVaga()==null){
                throw new InsertException( "a Vaga" );
            }else{
            vaga.setDataCriacao(new Date());

            try
            {
                this.vagaDao.save(vaga);
            }
            catch(Exception e)
            {
                throw new InsertException( "a Vaga" );
            }
            }

            return vaga;
        }
        
    }

    //deletar vaga
    public void deletarVaga(Integer id) throws NotFoundException
    {
        if(id == null)
        {
            throw new NotFoundException("Vaga", id);
        }
        else
        {
            this.vagaDao.deleteById(id);
        }
    }

    //encontrar vaga
    public Vaga encontrarVaga(Integer id) throws NotFoundException 
    {

        Vaga vaga = null;

        if(id == null)
        {
            throw new NotFoundException("Vaga", id);
        }
        else
        {
            Optional<Vaga> vagaOptional;
            try
            {
                vagaOptional = this.vagaDao.findById(id);
            }
            catch(Exception e)
            {
                throw new NotFoundException("Vaga", id);
            }

            if(!vagaOptional.isPresent())
            {
                throw new NotFoundException("Vaga", id);
            }

            vaga = vagaOptional.get();
        } 
        return vaga; 
    }

    // alterar vaga
    public Vaga alterarVaga(Vaga vaga) throws IdNullException,InsertException
    {
        if(vaga.getId() == null)
        {
            throw new IdNullException("Vaga");
        }else{
            if(vaga.getFuncao()==""||vaga.getRequisitosNecessarios()==""||vaga.getRequisitosDesejados()==""||vaga.getDuracaoVaga()==null){
                throw new InsertException( "a Vaga" );
            }else{
        this.vagaDao.save(vaga);

        return this.vagaDao.findById(vaga.getId()).get();
            }
        }
    }

    // realizar Candidatura à vaga
    public Boolean realizarCandidatura(String nome, String email, String telefone, Integer idVaga) throws NotFoundException 
    {
        Vaga vaga = this.encontrarVaga(idVaga);

        if( nome == null || (email == null && telefone == null) )
        {
            return false;
        }
        
        if(telefone == null )
        {
            telefone = "Não informado";
        }

        if(email == null )
        {
            email = "Não informado";
        }

        String content = String.format("O candidato %s demonstrou interesse pela vaga \"%s\" disponibilizada.\nE-mail do candidato: %s\nTelefone do candidato: %s", nome, vaga.getResumo(), email, telefone );

        this.emailService.enviar(vaga.getEmpresa().getEmail(), "SEM BARREIRAS INFORMA - Interesse em vaga publicada", content);

        return true;
    }

    // limpar códigos de verificação para acesso da empresa
    @Scheduled( cron = "${cronSchedule.limpaControleLogin:-}", zone = "${cronSchedule.timeZone:-}" )
    private void deletarVagaPassado30Dias() throws NotFoundException
    {
        List<Integer> idsParaRemover = this.vagaDao.recuperaVagaPassado30Dias();

        for(Integer idRemover : idsParaRemover)
        {
            if( idRemover != null )
            {
                this.deletarVaga( idRemover );
            }   
        }
    }

    // Subquery para filtro de deficiencia
    public Specification<Vaga> hasDeficiencia(final Integer deficienciaId) 
    {

        List<Integer> idList = this.vagaDao.idVagasByDeficiencia(deficienciaId);
        return ( root, query, cb ) -> {

            return root.get( "id" ).in( idList );
        };

    }

    @Autowired
    private VagaDao vagaDao;

    @Autowired
    private EmailService emailService;
}
