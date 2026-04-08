package com.seguridadlimite.springboot.backend.apirest.services;

import jakarta.mail.MessagingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;

@Service
public class MailServiceImpl {
	/*
	@Autowired
	JavaMailSender javaMailSender;
	*/

	public String sendTest() {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		
		mailMessage.setFrom("seguridadallimiteapp@gmail.com");
		mailMessage.setTo("jihenaor@gmail.com");
		mailMessage.setSubject("Prueba");
		mailMessage.setText("Text");
		
//		javaMailSender.send(mailMessage);
		
		return "mail";
	}

	public void sendExceptionLog(String origen, Exception e) {
		try {
			Logger logger = LogManager.getLogger(origen);
			logger.error("Se ha producido un error", e);
		} catch (Exception ex) {
			e.printStackTrace();
			ex.printStackTrace();
		}
	}

		public String sendException(String origen, Exception e) {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			String mensaje = "";

			sendExceptionLog(origen, e);

			try {
				mailMessage.setFrom("seguridadallimiteapp@gmail.com");
				mailMessage.setTo("jihenaor@gmail.com");
				mailMessage.setSubject("Error: " + origen);

				if (e instanceof DataIntegrityViolationException) {
					DataIntegrityViolationException e1 = (DataIntegrityViolationException) e;

					if (e1.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
						mensaje = String.valueOf(((ConstraintViolationException) e1.getCause()).getSQLException());
						mailMessage.setText(mensaje);
					} else {
						e1.getCause().printStackTrace(pw);
						mailMessage.setText(sw.toString());
						mensaje = "Se he presentado un error con los datos enviados";
					}
				} else {
					mensaje = "Se ha presentado un error del sistema";
					e.printStackTrace(pw);
					mailMessage.setText(sw.toString());
				}

//				javaMailSender.send(mailMessage);

			} catch (Exception e1) {
				e.printStackTrace();
				e1.printStackTrace();
			}

			return mensaje;
		}

	public String send(String from, String to, String subject, String text) {
/*
		MimeMessage msg = javaMailSender.createMimeMessage();

        MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(msg, true);
	        helper.setTo(to);
	        helper.setFrom(from);

	        helper.setSubject(subject);

	        helper.setText(text, true);

		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		javaMailSender.send(msg);
*/
		return "mail";
	}
	
	public void sendMail(String from, String to, String subject, String msg) throws MessagingException {

/*
        MimeMessage message = javaMailSender.createMimeMessage();

        message.setSubject(subject);
        MimeMessageHelper helper;
        helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setText(msg, true);
		javaMailSender.send(message);
*/
    }
}
