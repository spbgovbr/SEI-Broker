# SEI-Broker - Camada REST de acesso ao SEI
O SEI-Broker é uma camada de integração que foi desenvolvida para integrar os sistemas corporativos da ANS e o SEI, Sistema Eletrônico de Informações do TRF 4. Toda essa camada foi criada utilizando o padrão RESTful de serviços web, sua função é facilitar a integração dos sistemas da ANS com o SEI, utilizando um protocolo mais simples, moderno e performático. A adoção do broker nos permite criar uma camada de abstração para evitar que mudanças no SEI afetem as integrações, isso é possível pelo fato do broker utilizar REST e possuir contrato fraco.

O Broker atualmente está na versão 2.9, essa versão é compatível com o SEI 3.0.13. É importante destacar que é necessário atualizar o SEI-Broker a cada atualização do SEI que altere os serviços web.

Alguns serviços adicionais foram desenvolvidos extrair dados que não são ofertados pelos serviços nativos do SEI, estes serviços fazem acesso a base de dados do SEI e realizam operações apenas de consulta. A escrita de dados continua a ser exclusividade dos serviços nativos, desta forma a integridade e coerência das inserções é mantida.

## Requisitos
- SEI 3.0.13 instalado/atualizado.
- Código-fonte do Broker pode ser baixado a partir do link a seguir, sempre utilize uma versão compatível com o SEI: [https://softwarepublico.gov.br/gitlab/ans/sei-broker/tags](https://softwarepublico.gov.br/gitlab/ans/sei-broker/tags "Clique e acesse")
- Apache Maven para baixar as dependências e compilar o pacote.
- Servidor JBoss EAP 7.0.4 ou Wildfly 10.
- Banco relacional, o Broker foi desenvolvido usando Oracle 12g, mas com pouco esforço pode utilizar o MySQL.
- Conexão com a internet para que o Maven acesse os repositórios hospedeiros das dependências.

## Procedimentos para instalação
1. Configure as propriedades dos datasources no JBoss, elas são declaradas como System Properties.

O broker possui dois datasources e ambos estão declarados no arquivo `sei-broker-ds.xml`, eles são identificados como `jdbc/sei-broker` e `jdbc/sei-mysql`. O `jdbc/sei-broker` foi definido para acessar as tabelas que foram projetadas para o broker, o `jdbc/sei-mysql` se conecta ao banco de dados do SEI.

| Chave											| Valor 										|
| --------------------------------------------- | --------------------------------------------- |
| br.gov.ans.seiBroker.db.oracle.connectionUrl	| String de conexão com o banco Oracle			|
| br.gov.ans.seiBroker.db.oracle.password		| Senha do usuário utilizado no datasource		|
| br.gov.ans.seiBroker.db.mysql.connectionUrl	| String de conexão com o banco MySQL do SEI	|
| br.gov.ans.seiBroker.db.mysql.user			| Nome usuário com acesso ao MySQL do SEI		|
| br.gov.ans.seiBroker.db.oracle.password		| Senha do usuário com acesso ao MySQL do SEI	|

Abaixo um exemplo de declaração de propriedades feita no arquivo `standalone.xml`.
```xml
<!-- Geralmente no início do arquivo, após as extensions -->
<system-properties>
	<!-- Outras propriedades ... -->
	<property name="br.gov.ans.seiBroker.db.oracle.connectionUrl" value="jdbc:oracle:thin:@exans01db04.ans.gov.br:1523:anshm2"/>
	<property name="br.gov.ans.seiBroker.db.oracle.password" value="******"/>
	<property name="br.gov.ans.seiBroker.db.mysql.user" value="usuario_sei_brok"/>
	<property name="br.gov.ans.seiBroker.db.mysql.connectionUrl" value="jdbc:mysql://anshmmysql01:3306/sei-ds"/>
	<property name="br.gov.ans.seiBroker.db.mysql.password" value="******"/>
</system-properties>
```
