package med.voll.api.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.api.domain.evento.DadosCadastroEvento;
import med.voll.api.domain.evento.DadosListagemEvento;
import med.voll.api.domain.evento.Evento;
import med.voll.api.domain.evento.EventoRepository;

@RestController
@RequestMapping("evento")
public class EventoController {

    @Autowired
    private EventoRepository repositoryEvento;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroEvento evento) {
    	repositoryEvento.save(new Evento(evento));
    }
    
    @PutMapping("/{id}")
    @Transactional
    public void atualizar(@RequestBody @Valid DadosCadastroEvento evento, @PathVariable Long id) {
    	
    	Evento referenceById = repositoryEvento.getReferenceById(id);
    	referenceById.setNome(evento.campanha());
    	
    	referenceById.setDataInicio(    			
    			LocalDate.parse(evento.dataInicio(), 
    					DateTimeFormatter.ofPattern("yyyy-MM-dd")) );
    	
    	referenceById.setDataFim( LocalDate.parse(evento.dataFim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")) );
    	referenceById.setImagemUrl(evento.imagemUrl());
    	
    	repositoryEvento.save(referenceById);
    }
    
    @PutMapping("/publicar/{id}/{status}")
    @Transactional
    public String publicarCampanha(@PathVariable Long id, @PathVariable String status) {
    	
    	Evento referenceById = repositoryEvento.getReferenceById(id);
    	
    	boolean publicar = status.contains("1") || status.toLowerCase().contains("true") || status.toLowerCase().contains("sim");
   		referenceById.setPublicado(publicar);
   		
   		if (publicar) {
   			referenceById.setDataHorasPublicacao(LocalDate.now());
   			referenceById.setDataHorasPublicacaoSuspensao(null);
   		} else {
   			referenceById.setDataHorasPublicacaoSuspensao(LocalDate.now());
   		}
   		
   		if (referenceById.getHash()==null || referenceById.getHash().isEmpty()) {
   			referenceById.setHash(UUID.randomUUID().toString());
   		}
    	repositoryEvento.save(referenceById);
    	
    	return referenceById.getHash();
    }
    
    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@PathVariable Long id) {
    	
    	repositoryEvento.deleteById(id);
    }

    @GetMapping
    public Page<DadosListagemEvento> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return repositoryEvento.findAll(paginacao).map(DadosListagemEvento::new);
    }
    
//
//    @PutMapping
//    @Transactional
//    public void atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados) {
//        var paciente = repository.getReferenceById(dados.id());
//        paciente.atualizarInformacoes(dados);
//    }
//
//    @DeleteMapping("/{id}")
//    @Transactional
//    public void excluir(@PathVariable Long id) {
//        var paciente = repository.getReferenceById(id);
//        paciente.excluir();
//    }


}
