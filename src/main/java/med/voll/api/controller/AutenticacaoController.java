package med.voll.api.controller;

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
import med.voll.api.infra.security.TokenService;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {
	
	@Autowired
	private AuthenticationManager manager;
	
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
		
//    	window.screen.colorDepth
//    	window.screen.availWidth
//    	screen.height
//    	screen.width
//    	et text = "<p>Browser CodeName: " + navigator.appCodeName + "</p>" +
//    			"<p>Browser Name: " + navigator.appName + "</p>" +
//    			"<p>Browser Version: " + navigator.appVersion + "</p>" +
//    			"<p>Cookies Enabled: " + navigator.cookieEnabled + "</p>" +
//    			"<p>Browser Language: " + navigator.language + "</p>" +
//    			"<p>Browser Online: " + navigator.onLine + "</p>" +
//    			"<p>Platform: " + navigator.platform + "</p>" +
//    			"<p>Platform: " + JSON.stringify(navigator.hardwareConcurrency) + "</p>" +
//    			"<p>Platform: " + JSON.stringify(navigator.webdriver) + "  "+ window.clientInformation + "</p>"
//    			"<p>User-agent header: " + navigator.userAgent + "</p>";
//
//    			document.getElementById("demo").innerHTML = text;
//    			</script>
    	String gerarToken = tokenService.gerarToken("123", (Usuario)authenticate.getPrincipal());
		return new DadosAutenticacaoToken(gerarToken);
		
	}
	
}
