package br.gov.ans.integracao.sei.testes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

public class Base64Encoder {

	public static void main(String[] args) throws IOException {
		String string = "{\"content\":\"tentativa 1 &#x000D; tentativa 2 &#13; tentativa 3 \\r tentativa 4 \\n fim! <br> teste da tag \"}";
		
//		System.out.println(string);
		
//		System.out.println(Base64.encodeBase64String(string.getBytes()));
		Base64Encoder encoder = new Base64Encoder();
				
		System.out.println(encoder.getBase64("0693609.pdf"));
		System.out.println(encoder.getMD5("0693609.pdf"));
		System.out.println(Base64.encodeBase64String("{\"regAns\":\"363022\",\"razaoSocial\":\"BRADESCO SAUDE E ASSISTENCIA S.A\",\"emailCadop\":\"teste@teste.com\",\"nuCnpj\":\"0000000000\",\"noCargoRepr\":\"Diretor\",\"noRepresentante\":\"André Guimarães\",\"anoInicio\":\"2016\",\"anoFinal\":\"2017\",\"numeroDocumento\":\"00000000\"}".getBytes()));
	}
	
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
	    
	    return Base64.encodeBase64String(bytes);
	}
	
	public String getMD5(String arquivo) throws IOException{
		ClassLoader classLoader = getClass().getClassLoader();
		FileInputStream fis = new FileInputStream(new File(classLoader.getResource(arquivo).getFile()));
		String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
		fis.close();
		
		return md5;
	}
}

