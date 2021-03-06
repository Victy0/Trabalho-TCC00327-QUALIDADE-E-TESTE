package com.uff.sem_barreiras.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.uff.sem_barreiras.dao.VagaDao;
import com.uff.sem_barreiras.dto.ResponseObject;
import com.uff.sem_barreiras.exceptions.IdNullException;
import com.uff.sem_barreiras.exceptions.InsertException;
import com.uff.sem_barreiras.exceptions.InsertWithAttributeException;
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
    public Vaga criarVaga(Vaga vaga) throws InsertException, InsertWithAttributeException
    {
        if(vaga == null)
        {
            throw new InsertException( "a Vaga" );
        }
        else
        {
            String campoObrigatoriofaltando = this.campoObrigatorioFaltando(vaga, false);
            if( campoObrigatoriofaltando != null)
            {
                throw new InsertWithAttributeException( "a Vaga", campoObrigatoriofaltando );
            }
            else
            {
                vaga.setDataCriacao(new Date());
                vaga.setDuracaoVaga( vaga.getDuracaoVaga() == null ? 30 : vaga.getDuracaoVaga() );

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
    public ResponseObject deletarVaga(Integer id) throws NotFoundException
    {
        if(id == null || !vagaDao.findById(id).isPresent())
        {
            throw new NotFoundException("Vaga", id);
        }
        else
        {
            this.vagaDao.deleteById(id);
        }
        return new ResponseObject(true, "Vaga removida com sucesso");
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
    public Vaga alterarVaga(Vaga vaga) throws IdNullException, InsertException, InsertWithAttributeException
    {
        if(vaga == null || vaga.getId() == null)
        {
            throw new IdNullException("Vaga");
        }
        else
        {
            String campoObrigatoriofaltando = this.campoObrigatorioFaltando(vaga, true);
            if( campoObrigatoriofaltando != null)
            {
                throw new InsertWithAttributeException( "a Vaga", campoObrigatoriofaltando );
            }
            else
            {
                vaga.setDataCriacao(new Date());
                vaga.setDuracaoVaga( vaga.getDuracaoVaga() == null ? 30 : vaga.getDuracaoVaga() );
                try
                {
                    this.vagaDao.save(vaga);
                }
                catch(Exception e)
                {
                    throw new InsertException( "a Vaga" );
                }  
            }
        }
        return vaga;
    }

    // realizar Candidatura ?? vaga
    public ResponseObject realizarCandidatura(String nome, String email, String telefone, Integer idVaga) throws NotFoundException 
    {
        if( (nome == null || nome == "") )
        {
            return new ResponseObject(true, "Candidatura n??o pode ser realizada! Faltam informa????o do nome candidato");
        }

        if( (email == null && telefone == null) )
        {
            return new ResponseObject(true, "Candidatura n??o pode ser realizada! Faltam informa????es de e-mail ou telefone do candidato");
        }

        Vaga vaga = this.encontrarVaga(idVaga);
        
        if(telefone == null )
        {
            telefone = "N??o informado";
        }

        if(email == null )
        {
            email = "N??o informado";
        }

        String content = String.format("O candidato %s demonstrou interesse pela vaga \"%s\" disponibilizada.\nE-mail do candidato: %s\nTelefone do candidato: %s", nome, vaga.getResumo(), email, telefone );

        try
        {
            this.emailService.enviar(vaga.getEmpresa().getEmail(), "SEM BARREIRAS INFORMA - Interesse em vaga publicada", content);
        }
        catch( Exception e )
        {
            return new ResponseObject(false, "Candidatura n??o realizada. Tente novamente");
        }

        return new ResponseObject(true, "Candidatura realizada com sucesso");
    }

    // validar campos obrigat??rios de empresa
    public String campoObrigatorioFaltando(Vaga vaga, boolean alteracao)
    {
        if( alteracao && vaga.getId() == null )
        {
            return "id";
        }

        if( vaga.getEmpresa() == null )
        {
            return "empresa";
        }

        if( vaga.getResumo() == "" || vaga.getResumo() == null )
        {
            return "resumo";
        }

        if( vaga.getDescricao() == "" || vaga.getDescricao() == null )
        {
            return "descri????o";
        }
        
        if( vaga.getFuncao() == "" || vaga.getFuncao() == null )
        {
            return "fun????o";
        }

        if( vaga.getNivel() == "" || vaga.getNivel() == null )
        {
            return "n??vel";
        }

        if( vaga.getArea() == null )
        {
            return "??rea de atua????o";
        }

        if( vaga.getEscolaridade() == null )
        {
            return "escolaridade";
        }

        return null;
    }

    // deletar vagas expiradas
    @Scheduled( cron = "${cronSchedule.vagaDeletar:-}", zone = "${cronSchedule.timeZone:-}" )
    public void deletarVagaPassado30Dias() throws NotFoundException
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

    // informar vagas que ir??o expirar
    @Scheduled( cron = "${cronSchedule.vagaExpirar:-}", zone = "${cronSchedule.timeZone:-}" )
    public void notificarVagaQueIraExperiar() throws NotFoundException
    {
        List<Integer> idsVagas = this.vagaDao.recuperaVagaPassado27Dias();

        for(Integer idVaga : idsVagas)
        {
            if( idVaga != null )
            {
                Vaga vaga = this.vagaDao.getOne(idVaga);

                String url = String.format("http://sem_bareiras.herokuapp.com/acessar-vaga/%s", vaga.getId());

                String content = String.format("A vaga %s cadastrada est?? preste a expirar em 3 dias.\nEdite a vaga caso deseje que ela n??o seja deletada\nLink da vaga: %s", vaga.getDescricao(), url );

                this.emailService.enviar(vaga.getEmpresa().getEmail(), "SEM BARREIRAS INFORMA - Vaga pr??ximo de expirar", content);

            }   
        }
    }

    

    @Autowired
    private VagaDao vagaDao;

    @Autowired
    private EmailService emailService;
}
