package med.voll.api.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import med.voll.api.domain.evento.DadosCadastroEvento;
import med.voll.api.domain.evento.DadosListagemEvento;
import med.voll.api.domain.evento.Evento;
import med.voll.api.domain.evento.EventoRepository;
import med.voll.api.service.evento.EventoService;

@RestController
@RequestMapping("evento")
public class EventoController {
	
	private static final Logger LOG = LoggerFactory.getLogger(EventoController.class);

	@Autowired
	private EventoRepository eventoRepository;
	
    @Autowired
    private EventoService eventoService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid DadosCadastroEvento evento) {
    	eventoRepository.save(new Evento(evento));
    	
    	return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<String> atualizar(@RequestBody @Valid DadosCadastroEvento evento, @PathVariable Long id) {
    	
    	Evento referenceById = eventoRepository.getReferenceById(id);
    	referenceById.setNome(evento.campanha());
    	
    	referenceById.setDataInicio(    			
    			LocalDate.parse(evento.dataInicio(), 
    					DateTimeFormatter.ofPattern("yyyy-MM-dd")) );
    	
    	referenceById.setDataFim( LocalDate.parse(evento.dataFim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")) );
    	referenceById.setImagemUrl(evento.imagemUrl());
    	referenceById.setLayoutPainelVotacao(evento.layoutPainelVotacao());
    	referenceById.setDescricao(evento.descricao());
    	
    	eventoRepository.save(referenceById);
    	return ResponseEntity.noContent().build(); 
    }
    
    @GetMapping("/{id}")
    @Transactional
    public DadosCadastroEvento obterEvento(@PathVariable Long id) {
    	
    	Evento evento = eventoRepository.getReferenceById(id);
    	return new DadosCadastroEvento(evento); 
    }
    
    @GetMapping("/publicar/status/{id}")
    @Transactional
    public boolean statusEvento(@PathVariable Long id) {
    	Evento referenceById = eventoRepository.getReferenceById(id);
    	return referenceById.isPublicado();
    }
    
    @PutMapping("/publicar/{id}/{status}")
    @Transactional
    public DadosCadastroEvento publicarCampanha(@PathVariable Long id, @PathVariable String status, HttpServletRequest request) {
    	
    	Evento referenceById = eventoRepository.getReferenceById(id);
    	
    	if (referenceById.getListaPesquisa().isEmpty()) {
    		LOG.info("Nao possui Perguntas !!", new Object[]{id});
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Evento não publicado, pois não há perguntas cadastradas !");
    	} 
    	
    	
    	boolean publicar = status.contains("1") || status.toLowerCase().contains("true") || status.toLowerCase().contains("sim");
   		referenceById.setPublicado(publicar);
   		
   		if (referenceById.getHash()==null || referenceById.getHash().isEmpty()) {
   			referenceById.setHash(UUID.randomUUID().toString());
   		}
   		
   		String url = request.getHeader("origin");
   		if (publicar) {
   			
   			referenceById.setUrlPublicacao(url+"/painelvotacao?hash=" + referenceById.getHash());
   			referenceById.setDataHorasPublicacao(LocalDate.now());
   			referenceById.setDataHorasPublicacaoSuspensao(null);
   		} else {
   			referenceById.setUrlPublicacao(url+"/painelvotacao?hash=" + referenceById.getHash());
   			referenceById.setDataHorasPublicacaoSuspensao(LocalDate.now());
   		}
   		
   		Evento save = eventoRepository.save(referenceById);
    	
    	return new DadosCadastroEvento(save);
    }
    
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> delete(@PathVariable Long id) {
    	
    	eventoRepository.deleteById(id);
    	return ResponseEntity.noContent().build(); 
    }

    @GetMapping
    public Page<DadosListagemEvento> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return eventoService.recuperarListaEventos(paginacao);
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
