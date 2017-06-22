package br.gov.ans.integracao.sei.testes;

import static com.jayway.restassured.RestAssured.given;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.codec.binary.Base64;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.gov.ans.integracao.sei.client.Assunto;
import br.gov.ans.integracao.sei.client.Destinatario;
import br.gov.ans.integracao.sei.client.Documento;
import br.gov.ans.integracao.sei.client.Interessado;
import br.gov.ans.integracao.sei.client.Procedimento;
import br.gov.ans.integracao.sei.client.Remetente;
import br.gov.ans.integracao.sei.client.RetornoGeracaoProcedimento;
import br.gov.ans.integracao.sei.client.RetornoInclusaoDocumento;
import br.gov.ans.integracao.sei.modelo.CancelamentoDocumento;
import br.gov.ans.integracao.sei.modelo.EnvioDeProcesso;
import br.gov.ans.integracao.sei.modelo.NovoBloco;
import br.gov.ans.integracao.sei.modelo.NovoProcesso;
import br.gov.ans.integracao.sei.modelo.TipoBloco;

import com.jayway.restassured.response.Response;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SeiBrokerTest extends FunctionalTest{	
	
	static String documentoCriado;
	static String documentoInternoCriado;
	static String processoCriado;
	static String blocoCriado;
		
	@Test
	public void AA_incluirProcessoTest(){
		Response response = given()
				.auth()
				.basic(USUARIO, SENHA)
				.contentType("application/json")
				.body(buildNovoProcesso())
				.when().post("/COSAP/processos");
		
		processoCriado = response.getBody().as(RetornoGeracaoProcedimento.class).getProcedimentoFormatado().replaceAll("[^0-9+]", "");
				
		response.then().statusCode(201);
	}
	
	@Test
	public void AB_consultarProcessoTest(){
		given().auth().basic(USUARIO, SENHA).when().get("/cosap/processos/"+processoCriado).then().statusCode(200);
	}
	
	@Test
	public void AC_incluirDocumentoTest() {
		Response response = given()
		.auth()
		.basic(USUARIO, SENHA)
		.contentType("application/json")
		.body(buildDocumento())
		.when().post("/COSAP/documentos");
		
		documentoCriado = response.getBody().as(RetornoInclusaoDocumento.class).getDocumentoFormatado();
		
		response.then().statusCode(201);
	}
	
	@Test
	public void AD_incluirDocumentoInternoTest() {
		Response response = given()
		.auth()
		.basic(USUARIO, SENHA)
		.contentType("application/json")
		.body(buildDocumentoInterno())
		.when().post("/COSAP/documentos");
		
		documentoInternoCriado = response.getBody().as(RetornoInclusaoDocumento.class).getDocumentoFormatado();
		
		response.then().statusCode(201);
	}

	@Test
    public void AE_consultarDocumentoTest() {	
        given().auth().basic(USUARIO, SENHA).when().get("/cosap/documentos/"+documentoCriado).then().statusCode(200);
    }
	
	@Test
	public void AF_consultarDocumentosEnviadosBrokerTest(){
		given().auth().basic(USUARIO, SENHA).when().get("/cosap/documentos/enviados-broker").then().statusCode(200);
	}
	
	@Test
	public void AG_consultarDocumentosPorInteressadosTest(){
		given().auth().basic(USUARIO, SENHA).when().get("/363022/documentos").then().statusCode(200);
	}
	
	@Test
	public void AH_consultarProcessosPorInteressadosTest(){
		given().auth().basic(USUARIO, SENHA).when().get("/363022/processos").then().statusCode(200);
	}
	
	@Test
	public void AI_exportarDocumentoTest(){
		given().auth().basic(USUARIO, SENHA).when().get("/cosap/documentos/"+documentoCriado+"/pdf").then().statusCode(200);
	}
	
	@Test
	public void AJ_listarUnidadesTest(){
		given().auth().basic(USUARIO, SENHA).when().get("/unidades").then().statusCode(200);
	}
	
	@Test
	public void AL_listarSeriesTest(){
		given().auth().basic(USUARIO, SENHA).when().get("/cosit/series").then().statusCode(200);
	}
	
	@Test
	public void AM_listarTiposDeProcessoTest(){
		given().auth().basic(USUARIO, SENHA).when().get("/cosit/processos/tipos").then().statusCode(200);
	}
	
	@Test
	public void AN_listarExtensoesTest(){
		given().auth().basic(USUARIO, SENHA).when().get("/cosit/extensoes").then().statusCode(200);
	}
	
	@Test
	public void AO_consultarCodigoUnidadeTest(){
		given().auth().basic(USUARIO, SENHA).when().get("/unidades/cotec/codigo").then().statusCode(200);
	}
	
	@Test
	public void AP_cancelarDocumentoTest(){
		given()
		.auth()
		.basic(USUARIO, SENHA)
		.contentType("application/json")
		.body(buildCancelamentoDocumento())
		.when().post("/cosap/documentos/cancelados").then().statusCode(200);
	}
	
	@Test
	public void AQ_concluirProcessoTest(){
		given()
		.auth()
		.basic(USUARIO, SENHA)
		.contentType("application/json")
		.body(processoCriado)
		.when().post("/cosap/processos/concluidos").then().statusCode(200);
	}
	
	@Test
	public void AR_reabrirProcessoTest(){
		given().auth().basic(USUARIO, SENHA).when().delete("/cosap/processos/concluidos/"+processoCriado).then().statusCode(200);
	}	
	
	@Test
	public void AS_gerarBlocoReuniaoTest(){
		Response response = given()
		.auth()
		.basic(USUARIO, SENHA)
		.contentType("application/json")
		.body(buildNovoBloco(TipoBloco.REUNIAO))
		.when().post("/cosap/blocos");
		
		blocoCriado = response.getBody().as(String.class);
		
		response.then().statusCode(201);
	}
	
	@Test
	public void AT_incluirProcessoNoBlocoTest(){
		given()
		.auth()
		.basic(USUARIO, SENHA)
		.body(processoCriado)
		.when().post("/cosap/blocos/"+blocoCriado+"/processos").then().statusCode(200);
		
	}
	
	@Test
	public void AU_removerProcessoBlocoTest(){
		given()
		.auth()
		.basic(USUARIO, SENHA)
		.body(processoCriado)
		.when().delete("/cosap/blocos/"+blocoCriado+"/processos/"+processoCriado).then().statusCode(200);		
	}
	
	@Test
	public void AV_gerarBlocoAssinaturaTest(){
		Response response = given()
		.auth()
		.basic(USUARIO, SENHA)
		.contentType("application/json")
		.body(buildNovoBloco(TipoBloco.ASSINATURA))
		.when().post("/cosap/blocos");
		
		blocoCriado = response.getBody().as(String.class);
		
		response.then().statusCode(201);
	}
	
	@Test
	public void AX_incluirDocumentoNoBlocoTest(){
		given()
		.auth()
		.basic(USUARIO, SENHA)
		.body(documentoInternoCriado)
		.when().post("/cosap/blocos/"+blocoCriado+"/documentos").then().statusCode(200);
		
	}
	
	@Test
	public void AZ_disponibilizarBlocoTest(){
		given()
		.auth()
		.basic(USUARIO, SENHA)
		.body(blocoCriado)
		.when().post("/cosap/blocos/disponibilizados").then().statusCode(200);
		
	}

	@Test
	public void BA_indisponibilizarBlocoTest(){
		given()
		.auth()
		.basic(USUARIO, SENHA)
		.when().delete("/cosap/blocos/disponibilizados/"+blocoCriado).then().statusCode(200);

	}
	
	@Test
	public void BB_consultarBlocoTest(){
		given()
		.auth()
		.basic(USUARIO, SENHA)
		.when().get("/cosap/blocos/"+blocoCriado).then().statusCode(200);

	}
	
	@Test
	public void BC_removerDocumentoBlocoTest(){
		given()
		.auth()
		.basic(USUARIO, SENHA)
		.when().delete("/cosap/blocos/"+blocoCriado+"/documentos/"+documentoInternoCriado).then().statusCode(200);
	}
	
	@Test
	public void BD_excluirBlocoTest(){
		given()
		.auth()
		.basic(USUARIO, SENHA)
		.when().delete("/cosap/blocos/"+blocoCriado).then().statusCode(200);
	}
	
	@Test
	public void BE_enviarProcessoTest(){
		given()
		.auth()
		.basic(USUARIO, SENHA)
		.contentType("application/json")
		.body(buildEnvioDeProcesso())
		.when().post("/cosap/processos/enviados").then().statusCode(200);
	}

	public NovoBloco buildNovoBloco(TipoBloco tipoBloco){
		NovoBloco bloco = new NovoBloco();
		
		bloco.setDescricao("Bloco criado por teste unitário.");
		bloco.setTipo(tipoBloco);
//		bloco.setDocumentos(new String[]{documentoCriado});
		bloco.setUnidades(new String[]{"110000935"});
		
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
		
		dadosProcesso.setIdTipoProcedimento("100000563");
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
		processo.setUnidadesDestino(new String[]{"110000935","110000934"});
		
		return processo;
	}
	
	public Documento buildDocumento(){
		Documento documento = new Documento();
		
		documento.setTipo("R");
		documento.setIdProcedimento(processoCriado);
		documento.setIdSerie("5");
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
		
		documento.setNomeArquivo("Dissertacao_GuilhermeMauroGerrmoglioBarbosa.pdf");
		documento.setConteudo(getBase64());		
		
		return documento;
	}
	
	public Documento buildDocumentoInterno(){
		Documento documento = new Documento();
		
		documento.setTipo("G");
		documento.setIdProcedimento(processoCriado);
		documento.setIdSerie("15");
		
		Interessado i = new Interessado();
		i.setNome("BRADESCO SAUDE E ASSISTENCIA S.A");
		i.setSigla("363022");		
		documento.setInteressados(new Interessado[]{i});
				
		documento.setConteudo(Base64.encodeBase64String("<BR><BR>Tendo em vista que o processo foi criado indevidamente pelo sistema de integração do ressarcimento ao SUS, considera-se encerrado o processo eletrônico, sendo vedada qualquer juntada de novos documentos eletrônicos por meio do SEI.<BR>".getBytes()));		
		
		return documento;
	}
	
	public String getBase64(){
		ClassLoader classLoader = getClass().getClassLoader();
		
		File file = new File(classLoader.getResource("Dissertacao_GuilhermeMauroGerrmoglioBarbosa.pdf").getFile());
	 
	    byte[] bytes = new byte[(int) file.length()];
	    
        try {
        	FileInputStream fileInputStream = new FileInputStream(file);
			fileInputStream.read(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    return Base64.encodeBase64String(bytes);
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
}
