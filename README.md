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
### 1 - Configure as propriedades dos datasources no JBoss, elas são declaradas como System Properties.

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
	<property name="br.gov.ans.seiBroker.db.oracle.connectionUrl" value="STRING_CONEXAO_BD_BROKER"/>
	<property name="br.gov.ans.seiBroker.db.oracle.password" value="SENHA_USUARIO_SEI_BROKER"/>
	<property name="br.gov.ans.seiBroker.db.mysql.user" value="USUARIO_MYSQL"/>
	<property name="br.gov.ans.seiBroker.db.mysql.connectionUrl" value="STRING_CONEXAO_BD_SEI"/>
	<property name="br.gov.ans.seiBroker.db.mysql.password" value="SENHA_USUARIO_BD_SEI"/>
</system-properties>
```

### 2 - Arquivos de propriedades
O SEI-Broker faz uso de dois arquivos de propriedades que ficam na pasta `<JBOSS_HOME>\ans\properties`, os arquivos necessários são `services.properties` e `ws-users.properties`.

<table>
  <tr>
    <th>Arquivo</th>
    <th>Propriedade</th>
    <th>Descrição</th>
  </tr>
  <tr>
    <td rowspan="3">services.properties</td>
    <td>sei.ws.uri</td>
    <td>URL do SEI</td>
  </tr>
  <tr>
    <td>sip.ws.uri</td>
    <td>URL do SIP</td>
  </tr>
  <tr>
    <td>templates.broker.uri</td>
    <td>URL do templates-broker</td>
  </tr>
  <tr>
    <td rowspan="2">ws-users-properties</td>
    <td>sei.broker.user</td>
    <td>Usuário de integração do broker</td>
  </tr>
  <tr>
    <td>sei.broker.password</td>
    <td>Senha do usuário de integração</td>
  </tr>
</table>