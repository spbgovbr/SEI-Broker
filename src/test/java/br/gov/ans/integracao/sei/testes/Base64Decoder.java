package br.gov.ans.integracao.sei.testes;

import org.apache.commons.codec.binary.Base64;


public class Base64Decoder {
	public static void main(String[] args) {
		String base64 = "PGh0bWw+PGhlYWQ+PC9oZWFkPjxib2R5PlRFU1RFVEVTVEU8L2JvZHk+PC9odG1sPg==";

		System.out.println(new String(Base64.decodeBase64(base64.getBytes())));
	}
}
