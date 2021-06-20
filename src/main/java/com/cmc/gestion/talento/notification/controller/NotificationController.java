package com.cmc.gestion.talento.notification.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.gestion.talento.notification.NotificationException;
import com.cmc.gestion.talento.notification.bussines.NotificationBussines;
import com.cmc.gestion.talento.notification.dto.EmailNotificationDto;


@RestController
@RequestMapping(path = "/notification")
public class NotificationController {
	
	private static final Logger logger = LogManager.getLogger(NotificationController.class);
	
	@Autowired
	private NotificationBussines notificationBussines;
	
	@PostMapping
	public ResponseEntity<?> sendNotification(@Valid @RequestBody EmailNotificationDto emailNotificationDto) throws NotificationException{
		try {
			logger.debug("Inicio envio de mensajeria : "+emailNotificationDto.toString());
			notificationBussines.sendMail(emailNotificationDto);
		} catch (NotificationException e) {
			throw e;
		}
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
