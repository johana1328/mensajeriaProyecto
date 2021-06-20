package com.cmc.gestion.talento.notification;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class NotificationExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { NotificationException.class })
	protected ResponseEntity<ExceptionModel> handleConflict(RuntimeException ex, WebRequest request) {
		ExceptionModel resp = new ExceptionModel("500", ex.getMessage());
		return new ResponseEntity<ExceptionModel>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
