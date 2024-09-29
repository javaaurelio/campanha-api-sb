package med.voll.api.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import med.voll.api.domain.usuario.Usuario;

@Service
public class TokenService {

	public String gerarToken(String numeroMagico, Usuario usuario) {

		Algorithm algorithm = Algorithm.HMAC512(numeroMagico);
		return JWT.create().withIssuer("APP-Campanha").withSubject(usuario.getLogin()).withExpiresAt(dataExpiracao()).sign(algorithm);
	}

	public String validarToken(String numeroMagico, String token) {
		Algorithm algorithm = Algorithm.HMAC512(numeroMagico);
		return JWT.require(algorithm).withIssuer("APP-Campanha").build().verify(token).getSubject();

	}

	private Instant dataExpiracao() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}

}
