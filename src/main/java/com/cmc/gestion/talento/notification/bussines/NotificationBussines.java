package com.cmc.gestion.talento.notification.bussines;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.cmc.gestion.talento.notification.NotificationException;
import com.cmc.gestion.talento.notification.dto.AttachmenType;
import com.cmc.gestion.talento.notification.dto.EmailNotificationDto;
import com.cmc.gestion.talento.notification.dto.MapParametriaDto;

@Service
public class NotificationBussines {
	private final static String ATTACHMENTS_PATH = "com.gestion.attachments.path";
	
	private static final Logger logger = LogManager.getLogger(NotificationBussines.class);

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private Environment environment;

	@Autowired
	private SpringTemplateEngine templateEngine;

	public void sendMail(EmailNotificationDto emailNotificationDto) throws NotificationException {
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			Context context = new Context();
			context.setVariables(buildMapVar(emailNotificationDto.getParametros()));
			String html = templateEngine.process(emailNotificationDto.getTemplate(), context);
			helper.setTo(emailNotificationDto.getCorreoDestinatario());
			helper.setText(html, true);
			helper.setSubject(emailNotificationDto.getAsunto());
			if(emailNotificationDto.getTemplateLayout().equals("GENERAL")) {
				attachmenGeneric(helper);
			}
			if (emailNotificationDto.getAttachment() != null) {
				emailNotificationDto.getAttachment().forEach((attachment) -> {
					try {
						if(attachment.getType()==AttachmenType.ADJUNTO) {
							byte[] fileContent = Files.readAllBytes(getAttachment(attachment.getPath()).toPath());
							helper.addAttachment(attachment.getName(),  new ByteArrayResource(fileContent));
						}else {
						   helper.addInline(attachment.getName(), getAttachment(attachment.getPath()));
						}
						
					} catch (MessagingException | IOException e) {
						logger.error("Error al adjuntar archivo "+e.getMessage());
					}
				});
			}
			emailSender.send(message);
		} catch (MessagingException e) {
			logger.error("Error al adjuntar archivo "+e.getMessage());
			throw new NotificationException(e.getMessage());
		} catch (Exception e) {
			logger.error("Error al adjuntar archivo "+e.getMessage());
			throw new NotificationException(e.getMessage());
		}
		logger.debug("Notificacion enviada con exito");
	}

	private Map<String, Object> buildMapVar(List<MapParametriaDto> listaVariable) {
		Map<String, Object> variables = new HashMap<>();
		for (MapParametriaDto in : listaVariable) {
			variables.put(in.getKey(), in.getValue());
		}
		return variables;
	}

	private File getAttachment(String fileName) throws IOException {
		String rutaFile = environment.getProperty(ATTACHMENTS_PATH, "classpath:attachments/");
		rutaFile = rutaFile.concat(fileName);
		Resource resource = resourceLoader.getResource(rutaFile);
		return resource.getFile();
	}
	
   private void attachmenGeneric(MimeMessageHelper helper) throws MessagingException, IOException {
	  
	   helper.addInline("rss-circle-black", getAttachment("imagesGeneric/rss-circle-black.png"));
	   helper.addInline("linkedin-circle-black", getAttachment("imagesGeneric/linkedin-circle-black.png"));
	   helper.addInline("twitter-circle-black", getAttachment("imagesGeneric/twitter-circle-black.png"));
	   helper.addInline("youtube-circle-black", getAttachment("imagesGeneric/youtube-circle-black.png"));
	   helper.addInline("footerlogo", getAttachment("imagesGeneric/footerlogo.jpeg"));
	   helper.addInline("headerlogo", getAttachment("imagesGeneric/headerlogo.png"));
	   
   }

}
