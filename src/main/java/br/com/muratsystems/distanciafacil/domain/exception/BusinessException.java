package br.com.muratsystems.distanciafacil.domain.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 3545058764918875861L;

	public BusinessException(String message) {
		super(message);
	}
	
}
