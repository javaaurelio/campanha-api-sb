package med.voll.api.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.google.gson.Gson;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.domain.usuario.UsuarioRepository;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UsuarioRepository usuarioRepository;
	private Gson gson = new Gson();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		System.out.println("Request para ->" + request.getRequestURI());
		
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Max-Age", "");
		response.setHeader("Access-Control-Allow-Headers", "x-controlado, Content-Type, Accept, X-Requested-With, authorization");
		response.setCharacterEncoding("UTF-8");
		
		String token = recuperarToken(request);
		System.out.println("Token Recuperado do Header ->" + token);

		if (token != null) {

			try {
				System.out.println("Validando token" + token);

				String loginUsuario = tokenService.validarToken("123", token);

				System.out.println("Usuario Logado pelo JWT->" + loginUsuario);
				UserDetails byLoginDB = usuarioRepository.findByLogin(loginUsuario);
				System.out.println("Usuario LOCALIZADO ->" + ((Usuario)byLoginDB).getNome() + ":"+ ((Usuario)byLoginDB).getId());

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(byLoginDB,
						null, byLoginDB.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				request.setAttribute("usuarioLogado", (Usuario)byLoginDB);
				
				System.out.println("Logado com Sucesso");

			} catch (TokenExpiredException ex) {
				
				ex.printStackTrace();
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				response.setCharacterEncoding("UTF-8");
				String json = gson.toJson(new ErroValidacaoJwt("Token Inválido: " + ex.getMessage()));
				response.getWriter().write(json);
				return;
			} catch (JWTVerificationException ex) {
				ex.printStackTrace();
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				response.setCharacterEncoding("UTF-8");
				String json = gson.toJson(new ErroValidacaoJwt("Falha na validacao do Token " + ex.getMessage()));
				response.getWriter().write(json);
				return;
			} catch (Exception ex) {
				ex.printStackTrace();
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				response.setCharacterEncoding("UTF-8");
				String json = gson.toJson(new ErroValidacaoJwt("Falha na autenticacao."));
				response.getWriter().write(json);
				return;
			}
		} else {
			System.err.println("Nao logado, token nao informado.");
		}
		
		try {
			
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String recuperarToken(HttpServletRequest request) {

		StringBuffer sbHeaderNames = new StringBuffer();
		request.getHeaderNames().asIterator().forEachRemaining(entry -> {
			sbHeaderNames.append(entry + ":" + request.getHeader(entry) + "\n ");
		});
		System.out.println("Header: " + sbHeaderNames);

		String headerAuthorization = request.getHeader("Authorization");

		if (headerAuthorization != null) {
			return headerAuthorization.replaceAll("Bearer ", "");
		}

		return null;
	}

}
