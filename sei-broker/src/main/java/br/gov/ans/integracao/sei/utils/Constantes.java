package br.gov.ans.integracao.sei.utils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class Constantes {
	public static final String CODIGO_ORGAO_ANS = "0";
	public static final String DOCUMENTO_GERAL = "G";
	public static final String DOCUMENTO_RECEBIDO = "R";
	public static final String MASCARA_PROCESSO = "#####.######/####-##";
	public static final String JBOSS_HOME = System.getProperty("jboss.home.dir");
	public static final String NOME_SISTEMA = "sei-broker";
	public static final String REGEX_MASCARA_PROCESSO = "(\\d)(\\d)(\\d)(\\d)(\\d)(\\.)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\/)(\\d)(\\d)(\\d)(\\d)(-)(\\d)(\\d)";
	public static final String SEI_BROKER = "SEI-Broker";	
	public static final Integer TAMANHO_PAGINA_PADRAO = 50;	 
	public static final String TEMPLATE_DIR = "sei";
	public static final String TEMPLATES_HOME = JBOSS_HOME + "/ans/templates/";	
}
