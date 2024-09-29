package med.voll.api.controller;

import java.lang.reflect.Executable;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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

import jakarta.validation.Valid;
import med.voll.api.domain.apresentacao.Apresentacao;
import med.voll.api.domain.apresentacao.ApresentacaoRepository;
import med.voll.api.domain.pergunta.DadosCadastroPergunta;
import med.voll.api.domain.pergunta.DadosListagemPergunta;
import med.voll.api.domain.pergunta.Pergunta;
import med.voll.api.domain.pergunta.PerguntaRepository;

@RestController
@RequestMapping("pergunta")
public class PerguntaController {
	private static final Logger LOG = LoggerFactory.getLogger(PerguntaController.class);

	@Autowired
	private PerguntaRepository perguntaRepository;
	
	@Autowired
	private ApresentacaoRepository apresentacaoRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<String> cadastrar(@RequestBody @Valid DadosCadastroPergunta pergunta) {
		
		Apresentacao byId = apresentacaoRepository.getReferenceById(pergunta.idApresentacao());
		
		Pergunta entity = new Pergunta(pergunta);
		entity.setPergunta(StringUtils.capitalize(entity.getPergunta().toLowerCase().trim()));
		entity.setApresentacao(byId);
		
		List<Pergunta> allByApresentacaoIdAndPergunta = perguntaRepository.findAllByApresentacaoIdAndPergunta(byId.getId(), StringUtils.capitalize(pergunta.pergunta().toLowerCase().trim()));
		if (allByApresentacaoIdAndPergunta.isEmpty()) {
			perguntaRepository.save(entity);
		} else {
			LOG.info("Pergunta ja existem !", new Object[]{byId.getId(), StringUtils.capitalize(pergunta.pergunta().trim()) });
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pergunta ja existem ! " + byId.getId() + " "+ StringUtils.capitalize(pergunta.pergunta().trim()));
		}
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/ordem/{id}/up")
	@Transactional
	public ResponseEntity<String> atualizarOrdemUP(@PathVariable Long id) {
		
		Pergunta entity = perguntaRepository.getReferenceById(id);
		int ordemAtual = entity.getOrdem();
		
		List<Pergunta> allByEventoIdOrderByOrdemAsc = perguntaRepository.findAllByApresentacaoIdOrderByOrdemAsc(entity.getApresentacao().getId());
			
			int novaOrdem = 0;
			if (allByEventoIdOrderByOrdemAsc.getFirst().getOrdem() == ordemAtual) {
				for (Pergunta pesquisa : allByEventoIdOrderByOrdemAsc) {
					novaOrdem++;
					pesquisa.setOrdem(novaOrdem);
				}	
				perguntaRepository.saveAll(allByEventoIdOrderByOrdemAsc);
			} else {
				
				Pergunta entityOrdemQueDesejo = perguntaRepository.findByApresentacaoIdAndOrdem(entity.getApresentacao().getId(), ordemAtual-1);
				if (entityOrdemQueDesejo != null) {
					entity.setOrdem(entityOrdemQueDesejo.getOrdem());
					entityOrdemQueDesejo.setOrdem(ordemAtual);
					perguntaRepository.save(entity);
					perguntaRepository.save(entityOrdemQueDesejo);
				} else {
					LOG.info("Alteracao de ordem nao realizada! Pesquisa {} ja esta na primeira possicao!", new Object[]{id});
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Alteracao de ordem nao realizada! Pesquisa ja esta na primeira possicao!");
				}
			}
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/ordem/{id}/down")
	@Transactional
	public ResponseEntity<String> atualizarOrdemDown(@PathVariable Long id) {
		
		Pergunta entity = perguntaRepository.getReferenceById(id);
		int ordemAtual = entity.getOrdem();
		Pergunta entityOrdemQueDesejo = perguntaRepository.findByApresentacaoIdAndOrdem(entity.getApresentacao().getId(), ordemAtual+1);
		if (entityOrdemQueDesejo!=null) {
			entity.setOrdem(entityOrdemQueDesejo.getOrdem());
			entityOrdemQueDesejo.setOrdem(ordemAtual);
			perguntaRepository.save(entity);
			perguntaRepository.save(entityOrdemQueDesejo);
		} else {
			LOG.info("Alteracao de ordem nao realizada! Pesquisa {} ja esta na ultima possicao!", new Object[]{id});
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Alteracao de ordem nao realizada! Pesquisa ja esta na primeira possicao!");
		}
		
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{idApresentacao}")
	public List<DadosListagemPergunta> listarPerguntasPelaApresentacao(@PathVariable Long idApresentacao) {

		List<Pergunta> allByEventoIdOrderByOrdemAsc = perguntaRepository.findAllByApresentacaoIdOrderByOrdemAsc(idApresentacao);
		List<DadosListagemPergunta> map = allByEventoIdOrderByOrdemAsc.stream().map(
				item -> new DadosListagemPergunta(item, null)).collect(Collectors.toList());
		return map;
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<String> delete(@PathVariable Long id) {
		
		Pergunta entity = perguntaRepository.getReferenceById(id);
		perguntaRepository.deleteById(id);
		
		List<Pergunta> allByEventoIdOrderByOrdemAsc = perguntaRepository.findAllByApresentacaoIdOrderByOrdemAsc(entity.getApresentacao().getId());
		int novaOrdem = 0;
		for (Pergunta pesquisa : allByEventoIdOrderByOrdemAsc) {
			novaOrdem++;
			pesquisa.setOrdem(novaOrdem);
		}	
		perguntaRepository.saveAll(allByEventoIdOrderByOrdemAsc);
		return ResponseEntity.noContent().build();
	}

}
