package br.gov.ans.integracao.sei.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.text.MaskFormatter;

import br.gov.ans.exceptions.BusinessException;

public class Util {
	private static SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy");
	
	private static Pattern pattern = Pattern.compile(Constantes.REGEX_MASCARA_PROCESSO, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		
	public static String getSOuN(String valor){		
		if("S".equals(valor) || "s".equals(valor)){
			return "S";
		}
		
		return "N";
	}
	
	public static String getSOuN(Boolean valor){		
		if(valor != null && valor){
			return "S";
		}
		
		return "N";
	}
	
	public static String formatarData(Date data){
		if(data != null){
			return dateFormater.format(data);			
		}
		
		return null;		
	}
	
    public static String formatarString(String texto, String mascara) throws Exception {
    	if(texto.length() != 17){
    		throw new BusinessException("Número de processo inválido");
    	}
    	
        MaskFormatter mf = new MaskFormatter(mascara);
        mf.setValueContainsLiteralCharacters(false);
        return mf.valueToString(texto);
    }
	
    public static String formatarNumeroProcesso(String numero) throws Exception{
    	if(validarNumeroProcesso(numero)){
    		return numero;
    	}
    		
    	try {
			return formatarString(numero, Constantes.MASCARA_PROCESSO);
		} catch (ParseException ex) {
			throw new BusinessException("Número de processo inválido");
		}
    }
    
    public static String[] formatarNumeroProcesso(String[] processos) throws Exception{
    	if(processos.length == 0){
    		return processos;
    	}
    	
    	for(int i = 0; i <= processos.length; i++){
    		processos[i] = formatarNumeroProcesso(processos[i]);
    	}
    	
    	return processos;
    }
    
	public static boolean validarNumeroProcesso(String processo){
		return pattern.matcher(processo).matches();
	}
	
	public static boolean trueOrFalse(String valor){
		if(valor.length() > 1){
			new Boolean(valor);
		}
		
		return (valor.equals("1"));
	}
	
	public static Integer parseInt(String valor) throws BusinessException{
		try{
			return Integer.parseInt(valor);			
		}catch(Exception e){
			throw new BusinessException("O valor '"+valor+"' não pode ser convertido para int.");
		}		
	}
}
