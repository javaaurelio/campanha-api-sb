package med.voll.api.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DadosAutenticacao;
import med.voll.api.domain.usuario.DadosAutenticacaoToken;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.domain.usuario.UsuarioRepository;
import med.voll.api.infra.security.TokenService;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public DadosAutenticacaoToken efetuarLogin(@RequestBody @Valid DadosAutenticacao dadosLogin, HttpServletRequest request) {
		
		UsernamePasswordAuthenticationToken token = 
				new UsernamePasswordAuthenticationToken(dadosLogin.login(), dadosLogin.senha());
		Authentication authenticate = manager.authenticate(token);
		StringBuffer sbHeaderNames = new StringBuffer();    	
    	request.getHeaderNames().asIterator().forEachRemaining(entry -> { sbHeaderNames.append(entry+":"+request.getHeader(entry)+"\n "); });
    	System.out.println(sbHeaderNames);
		
    	Usuario principal = (Usuario)authenticate.getPrincipal();
		String gerarToken = tokenService.gerarToken("123", principal);
    	
    	Usuario referenceById = usuarioRepository.getReferenceById(principal.getId());
    	if (referenceById.getDataHoraPrimeiroAcesso() == null) {
    		referenceById.setDataHoraPrimeiroAcesso(LocalDateTime.now());
    	}
    	referenceById.setDataHoraUltimoAcesso(LocalDateTime.now());
    	usuarioRepository.save(referenceById);
    	
		return new DadosAutenticacaoToken(gerarToken);
		
	}
	
}
