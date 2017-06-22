package br.gov.ans.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.Charset;

import javax.inject.Inject;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import br.gov.ans.integracao.templates.client.ClientTemplatesBroker;

public class MustacheUtils {
	
	@Inject
	private ClientTemplatesBroker templates;

	public static final String SERVER_PATH = System.getProperty("jboss.home.dir") + "/ans/templates/";
	
	private static final MustacheFactory mf = new DefaultMustacheFactory();
	
	/**
	 * Compila a partir do template externo
	 * @param file : Template externo
	 * @return
	 * @throws IOException
	 */
	public Mustache compile(File file) throws IOException{
		return mf.compile(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")), file.getName());
	}
	
	/**
	 * Compila a partir de template hospedado no JBoss ([JBOSS_HOME]/ans/templates/)
	 * @param dir : Diretório específico no JBoss
	 * @param file : Template hospedado no JBoss
	 * @return
	 * @throws IOException
	 */
	public Mustache compile(String dir, String file) throws IOException{
		File f = new File(new File(SERVER_PATH + dir), file);
		return mf.compile(new InputStreamReader(new FileInputStream(f), Charset.forName("UTF-8")), f.getName());
	}
	
	/**
	 * Compila a partir de uma String com o template
	 * @param template : código fonte do template
	 * @return
	 * @throws Exception 
	 * @throws IOException
	 */
	public Mustache compile(String template) throws Exception{
		String conteudo = templates.getTemplate(template);
		
		return mf.compile(new StringReader(conteudo), template);
	}
}
