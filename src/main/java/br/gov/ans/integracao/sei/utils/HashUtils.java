package br.gov.ans.integracao.sei.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;


public class HashUtils {
	
	/**
	 * Exclusiva para encoding Base64
	 * 
	 */
	public static class BASE64 {

		
		/**
		 * <p>Codifica uma String utilizado o algoritimo Base64.</p>
		 * 
		 * @param input valor a ser decodificado
		 * @param salt valor opcional a ser concatenado na codificação
		 * @return String codificada 
		 */
		public static String encrypt(String input, String salt) {
			String cipher = null;

			if(salt != null){
				cipher = new String(Base64.encodeBase64(salt.getBytes(StandardCharsets.UTF_8)));
			}
			cipher += new String(Base64.encodeBase64(input.getBytes(StandardCharsets.UTF_8)));
				      
			return cipher; 
		}

		/**
		 * <p>Decodifica uma String utilizado o algoritimo Base64.</p>
		 * 
		 * @param input valor a ser decodificado.
		 * @param salt valor aleatório opcional concatenado na codificação que deve ser removido
		 * @return String decodificada
		 */
		public static String decrypt(String input, String salt) {
			if(salt != null){
				if(input.length() > salt.length()) {
					byte[] key =  Base64.encodeBase64(salt.getBytes(StandardCharsets.UTF_8));
					input = input.substring(key.length);
				} else{
					return null;
				}
			} 
			
			return new String(Base64.decodeBase64(input.getBytes()), StandardCharsets.UTF_8);
		}
	}
	
	/**
	 * Exclusiva para encoding SHA-256
	 * 
	 */
	public static class SHA256 {
		
		/**
		 * <p>Calcula o hash utilizado o algoritimo SHA-256, o método recebe os parametros input e salt,
		 * sendo o input obrigatório e o salt opcional.</p>
		 * 	
		 * @param input valor a ser codificado
		 * @param salt valor opcional a ser concatenado na codificação (opcional)
		 * 
		 * @return resultado do calculo do hash.
		 *         
		 * @throws NoSuchAlgorithmException quando o algoritimo de criptografia <b>SHA-256</b> não estiver disponível 
		 * 
		 */
		public static String encrypt(String input, String salt) throws NoSuchAlgorithmException {
			MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

			if (salt != null) {
				input = input + salt;
			}

			byte[] inputBytes = input.getBytes();
			byte[] inputHash = sha256.digest(inputBytes);
			StringBuilder sb = new StringBuilder();
			
			for (int i = 0; i < inputHash.length; i++) {
				sb.append(Integer.toString((inputHash[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			
			String generatedHash = sb.toString();
			return generatedHash;
		}
	}


}
