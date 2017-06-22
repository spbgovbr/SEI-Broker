package br.gov.ans.integracao.sei.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.persistence.Query;
import javax.swing.text.MaskFormatter;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

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
    	if(processos == null){
    		return null;
    	}
    	
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
		if(valor != null && valor.length() > 1){
			new Boolean(valor);
		}
		
		return ("1".equals(valor));
	}
	
	public static Integer parseInt(String valor) throws BusinessException{
		try{
			return Integer.parseInt(valor);			
		}catch(Exception e){
			throw new BusinessException("O valor '"+valor+"' não pode ser convertido para int.");
		}		
	}
	
	public static Map<String, Object> jsonToMap(String json) throws JsonParseException,JsonMappingException, IOException {
		Map<String,Object> out = new HashMap<String,Object>();
		
		ObjectMapper mapper = new ObjectMapper();
		
		out = mapper.readValue(json, new TypeReference<Map<String, Object>>(){});
		
		return  out;
	}
	
	
	public static String decodeBase64(String base64){
		return new String(Base64.decodeBase64(base64));
	}
	
	public static String encodeBase64(String texto){
		return new String(Base64.encodeBase64(texto.getBytes()));
	}
	
	public static Map<String, Object> decodeConteudoMustache(String conteudo) throws JsonParseException, JsonMappingException, IOException{
		Map<String,Object> jsonToMap = jsonToMap(decodeBase64(conteudo));
		
		return jsonToMap;
	}
	
	public static void setQueryParameters(Query query, HashMap<String, Object> parametros){
		if ((parametros != null) && (!parametros.isEmpty())) {
			for (Map.Entry entry : parametros.entrySet()) {
				query.setParameter((String) entry.getKey(),entry.getValue());
			}
		}
	}
	
	public static void setPaginacaoQuery(Query query, Integer pagina, Integer qtdRegistros){
		if(qtdRegistros == null){
			qtdRegistros = Constantes.TAMANHO_PAGINA_PADRAO;
		}
		
		if(pagina == null){
			pagina = 1;
		}
		
		int firstResult = ((pagina - 1)* qtdRegistros);		
		query.setFirstResult(firstResult);
		query.setMaxResults(qtdRegistros);		
	}	
}
