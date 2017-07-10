package br.gov.ans.utils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

public class HttpHeadersUtil {
		
	private static Logger logger = Logger.getLogger(HttpHeadersUtil.class);
			
	public static String getAcceptType(HttpHeaders headers){
		try{
			String accept = headers.getRequestHeader("Accept").get(0);
			
			if(!StringUtils.isBlank(accept)&& StringUtils.contains(accept, MediaType.APPLICATION_XML)){
				return MediaType.APPLICATION_XML;		
			}
		}catch(Exception e){
			logger.warn("Não foi encontrado o campo Accept na requisição, respondendo com JSON.");
		}
				
		return MediaType.APPLICATION_JSON;		
	}
	
	public static String getAcceptType(HttpServletRequest request){
		try{
			String accept = request.getHeader("Accept");
			
			if(!StringUtils.isBlank(accept)&& StringUtils.contains(accept, MediaType.APPLICATION_XML)){
				return MediaType.APPLICATION_XML;		
			}
		}catch(Exception e){
			logger.warn("Não foi encontrado o campo Accept na requisição, respondendo com JSON.");
		}
				
		return MediaType.APPLICATION_JSON;		
	}
}
