package br.com.muratsystems.distanciafacil.domain.exception;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = -28792085827484157L;

	public ValidationException(String message) {
		super(message);
	}
	
}
