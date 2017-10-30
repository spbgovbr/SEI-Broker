package br.gov.ans.exceptions;

import javax.ejb.ApplicationException;

@ApplicationException
public class ResourceConflictException extends Exception{
	
	private static final long serialVersionUID = 1L;

	private String message;

	public ResourceConflictException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
