package med.voll.api.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import med.voll.api.domain.agremiacao.Agremiacao;
import med.voll.api.domain.apresentacao.Apresentacao;
import med.voll.api.domain.apresentacao.ApresentacaoRepository;
import med.voll.api.domain.apresentacao.DadosCadastroApresentacao;
import med.voll.api.domain.ensaio.DadosCadastroEnsaio;
import med.voll.api.domain.ensaio.Ensaio;
import med.voll.api.domain.usuario.Usuario;

@RestController
@RequestMapping("/apresentacao")
public class ApresentacaoController {

	@Autowired
	ApresentacaoRepository apresentacaoRepository;
	
    @PostMapping
    @Transactional
    public ResponseEntity<DadosCadastroApresentacao> registrar(@RequestBody @Valid DadosCadastroApresentacao cadastroApresentacao, HttpServletRequest request) {
    	
    	Apresentacao entity = new Apresentacao(cadastroApresentacao);
    	entity.setDataHoraCadastro(LocalDateTime.now());
    	
    	try {
    		apresentacaoRepository.save(entity);
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Falhar!");
    	}
    	
    	return ResponseEntity.ok(new DadosCadastroApresentacao(entity));
    }
    
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<String> atuaizar(@PathVariable Long id, @RequestBody @Valid DadosCadastroApresentacao cadastroApresentacao, HttpServletRequest request) {
    	
    	Usuario usuarioLogado = (Usuario)request.getAttribute("usuarioLogado");
    	
    	Apresentacao referenceById = apresentacaoRepository.getReferenceById(id);

    	referenceById.setDataApresentacao( LocalDate.parse(cadastroApresentacao.dataApresentacao()));
    	referenceById.setEnsaio(new Ensaio());
    	referenceById.getEnsaio().setId(cadastroApresentacao.codEnsaio());
    	referenceById.setEstado(cadastroApresentacao.estado());
    	
    	apresentacaoRepository.save(referenceById);
    	return ResponseEntity.ok("{}");
    }
    
    @GetMapping(path = "listaPaginada/agremiacao/{codAgremiacao}")
    public ResponseEntity<Page<DadosCadastroApresentacao>> listarEnsaioPaginado(@PageableDefault(size = 3, sort = {"dataApresentacao"}) Pageable paginacao, 
    		HttpServletRequest request, 
    		@PathVariable Long codAgremiacao, 
    		@RequestParam(name = "estado", required = false) String estado, 
    		@RequestParam(name = "data", required = false) String data, 
    		@RequestParam(name = "codEnsaio", required = false) Long codEnsaio) {
    	
    	Apresentacao apresentacao =  new Apresentacao();
    	apresentacao.setAgremiacao(new Agremiacao());
    	apresentacao.getAgremiacao().setId(codAgremiacao);
    	
    	if (codEnsaio != null) {
    		apresentacao.setEnsaio(new Ensaio());
    		apresentacao.getEnsaio().setId(codEnsaio);
    	}
    	if (estado != null && estado != "") {
    		apresentacao.setEstado(estado);
    	}
    			
    	Example<Apresentacao> example = Example.of(apresentacao);  
    	
    	var page = apresentacaoRepository.findAll(example, paginacao).map(DadosCadastroApresentacao::new);
        return ResponseEntity.ok(page);
    }
    
    @GetMapping(path = "lista")
    public List<DadosCadastroApresentacao> listarApresentacao(HttpServletRequest request) {
    	List<DadosCadastroApresentacao>  ensaios = apresentacaoRepository.findAll().stream().map(item -> new DadosCadastroApresentacao(item)).collect(Collectors.toList());
    	return ensaios;
    }
    
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deletarApresentacao(@PathVariable Long id, HttpServletRequest request) {
    	apresentacaoRepository.deleteById(id);
    	return ResponseEntity.ok("{}");
    }
    
    @GetMapping("/{id}")
    public DadosCadastroApresentacao obterDadosApresentacao(@PathVariable Long id, HttpServletRequest request) {
    	
    	return new DadosCadastroApresentacao(apresentacaoRepository.getReferenceById(id));
    }
    
}
