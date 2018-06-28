package br.gov.ans.integracao.sei.testes;

import static com.jayway.restassured.RestAssured.given;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.jayway.restassured.response.Response;

import br.gov.ans.integracao.sei.client.Assunto;
import br.gov.ans.integracao.sei.client.Destinatario;
import br.gov.ans.integracao.sei.client.Documento;
import br.gov.ans.integracao.sei.client.Interessado;
import br.gov.ans.integracao.sei.client.Procedimento;
import br.gov.ans.integracao.sei.client.Remetente;
import br.gov.ans.integracao.sei.client.RetornoGeracaoProcedimento;
import br.gov.ans.integracao.sei.client.RetornoInclusaoDocumento;
import br.gov.ans.integracao.sei.modelo.Arquivo;
import br.gov.ans.integracao.sei.modelo.ArquivoCriado;
import br.gov.ans.integracao.sei.modelo.CancelamentoDocumento;
import br.gov.ans.integracao.sei.modelo.EnvioDeProcesso;
import br.gov.ans.integracao.sei.modelo.NovoBloco;
import br.gov.ans.integracao.sei.modelo.NovoProcesso;
import br.gov.ans.integracao.sei.modelo.ParteArquivo;
import br.gov.ans.integracao.sei.modelo.SobrestamentoProcesso;
import br.gov.ans.integracao.sei.modelo.enums.TipoBloco;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SeiBrokerTest extends FunctionalTest{	
	
	static String documentoCriado;
	static String documentoInternoCriado;
	static String processoCriado;
	static String blocoCriado;
	static String arquivoCriado;
	static String documentoCriadoPorArquivo;
	static List<String> partesArquivo;
		
	@Test
	public void AA_incluirProcessoTest(){
		Response response = given()
				.header("Authorization",BASIC)
				.contentType("application/json")
				.accept("application/json")
				.body(buildNovoProcesso())
				.when().post("/COSAP/processos");
		
		processoCriado = response.getBody().as(RetornoGeracaoProcedimento.class).getProcedimentoFormatado().replaceAll("[^0-9+]", "");
				
		response.then().statusCode(201);
	}
	
	@Test
	public void AB_consultarProcessoTest(){
		given().header("Authorization",BASIC).accept("application/json").when().get("/cosap/processos/"+processoCriado).then().statusCode(200);
	}
	
	@Test
	public void AC_incluirDocumentoTest() {
		Response response = given()
		.header("Authorization",BASIC)
		.contentType("application/json")
		.accept("application/json")
		.body(buildDocumento())
		.when().post("/COSAP/documentos");
		
		documentoCriado = response.getBody().as(RetornoInclusaoDocumento.class).getDocumentoFormatado();
		
		response.then().statusCode(201);
	}
	
	@Test
	public void AD_incluirDocumentoInternoTest() {
		Response response = given()
		.header("Authorization",BASIC)
		.contentType("application/json")
		.accept("application/json")
		.body(buildDocumentoInterno())
		.when().post("/COSAP/documentos");
		
		documentoInternoCriado = response.getBody().as(RetornoInclusaoDocumento.class).getDocumentoFormatado();
		
		response.then().statusCode(201);
	}

	@Test
    public void AE_consultarDocumentoTest() {	
        given().header("Authorization",BASIC).accept("application/json").when().get("/cosap/documentos/"+documentoCriado).then().statusCode(200);
    }
	
	@Test
	public void AF_consultarDocumentosEnviadosBrokerTest(){
		given().header("Authorization",BASIC).accept("application/json").when().get("/cosap/documentos/enviados-broker").then().statusCode(200);
	}
	
	@Test
	public void AG_consultarDocumentosPorInteressadosTest(){
		given().header("Authorization",BASIC).accept("application/json").when().get("/interessados/363022/documentos").then().statusCode(200);
	}
	
	@Test
	public void AH_consultarProcessos(){
		given().header("Authorization",BASIC).accept("application/json").when().get("/processos").then().statusCode(200);
	}
	
	@Test
	public void AI_exportarDocumentoTest(){
		given().header("Authorization",BASIC).when().get("/cosap/documentos/"+documentoCriado+"/pdf").then().statusCode(200);
	}
	
	@Test
	public void AJ_listarUnidadesTest(){
		given().header("Authorization",BASIC).accept("application/json").when().get("/unidades").then().statusCode(200);
	}
	
	@Test
	public void AL_listarSeriesTest(){
		given().header("Authorization",BASIC).accept("application/json").when().get("/cosit/series").then().statusCode(200);
	}
	
	@Test
	public void AM_listarTiposDeProcessoTest(){
		given().header("Authorization",BASIC).accept("application/json").when().get("/cosit/processos/tipos").then().statusCode(200);
	}
	
	@Test
	public void AN_listarExtensoesTest(){
		given().header("Authorization",BASIC).accept("application/json").when().get("/cosit/extensoes").then().statusCode(200);
	}
	
	@Test
	public void AO_consultarCodigoUnidadeTest(){
		given().header("Authorization",BASIC).accept("application/json").when().get("/unidades/cotec/codigo").then().statusCode(200);
	}
	
//	@Test
	public void AP_cancelarDocumentoTest(){
		given()
		.header("Authorization",BASIC)
		.contentType("application/json")
		.accept("application/json")
		.body(buildCancelamentoDocumento())
		.when().post("/cosap/documentos/cancelados").then().statusCode(200);
	}
	
	@Test
	public void AQ_concluirProcessoTest(){
		given()
		.header("Authorization",BASIC)
		.contentType("application/json")
		.accept("application/json")
		.body(processoCriado)
		.when().post("/cosap/processos/concluidos").then().statusCode(200);
	}
	
	@Test
	public void AR_reabrirProcessoTest(){
		given().header("Authorization",BASIC).accept("application/json").when().delete("/cosap/processos/concluidos/"+processoCriado).then().statusCode(200);
	}	
	
	@Test
	public void AS_gerarBlocoReuniaoTest(){
		Response response = given()
		.header("Authorization",BASIC)
		.contentType("application/json")
		.accept("application/json")
		.body(buildNovoBloco(TipoBloco.REUNIAO))
		.when().post("/COSAP/blocos");
		
		blocoCriado = response.getBody().as(String.class);
		
		response.then().statusCode(201);
	}
	
	@Test
	public void AT_incluirProcessoNoBlocoTest(){
		given()
		.header("Authorization",BASIC)
		.body(processoCriado)
		.when().post("/cosap/blocos/"+blocoCriado+"/processos").then().statusCode(200);
		
	}
	
	@Test
	public void AU_removerProcessoBlocoTest(){
		given()
		.header("Authorization",BASIC)
		.body(processoCriado)
		.when().delete("/cosap/blocos/"+blocoCriado+"/processos/"+processoCriado).then().statusCode(200);		
	}
	
	@Test
	public void AV_gerarBlocoAssinaturaTest(){
		Response response = given()
		.header("Authorization",BASIC)
		.contentType("application/json")
		.body(buildNovoBloco(TipoBloco.ASSINATURA))
		.when().post("/cosap/blocos");
		
		blocoCriado = response.getBody().as(String.class);
		
		response.then().statusCode(201);
	}
	
	@Test
	public void AX_incluirDocumentoNoBlocoTest(){
		given()
		.header("Authorization",BASIC)
		.body(documentoInternoCriado)
		.when().post("/cosap/blocos/"+blocoCriado+"/documentos").then().statusCode(200);
		
	}
	
	@Test
	public void AZ_disponibilizarBlocoTest(){
		given()
		.header("Authorization",BASIC)
		.body(blocoCriado)
		.when().post("/cosap/blocos/disponibilizados").then().statusCode(200);
		
	}

	@Test
	public void BA_indisponibilizarBlocoTest(){
		given()
		.header("Authorization",BASIC)
		.when().delete("/cosap/blocos/disponibilizados/"+blocoCriado).then().statusCode(200);

	}
	
	@Test
	public void BB_consultarBlocoTest(){
		given()
		.header("Authorization",BASIC)
		.accept("application/json")
		.when().get("/cosap/blocos/"+blocoCriado).then().statusCode(200);

	}
	
	@Test
	public void BC_removerDocumentoBlocoTest(){
		given()
		.header("Authorization",BASIC)
		.when().delete("/cosap/blocos/"+blocoCriado+"/documentos/"+documentoInternoCriado).then().statusCode(200);
	}
	
	@Test
	public void BD_excluirBlocoTest(){
		given()
		.header("Authorization",BASIC)
		.when().delete("/cosap/blocos/"+blocoCriado).then().statusCode(200);
	}
	
	@Test
	public void BE_enviarProcessoTest(){
		given()
		.header("Authorization",BASIC)
		.contentType("application/json")
		.accept("application/json")
		.body(buildEnvioDeProcesso())
		.when().post("/cosap/processos/enviados").then().statusCode(200);
	}
	
	@Test
	public void BF_enviarArquivoTest() throws IOException{		
//		String nomeArquivo = "Cronicas_da_Inforfobia-Centro_Atlantico.pdf";
//		String nomeArquivo = "certificados.pdf";
		String nomeArquivo = "Dissertacao_GuilhermeMauroGerrmoglioBarbosa.pdf";
//		String nomeArquivo = "OF-272-2016-DIDES-33910000610201675-63554067000198-ABI_v1_r0.pdf";
//		String nomeArquivo = "OF-599-2016-DIDES-33902438169201608-16513178000176-ABI.pdf";
		
		Response response = given()
		.header("Authorization",BASIC)
		.contentType("application/json")
		.accept("application/json")
		.body(buildArquivo(nomeArquivo))
		.when().post("/cosap/arquivos");
		
		arquivoCriado = response.getBody().as(ArquivoCriado.class).getIdentificador();
		
		response.then().statusCode(201);
	}
	
	@Test
	public void BG_incluirDocumentoPorArquivoTest() {
		Response response = given()
		.header("Authorization",BASIC)
		.contentType("application/json")
		.accept("application/json")
		.body(buildDocumentoPorArquivo())
		.when().post("/COSAP/documentos");
		
		documentoCriadoPorArquivo = response.getBody().as(RetornoInclusaoDocumento.class).getDocumentoFormatado();
		
		response.then().statusCode(201);
	}
	
	@Test
	public void BH_incluirDocumentoComTemplateTest() {
		String template = "gear-reajuste";
		
		Response response = given()
		.header("Authorization",BASIC)
		.contentType("application/json")
		.accept("application/json")
		.body(buildDocumentoComTemplate())
		.when().post("/COSAP/documentos?template="+template);
		
		documentoCriadoPorArquivo = response.getBody().as(RetornoInclusaoDocumento.class).getDocumentoFormatado();
		
		response.then().statusCode(201);
	}
	
//	@Test
	public void BI_incluirArquivoParticionado() throws IOException{
		String nomeArquivo = "OF-599-2016-DIDES-33902438169201608-16513178000176-ABI.pdf";
		Arquivo arquivo = buildArquivo(nomeArquivo);
		
		partesArquivo = dividirString(arquivo.getConteudo(), 4);
		arquivo.setConteudo(partesArquivo.get(0));
		
		Response response = given()
		.header("Authorization",BASIC)
		.contentType("application/json")
		.accept("application/json")
		.body(arquivo)
		.when().post("/cosap/arquivos");
		
		arquivoCriado = response.getBody().as(ArquivoCriado.class).getIdentificador();
		
		response.then().statusCode(201);
	}
	
//	@Test
	public void BJ_incluirPartesArquivo() throws IOException{
		Response response = null;
		ParteArquivo parte = new ParteArquivo();
		parte.setArquivo(arquivoCriado);
		
		
		for(int i=1; i < partesArquivo.size(); i++){
			System.out.println("Parte "+i);
			parte.setConteudo(partesArquivo.get(i));
			
			response = given()
				.header("Authorization",BASIC)
				.contentType("application/json")
				.accept("application/json")
				.body(parte)
				.when().put("/cosap/arquivos/"+arquivoCriado);			
		}
		
		response.then().statusCode(200);
	}
	
////	@Test
	public void BK_incluirDocumentoPorArquivoTest() {
		Response response = given()
		.header("Authorization",BASIC)
		.contentType("application/json")
		.accept("application/json")
		.body(buildDocumentoPorArquivo())
		.when().post("/COSAP/documentos");
		
		documentoCriadoPorArquivo = response.getBody().as(RetornoInclusaoDocumento.class).getDocumentoFormatado();
		
		response.then().statusCode(201);
	}
	
	@Test
	public void BL_abrirProcessoTest(){
		Response response = given()
				.header("Authorization",BASIC)
				.contentType("application/json")
				.accept("application/json")
				.body(buildNovoProcessoUmaUnidade())
				.when().post("/COSAP/processos");
		
		processoCriado = response.getBody().as(RetornoGeracaoProcedimento.class).getProcedimentoFormatado().replaceAll("[^0-9+]", "");
				
		response.then().statusCode(201);
	}
	
	@Test
	public void BM_sobrestarProcessoTest(){
		SobrestamentoProcesso s = new SobrestamentoProcesso();
		s.setProcesso(processoCriado);
		s.setMotivo("Teste automatizado de sobrestamento.");
		
		given()
		.header("Authorization",BASIC)
		.contentType("application/json")
		.accept("application/json")
		.body(s)
		.when().post("/cosap/processos/sobrestados").then().statusCode(200);
	}
	
	@Test
	public void BN_removerSobrestamentoProcessoTest(){
		given()
		.header("Authorization",BASIC)
		.contentType("application/json")
		.accept("application/json")
		.when().delete("/cosap/processos/sobrestados/"+processoCriado).then().statusCode(200);
	}
	
	@Test
	public void BO_listarContatos(){
		given()
		.header("Authorization",BASIC)
		.contentType("application/json")
		.accept("application/json")
		.when().get("cosap/contatos/operadoras?qtdRegistros=20").then().statusCode(200);
	}
	
	public NovoBloco buildNovoBloco(TipoBloco tipoBloco){
		NovoBloco bloco = new NovoBloco();
		
		
		bloco.setDescricao("Bloco criado por teste unitário.");
		bloco.setTipo(tipoBloco);
//		bloco.setDocumentos(new String[]{"186520"});
		bloco.setUnidades(new String[]{"110000935"});
		
		bloco.setDocumentos(null);
//		bloco.setUnidades(new String[1]);
				
		return bloco;
	}
	
	
	public EnvioDeProcesso buildEnvioDeProcesso(){
		EnvioDeProcesso envio = new EnvioDeProcesso();
		
		envio.setNumeroDoProcesso(processoCriado);
		envio.setUnidadesDestino(new String[]{"110000981","110001083"});
		
		return envio;
	}
	
	public CancelamentoDocumento buildCancelamentoDocumento(){
		CancelamentoDocumento cancelamento = new CancelamentoDocumento();
		
		cancelamento.setDocumento(documentoCriado);
		cancelamento.setMotivo("Testes unitários");
		
		return cancelamento;
	}
	
	public NovoProcesso buildNovoProcesso(){
		NovoProcesso processo = new NovoProcesso();
		Procedimento dadosProcesso = new Procedimento();
		
		dadosProcesso.setIdTipoProcedimento("100000832");
//		dadosProcesso.setIdTipoProcedimento("100000346");
		dadosProcesso.setEspecificacao("Processo de Teste criado via teste automatizado.");
		dadosProcesso.setAssuntos(new Assunto[]{});
		
		Interessado i = new Interessado();
		i.setNome("BRADESCO SAUDE E ASSISTENCIA S.A");
		i.setSigla("363022");
		
		dadosProcesso.setInteressados(new Interessado[]{i});
		dadosProcesso.setObservacao("Este processo foi inserido via ws para testar a integração.");
		dadosProcesso.setNivelAcesso("0");
				
		processo.setDadosProcesso(dadosProcesso);
		
		processo.setDocumentos(new Documento[]{});
		processo.setUnidadesDestino(new String[]{"COSAP"});
		
		return processo;
	}
	
	public NovoProcesso buildNovoProcessoUmaUnidade(){
		NovoProcesso processo = new NovoProcesso();
		Procedimento dadosProcesso = new Procedimento();
		
		dadosProcesso.setIdTipoProcedimento("100000832");
//		dadosProcesso.setIdTipoProcedimento("100000346");
		dadosProcesso.setEspecificacao("Processo de Teste criado via teste automatizado.");
		dadosProcesso.setAssuntos(new Assunto[]{});
		
		Interessado i = new Interessado();
		i.setNome("BRADESCO SAUDE E ASSISTENCIA S.A");
		i.setSigla("363022");
		
		dadosProcesso.setInteressados(new Interessado[]{i});
		dadosProcesso.setObservacao("Este processo foi inserido via ws para testar a integração.");
		dadosProcesso.setNivelAcesso("0");
				
		processo.setDadosProcesso(dadosProcesso);
		
		processo.setDocumentos(new Documento[]{});
		
		return processo;
	}	
	
	public Documento buildDocumento(){
//		String nomeArquivo = "caelum.pdf";
//		String nomeArquivo = "OF-272-2016-DIDES-33910000610201675-63554067000198-ABI_v1_r0.pdf";
//		String nomeArquivo = "introducao-a-arquitetura-e-design-de-software-uma-visao-sobre-a-plataforma-java.pdf";
//		String nomeArquivo = "OF-599-2016-DIDES-33902438169201608-16513178000176-ABI.pdf";
//		String nomeArquivo = "k19-k02-DesenvolvimentoWebComHTMLCSSJavaScript.pdf";
//		String nomeArquivo = "certificados.pdf";
		String nomeArquivo = "Dissertacao_GuilhermeMauroGerrmoglioBarbosa.pdf";
//		String nomeArquivo = "primefaces_users_guide_4_0_edtn2.pdf";
//		String nomeArquivo = "Cronicas_da_Inforfobia-Centro_Atlantico.pdf";
		Documento documento = new Documento();
				
		documento.setTipo("R");
		documento.setIdProcedimento(processoCriado);
		documento.setIdSerie("7");
		documento.setData("10/08/2015");
						
		Remetente remetente = new Remetente();
		remetente.setNome("Silvio Gomes");
		remetente.setSigla("silvio.gomes");
		documento.setRemetente(remetente);
		
		Interessado i = new Interessado();
		i.setNome("BRADESCO SAUDE E ASSISTENCIA S.A");
		i.setSigla("363022");		
		documento.setInteressados(new Interessado[]{i});
	
		Destinatario d = new Destinatario();
		d.setNome("Alexander Mesquita");
		d.setSigla("alexander.mesquita");		
		documento.setDestinatarios(new Destinatario[]{d});
				
		documento.setNomeArquivo(nomeArquivo);
		documento.setConteudo(getBase64(nomeArquivo));		
		
		return documento;
	}
	
	public Documento buildDocumentoPorArquivo(){
		Documento documento = new Documento();
		
		documento.setTipo("R");
		documento.setIdProcedimento(processoCriado);
		documento.setIdSerie("7");
		documento.setData("10/08/2015");
						
		Remetente remetente = new Remetente();
		remetente.setNome("Silvio Gomes");
		remetente.setSigla("silvio.gomes");
		documento.setRemetente(remetente);
		
		Interessado i = new Interessado();
		i.setNome("BRADESCO SAUDE E ASSISTENCIA S.A");
		i.setSigla("363022");		
		documento.setInteressados(new Interessado[]{i});
	
		Destinatario d = new Destinatario();
		d.setNome("Alexander Mesquita");
		d.setSigla("alexander.mesquita");		
		documento.setDestinatarios(new Destinatario[]{d});

		documento.setConteudo(null);
		documento.setIdArquivo(arquivoCriado);
		
		return documento;
	}
	
	public Documento buildDocumentoComTemplate(){
		Documento documento = new Documento();
		
		documento.setTipo("G");
		documento.setIdProcedimento(processoCriado);
		documento.setIdSerie("15");
								
		Remetente remetente = new Remetente();
		remetente.setNome("Silvio Gomes");
		remetente.setSigla("silvio.gomes");
		documento.setRemetente(remetente);
		
		Interessado i = new Interessado();
		i.setNome("BRADESCO SAUDE E ASSISTENCIA S.A");
		i.setSigla("363022");		
		documento.setInteressados(new Interessado[]{i});
	
		Destinatario d = new Destinatario();
		d.setNome("Alexander Mesquita");
		d.setSigla("alexander.mesquita");		
		documento.setDestinatarios(new Destinatario[]{d});

		documento.setConteudo(
				new String(Base64.encodeBase64(
						"{\"regAns\":\"363022\",\"razaoSocial\":\"BRADESCO SAUDE E ASSISTENCIA S.A\",\"emailCadop\":\"teste@teste.com\",\"nuCnpj\":\"0000000000\",\"noCargoRepr\":\"Diretor\",\"noRepresentante\":\"André Guimarães\",\"anoInicio\":\"2016\",\"anoFinal\":\"2017\",\"numeroDocumento\":\"00000000\"}"
						.getBytes())));
				
		return documento;
	}
	
	public Documento buildDocumentoInterno(){
		Documento documento = new Documento();
		
		documento.setTipo("G");
//		documento.setIdProcedimento("33910000173201771");
		documento.setIdProcedimento(processoCriado);
		documento.setIdSerie("15");
				
		Interessado i = new Interessado();
		i.setNome("BRADESCO SAUDE E ASSISTENCIA S.A");
		i.setSigla("363022");		
		documento.setInteressados(new Interessado[]{i});

		documento.setConteudo("PGh0bWw+PGhlYWQ+PC9oZWFkPjxib2R5PlRFU1RFVEVTVEU8L2JvZHk+PC9odG1sPg==");
		
//		documento.setConteudo(Base64.encodeBase64String("<BR><BR>Tendo em vista que o processo foi criado indevidamente pelo sistema de integração do ressarcimento ao SUS, considera-se encerrado o processo eletrônico, sendo vedada qualquer juntada de novos documentos eletrônicos por meio do SEI.<BR>".getBytes()));		
		
		return documento;
	}
	
	public Arquivo buildArquivo(String nomeArquivo) throws IOException{		
		Arquivo arquivo = new Arquivo();
			
		arquivo.setNome(nomeArquivo);
		arquivo.setTamanho("4477292");
		arquivo.setConteudo(getBase64(nomeArquivo));
		arquivo.setHash(getMD5(nomeArquivo));
		
		return arquivo;
	}
		
	public String getMD5(String arquivo) throws IOException{
		ClassLoader classLoader = getClass().getClassLoader();
		FileInputStream fis = new FileInputStream(new File(classLoader.getResource(arquivo).getFile()));
		String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(IOUtils.toByteArray(fis));
		fis.close();
		
		return md5;
	}
	
	@SuppressWarnings("resource")
	public String getBase64(String arquivo){
		ClassLoader classLoader = getClass().getClassLoader();
		
		File file = new File(classLoader.getResource(arquivo).getFile());
	 
	    byte[] bytes = new byte[(int) file.length()];
	    
        try {
        	FileInputStream fileInputStream = new FileInputStream(file);
			fileInputStream.read(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    return new String(Base64.encodeBase64(bytes));
	}
	
	public String getProcessoCriado(){
		return processoCriado;
	}
	
	public String getDocumentoCriado(){
		return documentoCriado;
	}

	public static String getDocumentoInternoGerado() {
		return documentoInternoCriado;
	}

	public static void setDocumentoInternoGerado(String documentoInternoCriado) {
		SeiBrokerTest.documentoInternoCriado = documentoInternoCriado;
	}
	
	public static List<String> dividirString(String texto, int qtdPartes){
		List<String> partes = new ArrayList<String>();
		
		int tamanhoParte = texto.length() / qtdPartes;
			
		for(int inicio = 0; inicio < texto.length(); inicio += tamanhoParte){
			
			partes.add(texto.substring(inicio, Math.min(texto.length(), inicio + tamanhoParte)));
		}
		
		return partes;
	}
 }
