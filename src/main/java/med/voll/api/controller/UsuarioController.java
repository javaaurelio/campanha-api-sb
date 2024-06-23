package med.voll.api.controller;

import java.time.LocalDateTime;

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
import med.voll.api.domain.usuario.DadosCadastroUsuario;
import med.voll.api.domain.usuario.DadosListagemUsuario;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.domain.usuario.UsuarioRepository;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

	@Autowired
	UsuarioRepository usuarioRepository;
	
    @PostMapping
    @Transactional
    public void registrar(@RequestBody @Valid DadosCadastroUsuario dadosCadastroUsuario, HttpServletRequest request) {
    	
    	usuarioRepository.save(new Usuario(dadosCadastroUsuario));
    }
    
    @PutMapping("/imagem/perfil/{id}")
    @Transactional
    public void registrarImagemPerfil(@PathVariable Long id, @RequestBody @Valid String base64Imagem, HttpServletRequest request) {
    	
    	Usuario referenceById = usuarioRepository.getReferenceById(id);
    	referenceById.setFotoPerfil(base64Imagem.getBytes());
    	usuarioRepository.save(referenceById);
    }
    
    @GetMapping("/imagem/perfil/{id}")
    @Transactional
    public String obterImagemPerfil(@PathVariable Long id, HttpServletRequest request) {
    	
    	Usuario referenceById = usuarioRepository.getReferenceById(id);
    	
    	if (referenceById.getFotoPerfil() ==null) {
    		return new String("");
    	};
    	return new String(referenceById.getFotoPerfil());
    }
    
    @PostMapping("/selfregistration")
    @Transactional
    public void registrarSelfregistration(@RequestBody @Valid DadosCadastroUsuario dadosCadastroUsuario, HttpServletRequest request) {
    	Usuario entity = new Usuario(dadosCadastroUsuario);
    	entity.setDataHoraPreRegistro(LocalDateTime.now());
    	entity.setDataHoraRegistro(null);
		usuarioRepository.save(entity);
    }
    
    @PostMapping("/selfregistration/confirmar")
    @Transactional
    public void registrarSelfregistrationConfirmar(@RequestBody @Valid DadosCadastroUsuario dadosCadastroUsuario, HttpServletRequest request) {
    	
    	usuarioRepository.save(new Usuario(dadosCadastroUsuario));
    }
    
    @PutMapping("/{id}")
    @Transactional
    public void atualizar(@PathVariable Long id, @RequestBody @Valid DadosCadastroUsuario dadosCadastroUsuario, HttpServletRequest request) {
    	
    	Usuario entity = new Usuario(dadosCadastroUsuario);
    	entity.setId(id);
    	usuarioRepository.save(entity);
    }

    @GetMapping()
    public ResponseEntity<Page<DadosListagemUsuario>> listarUsuario(@PageableDefault(size = 3, sort = {"nome"}) Pageable paginacao, 
    		HttpServletRequest request) {
    	var page = usuarioRepository.findAll(paginacao).map(DadosListagemUsuario::new);
        return ResponseEntity.ok(page);
    }
    
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id, HttpServletRequest request) {
    	usuarioRepository.deleteById(id);
    	return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public DadosCadastroUsuario obterDadosCadastroUsuario(@PathVariable Long id, HttpServletRequest request) {
    	Usuario referenceById = usuarioRepository.getReferenceById(id);
    	return new DadosCadastroUsuario(referenceById);
    }
    
    @GetMapping("/totalPreCadastroPendente")
    public Long obterQtdPreCadastroPendente(HttpServletRequest request) {
    	
    	Long countDataHoraPreRegistroIsNotNullAndDataHoraRegistroIsNull = usuarioRepository.countByDataHoraPreRegistroIsNotNullAndDataHoraRegistroIsNull();
    	if (countDataHoraPreRegistroIsNotNullAndDataHoraRegistroIsNull==null) {
    		countDataHoraPreRegistroIsNotNullAndDataHoraRegistroIsNull = 0l;
    	}
		return countDataHoraPreRegistroIsNotNullAndDataHoraRegistroIsNull;
    }
    
}
