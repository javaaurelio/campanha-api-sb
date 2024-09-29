package med.voll.api.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.Valid;
import med.voll.api.domain.parametro.Parametro;
import med.voll.api.domain.parametro.ParametroRepository;

@RestController
@RequestMapping("config")
public class SQLController {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private ParametroRepository parametroRepository;
	
	@GetMapping("/sql_")
	public String campanha(ModelMap model, @RequestParam String sql){
		 Query query = em.createNativeQuery(sql);
		 List list = query.getResultList();
		 return "<html>SQL: " + sql +" <br> -> " + Arrays.deepToString(list.toArray()).replaceAll("\\], \\[", "]<br> -> <br>[ ") + "</html>";
	}
	
	@PostMapping("/parametro_")
	public ResponseEntity<String> param(ModelMap model, @RequestBody @Valid Parametro parametro){
		parametroRepository.save(parametro);
		
		return ResponseEntity.noContent().build();
	}
	
	
	@PostMapping("/emailTeste")
	public ResponseEntity<String> testeEmail(ModelMap model){
		
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(587);
	    
//	    mailSender.setUsername("my.gmail@gmail.com");
//	    mailSender.setPassword("password");
	    
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
//	    props.put("mail.smtp.auth", "true");
//	    props.put("mail.smtp.starttls.enable", "true");
//	    props.put("mail.debug", "true");
	    
	    SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("noreply@baeldung.com");
        message.setTo(""); 
        message.setSubject("Teste API"); 
        message.setText("TESTE API " + LocalDateTime.now());
        mailSender.send(message);

        return ResponseEntity.noContent().build();
	}
}
