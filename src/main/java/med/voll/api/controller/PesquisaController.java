package med.voll.api.controller;

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
import med.voll.api.domain.evento.Evento;
import med.voll.api.domain.evento.EventoRepository;
import med.voll.api.domain.pesquisa.DadosCadastroPesquisa;
import med.voll.api.domain.pesquisa.DadosListagemPesquisa;
import med.voll.api.domain.pesquisa.Pesquisa;
import med.voll.api.domain.pesquisa.PesquisaRepository;

@RestController
@RequestMapping("pesquisa")
public class PesquisaController {

	@Autowired
	private PesquisaRepository pesquisaRepository;
	
	@Autowired
	private EventoRepository eventoRepository;

	@PostMapping
	@Transactional
	public void cadastrar(@RequestBody @Valid DadosCadastroPesquisa pesquisa) {
		
		Evento byId = eventoRepository.getReferenceById(pesquisa.idEvento());
		
		Pesquisa entity = new Pesquisa(pesquisa);
		entity.setEvento(byId);
		pesquisaRepository.save(entity);
	}
	
	@PutMapping
	@Transactional
	public void atualizar(@RequestBody @Valid DadosCadastroPesquisa pesquisa) {
		
		Evento byId = eventoRepository.getReferenceById(pesquisa.idEvento());
		
		Pesquisa entity = new Pesquisa(pesquisa);
		entity.setEvento(byId);
		pesquisaRepository.save(entity);
	}

	@GetMapping
	public Page<DadosListagemPesquisa> listar(@PageableDefault(size = 10, sort = { "ordem" }) Pageable paginacao) {
		return pesquisaRepository.findAll(paginacao).map( item -> new DadosListagemPesquisa(item, null));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public void delete(@PathVariable Long id) {

		pesquisaRepository.deleteById(id);
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
