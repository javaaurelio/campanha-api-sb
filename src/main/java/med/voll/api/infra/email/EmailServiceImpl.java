package med.voll.api.infra.email;

import java.util.Properties;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import med.voll.api.infra.ParametrosUtil;

@Component
public class EmailServiceImpl {
	
	public JavaMailSender getJavaMailSender(String host, int porta) {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost(host);
	    mailSender.setPort(porta);
	    
//	    mailSender.setUsername("my.gmail@gmail.com");
//	    mailSender.setPassword("password");
	    
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "false");
	    props.put("mail.smtp.starttls.enable", "false");
	    props.put("mail.debug", "true");
	    
	    return mailSender;
	}

	public void sendSimpleMessage(String to, String url) {
		
		if (ParametrosUtil.get("host-email") != null) {
			
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("noreply@dashboard.com.br");
			message.setTo(to);
			message.setSubject("Ativar conta - Dashboard Carnaval");
			message.setText("Clique para ativar sua conta: "+ url);
			getJavaMailSender(ParametrosUtil.get("host-email"), Integer.valueOf(ParametrosUtil.get("porta-email"))).send(message);
		} else {
			throw new RuntimeException("Servidor de email nao configurado(host-email, porta-email)");
		}
	}
}
