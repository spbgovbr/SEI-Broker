package br.gov.ans.exceptions;

public class BusinessException extends Exception{

	private static final long serialVersionUID = 1L;

	private String message;

	public BusinessException(String message){
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}	
}
