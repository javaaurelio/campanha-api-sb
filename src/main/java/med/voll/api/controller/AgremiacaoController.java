package med.voll.api.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import med.voll.api.domain.agremiacao.Agremiacao;
import med.voll.api.domain.agremiacao.AgremiacaoRepository;
import med.voll.api.domain.agremiacao.DadosCadastroAgremiacao;
import med.voll.api.domain.apresentacao.ApresentacaoRepository;
import med.voll.api.domain.apresentacao.DadosDescricaoTabelaAgremiacao;
import med.voll.api.domain.usuario.Usuario;

@RestController
@RequestMapping("/agremiacao")
public class AgremiacaoController {

	@Autowired
    AgremiacaoRepository agremiacaoRepository;
	
	@Autowired
	ApresentacaoRepository apresentacaoRepository;
	
    @PostMapping
    @Transactional
    public ResponseEntity<String> registrar(@RequestBody @Valid DadosCadastroAgremiacao dadosCadastroAgremiacao, HttpServletRequest request) {
    	
    	Agremiacao entity = new Agremiacao(dadosCadastroAgremiacao);
    	entity.setDataHorasCadastro(LocalDateTime.now());
    	agremiacaoRepository.save(entity);
    	return ResponseEntity.ok("{}");
    }
    
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<String> atuaizar(@PathVariable Long id, @RequestBody @Valid DadosCadastroAgremiacao dadosCadastroAgremiacao, HttpServletRequest request) {
    	
    	Usuario usuarioLogado = (Usuario)request.getAttribute("usuarioLogado");
    	
    	Agremiacao entity = new Agremiacao(dadosCadastroAgremiacao);
    	entity.setId(id);
    	entity.setDataHorasAtualizacao(LocalDateTime.now());
    	agremiacaoRepository.save(entity);
    	return ResponseEntity.ok("{}");
    }
    
    @GetMapping()
    public ResponseEntity<Page<DadosCadastroAgremiacao>> listarPaginada(@PageableDefault(size = 3, sort = {"agremiacao"}) Pageable paginacao, 
    		HttpServletRequest request) {
    	
//    	var page = agremiacaoRepository.findAll(paginacao).map(DadosCadastroAgremiacao::new);
    	
    	var page = agremiacaoRepository.findAll(paginacao).map( agremiacao -> {
    		List<DadosDescricaoTabelaAgremiacao> dadosGeraisAgremiacaoTabela = 
    				apresentacaoRepository.dadosGeraisAgremiacaoTabela(agremiacao.getId());
    		
			DadosCadastroAgremiacao dto = new DadosCadastroAgremiacao(agremiacao, parseText(dadosGeraisAgremiacaoTabela));
            return dto;
        } );
    	
        return ResponseEntity.ok(page);
    }
    
    @GetMapping("/resumo/apresentacao/{idAgremiacao}")
    public List<DadosDescricaoTabelaAgremiacao> dadosGeraisAgremiacaoResmuo(@PathVariable Long idAgremiacao, HttpServletRequest request) {
    	
    	List<DadosDescricaoTabelaAgremiacao> dadosGeraisAgremiacaoTabela = apresentacaoRepository.dadosGeraisAgremiacaoTabela(idAgremiacao);
    	
    	return dadosGeraisAgremiacaoTabela;
    }
    
    
    private String parseText(List<DadosDescricaoTabelaAgremiacao> dadosGeraisAgremiacaoTabela) {
    	
    	StringBuffer sb = new StringBuffer();
    	for (DadosDescricaoTabelaAgremiacao dadosDescricaoTabelaAgremiacao : dadosGeraisAgremiacaoTabela) {
    		sb.append(dadosDescricaoTabelaAgremiacao.getEstado() + "|");
    		sb.append(dadosDescricaoTabelaAgremiacao.getEnsaio() + "|");
    		sb.append(dadosDescricaoTabelaAgremiacao.getDataApresentacao() + "|");
    		sb.append("Qtd Perguntas: ");
    		sb.append(dadosDescricaoTabelaAgremiacao.getQtdPerguntas() + " <br>");
		}
    	return sb.toString();
    }
    
    
    @GetMapping(path = "lista")
    public List<DadosCadastroAgremiacao> listar(HttpServletRequest request) {
    	
    	List<DadosCadastroAgremiacao>  listaAgremiacao = agremiacaoRepository.findAll().stream().map(item -> new DadosCadastroAgremiacao(item)).collect(Collectors.toList());
    	return listaAgremiacao;
    }
    
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deletar(@PathVariable Long id, HttpServletRequest request) {
    	agremiacaoRepository.deleteById(id);
    	return ResponseEntity.ok("{}");
    }
    
    @GetMapping("/{id}")
    public DadosCadastroAgremiacao obter(@PathVariable Long id, HttpServletRequest request) {
    	
    	Agremiacao referenceById = agremiacaoRepository.getReferenceById(id);
    	return new DadosCadastroAgremiacao(referenceById);
    }
    
}
