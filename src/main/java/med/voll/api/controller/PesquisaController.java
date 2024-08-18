package med.voll.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import med.voll.api.domain.evento.Evento;
import med.voll.api.domain.evento.EventoRepository;
import med.voll.api.domain.pesquisa.DadosCadastroPesquisa;
import med.voll.api.domain.pesquisa.DadosListagemPesquisa;
import med.voll.api.domain.pesquisa.Pesquisa;
import med.voll.api.domain.pesquisa.PesquisaRepository;

@RestController
@RequestMapping("pesquisa")
public class PesquisaController {
	private static final Logger LOG = LoggerFactory.getLogger(PesquisaController.class);

	@Autowired
	private PesquisaRepository pesquisaRepository;
	
	@Autowired
	private EventoRepository eventoRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<String> cadastrar(@RequestBody @Valid DadosCadastroPesquisa pesquisa) {
		
		Evento byId = eventoRepository.getReferenceById(pesquisa.idEvento());
		
		Pesquisa entity = new Pesquisa(pesquisa);
		entity.setEvento(byId);
		pesquisaRepository.save(entity);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<String> atualizar(@RequestBody @Valid DadosCadastroPesquisa pesquisa) {
		
		Evento byId = eventoRepository.getReferenceById(pesquisa.idEvento());
		
		Pesquisa entity = new Pesquisa(pesquisa);
		entity.setEvento(byId);
		pesquisaRepository.save(entity);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/ordem/{id}/up")
	@Transactional
	public ResponseEntity<String> atualizarOrdemUP(@PathVariable Long id) {
		
		Pesquisa entity = pesquisaRepository.getReferenceById(id);
		int ordemAtual = entity.getOrdem();
		
		List<Pesquisa> allByEventoIdOrderByOrdemAsc = pesquisaRepository.findAllByEventoIdOrderByOrdemAsc(entity.getEvento().getId());
			
			int novaOrdem = 0;
			if (allByEventoIdOrderByOrdemAsc.getFirst().getOrdem() == ordemAtual) {
				for (Pesquisa pesquisa : allByEventoIdOrderByOrdemAsc) {
					novaOrdem++;
					pesquisa.setOrdem(novaOrdem);
				}	
				pesquisaRepository.saveAll(allByEventoIdOrderByOrdemAsc);
			} else {
				
				Pesquisa entityOrdemQueDesejo = pesquisaRepository.findByEventoIdAndOrdem(entity.getEvento().getId(), ordemAtual-1);
				if (entityOrdemQueDesejo != null) {
					entity.setOrdem(entityOrdemQueDesejo.getOrdem());
					entityOrdemQueDesejo.setOrdem(ordemAtual);
					pesquisaRepository.save(entity);
					pesquisaRepository.save(entityOrdemQueDesejo);
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
		
		Pesquisa entity = pesquisaRepository.getReferenceById(id);
		int ordemAtual = entity.getOrdem();
		Pesquisa entityOrdemQueDesejo = pesquisaRepository.findByEventoIdAndOrdem(entity.getEvento().getId(), ordemAtual+1);
		if (entityOrdemQueDesejo!=null) {
			entity.setOrdem(entityOrdemQueDesejo.getOrdem());
			entityOrdemQueDesejo.setOrdem(ordemAtual);
			pesquisaRepository.save(entity);
			pesquisaRepository.save(entityOrdemQueDesejo);
		} else {
			LOG.info("Alteracao de ordem nao realizada! Pesquisa {} ja esta na ultima possicao!", new Object[]{id});
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Alteracao de ordem nao realizada! Pesquisa ja esta na primeira possicao!");
		}
		
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{idEvento}")
	public List<DadosListagemPesquisa> listar(@PathVariable Long idEvento) {
		List<Pesquisa> allByEventoIdOrderByOrdemAsc = pesquisaRepository.findAllByEventoIdOrderByOrdemAsc(idEvento);
		List<DadosListagemPesquisa> map = allByEventoIdOrderByOrdemAsc.stream().map(
				item -> new DadosListagemPesquisa(item, null)).collect(Collectors.toList());
		return map;
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<String> delete(@PathVariable Long id) {
		
		Pesquisa entity = pesquisaRepository.getReferenceById(id);
		pesquisaRepository.deleteById(id);
		
		List<Pesquisa> allByEventoIdOrderByOrdemAsc = pesquisaRepository.findAllByEventoIdOrderByOrdemAsc(entity.getEvento().getId());
		int novaOrdem = 0;
		for (Pesquisa pesquisa : allByEventoIdOrderByOrdemAsc) {
			novaOrdem++;
			pesquisa.setOrdem(novaOrdem);
		}	
		pesquisaRepository.saveAll(allByEventoIdOrderByOrdemAsc);
		return ResponseEntity.noContent().build();
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
