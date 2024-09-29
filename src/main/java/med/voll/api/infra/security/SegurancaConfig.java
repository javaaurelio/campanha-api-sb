package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SegurancaConfig {

	@Autowired
	private SecurityFilter securityFilter;

	@Bean
	public SecurityFilterChain filtroSeguranca(HttpSecurity httpSecurity) throws Exception {

		return httpSecurity.csrf().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().authorizeHttpRequests()
				.antMatchers("/login").permitAll()
				.antMatchers("/usuario/selfregistration").permitAll()
				.antMatchers("/voto/**").permitAll()
				.antMatchers(HttpMethod.OPTIONS, "**").permitAll()
//				.antMatchers(HttpMethod.GET, "**").permitAll()
				.dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
				.anyRequest().authenticated()
				.and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
		        .build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncode() {
		return new BCryptPasswordEncoder();
	}

}
