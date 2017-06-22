package br.gov.ans.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

public class MustacheUtils {

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
}
