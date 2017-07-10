package br.gov.ans.exceptions;

public class WrappedException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private Exception ex;

	public WrappedException(Exception ex){
		this.ex = ex;
	}
	
	public Exception getEx() {
		return ex;
	}	
}
