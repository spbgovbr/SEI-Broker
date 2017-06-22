package br.gov.ans.factories.qualifiers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
public @interface Autenticado {
	
	/**
	 * Prefixo da chave registrada no arquivo ws-users.properties no JBoss.
	 * @return
	 */
	String value();
}
