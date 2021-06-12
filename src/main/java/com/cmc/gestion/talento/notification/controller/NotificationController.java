package com.cmc.gestion.talento.notification.controller;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.gestion.talento.notification.bussines.NotificationBussines;
import com.cmc.gestion.talento.notification.dto.EmailNotificationDto;


@RestController
@RequestMapping(path = "/notification")
public class NotificationController {
	
	@Autowired
	private NotificationBussines notificationBussines;
	
	@PostMapping
	public ResponseEntity sendNotification(@Valid @RequestBody EmailNotificationDto emailNotificationDto){
		try {
			notificationBussines.sendMail(emailNotificationDto);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}

}
