package br.com.muratsystems.distanciafacil.api.exceptionhandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.muratsystems.distanciafacil.domain.exception.BusinessException;
import br.com.muratsystems.distanciafacil.domain.exception.ValidationException;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

	// Trata as exceptions lançadas em BusinessException
	@org.springframework.web.bind.annotation.ExceptionHandler(BusinessException.class)
	public ResponseEntity<Object> handleBusiness(BusinessException ex, WebRequest request) {
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	// Trata as exceptions lançadas em ValidationException
	@org.springframework.web.bind.annotation.ExceptionHandler(ValidationException.class)
	public ResponseEntity<Object> handleValidation(BusinessException ex, WebRequest request) {
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

}
