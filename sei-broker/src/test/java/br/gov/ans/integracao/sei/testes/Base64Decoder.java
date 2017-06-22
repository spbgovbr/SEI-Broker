package br.gov.ans.integracao.sei.testes;

import org.apache.commons.codec.binary.Base64;


public class Base64Decoder {
	public static void main(String[] args) {
		String base64 = "eyJudW1lcm9Qcm9jZXNzbyI6IjMzOTEwMDAwMTIzMjAxNzkzIiwKInJhemFvU29jaWFsIjoiQU1JTCBBU1NJU1TKTkNJQSBNyURJQ0EgSU5URVJOQUNJT05BTCBTLkEuIiwKImNucGpPcGVyYWRvcmEiOiIyOTMwOTEyNzAwMDE3OSIsCiJudW1lcm9SZWdpc3Ryb0FucyI6IjMyNjMwNSIsCiJkYXRhUHJvdG9jb2xvIjoiMDEvMDIvMjAxNyIsCiJtZXNJbmljaW9BcGxpY2FjYW8iOiIwNS8yMDE3IiwKImFub0luaWNpb0FwbGljYWNhbyI6IjA0LzIwMTgifQo=";
		
		System.out.println(new String(Base64.decodeBase64(base64)));
	}
}
