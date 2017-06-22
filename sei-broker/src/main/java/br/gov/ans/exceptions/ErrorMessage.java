package br.gov.ans.exceptions;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorMessage {
	private String error;
	private String code;

	public ErrorMessage(){}
	
	public ErrorMessage(String error) {
		this.error = error;
	}
	
	public ErrorMessage(String error, String code) {
		this.error = error;
		this.code = code;
	}
	
	public ErrorMessage(String error, int code) {
		this.error = error;
		this.code = String.valueOf(code);
	}

	public String getError() {
		return error;
	}
	
	public void setError(String error){
		this.error = error;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}	
}
