package br.gov.ans.integracao.sei.utils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class Constantes {
	private static final int AXIS_TIMEOUT_IN_MINUTES = 10;
	public static final int AXIS_TIMEOUT = 1000 * (60 * AXIS_TIMEOUT_IN_MINUTES);
	public static final String CODIGO_BRASIL = "76";
	public static final String CODIGO_ORGAO_ANS = "0";
	public static final String DOCUMENTO_GERAL = "G";
	public static final String DOCUMENTO_RECEBIDO = "R";
	public static final String MASCARA_PROCESSO_17 = "#####.######/####-##";
	public static final String MASCARA_PROCESSO_21 = "#######.########/####-##";
	public static final String JBOSS_HOME = System.getProperty("jboss.home.dir");
	public static final String NOME_SISTEMA = "sei-broker";
	public static final String REGEX_MASCARA_PROCESSO = "(\\d)(\\d)(\\d)(\\d)(\\d)(\\.)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\/)(\\d)(\\d)(\\d)(\\d)(-)(\\d)(\\d)";
	public static final String SEI_BROKER = "SEI-Broker";
	public static final int TAMANHO_MAXIMO_ARQUIVO = 16777216;
	public static final int TAMANHO_MAXIMO_DOCUMENTO = 22020096;
	public static final Integer TAMANHO_PAGINA_PADRAO = 50; 
	public static final String TEMPLATE_DIR = "sei";
	public static final String TEMPLATES_HOME = JBOSS_HOME + "/ans/templates/";
}
