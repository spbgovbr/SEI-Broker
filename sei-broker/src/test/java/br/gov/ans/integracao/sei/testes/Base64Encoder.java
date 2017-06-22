package br.gov.ans.integracao.sei.testes;

import org.apache.commons.codec.binary.Base64;

public class Base64Encoder {

	public static void main(String[] args) {
		String string = "{\"conteudo\":\"tentativa 1 &#x000D; tentativa 2 &#13; tentativa 3 \\r tentativa 4 \\n fim! \"}";
		System.out.println(string);
		System.out.println(Base64.encodeBase64String(string.getBytes()));
	}
	
}
