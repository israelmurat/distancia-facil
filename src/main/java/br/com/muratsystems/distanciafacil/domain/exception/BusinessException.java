package br.com.muratsystems.distanciafacil.domain.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -3812979063668200888L;

	public BusinessException(String message) {
		super(message);
	}
	
}
