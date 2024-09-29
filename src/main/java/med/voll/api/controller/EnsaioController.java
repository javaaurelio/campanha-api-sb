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
import med.voll.api.domain.ensaio.DadosCadastroEnsaio;
import med.voll.api.domain.ensaio.Ensaio;
import med.voll.api.domain.ensaio.EnsaioRepository;
import med.voll.api.domain.pesquisa.DadosListagemPesquisa;
import med.voll.api.domain.usuario.Usuario;

@RestController
@RequestMapping("/ensaio")
public class EnsaioController {

	@Autowired
	EnsaioRepository ensaioRepository;
	
    @PostMapping
    @Transactional
    public ResponseEntity<String> registrar(@RequestBody @Valid DadosCadastroEnsaio cadastroEnsaio, HttpServletRequest request) {
    	
    	Ensaio entity = new Ensaio(cadastroEnsaio);
    	entity.setDataHorasCadastro(LocalDateTime.now());
    	ensaioRepository.save(entity);
    	
    	return ResponseEntity.ok("{}");
    }
    
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<String> atuaizar(@PathVariable Long id, @RequestBody @Valid DadosCadastroEnsaio cadastroEnsaio, HttpServletRequest request) {
    	
    	Usuario usuarioLogado = (Usuario)request.getAttribute("usuarioLogado");
    	
    	Ensaio entity = new Ensaio(cadastroEnsaio);
    	entity.setId(id);
    	entity.setDataHorasAtualizacao(LocalDateTime.now());
		ensaioRepository.save(entity);
    	return ResponseEntity.ok("{}");
    }
    
    @GetMapping()
    public ResponseEntity<Page<DadosCadastroEnsaio>> listarEnsaioPaginado(@PageableDefault(size = 3, sort = {"nome"}) Pageable paginacao, 
    		HttpServletRequest request) {
    	
    	var page = ensaioRepository.findAll(paginacao).map(DadosCadastroEnsaio::new);
        return ResponseEntity.ok(page);
    }
    
    @GetMapping(path = "lista")
    public List<DadosCadastroEnsaio> listarEnsaio(HttpServletRequest request) {
    	List<DadosCadastroEnsaio>  ensaios = ensaioRepository.findAll().stream().map(item -> new DadosCadastroEnsaio(item)).collect(Collectors.toList());
    	return ensaios;
    }
    
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deletarEnsaio(@PathVariable Long id, HttpServletRequest request) {
    	ensaioRepository.deleteById(id);
    	return ResponseEntity.ok("{}");
    }
    
    @GetMapping("/{id}")
    public DadosCadastroEnsaio obterDadosCadastroUsuario(@PathVariable Long id, HttpServletRequest request) {
    	
    	Ensaio referenceById = ensaioRepository.getReferenceById(id);
    	return new DadosCadastroEnsaio(referenceById);
    }
    
}
