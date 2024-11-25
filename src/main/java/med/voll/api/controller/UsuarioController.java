package med.voll.api.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import med.voll.api.domain.usuario.DadosCadastroAtualizarSenha;
import med.voll.api.domain.usuario.DadosCadastroUsuario;
import med.voll.api.domain.usuario.DadosImagemPerfil;
import med.voll.api.domain.usuario.DadosListagemUsuario;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.domain.usuario.UsuarioRepository;
import med.voll.api.infra.ParametrosUtil;
import med.voll.api.infra.email.EmailServiceImpl;
import med.voll.api.service.usuario.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private EmailServiceImpl emailServiceImpl;
	
    @PostMapping
    @Transactional
    public void registrar(@RequestBody @Valid DadosCadastroUsuario dadosCadastroUsuario, HttpServletRequest request) {
    	
    	usuarioRepository.save(new Usuario(dadosCadastroUsuario));
    }
    
    @PutMapping("/logado/imagem/perfil")
    @Transactional
    public DadosImagemPerfil registrarImagemPerfil(@RequestBody @Valid String base64Imagem, HttpServletRequest request) {
    	
    	Usuario usuarioLogado = (Usuario)request.getAttribute("usuarioLogado");
    	Usuario referenceById = usuarioRepository.getReferenceById(usuarioLogado.getId());
    	referenceById.setFotoPerfil(base64Imagem.getBytes());
    	usuarioRepository.save(referenceById);
    	
    	return new DadosImagemPerfil(new String(referenceById.getFotoPerfil()));
    }
    
    @GetMapping("/imagem/perfil")
    public DadosImagemPerfil obterImagemPerfil(HttpServletRequest request) {
    	
    	Usuario usuarioLogado = (Usuario)request.getAttribute("usuarioLogado");

    	Usuario referenceById = usuarioRepository.getReferenceById(usuarioLogado.getId());
    	if (referenceById.getFotoPerfil() ==null) {
    		return new DadosImagemPerfil("");
    	};
    	return new DadosImagemPerfil(new String(referenceById.getFotoPerfil()));
    }
    
    @PostMapping("/selfregistration")
    @Transactional
    public ResponseEntity<String> registrarSelfregistration(@RequestBody @Valid DadosCadastroUsuario dadosCadastroUsuario, HttpServletRequest request) {

    	Usuario entity = new Usuario(dadosCadastroUsuario);
    	entity.setNome(dadosCadastroUsuario.nome());
    	entity.setSenha(new BCryptPasswordEncoder().encode(entity.getSenha()));
    	entity.setDataHoraPreRegistro(LocalDateTime.now());
    	entity.setDataHoraRegistro(null);
    	entity.setAtivo(true);
    	entity.setHash(UUID.randomUUID().toString());
//    	emailServiceImpl.sendSimpleMessage(ParametrosUtil.get("email-adm"), "URL?/selfregistration/confirmar/email/"+entity.getHash());
		
		usuarioRepository.save(entity);		
		return ResponseEntity.ok("{}");
    }
    
    @PostMapping("/selfregistration/confirmar/email/{hash}")
    @Transactional
    public void registrarSelfregistrationConfirmarEmail(@PathVariable String hash, HttpServletRequest request) {
    	
    	Usuario usuarioByHash = usuarioRepository.findUsuarioByHash(hash);
    	usuarioByHash.setAtivo(true);
    	usuarioByHash.setDataHoraRegistro(LocalDateTime.now());
    	usuarioRepository.save(usuarioByHash);
    }
    
    @PostMapping("/selfregistration/confirmar")
    @Transactional
    public void registrarSelfregistrationConfirmar(@RequestBody @Valid DadosCadastroUsuario dadosCadastroUsuario, HttpServletRequest request) {
    	
    	usuarioRepository.save(new Usuario(dadosCadastroUsuario));
    }
    
    @PutMapping("/logado/perfil")
    @Transactional
    public ResponseEntity<String> atualizar(@RequestBody @Valid DadosCadastroUsuario dadosCadastroUsuario, HttpServletRequest request) {
    	
    	Usuario usuarioLogado = (Usuario)request.getAttribute("usuarioLogado");
    	Usuario usuarioBanco = usuarioRepository.getReferenceById(usuarioLogado.getId());
    	usuarioBanco.setNome(dadosCadastroUsuario.nome());
    	usuarioBanco.setEmail(dadosCadastroUsuario.email());
    	usuarioBanco.setEndereco(dadosCadastroUsuario.endereco());
    	usuarioBanco.setCidade(dadosCadastroUsuario.cidade());
    	usuarioBanco.setDataNascimento(dadosCadastroUsuario.dataNascimento());
    	usuarioRepository.save(usuarioBanco);
    	return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<Page<DadosListagemUsuario>> listarUsuario(@PageableDefault(size = 3, sort = {"nome"}) Pageable paginacao, 
    		HttpServletRequest request) {
    	var page = usuarioRepository.findAll(paginacao).map(DadosListagemUsuario::new);
    	
    	
        return ResponseEntity.ok(page);
    }
    
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deletarUsuario(@PathVariable Long id, HttpServletRequest request) {
    	usuarioRepository.deleteById(id);
    	return ResponseEntity.ok("{}");
    }
    
    @GetMapping("/{id}")
    public DadosCadastroUsuario obterDadosCadastroUsuario(@PathVariable Long id, HttpServletRequest request) {
    	
    	Usuario referenceById = usuarioRepository.getReferenceById(id);
    	return new DadosCadastroUsuario(referenceById);
    }
    
    @GetMapping("/logado/perfil")
    public DadosCadastroUsuario obterDadosCadastroUsuarioPerfil(HttpServletRequest request) {
    	
    	Usuario usuarioLogado = (Usuario)request.getAttribute("usuarioLogado");
    	Usuario referenceById = usuarioRepository.getReferenceById(usuarioLogado.getId());

		return new DadosCadastroUsuario(referenceById);
    }
    
    @PutMapping("/logado/perfil/senha")
    @Transactional
    public ResponseEntity<String> alterarSenha(@RequestBody @Valid DadosCadastroAtualizarSenha cadastroAtualizarSenha, HttpServletRequest request) {
    	
    	usuarioService.alterarSenha(cadastroAtualizarSenha);
    	return ResponseEntity.ok("{}");
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
