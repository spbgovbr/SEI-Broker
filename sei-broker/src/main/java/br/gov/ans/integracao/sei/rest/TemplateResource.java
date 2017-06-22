package br.gov.ans.integracao.sei.rest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.gov.ans.exceptions.BusinessException;
import br.gov.ans.integracao.sei.modelo.Template;
import br.gov.ans.integracao.sei.utils.Constantes;
import br.gov.ans.utils.MessageUtils;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

@Path("/templates")
public class TemplateResource {
	
    @Inject
    private MessageUtils messages;
		
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public ArrayList<Template> getTemplates() throws IOException{
		ArrayList<Template> templates = new ArrayList<Template>();
		File pasta = new File(Constantes.TEMPLATES_HOME + Constantes.TEMPLATE_DIR);
		
		for(File arquivo : pasta.listFiles()){
			Template template = new Template();
			
			template.setNome(arquivo.getName());
			template.setDataModificacao(new Date(arquivo.lastModified()));
			template.setConteudo(readTemplate(arquivo));
			
			templates.add(template);
		}
		
		return templates;
	}
	
	@GET
	@Path("{template}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Template getTemplate(@PathParam("template") String nome) throws IOException, BusinessException{
		Template template = new Template();
		
		try{
			File arquivo = new File(Constantes.TEMPLATES_HOME + Constantes.TEMPLATE_DIR + "/" + nome);
			
			template.setNome(arquivo.getName());
			template.setDataModificacao(new Date(arquivo.lastModified()));
			template.setConteudo(readTemplate(arquivo));			
		}catch(java.io.FileNotFoundException ex){
			throw new BusinessException(messages.getMessage("erro.template.nao.encontrado", nome));
		}
		
		return template;
	}
	
	public String readTemplate(File arquivo) throws IOException{
		return Files.toString(arquivo, Charsets.UTF_8);
	}
}
