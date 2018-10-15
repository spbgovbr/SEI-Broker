# SEI-Broker - Camada REST de acesso ao SEI
O SEI-Broker é uma camada de integração que foi desenvolvida para integrar os sistemas corporativos da ANS e o SEI, Sistema Eletrônico de Informações do TRF 4. Toda essa camada foi criada utilizando o padrão RESTful de serviços web, sua função é facilitar a integração dos sistemas da ANS com o SEI, utilizando um protocolo mais simples, moderno e performático. A adoção do broker nos permite criar uma camada de abstração para evitar que mudanças no SEI afetem as integrações, isso é possível pelo fato do broker utilizar REST e possuir contrato fraco.

O Broker atualmente está na versão 2.9, essa versão é compatível com o SEI 3.0.13. É importante destacar que é necessário atualizar o SEI-Broker a cada atualização do SEI que altere os serviços web.

Alguns serviços adicionais foram desenvolvidos para extrair dados que não são ofertados pelos serviços nativos do SEI, estes serviços fazem acesso a base de dados do SEI e realizam operações apenas de consulta. A escrita de dados continua a ser exclusividade dos serviços nativos, desta forma a integridade e coerência das inserções é mantida.

## Requisitos
- SEI 3.0.13 instalado/atualizado.
- Código-fonte do Broker pode ser baixado a partir do link a seguir, sempre utilize uma versão compatível com o SEI: [https://softwarepublico.gov.br/gitlab/ans/sei-broker/tags](https://softwarepublico.gov.br/gitlab/ans/sei-broker/tags "Clique e acesse")
- [Apache Maven](https://maven.apache.org/) para baixar as dependências e compilar o pacote.
- Servidor [JBoss EAP 7.0.4](https://developers.redhat.com/products/eap/download/) ou [Wildfly 10](http://wildfly.org/downloads/).
- Banco relacional, o Broker foi desenvolvido usando Oracle 12g, mas com pouco esforço pode utilizar o MySQL.
- Conexão com a internet para que o Maven acesse os repositórios hospedeiros das dependências.
- [Templates-broker](https://softwarepublico.gov.br/gitlab/ans/templates-broker) implantado e configurado. Este requisito é **opcional**, ele é necessário caso haja interesse em utilizar o [Gerenciador de Templates](https://softwarepublico.gov.br/gitlab/ans/templates-web).

## Procedimentos para instalação
### Configure as propriedades dos datasources no JBoss, elas são declaradas como System Properties.

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

### Criar e configurar os arquivos de propriedades no JBoss

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
    <td rowspan="2">ws-users.properties</td>
    <td>sei.broker.user</td>
    <td>Usuário de integração do broker</td>
  </tr>
  <tr>
    <td>sei.broker.password</td>
    <td>Senha do usuário de integração</td>
  </tr>
</table>

### Criar security-domain no JBoss

É necessário que haja um security-domain registrado com o nome `ans-ws-auth`, o mesmo pode utilizar um banco de dados[^1] ou o LDAP. É importante destacar que o Broker trabalha com autorização baseada em papéis(RBAC[^2]) e que os usuários precisam ter seus papéis atribuídos.
[^1]: https://access.redhat.com/documentation/en-us/red_hat_jboss_enterprise_application_platform/7.0/html/how_to_configure_identity_management/configuring_a_security_domain_to_use_a_database
[^2]: https://en.wikipedia.org/wiki/Role-based_access_control

### Implantar pacote gerado pelo Maven

Após a realização de todos os passos anteriores, teremos o JBoss pronto para receber o pacote do SEI-Broker. O deploy pode ser feito de diversas maneiras e não é o foco desse manual. 

Para essa etapa é necessário ter o Maven instalado e configurado. Ao realizar o primeiro build devemos desabilitar os testes automatizados, os testes dependem de uma instância ativa e impedirão a geração do pacote.

### Configurar Sistema no SEI

É preciso cadastrar o SEI-Broker como um sistema que se integrará ao SEI, conforme [manual de webservices do SEI](https://softwarepublico.gov.br/social/sei/manuais/manual-de-webservices). É importante ter atenção para a **sigla** quer será definida, por padrão o broker está configurado para utilizar a sigla `SEI-Broker`.

Após o cadastro do Sistema precisaremos atribuir os serviços que serão utilizados pelo Broker, nesta etapa é preciso ter atenção para o valor que será definido no campo **identificação** e aos **servidores**. A identificação do serviço precisa ser enviada a cada requisição feita aos serviços do SEI, por padrão o Broker utiliza o valor `REALIZAR_INTEGRACAO`. No campo servidores informaremos os IPs dos servidores onde o SEI-Broker estará implantado.

Os valores utilizados pelo Broker podem ser configurados na classe `Constantes`, **sigla** e **identificação** são respectivamente `SIGLA_SEI_BROKER` e `CHAVE_IDENTIFICACAO`.
