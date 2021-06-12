package com.cmc.gestion.talento.notification.bussines;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.cmc.gestion.talento.notification.dto.EmailNotificationDto;
import com.cmc.gestion.talento.notification.dto.MapParametriaDto;

@Service
public class NotificationBussines {
	
	@Autowired
	private JavaMailSender emailSender;
	@Autowired
	private SpringTemplateEngine templateEngine;
	
	public void sendMail(EmailNotificationDto emailNotificationDto) throws MessagingException {
		 
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message,
		MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
		StandardCharsets.UTF_8.name());
		//helper.addAttachment("template-cover.png", new ClassPathResource("javabydeveloper-email.PNG"));
		Context context = new Context();
		context.setVariables(buildMapVar(emailNotificationDto.getParametria()));
		String html = templateEngine.process(emailNotificationDto.getTemplate(), context);
		helper.setTo(emailNotificationDto.getCorreoDestinatario());
		helper.setText(html, true);
		helper.setSubject(emailNotificationDto.getAsunto());
		//helper.setFrom(mail.getFrom());
		emailSender.send(message);
		
	}
	
	
	private Map<String, Object> buildMapVar(List<MapParametriaDto> listaVariable){
		
		Map<String,Object> variables = new HashMap<>();
		
		for(MapParametriaDto in: listaVariable) {
			variables.put(in.getKey(), in.getValue());
		}
		
		return variables;
		
	}

}
