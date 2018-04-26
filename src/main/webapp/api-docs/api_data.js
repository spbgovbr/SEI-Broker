define({ "api": [
  {
    "type": "post",
    "url": "/:unidade/arquivos",
    "title": "Adicionar arquivo",
    "name": "adicionarArquivo",
    "group": "Arquivo",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>O serviço criará um arquivo no repositório de documentos e retornará seu identificador. O envio do arquivo poderá ser particionado com chamadas posteriores ao serviço de Adicionar Conteúdo Arquivo. Após todo o conteúdo ser transferido o arquivo será ativado e poderá ser associado com um documento externo no serviço de inclusão de documento. Serão excluídos em 24 horas os arquivos não completados e não associados a um documento.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          }
        ],
        "Request Body": [
          {
            "group": "Request Body",
            "type": "Arquivo",
            "optional": false,
            "field": "arquivo",
            "description": "<p>Objeto representando um arquivo.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "arquivo.nome",
            "description": "<p>Nome do arquivo.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "arquivo.tamanho",
            "description": "<p>Tamanho total do arquivo em bytes.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "arquivo.hash",
            "description": "<p>MD5 do conteúdo total do arquivo.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "arquivo.conteudo",
            "description": "<p>Conteúdo total ou parcial codificado em Base64, máximo de 15MB.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: https://<host>/sei-broker/service/COSAP/arquivos\n\nbody:\n{\n\t\"nome\":\"documentos-sei-broker.pdf\",\n\t\"tamanho\":\"2048\",\n\t\"hash\":\"45F1DEFFB45A5F6C2380A4CEE9B3E452\",\n\t\"conteudo\":\"Conteúdo total ou parcial do arquivo\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 201": [
          {
            "group": "Sucesso - 201",
            "type": "ArquivoCriado",
            "optional": false,
            "field": "arquivo",
            "description": "<p>Objeto de retorno da criação do arquivo</p>"
          },
          {
            "group": "Sucesso - 201",
            "type": "String",
            "optional": false,
            "field": "arquivo.identificador",
            "description": "<p>Identificador do arquivo criado.</p>"
          }
        ],
        "Sucesso Response Header - 201": [
          {
            "group": "Sucesso Response Header - 201",
            "type": "header",
            "optional": false,
            "field": "Location",
            "description": "<p>URL de acesso ao recurso criado.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 201 Created\n{\n\t\"identificador\":\"123456\"\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/ArquivoResource.java",
    "groupTitle": "Arquivo"
  },
  {
    "type": "put",
    "url": "/:unidade/arquivos/:arquivo",
    "title": "Adicionar conteúdo arquivo",
    "name": "adicionarConteudoArquivo",
    "group": "Arquivo",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Adiciona conteúdo a um arquivo criado, o sistema identificará automaticamente quando o conteúdo foi completado validando o tamanho em bytes e o hash do conteúdo. Quando as condições forem satisfeitas o arquivo será ativado e poderá ser utilizado nas chamadas de inclusão de documento.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI.</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "arquivo",
            "description": "<p>Identificador do arquivo que receberá o conteúdo.</p>"
          }
        ],
        "Request Body": [
          {
            "group": "Request Body",
            "type": "ParteArquivo",
            "optional": false,
            "field": "parte",
            "description": "<p>Objeto representando uma parte do arquivo.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "parte.arquivo",
            "description": "<p>Identificador do arquivo que receberá o conteúdo.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "parte.conteudo",
            "description": "<p>Conteúdo parcial codificado em Base64, máximo de 15MB.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: https://<host>/sei-broker/service/COSAP/arquivos/123456\n\nbody:\n{\n\t\"arquivo\":\"123456\",\n\t\"conteudo\":\"Conteúdo parcial do arquivo\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "ArquivoCriado",
            "optional": false,
            "field": "arquivo",
            "description": "<p>Objeto de retorno da criação do arquivo</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "arquivo.identificador",
            "description": "<p>Identificador do arquivo criado.</p>"
          }
        ],
        "Sucesso Response Header - 200": [
          {
            "group": "Sucesso Response Header - 200",
            "type": "header",
            "optional": false,
            "field": "Location",
            "description": "<p>URL de acesso ao recurso alterado.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 Created\n{\n\t\"identificador\":\"123456\"\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/ArquivoResource.java",
    "groupTitle": "Arquivo"
  },
  {
    "type": "delete",
    "url": "/:unidade/blocos/disponibilizados/12",
    "title": "Indisponibilizar bloco",
    "name": "cancelarDisponibilizacaoBloco",
    "group": "Bloco",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Cancela a disponibilização de blocos.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "bloco",
            "description": "<p>Numero do bloco que deseja indisponibilizar</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -X DELETE http://<host>/sei-broker/service/COSAP/blocos/disponibilizados/12",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "Boolean",
            "optional": false,
            "field": "resultado",
            "description": "<p>Booleano informando sucesso da requisição</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/BlocoResource.java",
    "groupTitle": "Bloco"
  },
  {
    "type": "get",
    "url": "/:unidade/blocos/:bloco",
    "title": "Consultar bloco",
    "name": "consultarBloco",
    "group": "Bloco",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Recupera as informações do bloco informado.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "bloco",
            "description": "<p>Numero do bloco que deseja consultar</p>"
          }
        ],
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "protocolos",
            "defaultValue": "N",
            "description": "<p>S ou N para exibir os protocolos do bloco</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:",
        "content": "curl -i http://<host>/sei-broker/service/COSAP/blocos/12",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "RetornoConsultaBloco",
            "optional": false,
            "field": "bloco",
            "description": "<p>Objeto representando o bloco encontrado</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "bloco.idBloco",
            "description": "<p>Número do bloco</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Unidade",
            "optional": false,
            "field": "bloco.unidade",
            "description": "<p>Dados das unidade que gerou o bloco</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "bloco.unidade.idUnidade",
            "description": "<p>Identificador da Unidade</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "bloco.unidade.sigla",
            "description": "<p>Sigla da unidade</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "bloco.unidade.descricao",
            "description": "<p>Descrição do unidade</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "bloco.unidade.sinProtocolo",
            "description": "<p>:TODO pendente</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "bloco.unidade.sinArquivamento",
            "description": "<p>:TODO pendente</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "bloco.unidade.sinOuvidoria",
            "description": "<p>:TODO pendente</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Usuario",
            "optional": false,
            "field": "bloco.usuario",
            "description": "<p>Dados das unidade que gerou o bloco</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "bloco.usuario.idUsuario",
            "description": "<p>Identificador do suário</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "bloco.usuario.sigla",
            "description": "<p>Sigla do usuário</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "bloco.usuario.nome",
            "description": "<p>Nome do usuário</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "bloco.descricao",
            "description": "<p>Descrição do bloco</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "bloco.tipo",
            "description": "<p>Tipo do bloco (A=Assinatura, R=Reunião ou I=Interno)</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "bloco.estado",
            "description": "<p>Estado do bloco (A=Aberto, D=Disponibilizado, R=Retornado ou C=Concluído)</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Unidade[]",
            "optional": false,
            "field": "bloco.unidadesDisponibilizacao",
            "description": "<p>Dados das unidades configuradas para disponibilização (ver estrutura Unidade)</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "ProtocoloBloco[]",
            "optional": false,
            "field": "bloco.protocolos",
            "description": "<p>Processos ou documentos do bloco</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "bloco.protocolos.protocoloFormatado",
            "description": "<p>Número de protocolo formatado</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "bloco.protocolos.identificacao",
            "description": "<p>:TODO pendente</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Assinatura[]",
            "optional": false,
            "field": "bloco.protocolos.assinaturas",
            "description": "<p>Conjunto de assinaturas dos documentos. Será um conjunto vazio caso não existam informações ou se o protocolo representa um processo</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "bloco.protocolos.assinaturas.nome",
            "description": "<p>Nome do assinante</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "bloco.protocolos.assinaturas.cargoFuncao",
            "description": "<p>Cargo ou função utilizado no momento da assinatura</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "bloco.protocolos.assinaturas.dataHora",
            "description": "<p>Data/hora em que ocorreu a assinatura</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "bloco.protocolos.assinaturas.idUsuario",
            "description": "<p>Identificador do usuário</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "bloco.protocolos.assinaturas.idOrigem",
            "description": "<p>:TODO pendente</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "bloco.protocolos.assinaturas.idOrgao",
            "description": "<p>:TODO pendente</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "bloco.protocolos.assinaturas.sigla",
            "description": "<p>Login do usuário responsável pela assinatura</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/BlocoResource.java",
    "groupTitle": "Bloco"
  },
  {
    "type": "post",
    "url": "/:unidade/blocos/disponibilizados",
    "title": "Disponibilizar bloco",
    "name": "disponibilizarBloco",
    "group": "Bloco",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Disponibiliza um determinado bloco.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          }
        ],
        "Request Body": [
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "bloco",
            "description": "<p>Numero do bloco que deseja disponibilizar</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: [POST] http://<host>/sei-broker/service/COSAP/blocos/disponibilizados\n\nbody:\n12",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "Boolean",
            "optional": false,
            "field": "resultado",
            "description": "<p>Booleano informando sucesso da requisição</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/BlocoResource.java",
    "groupTitle": "Bloco"
  },
  {
    "type": "delete",
    "url": "/:unidade/blocos/:bloco",
    "title": "Excluir bloco",
    "name": "excluirBloco",
    "group": "Bloco",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Exclui um bloco criado.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "bloco",
            "description": "<p>Numero do bloco que deseja excluir</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -X DELETE http://<host>/sei-broker/service/COSAP/blocos/12",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "Boolean",
            "optional": false,
            "field": "resultado",
            "description": "<p>Booleano informando sucesso da requisição</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/BlocoResource.java",
    "groupTitle": "Bloco"
  },
  {
    "type": "post",
    "url": "/:unidade/blocos",
    "title": "Gerar bloco",
    "name": "gerarBloco",
    "group": "Bloco",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Gera um novo bloco.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          }
        ],
        "Request Body": [
          {
            "group": "Request Body",
            "type": "NovoBloco",
            "optional": false,
            "field": "novoBloco",
            "description": "<p>Objeto de criação de bloco</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "allowedValues": [
              "\"ASSINATURA\"",
              "\"INTERNO\"",
              "\"REUNIAO\""
            ],
            "optional": false,
            "field": "novoBloco.tipo",
            "description": "<p>Tipo do bloco a ser criado</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "novoBloco.descricao",
            "description": "<p>Descrição do bloco</p>"
          },
          {
            "group": "Request Body",
            "type": "String[]",
            "optional": false,
            "field": "novoBloco.unidades",
            "description": "<p>Códigos das unidades onde o bloco deve ser disponibilizado, ou vazio para não disponibilizar</p>"
          },
          {
            "group": "Request Body",
            "type": "String[]",
            "optional": false,
            "field": "novoBloco.documentos",
            "description": "<p>Código dos documentos que serão incluídos no bloco</p>"
          },
          {
            "group": "Request Body",
            "type": "Boolean",
            "optional": false,
            "field": "novoBloco.disponibilizar",
            "defaultValue": "false",
            "description": "<p>Informa se o bloco criado deve ser disponibilizado automaticamente</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: http://<host>/sei-broker/service/COSAP/blocos\n\nbody:\n{\n\t\"tipo\":\"ASSINATURA\",\n\t\"descricao\":\"Bloco demonstrativo.\",\n\t\"unidades\":[\"110000935\"],\n\t\"documentos\":[\"0000131\"],\n\t\"disponibilizar\":true\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso Response Body - 201": [
          {
            "group": "Sucesso Response Body - 201",
            "type": "String",
            "optional": false,
            "field": "resultado",
            "description": "<p>Código do bloco criado</p>"
          }
        ],
        "Sucesso Response Header - 201": [
          {
            "group": "Sucesso Response Header - 201",
            "type": "header",
            "optional": false,
            "field": "Location",
            "description": "<p>URL de acesso ao recurso criado</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/BlocoResource.java",
    "groupTitle": "Bloco"
  },
  {
    "type": "POST",
    "url": "/:unidade/blocos/:bloco/documentos",
    "title": "Incluir documento anotado",
    "name": "incluirDocumentoComAnotacaoNoBloco",
    "group": "Bloco",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Inclui um documento no bloco.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "bloco",
            "description": "<p>Número do bloco onde o documento será incluído</p>"
          }
        ],
        "Request Body": [
          {
            "group": "Request Body",
            "type": "InclusaoDocumentoBloco",
            "optional": false,
            "field": "inclusao",
            "description": "<p>Objeto com os dados do documento a ser incluído</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "bloco",
            "description": "<p>Número do bloco onde o documento será inserido</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "documento",
            "description": "<p>Número do documento</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "anotacao",
            "description": "<p>Texto de anotação associado com o documento no bloco</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: [POST] http://<host>/sei-broker/service/COSAP/blocos/12/documentos",
        "type": "json"
      }
    ],
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/BlocoResource.java",
    "groupTitle": "Bloco"
  },
  {
    "type": "POST",
    "url": "/:unidade/blocos/:bloco/documentos",
    "title": "Incluir documento",
    "name": "incluirDocumentoNoBloco",
    "group": "Bloco",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Inclui um documento no bloco.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "bloco",
            "description": "<p>Número do bloco onde o documento será incluído</p>"
          }
        ],
        "Request Body": [
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "documento",
            "description": "<p>Número do documento que será incluído do bloco</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: [POST] http://<host>/sei-broker/service/COSAP/blocos/12/documentos\n\nbody:\n0000050",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "Boolean",
            "optional": false,
            "field": "resultado",
            "description": "<p>Booleano informando sucesso da requisição</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/BlocoResource.java",
    "groupTitle": "Bloco"
  },
  {
    "type": "post",
    "url": "/:unidade/blocos/:bloco/processos",
    "title": "Incluir processo anotado",
    "name": "incluirProcessoComAnotacaoNoBloco",
    "group": "Bloco",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Inclui um processo no bloco, junto com uma anotação.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "bloco",
            "description": "<p>Numero do bloco onde o processo será incluído</p>"
          }
        ],
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "String",
            "allowedValues": [
              "\"S (sim), N (não)\""
            ],
            "optional": true,
            "field": "auto-formatacao",
            "defaultValue": "S",
            "description": "<p>O broker utilizará a mascara padrão para formatar o número do processo</p>"
          }
        ],
        "Request Body": [
          {
            "group": "Request Body",
            "type": "InclusaoProcessoBloco",
            "optional": false,
            "field": "inclusao",
            "description": "<p>Objeto com os dados do processo a ser incluído</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "bloco",
            "description": "<p>Número do bloco onde o processo será inserido</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "processo",
            "description": "<p>Número do processo</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "anotacao",
            "description": "<p>Texto de anotação associado com o processo no bloco</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -X PUT http://<host>/sei-broker/service/COSAP/blocos/12/processos",
        "type": "json"
      }
    ],
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/BlocoResource.java",
    "groupTitle": "Bloco"
  },
  {
    "type": "post",
    "url": "/:unidade/blocos/:bloco/processos",
    "title": "Incluir processo",
    "name": "incluirProcessoNoBloco",
    "group": "Bloco",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Inclui um processo no bloco.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "bloco",
            "description": "<p>Numero do bloco onde o processo será incluído</p>"
          }
        ],
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "String",
            "allowedValues": [
              "\"S (sim), N (não)\""
            ],
            "optional": true,
            "field": "auto-formatacao",
            "defaultValue": "S",
            "description": "<p>O broker utilizará a mascara padrão para formatar o número do processo</p>"
          }
        ],
        "Request Body": [
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "processo",
            "description": "<p>Numero do processo que será incluído no bloco</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -X PUT http://<host>/sei-broker/service/COSAP/blocos/12/processos",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "Boolean",
            "optional": false,
            "field": "resultado",
            "description": "<p>Booleano informando sucesso da requisição</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/BlocoResource.java",
    "groupTitle": "Bloco"
  },
  {
    "type": "delete",
    "url": "/:unidade/:bloco/documentos/:documento",
    "title": "Remover documento",
    "name": "retirarDocumentoDoBloco",
    "group": "Bloco",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Remove o documento do bloco.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "bloco",
            "description": "<p>Numero do bloco de onde o documento será retirado</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "documento",
            "description": "<p>Numero do documento que será retirado do bloco</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -X DELETE http://<host>/sei-broker/service/COSAP/blocos/12/documentos/0000050",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "Boolean",
            "optional": false,
            "field": "resultado",
            "description": "<p>Booleano informando sucesso da requisição</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/BlocoResource.java",
    "groupTitle": "Bloco"
  },
  {
    "type": "delete",
    "url": "/:unidade/blocos/:bloco/processos/:processo",
    "title": "Remover processo",
    "name": "retirarProcessoDoBloco",
    "group": "Bloco",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Este método remove o processo do bloco.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "bloco",
            "description": "<p>Numero do bloco de onde o processo será retirado</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "processo",
            "description": "<p>Numero do processo que será retirado do bloco</p>"
          }
        ],
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "String",
            "allowedValues": [
              "\"S (sim), N (não)\""
            ],
            "optional": true,
            "field": "auto-formatacao",
            "defaultValue": "S",
            "description": "<p>O broker utilizará a mascara padrão para formatar o número do processo</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -X DELETE http://<host>/sei-broker/service/COSAP/blocos/12/processos/33910000029201653",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "Boolean",
            "optional": false,
            "field": "resultado",
            "description": "<p>Booleano informando sucesso da requisição</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/BlocoResource.java",
    "groupTitle": "Bloco"
  },
  {
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "optional": false,
            "field": "varname1",
            "description": "<p>No type.</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "varname2",
            "description": "<p>With type.</p>"
          }
        ]
      }
    },
    "type": "",
    "url": "",
    "version": "0.0.0",
    "filename": "sei-broker/src/main/webapp/api-docs/main.js",
    "group": "C__git_sei_broker_src_main_webapp_api_docs_main_js",
    "groupTitle": "C__git_sei_broker_src_main_webapp_api_docs_main_js",
    "name": ""
  },
  {
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "optional": false,
            "field": "varname1",
            "description": "<p>No type.</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "varname2",
            "description": "<p>With type.</p>"
          }
        ]
      }
    },
    "type": "",
    "url": "",
    "version": "0.0.0",
    "filename": "sei-broker/target/sei-broker-2.5/api-docs/main.js",
    "group": "C__git_sei_broker_target_sei_broker_2_5_api_docs_main_js",
    "groupTitle": "C__git_sei_broker_target_sei_broker_2_5_api_docs_main_js",
    "name": ""
  },
  {
    "type": "get",
    "url": "/:unidade/cargos",
    "title": "Listar cargos",
    "name": "listarCargos",
    "group": "Cargo",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Lista os cargos.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          }
        ],
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "idCargo",
            "description": "<p>Identificador do cargo</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -i http://<host>/sei-broker/service/COSAP/cargos",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "Cargo[]",
            "optional": false,
            "field": "resultado",
            "description": "<p>Lista com os cargos encontrados</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.idCargo",
            "description": "<p>Identificador do cargo no SEI</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.expressaoCargo",
            "description": "<p>Descrição do cargo (Ex.: Governador)</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.expressaoTratamento",
            "description": "<p>Tratamento para o cargo (Ex.: A Sua Excelência o Senhor)</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.expressaoVocativo",
            "description": "<p>Vocativo para o cargo (Ex.: Senhor Governador)</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/CargoResource.java",
    "groupTitle": "Cargo"
  },
  {
    "type": "put",
    "url": "/:unidade/contatos/:tipo/:sigla",
    "title": "Atualizar contato",
    "name": "atualizarContato",
    "group": "Contato",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Atualizar contato</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "tipo",
            "description": "<p>Tipo do contato</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "sigla",
            "description": "<p>Sigla(login) do contato</p>"
          }
        ],
        "Request Body": [
          {
            "group": "Request Body",
            "type": "Pessoa",
            "optional": false,
            "field": "resultado.pessoa",
            "description": "<p>Pessoa que representa o contato.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "allowedValues": [
              "\"Fisica\"",
              "\"Juridica\""
            ],
            "optional": false,
            "field": "resultado.pessoa.type",
            "description": "<p>Tipo de pessoa.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "resultado.pessoa.nome",
            "description": "<p>Nome do contato.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "resultado.pessoa.sigla",
            "description": "<p>Sigla(login) do contato.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.email",
            "description": "<p>Email do contato.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.telefone",
            "description": "<p>Telefone fixo do contato.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.celular",
            "description": "<p>Celular do contato.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.observacao",
            "description": "<p>Observações sobre o contato.</p>"
          },
          {
            "group": "Request Body",
            "type": "Boolean",
            "optional": true,
            "field": "resultado.pessoa.ativo",
            "defaultValue": "false",
            "description": "<p>Situação do contato.</p>"
          },
          {
            "group": "Request Body",
            "type": "Endereco",
            "optional": true,
            "field": "resultado.pessoa.endereco",
            "description": "<p>Endereço do contato.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.logradouro",
            "description": "<p>Logradouro do endereço.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.complemento",
            "description": "<p>Complento do endereço.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.numero",
            "description": "<p>Número do endereço.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.cep",
            "description": "<p>CEP do endereço.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.bairro",
            "description": "<p>Bairro do endereço.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.cidade",
            "description": "<p>Código do IBGE da cidade.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.uf",
            "description": "<p>Sigla do estado.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.pais",
            "description": "<p>País do endereço.</p>"
          },
          {
            "group": "Request Body",
            "type": "PessoaJuridica",
            "optional": true,
            "field": "resultado.pessoa.associado",
            "description": "<p>Pessoa Jurídica a qual o contato está associado.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "allowedValues": [
              "\"Juridica\""
            ],
            "optional": false,
            "field": "resultado.pessoa.associado.type",
            "description": "<p>Tipo de pessoa, neste caso o tipo deve ser Juridica.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.associado.sigla",
            "description": "<p>Sigla(login) do contato associado.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "allowedValues": [
              "\"MASCULINO\"",
              "\"FEMININO\""
            ],
            "optional": true,
            "field": "resultado.pessoa.sexo",
            "description": "<p>Sexo do contato, presente em contatos do tipo PessoaFisica.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.cpf",
            "description": "<p>CPF do contato, presente em contatos do tipo PessoaFisica.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.rg",
            "description": "<p>RG do contato, presente em contatos do tipo PessoaFisica.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.orgaoEmissor",
            "description": "<p>Orgão emissor do RG, presente em contatos do tipo PessoaFisica.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.dataNascimento",
            "description": "<p>Data de nascimento, presente em contatos do tipo PessoaFisica.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.matricula",
            "description": "<p>Matricula do contato, presente em contatos do tipo PessoaFisica.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.matriculaOab",
            "description": "<p>Número de registro do OAB, presente em contatos do tipo PessoaFisica.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.cnpj",
            "description": "<p>CNPJ do contato, presente em contatos do tipo PessoaJuridica.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.website",
            "description": "<p>Site do contato, presente em contatos do tipo PessoaJuridica.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "\tendpoint: http://<host>/sei-broker/service/cosap/contatos/operadoras\n\n\tbody:\n    {\n      \"@type\": \"Juridica\",\n      \"nome\": \"18 DE JULHO ADMINISTRADORA DE BENEFÍCIOS LTDA\",\n      \"sigla\": \"419761\",\n      \"endereco\": {\n      \t\"logradouro\": \"RUA CAPITÃO MEDEIROS DE REZENDE 274\",\n      \t\"complemento\": \"Teste de complemento\",\n      \t\"numero\": null,\n      \t\"uf\": \"MG\",\n      \t\"pais\": \"Brasil\",\n      \t\"cidade\": \"3101508\",\n      \t\"bairro\": \"PRAÇA DA BANDEIRA\",\n      \t\"cep\": \"36660000\"\n      },\n      \"email\": \"teste@email.com\",\n      \"celular\": \"(32)982538993\",\n      \"telefone\": \"(32)34624649\",\n      \"observacao\": \"Observado via SEI-Broker\",\n      \"ativo\": true,\n      \"associado\": null,\n      \"cnpj\": \"19541931000125\",\n      \"website\": \"sitiodopicapauamarelo.com.br\"\n    }",
        "type": "json"
      }
    ],
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/ContatoResource.java",
    "groupTitle": "Contato"
  },
  {
    "type": "post",
    "url": "/:unidade/contatos/:tipo",
    "title": "Incluir contato",
    "name": "criarContato",
    "group": "Contato",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Incluir contato</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "tipo",
            "description": "<p>Tipo do contato</p>"
          }
        ],
        "Request Body": [
          {
            "group": "Request Body",
            "type": "Pessoa",
            "optional": false,
            "field": "resultado.pessoa",
            "description": "<p>Pessoa que representa o contato.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "allowedValues": [
              "\"Fisica\"",
              "\"Juridica\""
            ],
            "optional": false,
            "field": "resultado.pessoa.type",
            "description": "<p>Tipo de pessoa.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "resultado.pessoa.nome",
            "description": "<p>Nome do contato.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "resultado.pessoa.sigla",
            "description": "<p>Sigla(login) do contato.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.email",
            "description": "<p>Email do contato.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.telefone",
            "description": "<p>Telefone fixo do contato.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.celular",
            "description": "<p>Celular do contato.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.observacao",
            "description": "<p>Observações sobre o contato.</p>"
          },
          {
            "group": "Request Body",
            "type": "Boolean",
            "optional": true,
            "field": "resultado.pessoa.ativo",
            "defaultValue": "false",
            "description": "<p>Situação do contato.</p>"
          },
          {
            "group": "Request Body",
            "type": "Endereco",
            "optional": true,
            "field": "resultado.pessoa.endereco",
            "description": "<p>Endereço do contato.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.logradouro",
            "description": "<p>Logradouro do endereço.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.complemento",
            "description": "<p>Complento do endereço.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.numero",
            "description": "<p>Número do endereço.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.cep",
            "description": "<p>CEP do endereço.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.bairro",
            "description": "<p>Bairro do endereço.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.cidade",
            "description": "<p>Código do IBGE da cidade.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.uf",
            "description": "<p>Sigla do estado.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.pais",
            "description": "<p>País do endereço.</p>"
          },
          {
            "group": "Request Body",
            "type": "PessoaJuridica",
            "optional": true,
            "field": "resultado.pessoa.associado",
            "description": "<p>Pessoa Jurídica a qual o contato está associado.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "allowedValues": [
              "\"Juridica\""
            ],
            "optional": false,
            "field": "resultado.pessoa.associado.type",
            "description": "<p>Tipo de pessoa, neste caso o tipo deve ser Juridica.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.associado.sigla",
            "description": "<p>Sigla(login) do contato associado.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "allowedValues": [
              "\"MASCULINO\"",
              "\"FEMININO\""
            ],
            "optional": true,
            "field": "resultado.pessoa.sexo",
            "description": "<p>Sexo do contato, presente em contatos do tipo PessoaFisica.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.cpf",
            "description": "<p>CPF do contato, presente em contatos do tipo PessoaFisica.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.rg",
            "description": "<p>RG do contato, presente em contatos do tipo PessoaFisica.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.orgaoEmissor",
            "description": "<p>Orgão emissor do RG, presente em contatos do tipo PessoaFisica.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.dataNascimento",
            "description": "<p>Data de nascimento, presente em contatos do tipo PessoaFisica.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.matricula",
            "description": "<p>Matricula do contato, presente em contatos do tipo PessoaFisica.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.matriculaOab",
            "description": "<p>Número de registro do OAB, presente em contatos do tipo PessoaFisica.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.cnpj",
            "description": "<p>CNPJ do contato, presente em contatos do tipo PessoaJuridica.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.website",
            "description": "<p>Site do contato, presente em contatos do tipo PessoaJuridica.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "\tendpoint: http://<host>/sei-broker/service/cosap/contatos/operadoras\n\n\tbody:\n    {\n      \"@type\": \"Juridica\",\n      \"nome\": \"18 DE JULHO ADMINISTRADORA DE BENEFÍCIOS LTDA\",\n      \"sigla\": \"419761\",\n      \"endereco\": {\n      \t\"logradouro\": \"RUA CAPITÃO MEDEIROS DE REZENDE 274\",\n      \t\"complemento\": \"Teste de complemento\",\n      \t\"numero\": null,\n      \t\"uf\": \"MG\",\n      \t\"pais\": \"Brasil\",\n      \t\"cidade\": \"3101508\",\n      \t\"bairro\": \"PRAÇA DA BANDEIRA\",\n      \t\"cep\": \"36660000\"\n      },\n      \"email\": \"teste@email.com\",\n      \"celular\": \"(32)982538993\",\n      \"telefone\": \"(32)34624649\",\n      \"observacao\": \"Observado via SEI-Broker\",\n      \"ativo\": true,\n      \"associado\": null,\n      \"cnpj\": \"19541931000125\",\n      \"website\": \"sitiodopicapauamarelo.com.br\"\n    }",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Criado - 201": [
          {
            "group": "Criado - 201",
            "type": "header",
            "optional": false,
            "field": "Location",
            "description": "<p>URL de acesso ao recurso criado.</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/ContatoResource.java",
    "groupTitle": "Contato"
  },
  {
    "type": "get",
    "url": "/:unidade/contatos/:tipo/:sigla",
    "title": "Consultar contato",
    "name": "getContato",
    "group": "Contato",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Consulta contato pela sigla(login)</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "tipo",
            "description": "<p>Tipo do contato</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "sigla",
            "description": "<p>Sigla(login) do contato</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -i http://<host>/sei-broker/service/cosap/contatos/operadoras/419761",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "Pessoa",
            "optional": false,
            "field": "resultado.pessoa",
            "description": "<p>Pessoa que representa o contato.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "allowedValues": [
              "\"Fisica\"",
              "\"Juridica\""
            ],
            "optional": false,
            "field": "resultado.pessoa.type",
            "description": "<p>Tipo de pessoa.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.pessoa.nome",
            "description": "<p>Nome do contato.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.pessoa.sigla",
            "description": "<p>Sigla(login) do contato.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.email",
            "description": "<p>Email do contato.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.telefone",
            "description": "<p>Telefone fixo do contato.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.celular",
            "description": "<p>Celular do contato.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.observacao",
            "description": "<p>Observações sobre o contato.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Boolean",
            "optional": true,
            "field": "resultado.pessoa.ativo",
            "defaultValue": "false",
            "description": "<p>Situação do contato.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Endereco",
            "optional": true,
            "field": "resultado.pessoa.endereco",
            "description": "<p>Endereço do contato.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.logradouro",
            "description": "<p>Logradouro do endereço.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.complemento",
            "description": "<p>Complento do endereço.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.numero",
            "description": "<p>Número do endereço.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.cep",
            "description": "<p>CEP do endereço.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.bairro",
            "description": "<p>Bairro do endereço.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.cidade",
            "description": "<p>Código do IBGE da cidade.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.uf",
            "description": "<p>Sigla do estado.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.pais",
            "description": "<p>País do endereço.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "PessoaJuridica",
            "optional": true,
            "field": "resultado.pessoa.associado",
            "description": "<p>Pessoa Jurídica a qual o contato está associado.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "allowedValues": [
              "\"Juridica\""
            ],
            "optional": false,
            "field": "resultado.pessoa.associado.type",
            "description": "<p>Tipo de pessoa, neste caso o tipo deve ser Juridica.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.associado.sigla",
            "description": "<p>Sigla(login) do contato associado.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "allowedValues": [
              "\"MASCULINO\"",
              "\"FEMININO\""
            ],
            "optional": true,
            "field": "resultado.pessoa.sexo",
            "description": "<p>Sexo do contato, presente em contatos do tipo PessoaFisica.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.cpf",
            "description": "<p>CPF do contato, presente em contatos do tipo PessoaFisica.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.rg",
            "description": "<p>RG do contato, presente em contatos do tipo PessoaFisica.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.orgaoEmissor",
            "description": "<p>Orgão emissor do RG, presente em contatos do tipo PessoaFisica.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.dataNascimento",
            "description": "<p>Data de nascimento, presente em contatos do tipo PessoaFisica.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.matricula",
            "description": "<p>Matricula do contato, presente em contatos do tipo PessoaFisica.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.matriculaOab",
            "description": "<p>Número de registro do OAB, presente em contatos do tipo PessoaFisica.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.cnpj",
            "description": "<p>CNPJ do contato, presente em contatos do tipo PessoaJuridica.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.website",
            "description": "<p>Site do contato, presente em contatos do tipo PessoaJuridica.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n  \"@type\": \"Juridica\",\n  \"nome\": \"18 DE JULHO ADMINISTRADORA DE BENEFÍCIOS LTDA\",\n  \"sigla\": \"419761\",\n  \"endereco\": {\n  \t\"logradouro\": \"RUA CAPITÃO MEDEIROS DE REZENDE 274\",\n  \t\"complemento\": \"Teste de complemento\",\n  \t\"numero\": null,\n  \t\"uf\": \"MG\",\n  \t\"pais\": \"Brasil\",\n  \t\"cidade\": \"3101508\",\n  \t\"bairro\": \"PRAÇA DA BANDEIRA\",\n  \t\"cep\": \"36660000\"\n  },\n  \"email\": \"teste@email.com\",\n  \"celular\": \"(32)982538993\",\n  \"telefone\": \"(32)34624649\",\n  \"observacao\": \"Observado via SEI-Broker\",\n  \"ativo\": true,\n  \"associado\": null,\n  \"cnpj\": \"19541931000125\",\n  \"website\": \"sitiodopicapauamarelo.com.br\"\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/ContatoResource.java",
    "groupTitle": "Contato"
  },
  {
    "type": "get",
    "url": "/:unidade/contatos/:tipo",
    "title": "Listar contatos",
    "name": "listarContatos",
    "group": "Contato",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Consulta os contatos de determinado tipo, o retorno pode ser PessoaFisica ou PessoaJuridica é recomendado utilizar a ans-commons-sei.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "tipo",
            "description": "<p>Tipo do contato</p>"
          }
        ],
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "nome",
            "description": "<p>Nome do contato</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "cpf",
            "description": "<p>CPF do contato</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "cnpj",
            "description": "<p>CNPJ do contato</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "sigla",
            "description": "<p>Sigla(login) do contato</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "matricula",
            "description": "<p>Matricula do contato</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "qtdRegistros",
            "defaultValue": "1",
            "description": "<p>Quantidade de contatos que serão exibidos</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "pagina",
            "defaultValue": "1",
            "description": "<p>Número da página</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -i http://<host>/sei-broker/service/cosap/contatos/operadoras?qtdRegistros=20",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "List",
            "optional": false,
            "field": "resultado",
            "description": "<p>Lista com os contatos encontrados.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Pessoa",
            "optional": false,
            "field": "resultado.pessoa",
            "description": "<p>Pessoa que representa o contato.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "allowedValues": [
              "\"Fisica\"",
              "\"Juridica\""
            ],
            "optional": false,
            "field": "resultado.pessoa.type",
            "description": "<p>Tipo de pessoa.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.pessoa.nome",
            "description": "<p>Nome do contato.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.pessoa.sigla",
            "description": "<p>Sigla(login) do contato.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.email",
            "description": "<p>Email do contato.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.telefone",
            "description": "<p>Telefone fixo do contato.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.celular",
            "description": "<p>Celular do contato.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.observacao",
            "description": "<p>Observações sobre o contato.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Boolean",
            "optional": true,
            "field": "resultado.pessoa.ativo",
            "defaultValue": "false",
            "description": "<p>Situação do contato.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Endereco",
            "optional": true,
            "field": "resultado.pessoa.endereco",
            "description": "<p>Endereço do contato.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.logradouro",
            "description": "<p>Logradouro do endereço.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.complemento",
            "description": "<p>Complento do endereço.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.numero",
            "description": "<p>Número do endereço.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.cep",
            "description": "<p>CEP do endereço.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.bairro",
            "description": "<p>Bairro do endereço.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.cidade",
            "description": "<p>Código do IBGE da cidade.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.uf",
            "description": "<p>Sigla do estado.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.endereco.pais",
            "description": "<p>País do endereço.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "PessoaJuridica",
            "optional": true,
            "field": "resultado.pessoa.associado",
            "description": "<p>Pessoa Jurídica a qual o contato está associado.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "allowedValues": [
              "\"Juridica\""
            ],
            "optional": false,
            "field": "resultado.pessoa.associado.type",
            "description": "<p>Tipo de pessoa, neste caso o tipo deve ser Juridica.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.associado.sigla",
            "description": "<p>Sigla(login) do contato associado.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "allowedValues": [
              "\"MASCULINO\"",
              "\"FEMININO\""
            ],
            "optional": true,
            "field": "resultado.pessoa.sexo",
            "description": "<p>Sexo do contato, presente em contatos do tipo PessoaFisica.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.cpf",
            "description": "<p>CPF do contato, presente em contatos do tipo PessoaFisica.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.rg",
            "description": "<p>RG do contato, presente em contatos do tipo PessoaFisica.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.orgaoEmissor",
            "description": "<p>Orgão emissor do RG, presente em contatos do tipo PessoaFisica.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.dataNascimento",
            "description": "<p>Data de nascimento, presente em contatos do tipo PessoaFisica.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.matricula",
            "description": "<p>Matricula do contato, presente em contatos do tipo PessoaFisica.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.matriculaOab",
            "description": "<p>Número de registro do OAB, presente em contatos do tipo PessoaFisica.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.cnpj",
            "description": "<p>CNPJ do contato, presente em contatos do tipo PessoaJuridica.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": true,
            "field": "resultado.pessoa.website",
            "description": "<p>Site do contato, presente em contatos do tipo PessoaJuridica.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n  \"@type\": \"Juridica\",\n  \"nome\": \"18 DE JULHO ADMINISTRADORA DE BENEFÍCIOS LTDA\",\n  \"endereco\": {\n  \t\"logradouro\": \"RUA CAPITÃO MEDEIROS DE REZENDE 274\",\n  \t\"complemento\": \"Teste de complemento\",\n  \t\"numero\": null,\n  \t\"uf\": \"MG\",\n  \t\"pais\": \"Brasil\",\n  \t\"cidade\": \"3101508\",\n  \t\"bairro\": \"PRAÇA DA BANDEIRA\",\n  \t\"cep\": \"36660000\"\n  },\n  \"email\": \"teste@email.com\",\n  \"celular\": \"(32)982538993\",\n  \"telefone\": \"(32)34624649\",\n  \"observacao\": \"Observado via SEI-Broker\",\n  \"ativo\": true,\n  \"associado\": null,\n  \"cnpj\": \"19541931000125\",\n  \"website\": \"sitiodopicapauamarelo.com.br\"\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/ContatoResource.java",
    "groupTitle": "Contato"
  },
  {
    "type": "get",
    "url": "/:unidade/contatos/tipos",
    "title": "Tipos de contato",
    "name": "listarTipos",
    "group": "Contato",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Consulta os tipos de contato.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -i http://<host>/sei-broker/service/cosap/contatos/tipos",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "TipoContato[]",
            "optional": false,
            "field": "resultado",
            "description": "<p>Lista com os tipos de contato, representados por uma Enum TipoContato.</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/ContatoResource.java",
    "groupTitle": "Contato"
  },
  {
    "type": "post",
    "url": "/:unidade/documentos/cancelados",
    "title": "Cancelar documento",
    "name": "cancelarDocumento",
    "group": "Documento",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Cancela um documento.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          }
        ],
        "Request Body": [
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "documento",
            "description": "<p>Numero do documento que será cancelado</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "motivo",
            "description": "<p>Motivo do cancelamento</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: https://<host>/sei-broker/service/COSAP/documentos/cancelados\n\nbody:\n{\n\t\"documento\":\"0000050\",\n\t\"motivo\":\"Motivo do cancelamento.\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Boolean",
            "optional": false,
            "field": "resultado",
            "description": "<p>Booleano informando sucesso da requisição</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/DocumentoResource.java",
    "groupTitle": "Documento"
  },
  {
    "type": "get",
    "url": "/:unidade/documentos/:documento",
    "title": "Consultar documento",
    "name": "consultarDocumento",
    "group": "Documento",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Consulta documento cadastrado no SEI.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "documento",
            "description": "<p>Id do documento que deseja recuperar as informações</p>"
          }
        ],
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "String",
            "allowedValues": [
              "\"S (sim), N (não)\""
            ],
            "optional": true,
            "field": "andamento",
            "defaultValue": "N",
            "description": "<p>exibir o andamento do processo</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "allowedValues": [
              "\"S (sim), N (não)\""
            ],
            "optional": true,
            "field": "assinaturas",
            "defaultValue": "N",
            "description": "<p>exibir as assinaturas presentes no documento</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "allowedValues": [
              "\"S (sim), N (não)\""
            ],
            "optional": true,
            "field": "publicacao",
            "defaultValue": "N",
            "description": "<p>exibir detalhes da publicação</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "allowedValues": [
              "\"S (sim), N (não)\""
            ],
            "optional": true,
            "field": "campos",
            "defaultValue": "N",
            "description": "<p>exibir campos do formulário</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:",
        "content": "curl -i https://<host>/sei-broker/service/COSAP/documentos/0000050?assinaturas=S&andamento=S",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "RetornoConsultaDocumento",
            "optional": false,
            "field": "retornoConsultaDocumento",
            "description": "<p>Informações do documento encontrado no SEI</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.idProcedimento",
            "description": "<p>Id interno do processo no SEI</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.procedimentoFormatado",
            "description": "<p>Número do processo visível para o usuário</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.idDocumento",
            "description": "<p>Id interno do documento no SEI</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.documentoFormatado",
            "description": "<p>Número do documento visível para o usuário</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.linkAcesso",
            "description": "<p>Link para acesso ao documento</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Serie",
            "optional": false,
            "field": "retornoConsultaDocumento.serie",
            "description": "<p>Dados do tipo do documento</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.serie.idSerie",
            "description": "<p>Identificador do tipo de documento</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.serie.nome",
            "description": "<p>Nome do tipo de documento</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.serie.aplicabilidade",
            "description": "<p>:TODO pendente</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.numero",
            "description": "<p>Número do documento</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.data",
            "description": "<p>Data de geração para documentos internos e para documentos externos é a data informada na tela de cadastro</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Unidade",
            "optional": false,
            "field": "retornoConsultaDocumento.unidadeElaboradora",
            "description": "<p>Dados da unidade que gerou o documento</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.unidadeElaboradora.descricao",
            "description": "<p>Nome da unidade</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.unidadeElaboradora.idUnidade",
            "description": "<p>Código da unidade</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.unidadeElaboradora.sigla",
            "description": "<p>Sigla da unidade</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.unidadeElaboradora.sinProtocolo",
            "description": "<p>:TODO pendente</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.unidadeElaboradora.sinArquivamento",
            "description": "<p>:TODO pendente</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.unidadeElaboradora.sinOuvidoria",
            "description": "<p>:TODO pendente</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Andamento",
            "optional": false,
            "field": "retornoConsultaDocumento.andamentoGeracao",
            "description": "<p>Informações do andamento de geração (opcional)</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.andamentoGeracao.idAndamento",
            "description": "<p>Identificador do andamento</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.andamentoGeracao.idTarefa",
            "description": "<p>:TODO pendente</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.andamentoGeracao.idTarefaModulo",
            "description": "<p>:TODO pendente</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.andamentoGeracao.descricao",
            "description": "<p>Descrição do andamento</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.andamentoGeracao.dataHora",
            "description": "<p>Data e hora do registro de andamento</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Unidade",
            "optional": false,
            "field": "retornoConsultaDocumento.andamentoGeracao.unidade",
            "description": "<p>Unidade responsável pelo andamento (ver estrutura Unidade)</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Usuario",
            "optional": false,
            "field": "retornoConsultaDocumento.andamentoGeracao.usuario",
            "description": "<p>Usuário responsável pela ação</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.andamentoGeracao.usuario.idUsuario",
            "description": "<p>Código do usuário</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.andamentoGeracao.usuario.nome",
            "description": "<p>Nome do usuário</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.andamentoGeracao.usuario.sigla",
            "description": "<p>Login do usuário</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "AtributoAndamento[]",
            "optional": false,
            "field": "retornoConsultaDocumento.andamentoGeracao.atributos",
            "description": "<p>Lista com os atributos relacionados ao andamento</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.andamentoGeracao.atributos.nome",
            "description": "<p>Nome do atributo</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.andamentoGeracao.atributos.valor",
            "description": "<p>Valor do atributo</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.andamentoGeracao.atributos.idOrigem",
            "description": "<p>:TODO pendente</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Assinatura[]",
            "optional": false,
            "field": "retornoConsultaDocumento.assinaturas",
            "description": "<p>Conjunto de assinaturas do documento</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.assinaturas.nome",
            "description": "<p>Nome do assinante</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.assinaturas.cargoFuncao",
            "description": "<p>Cargo ou função utilizado no momento da assinatura</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.assinaturas.dataHora",
            "description": "<p>Data/hora em que ocorreu a assinatura</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.assinaturas.idUsuario",
            "description": "<p>Identificador do usuário</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.assinaturas.idOrigem",
            "description": "<p>:TODO pendente</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.assinaturas.idOrgao",
            "description": "<p>:TODO pendente</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.assinaturas.sigla",
            "description": "<p>Login do usuário responsável pela assinatura</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Publicacao",
            "optional": false,
            "field": "retornoConsultaDocumento.publicacao",
            "description": "<p>:TODO pendente</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.publicacao.nomeVeiculo",
            "description": "<p>:TODO pendente</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.publicacao.numero",
            "description": "<p>:TODO pendente</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.publicacao.dataDisponibilizacao",
            "description": "<p>:TODO pendente</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.publicacao.dataPublicacao",
            "description": "<p>:TODO pendente</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.publicacao.estado",
            "description": "<p>:TODO pendente</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "PublicacaoImprensaNacional",
            "optional": false,
            "field": "retornoConsultaDocumento.publicacao.imprensaNacional",
            "description": "<p>:TODO pendente</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.publicacao.imprensaNacional.siglaVeiculo",
            "description": "<p>:TODO pendente</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.publicacao.imprensaNacional.descricaoVeiculo",
            "description": "<p>:TODO pendente</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.publicacao.imprensaNacional.pagina",
            "description": "<p>:TODO pendente</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.publicacao.imprensaNacional.secao",
            "description": "<p>:TODO pendente</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.publicacao.imprensaNacional.data",
            "description": "<p>:TODO pendente</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Campo[]",
            "optional": false,
            "field": "retornoConsultaDocumento.campos",
            "description": "<p>Conjunto de campos do formulário</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.campos.nome",
            "description": "<p>Nome do campo</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.campos.valor",
            "description": "<p>Valor do campo</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n\t\"idProcedimento\":\"52\",\n\t\"procedimentoFormatado\":\"16.0.000000005-5\",\n\t\"idDocumento\":\"152\",\n\t\"documentoFormatado\":\"0000123\",\n\t\"linkAcesso\":\"https://sei-hm.ans.gov.br/controlador.php?acao=procedimento_trabalhar&id_procedimento=52&id_documento=152\",\n\t\"serie\":{\n\t\t\"idSerie\":\"12\",\n\t\t\"nome\":\"Memorando\"\n\t},\n\t\"numero\":\"6\",\n\t\"data\":\"05/04/2016\",\n\t\"unidadeElaboradora\":{\n\t\t\"idUnidade\":\"110000934\",\n\t\t\"sigla\":\"COSAP\",\n\t\t\"descricao\":\"Coordenadoria de Sistemas e Aplicativos\"\n\t},\n\t\"andamentoGeracao\":null,\n\t\"assinaturas\":[],\n\t\"publicacao\":null,\n\t\"campos\":[] \t\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/DocumentoResource.java",
    "groupTitle": "Documento"
  },
  {
    "type": "get",
    "url": "/interessados/:interessado/documentos",
    "title": "Consultar por interessado",
    "name": "consultarDocumentoInteressado",
    "group": "Documento",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Retorna os documentos de um determinado interessado.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "interessado",
            "description": "<p>Identificador do interessado</p>"
          }
        ],
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "tipo",
            "description": "<p>Tipo/Série do documento</p>"
          },
          {
            "group": "Query Parameters",
            "type": "boolean",
            "optional": true,
            "field": "somenteAssinados",
            "defaultValue": "false",
            "description": "<p>Exibir somente documentos assinados</p>"
          },
          {
            "group": "Query Parameters",
            "type": "boolean",
            "optional": true,
            "field": "orderByProcesso",
            "defaultValue": "false",
            "description": "<p>Ordenar pelo número do processo, por padrão o retorno é ordenado pela dataGeracao</p>"
          },
          {
            "group": "Query Parameters",
            "type": "boolean",
            "optional": true,
            "field": "crescente",
            "defaultValue": "false",
            "description": "<p>Ordenar em ordem crescente</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "pagina",
            "defaultValue": "1",
            "description": "<p>Número da página</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "qtdRegistros",
            "defaultValue": "50",
            "description": "<p>Quantidade de registros retornados por página</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -i https://<host>/sei-broker/service/interessados/005711/documentos",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso Response Body - 200": [
          {
            "group": "Sucesso Response Body - 200",
            "type": "List",
            "optional": false,
            "field": "documentos",
            "description": "<p>Lista com os documentos encontrados.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "DocumentoResumido",
            "optional": false,
            "field": "documentos.documentoResumido",
            "description": "<p>Resumo do documento encontrado no SEI.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "documentos.documentoResumido.numero",
            "description": "<p>Número do documento.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "documentos.documentoResumido.numeroInformado",
            "description": "<p>Número informado na inclusão do documento, também conhecido como número de árvore.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "documentos.documentoResumido.unidade",
            "description": "<p>Unidade responsável pelo documento.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "allowedValues": [
              "\"GERADO\"",
              "\"RECEBIDO\""
            ],
            "optional": false,
            "field": "documentos.documentoResumido.origem",
            "description": "<p>Origem do documento, se o mesmo é um documento &quot;GERADO&quot; internamente ou &quot;RECEBIDO&quot; de uma fonte externa.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "Data",
            "optional": false,
            "field": "documentos.documentoResumido.dataGeracao",
            "description": "<p>Data de geração do documento.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "documentos.documentoResumido.processo",
            "description": "<p>Processo onde o documento está incluído.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "Tipo",
            "optional": false,
            "field": "documentos.documentoResumido.tipo",
            "description": "<p>Objeto representando o tipo do documento.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "documentos.documentoResumido.tipo.codigo",
            "description": "<p>Identificados do tipo do documento, também conhecido como série.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "documentos.documentoResumido.tipo.nome",
            "description": "<p>Nome do tipo do documento.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "documentos.documentoResumido.tipoConferencia",
            "description": "<p>Tipo de conferência do documento.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "boolean",
            "optional": false,
            "field": "documentos.documentoResumido.assinado",
            "description": "<p>Boolean indicando se o documento foi assinado.</p>"
          }
        ],
        "Sucesso Response Header - 200": [
          {
            "group": "Sucesso Response Header - 200",
            "type": "header",
            "optional": false,
            "field": "total_registros",
            "description": "<p>Quantidade de registros que existem para essa consulta</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n  \"numero\": \"0670949\",\n  \"numeroInformado\": \"594\",\n  \"unidade\": \"COSAP\",\n  \"origem\": \"RECEBIDO\",\n  \"dataGeracao\": \"2015-08-10T00:00:00-03:00\",\n  \"processo\": \"33910.000002/2017-41\",\n  \"tipo\": {\n  \t\"codigo\": \"629\",\n  \t\"nome\": \"Relatório de Arquivamento-SIF\"\n  }\n  \"tipoConferencia\": \"4\",\n  \"assinado\": true\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/DocumentoResource.java",
    "groupTitle": "Documento"
  },
  {
    "type": "get",
    "url": "/:unidade/documentos/enviados-broker",
    "title": "Listar documentos enviados",
    "name": "consultarDocumentosIncluidosBroker",
    "group": "Documento",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Consulta documentos inclusos pelo SEI-Broker.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          }
        ],
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "hash",
            "description": "<p>Hash SHA-256 gerado a partir do conteúdo enviado ao Broker</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "processo",
            "description": "<p>Número do processo onde o documento foi inserido</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "numeroInformado",
            "description": "<p>Número informado na inclusão do documento, exibido na árvore do processo.</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "pagina",
            "defaultValue": "1",
            "description": "<p>Número da página</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "qtdRegistros",
            "defaultValue": "50",
            "description": "<p>Quantidade de registros que serão exibidos por página</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -i https://<host>/sei-broker/service/COSAP/documentos/enviados-broker?pagina=1&qtdRegsitros=30",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso Response Body - 200": [
          {
            "group": "Sucesso Response Body - 200",
            "type": "InclusaoDocumento",
            "optional": false,
            "field": "resultado",
            "description": "<p>Objeto com dados sobre o documento</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "Date",
            "optional": false,
            "field": "resultado.data",
            "description": "<p>Data do envio (padrão ISO-8601)</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.hash",
            "description": "<p>Hash SHA-256 gerado a partir do conteúdo enviado ao Broker</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "Integer",
            "optional": false,
            "field": "resultado.id",
            "description": "<p>Identificação da inclusão de documento</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.nome",
            "description": "<p>Nome do documento incluído</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.numero",
            "description": "<p>Número retonado pelo SEI, NULL caso tenha ocorrido algum problema.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.processo",
            "description": "<p>Número do processo</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.sistema",
            "description": "<p>Sistema responsável pela inclusão</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.unidade",
            "description": "<p>Unidade onde foi incluído o documento</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.numeroInformado",
            "description": "<p>Valor opcional informado na inclusão do documento</p>"
          }
        ],
        "Sucesso Response Header- 200": [
          {
            "group": "Sucesso Response Header- 200",
            "type": "header",
            "optional": false,
            "field": "total_registros",
            "description": "<p>Quantidade de registros que existem para essa consulta</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n  \"id\": 1717,\n  \"data\": \"2016-10-31T11:59:56.016+0000\",\n  \"nome\": \"Doc Homologação.pdf\",\n  \"numero\": \"0003312\",\n  \"hash\": \"ca7ebe0c37419db14ffd4f09485a1ebed8e8deeed594e15720da185ee32e9d19\",\n  \"sistema\": \"desenv_integracao_sei\",\n  \"unidade\": \"COAI\",\n  \"processo\": \"33910000097201612\",\n  \"numeroInformado\":\"2016ans45875\"\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/DocumentoResource.java",
    "groupTitle": "Documento"
  },
  {
    "type": "get",
    "url": "/:unidade/documentos/:documento/pdf",
    "title": "Exportar documento",
    "name": "exportarDocumento",
    "group": "Documento",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Exporta documentos do SEI em PDF.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "documento",
            "description": "<p>Id do documento que deseja recuperar as informações</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:",
        "content": "curl -i https://<host>/sei-broker/service/COSIT/documentos/0003322/pdf",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "PDF",
            "optional": false,
            "field": "binario",
            "description": "<p>Arquivo no formato PDF.</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/DocumentoResource.java",
    "groupTitle": "Documento"
  },
  {
    "type": "post",
    "url": "/:unidade/documentos",
    "title": "Incluir documento",
    "name": "incluirDocumento",
    "group": "Documento",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Inclui um documento no SEI, podem ser incluídos documentos internos e externos, para documentos externo o tamanho máximo é 20MB.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          }
        ],
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "template",
            "description": "<p>Identificador do template que será utilizado na transformação do conteudo para HTML, o template precisa ser previamente cadastrado no templates-broker. (Este atributo exige que o atributo conteudo seja enviado em formato JSon e codificado em Base64)</p>"
          }
        ],
        "Request Body": [
          {
            "group": "Request Body",
            "type": "String",
            "allowedValues": [
              "\"G (Gerado)\"",
              "\"R (Recebido)\""
            ],
            "optional": false,
            "field": "tipo",
            "description": "<p>Tipo de documento</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "idSerie",
            "description": "<p>Identificador do tipo de documento no SEI (Consultar serviço Listar Séries)</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "idProcedimento",
            "description": "<p>Identificador do processo onde o documento deve ser inserido. Opcional se protocoloProcedimento informado</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "protocoloProcedimento",
            "description": "<p>Número do processo onde o documento deve ser inserido, visível para o usuário. Opcional se IdProcedimento informado.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "idTipoConferencia",
            "description": "<p>Identificador do tipo de conferência associada com o documento externo</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "size": "50",
            "optional": true,
            "field": "numero",
            "description": "<p>Número do documento, passar null para documentos gerados com numeração controlada pelo SEI.Para documentos externos informar o número ou nome complementar a ser exibido na árvore de documentos do processo (o SEI não controla numeração de documentos externos).</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "data",
            "description": "<p>Data do documento (dd/MM/yyyy), obrigatório para documentos externos. Passar null para documentos gerados.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "descricao",
            "description": "<p>Descrição do documento para documentos gerados. Passar null para documentos externos</p>"
          },
          {
            "group": "Request Body",
            "type": "Remetente",
            "optional": true,
            "field": "remetente",
            "description": "<p>Obrigatório para documentos externos, passar null para documentos gerados.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "remetente.nome",
            "description": "<p>Nome do remetente</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "remetente.sigla",
            "description": "<p>Login do remetente</p>"
          },
          {
            "group": "Request Body",
            "type": "Interessado[]",
            "optional": true,
            "field": "interessados",
            "description": "<p>Informar um conjunto com os dados de interessados. Se não existirem interessados deve ser informado um conjunto vazio</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "interessados.nome",
            "description": "<p>Nome do interessado</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "interessados.sigla",
            "description": "<p>Login do interessado</p>"
          },
          {
            "group": "Request Body",
            "type": "Destinatario[]",
            "optional": true,
            "field": "destinatarios",
            "description": "<p>Informar um conjunto com os dados de destinatários. Se não existirem destinatários deve ser informado um conjunto vazio</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "destinatarios.nome",
            "description": "<p>Nome do destinatário</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "destinatarios.sigla",
            "description": "<p>Login do destinatário</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "observacao",
            "description": "<p>Texto da observação da unidade, passar null se não existir</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "size": "200",
            "optional": true,
            "field": "nomeArquivo",
            "description": "<p>Nome do arquivo, obrigatório para documentos externos. Passar null para documentos gerados.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "conteudo",
            "description": "<p>Conteúdo do arquivo codificado em Base64. Para documentos gerados será o conteúdo da seção principal do editor HTML e para documentos externos será o conteúdo do anexo. Para documentos com template cadastrado, enviar Base64 do JSon referente ao conteúdo a ser preenchido no template.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "allowedValues": [
              "\"0 (público)\"",
              "\"1 (restrito)\"",
              "\"2 (sigiloso)\"",
              "\"null (herda do tipo de processo)\""
            ],
            "optional": true,
            "field": "nivelAcesso",
            "description": "<p>Nível de acesso do documento</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "idHipoteseLegal",
            "description": "<p>Identificador da hipótese legal associada</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "idArquivo",
            "description": "<p>Identificador do arquivo enviado pelo serviço de Incluir Arquivo</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "allowedValues": [
              "\"S (Sim)\"",
              "\"N (Não)\""
            ],
            "optional": true,
            "field": "sinBloqueado",
            "description": "<p>Bloquear o documento, não permite excluí-lo ou alterar seu conteúdo</p>"
          },
          {
            "group": "Request Body",
            "type": "Campo",
            "optional": true,
            "field": "campos",
            "description": "<p>Conjunto de campos associados com o formulário</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "campos.nome",
            "description": "<p>Nome do campo</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "campos.valor",
            "description": "<p>Valor do campo</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: https://<host>/sei-broker/service/COSAP/documentos\n\nbody:\n{\n\t\"tipo\":\"G\",\n\t\"idProcedimento\":\"33910000173201771\",\n\t\"protocoloProcedimento\":null,\n\t\"idSerie\":null,\n\t\"numero\":null,\n\t\"data\":null,\n\t\"descricao\":\"Documento demonstrativo\",\n\t\"remetente\":null,\n\t\"interessados\":[{\"sigla\":\"andre.guimaraes\",\"nome\":\"André Luís Fernandes Guimarães\"}],\n\t\"destinatarios\":[],\n\t\"observacao\":null,\n\t\"nomeArquivo\":null,\n\t\"conteudo\":\"Conteúdo Base64\",\n\t\"nivelAcesso\":\"0\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso Response Body - 201": [
          {
            "group": "Sucesso Response Body - 201",
            "type": "String",
            "optional": false,
            "field": "idDocumento",
            "description": "<p>número interno do documento</p>"
          },
          {
            "group": "Sucesso Response Body - 201",
            "type": "String",
            "optional": false,
            "field": "documentoFormatado",
            "description": "<p>número do documento visível para o usuário</p>"
          },
          {
            "group": "Sucesso Response Body - 201",
            "type": "String",
            "optional": false,
            "field": "linkAcesso",
            "description": "<p>link para acesso ao documento</p>"
          }
        ],
        "Sucesso Response Header - 201": [
          {
            "group": "Sucesso Response Header - 201",
            "type": "header",
            "optional": false,
            "field": "Location",
            "description": "<p>URL de acesso ao recurso criado.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 201 Created\n{\n  \"idDocumento\":\"1140000000872\",\n  \"documentoFormatado\":\"0003934\",\n  \"linkAcesso\":\"https://sei-hm.ans.gov.br/controlador.php?acao=arvore_visualizar&acao_origem=procedimento_visualizar&id_procedimento=267&id_documento=1017&sta_editor=I&infra_sistema=100000100&infra_unidade_atual=110000934&infra_hash=3d798777382d6ac455317f3a87ad9bd1f9650315e019ef922f388b829902a95b\"\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/DocumentoResource.java",
    "groupTitle": "Documento"
  },
  {
    "type": "get",
    "url": "/:unidade/tipos-documentos",
    "title": "Listar tipos documentos",
    "name": "listarTiposDocumentos",
    "group": "Documento",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Lista os tipos de documentos do SEI.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI.</p>"
          }
        ],
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "filtro",
            "description": "<p>Para filtrar por documentos que contenham o trecho no nome.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:",
        "content": "curl -i https://<host>/sei-broker/service/COSAP/tipos-documentos",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "List",
            "optional": false,
            "field": "tipos",
            "description": "<p>Lista com os tipos de documentos</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "tipos.identificador",
            "description": "<p>Identificador do tipo de documento</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "tipos.nome",
            "description": "<p>Nome do tipo de documento</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "series.aplicabilidade",
            "description": "<p>T = Documentos internos e externos, I = documentos internos, E = documentos externos e F = formulários</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/SeriesResource.java",
    "groupTitle": "Documento"
  },
  {
    "type": "get",
    "url": "/:unidade/estados/:estado/cidades/:cidade",
    "title": "Consultar cidade",
    "name": "getCidade",
    "group": "Endereco",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Consulta a cidade pelo código do IBGE.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "estado",
            "description": "<p>Sigla do estado.</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "cidade",
            "description": "<p>Código do IBGE.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -i http://<host>/sei-broker/service/cosap/estados/rj/cidades/3303708",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "Cidade",
            "optional": false,
            "field": "cidade",
            "description": "<p>Objeto representando uma cidade.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "cidade.nome",
            "description": "<p>Nome da cidade.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "cidade.codigoIbge",
            "description": "<p>Código do IBGE.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Estado",
            "optional": false,
            "field": "cidade.estado",
            "description": "<p>Objeto representando um estado.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "cidade.estado.nome",
            "description": "<p>Nome do estado.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "cidade.estado.sigla",
            "description": "<p>Sigla do estado.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n\t \"estado\": {\n  \t\"sigla\": \"RJ\",\n  \t\"nome\": \"Rio de Janeiro\"\n  },\n  \"nome\": \"Paraíba do Sul\",\n  \"codigoIbge\": \"3303708\"\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/InfraResource.java",
    "groupTitle": "Endereco"
  },
  {
    "type": "get",
    "url": "/:unidade/estados/:estado/cidades",
    "title": "Listar cidades",
    "name": "getCidades",
    "group": "Endereco",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Consulta as cidades de um determinado estado.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "estado",
            "description": "<p>Sigla do estado.</p>"
          }
        ],
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "filtro",
            "description": "<p>String utilizada para filtrar as cidades.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -i http://<host>/sei-broker/service/cosap/estados/rj/cidades?filtro=paraiba",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "List",
            "optional": false,
            "field": "resultado",
            "description": "<p>Lista com os cidades encontradas.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Cidade",
            "optional": false,
            "field": "resultado.cidade",
            "description": "<p>Objeto representando uma cidade.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.cidade.nome",
            "description": "<p>Nome da cidade.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.cidade.codigoIbge",
            "description": "<p>Código do IBGE.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Estado",
            "optional": false,
            "field": "resultado.cidade.estado",
            "description": "<p>Objeto representando um estado.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.cidade.estado.nome",
            "description": "<p>Nome do estado.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.cidade.estado.sigla",
            "description": "<p>Sigla do estado.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n\t \"estado\": {\n  \t\"sigla\": \"RJ\",\n  \t\"nome\": \"Rio de Janeiro\"\n  },\n  \"nome\": \"Paraíba do Sul\",\n  \"codigoIbge\": \"3303708\"\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/InfraResource.java",
    "groupTitle": "Endereco"
  },
  {
    "type": "get",
    "url": "/:unidade/estados",
    "title": "Consultar estado",
    "name": "getEstado",
    "group": "Endereco",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Consulta o estado pela sigla.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "sigla",
            "description": "<p>Sigla do estado</p>"
          }
        ],
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "pais",
            "defaultValue": "76 (Brasil)",
            "description": "<p>Identificador do pais que deseja listar os estados.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -i http://<host>/sei-broker/service/cosap/estados/AC",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "Estado",
            "optional": false,
            "field": "estado",
            "description": "<p>Objeto representando um estado.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "estado.idEstado",
            "description": "<p>Identificador do estado no SEI.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "estado.idPais",
            "description": "<p>Identificador do país no SEI.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "estado.sigla",
            "description": "<p>Sigla do estado.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "estado.nome",
            "description": "<p>Nome do estado.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "estado.codigoIbge",
            "description": "<p>Código do IBGE.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n\t \"idEstado\": \"2\",\n  \"idPais\": \"76\",\n  \"sigla\": \"AC\",\n  \"nome\": \"Acre\",\n  \"codigoIbge\": \"12\"\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/InfraResource.java",
    "groupTitle": "Endereco"
  },
  {
    "type": "get",
    "url": "/:unidade/estados",
    "title": "Listar estados",
    "name": "getEstados",
    "group": "Endereco",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Consulta estados cadastrados.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -i http://<host>/sei-broker/service/cosap/estados",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "List",
            "optional": false,
            "field": "resultado",
            "description": "<p>Lista com os estados.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Estado",
            "optional": false,
            "field": "resultado.estado",
            "description": "<p>Objeto representando um estado.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.estado.idEstado",
            "description": "<p>Identificador do estado no SEI.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.estado.idPais",
            "description": "<p>Identificador do país no SEI.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.estado.sigla",
            "description": "<p>Sigla do estado.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.estado.nome",
            "description": "<p>Nome do estado.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.estado.codigoIbge",
            "description": "<p>Código do IBGE.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n\t \"idEstado\": \"2\",\n  \"idPais\": \"76\",\n  \"sigla\": \"AC\",\n  \"nome\": \"Acre\",\n  \"codigoIbge\": \"12\"\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/InfraResource.java",
    "groupTitle": "Endereco"
  },
  {
    "type": "get",
    "url": "/:unidade/paises",
    "title": "Listar países",
    "name": "getPaises",
    "group": "Endereco",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Consulta países cadastrados.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -i http://<host>/sei-broker/service/cosap/paises",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "List",
            "optional": false,
            "field": "resultado",
            "description": "<p>Lista com os países.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Pais",
            "optional": false,
            "field": "resultado.pais",
            "description": "<p>Objeto representando o país.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.pais.idPais",
            "description": "<p>Identificador do país no SEI.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.pais.nome",
            "description": "<p>Nome do país.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n  \"idPais\": \"4\",\n  \"nome\": \"Afeganistão\"\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/InfraResource.java",
    "groupTitle": "Endereco"
  },
  {
    "type": "get",
    "url": "/:unidade/extensoes",
    "title": "Listar extensões",
    "name": "listarExtensoesPermitidas",
    "group": "Extensao",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Este método realiza uma busca pelas extensões de arquivos permitidas.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI.</p>"
          }
        ],
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "extensao",
            "defaultValue": "null",
            "description": "<p>Para filtrar por uma determinada extensão.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:",
        "content": "curl -i http://<host>/sei-broker/service/COSAP/extensoes/",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "ArquivoExtensao[]",
            "optional": false,
            "field": "extensoes",
            "description": "<p>Lista de extensões permitidas.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "extensoes.idArquivoExtensao",
            "description": "<p>Identificador interno do SEI relativo a extensão</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "extensoes.extensao",
            "description": "<p>Texto da extensão (ex.: pdf, ods, doc, ppt,...)</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "extensoes.descricao",
            "description": "<p>Descrição da extensão</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/ExtensoesResource.java",
    "groupTitle": "Extensao"
  },
  {
    "type": "get",
    "url": "/:unidade/hipoteses-legais",
    "title": "Listar hipóteses legais",
    "name": "listarHipoteses",
    "group": "Hipotese_Legal",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Lista as hipóteses legais.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          }
        ],
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "nivelAcesso",
            "description": "<p>Filtra hipóteses pelo nível de acesso associado (1 - restrito, 2 - sigiloso)</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:",
        "content": "curl -i https://<host>/sei-broker/service/COSAP/hipoteses-legais",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "HipoteseLegal[]",
            "optional": false,
            "field": "resultado",
            "description": "<p>Lista com as hipóteses legais encontrados</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.idHipoteseLegal",
            "description": "<p>Identificador da hipótese legal no SEI</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.nome",
            "description": "<p>Nome da hipótese legal</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.baseLegal",
            "description": "<p>Descrição da base legal</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.nivelAcesso",
            "description": "<p>Nivel de acesso associado a hipótese legal</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/HipoteseLegalResource.java",
    "groupTitle": "Hipotese_Legal"
  },
  {
    "type": "get",
    "url": "/info/versao",
    "title": "Consultar versão",
    "name": "getNumeroVersao",
    "group": "Info",
    "version": "2.0.0",
    "description": "<p>Este método realiza uma consulta para saber a versão do sei-broker que está disponível.</p>",
    "examples": [
      {
        "title": "Exemplo de requisição:",
        "content": "curl -i http://<host>/sei-broker/service/info/versao",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "versao",
            "description": "<p>Número da versão.</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/InfoResource.java",
    "groupTitle": "Info"
  },
  {
    "type": "get",
    "url": "/info/requests",
    "title": "Listar Requests",
    "name": "getUltimosRequests",
    "group": "Info",
    "version": "2.0.0",
    "description": "<p>Lista os requests recebidos pelo broker.</p>",
    "parameter": {
      "fields": {
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "operacao",
            "description": "<p>nome do método acessado</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "origem",
            "description": "<p>usuário que originou a requisição</p>"
          },
          {
            "group": "Query Parameters",
            "type": "int",
            "optional": true,
            "field": "pag",
            "defaultValue": "1",
            "description": "<p>número da página</p>"
          },
          {
            "group": "Query Parameters",
            "type": "int",
            "optional": true,
            "field": "itens",
            "defaultValue": "50",
            "description": "<p>quantidade de itens listados por página</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:",
        "content": "curl -i http://<host>/sei-broker/service/info/requests",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "mensagem",
            "description": "<p>Mensagem de sucesso.</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/InfoResource.java",
    "groupTitle": "Info"
  },
  {
    "type": "get",
    "url": "/info/conexoes/mysql",
    "title": "Testar conexão MySQL",
    "name": "testMySQLConnection",
    "group": "Info",
    "version": "2.0.0",
    "description": "<p>Testa a conexão com o MySQL e retorna o número de versão do banco.</p>",
    "examples": [
      {
        "title": "Exemplo de requisição:",
        "content": "curl -i http://<host>/sei-broker/service/info/conexoes/mysql",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "versao",
            "description": "<p>Número de versão do MySQL.</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/InfoResource.java",
    "groupTitle": "Info"
  },
  {
    "type": "get",
    "url": "/info/conexoes/oracle",
    "title": "Testar conexão Oracle",
    "name": "testOracleConnection",
    "group": "Info",
    "version": "2.0.0",
    "description": "<p>Testa a conexão com o Oracle e retorna o número de versão do banco.</p>",
    "examples": [
      {
        "title": "Exemplo de requisição:",
        "content": "curl -i http://<host>/sei-broker/service/info/conexoes/oracle",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "versao",
            "description": "<p>Número de versão do Oracle.</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/InfoResource.java",
    "groupTitle": "Info"
  },
  {
    "type": "get",
    "url": "/info/conexoes/sei",
    "title": "Testar conexão SEI",
    "name": "testSEIConnection",
    "group": "Info",
    "version": "2.0.0",
    "description": "<p>Testa a conexão com o SEI fazendo uma consulta ao serviço listar unidades.</p>",
    "examples": [
      {
        "title": "Exemplo de requisição:",
        "content": "curl -i http://<host>/sei-broker/service/info/conexoes/sei",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "mensagem",
            "description": "<p>Mensagem de sucesso.</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/InfoResource.java",
    "groupTitle": "Info"
  },
  {
    "type": "get",
    "url": "/:unidade/marcadores",
    "title": "Listar marcadores",
    "name": "listarMarcadores",
    "group": "Marcador",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Lista os marcadores de uma unidade.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:",
        "content": "curl -i https://<host>/sei-broker/service/cosap/marcadores",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "Marcador[]",
            "optional": false,
            "field": "marcadores",
            "description": "<p>Lista de marcadores</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "marcadores.id",
            "description": "<p>Identificador do marcador.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "marcadores.nome",
            "description": "<p>Nome do marcador.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "marcadores.icone",
            "description": "<p>Ícone do marcador em formato PNG codificado em Base64.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "marcadores.sinAtivo",
            "description": "<p>S/N - Sinalizador indica se o marcador está ativo.</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/MarcadorResource.java",
    "groupTitle": "Marcador"
  },
  {
    "type": "post",
    "url": "/:unidade/marcadores/:identificador/processos",
    "title": "Adicionar Processo",
    "name": "marcarProcesso",
    "group": "Marcador",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Adiciona um processo ao marcador.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI.</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "identificador",
            "description": "<p>Identificador do marcador no SEI.</p>"
          }
        ],
        "Request Body": [
          {
            "group": "Request Body",
            "type": "MarcacaoProcesso",
            "optional": false,
            "field": "marcacaoProcesso",
            "description": "<p>Objeto de com as definições da marcação.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "marcacaoProcesso.processo",
            "description": "<p>Número do processo no SEI.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "marcacaoProcesso.texto",
            "description": "<p>Texto para associação.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:",
        "content": "curl -i https://<host>/sei-broker/service/cosap/marcadores/3/processos",
        "type": "curl"
      }
    ],
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 201 Created",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/MarcadorResource.java",
    "groupTitle": "Marcador"
  },
  {
    "type": "post",
    "url": "/:unidade/processos",
    "title": "Abrir processo",
    "name": "abrirProcesso",
    "group": "Processo",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Abre um processo.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI.</p>"
          }
        ],
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "String",
            "allowedValues": [
              "\"S (sim), N (não)\""
            ],
            "optional": true,
            "field": "auto-formatacao",
            "defaultValue": "S",
            "description": "<p>O broker utilizará a mascara padrão para formatar o número do processo</p>"
          }
        ],
        "Request Body": [
          {
            "group": "Request Body",
            "type": "NovoProcesso",
            "optional": false,
            "field": "novoProcesso",
            "description": "<p>Objeto de representação de novo processo.</p>"
          },
          {
            "group": "Request Body",
            "type": "Procedimento",
            "optional": false,
            "field": "novoProcesso.dadosProcesso",
            "description": "<p>Dados do processo.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "novoProcesso.dadosProcesso.idTipoProcedimento",
            "description": "<p>Identificador do tipo de processo no SEI (Consultar tipos de processo).</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "novoProcesso.dadosProcesso.numeroProtocolo",
            "description": "<p>Número do processo, se não for informado o sistema irá gerar um novo número automaticamente.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "novoProcesso.dadosProcesso.dataAutuacao",
            "description": "<p>Data de autuação do processo, se não for informada o sistema utilizará a data atual.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "novoProcesso.dadosProcesso.especificacao",
            "description": "<p>Especificação do processo.</p>"
          },
          {
            "group": "Request Body",
            "type": "Assunto[]",
            "optional": true,
            "field": "novoProcesso.dadosProcesso.assuntos",
            "description": "<p>Assuntos do processo, os assuntos informados serão adicionados aos assuntos sugeridos para o tipo de processo. Passar um array vazio caso nenhum outro assunto seja necessário(caso apenas os sugeridos para o tipo bastem para classificação).</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "novoProcesso.dadosProcesso.assuntos.codigoEstruturado",
            "description": "<p>Código do assunto</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "novoProcesso.dadosProcesso.assuntos.descricao",
            "description": "<p>Descrição do assunto</p>"
          },
          {
            "group": "Request Body",
            "type": "Interessado[]",
            "optional": true,
            "field": "novoProcesso.dadosProcesso.interessados",
            "description": "<p>Informar um conjunto com os dados de interessados. Se não existirem interessados deve ser informado um conjunto vazio.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "novoProcesso.dadosProcesso.interessados.nome",
            "description": "<p>Nome do interessado</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "novoProcesso.dadosProcesso.interessados.sigla",
            "description": "<p>Login do interessado</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "novoProcesso.dadosProcesso.observacao",
            "description": "<p>Texto da observação, passar null se não existir.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "allowedValues": [
              "\"0 (público)\"",
              "\"1 (restrito)\"",
              "\"2 (sigiloso)\"",
              "\"null (herda do tipo do processo)\""
            ],
            "optional": true,
            "field": "novoProcesso.dadosProcesso.nivelAcesso",
            "description": "<p>Nível de acesso do processo.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "novoProcesso.dadosProcesso.idHipoteseLegal",
            "description": "<p>Identificador da hipótese legal associada.\t *</p>"
          },
          {
            "group": "Request Body",
            "type": "Documento[]",
            "optional": true,
            "field": "novoProcesso.documentos",
            "description": "<p>Informar os documentos que devem ser gerados em conjunto com o processo (ver serviço de incluir documento para instruções de preenchimento). Se nenhum documento for gerado informar um conjunto vazio.</p>"
          },
          {
            "group": "Request Body",
            "type": "String[]",
            "optional": true,
            "field": "novoProcesso.processosRelacionados",
            "description": "<p>Lista com os identificadores dos processos(idProcedimento) que devem ser relacionados automaticamente com o novo processo, atenção, não passar o número do processo formatado.</p>"
          },
          {
            "group": "Request Body",
            "type": "String[]",
            "optional": true,
            "field": "novoProcesso.unidadesDestino",
            "description": "<p>Lista com os identificadores das unidades de destino do processo, código ou nome da unidade.</p>"
          },
          {
            "group": "Request Body",
            "type": "Boolean",
            "optional": true,
            "field": "novoProcesso.manterAbertoOrigem",
            "defaultValue": "false",
            "description": "<p>Indica se o processo deve ser mantido aberto na unidade de origem.</p>"
          },
          {
            "group": "Request Body",
            "type": "Boolean",
            "optional": true,
            "field": "novoProcesso.enviarEmailNotificacao",
            "defaultValue": "false",
            "description": "<p>Indica se deve ser enviado email de aviso para as unidades destinatárias.</p>"
          },
          {
            "group": "Request Body",
            "type": "Date",
            "optional": true,
            "field": "novoProcesso.dataRetornoProgramado",
            "description": "<p>Data para definição de Retorno Programado.</p>"
          },
          {
            "group": "Request Body",
            "type": "Integer",
            "optional": true,
            "field": "novoProcesso.qtdDiasAteRetorno",
            "description": "<p>Número de dias para o Retorno Programado.</p>"
          },
          {
            "group": "Request Body",
            "type": "Boolean",
            "optional": true,
            "field": "novoProcesso.somenteDiasUteis",
            "defaultValue": "false",
            "description": "<p>Indica se o valor passado no parâmetro qtdDiasAteRetorno corresponde a dias úteis ou não.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "novoProcesso.idMarcador",
            "description": "<p>Identificador de um marcador da unidade para associação.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "novoProcesso.textoMarcador",
            "description": "<p>Texto do marcador.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: [POST] https://<host>/sei-broker/service/COSAP/processos\n\nbody:\n{\n\t\"dadosProcesso\":{\n\t\t\t\"idTipoProcedimento\":\"100000375\",\n\t\t\t\"especificacao\":\"Documentação REST\",\n\t\t\t\"assuntos\":[],\n\t\t\t\"interessados\":[{\"sigla\":\"andre.guimaraes\",\"nome\":\"André Luís Fernandes Guimarães\"}],\n\t\t\t\"observacao\":\"Exemplo de requisição\",\n\t\t\t\"nivelAcesso\":0\n\t\t},\n\t\t\"documentos\":[],\n\t\t\"processosRelacionados\":[\"186649\"],\n\t\t\"unidadesDestino\":[\"COTEC\",\"110000935\",\"COSAP\"],\n\t\t\"manterAbertoOrigem\":true,\n\t\t\"enviarEmailNotificacao\":true,\n\t\t\"qtdDiasAteRetorno\":null,\n\t\t\"somenteDiasUteis\":false\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso Response Body - 201": [
          {
            "group": "Sucesso Response Body - 201",
            "type": "String",
            "optional": false,
            "field": "idProcedimento",
            "description": "<p>Número do processo gerado</p>"
          },
          {
            "group": "Sucesso Response Body - 201",
            "type": "String",
            "optional": false,
            "field": "procedimentoFormatado",
            "description": "<p>Número formatado do processo gerado</p>"
          },
          {
            "group": "Sucesso Response Body - 201",
            "type": "String",
            "optional": false,
            "field": "linkAcesso",
            "description": "<p>Link de acesso ao processo</p>"
          },
          {
            "group": "Sucesso Response Body - 201",
            "type": "RetornoInclusaoDocumento",
            "optional": false,
            "field": "retornoInclusaoDocumentos",
            "description": "<p>Retorno dos documentos inseridos no processo (opcional)</p>"
          },
          {
            "group": "Sucesso Response Body - 201",
            "type": "String",
            "optional": false,
            "field": "retornoInclusaoDocumentos.idDocumento",
            "description": "<p>Número interno do documento</p>"
          },
          {
            "group": "Sucesso Response Body - 201",
            "type": "String",
            "optional": false,
            "field": "retornoInclusaoDocumentos.documentoFormatado",
            "description": "<p>Número do documento visível para o usuário</p>"
          },
          {
            "group": "Sucesso Response Body - 201",
            "type": "String",
            "optional": false,
            "field": "retornoInclusaoDocumentos.linkAcesso",
            "description": "<p>Link para acesso ao documento</p>"
          }
        ],
        "Sucesso Response Header - 201": [
          {
            "group": "Sucesso Response Header - 201",
            "type": "header",
            "optional": false,
            "field": "Location",
            "description": "<p>URL de acesso ao recurso criado.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n  \"idProcedimento\":\"33910000056201626\",\n  \"procedimentoFormatado\":\"33910.000056/2016-26\",\n  \"linkAcesso\":\"https://sei-hm.ans.gov.br/controlador.php?acao=arvore_visualizar&acao_origem=procedimento_visualizar&id_procedimento=267&infra_sistema=100000100&infra_unidade_atual=110000934&infra_hash=7a6a75f6b8ec6b43aaffc6616159a85e35e444b9b32da54108e467bc9f3bdfab\",\n  \"retornoInclusaoDocumentos\":[]\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/ProcessoResource.java",
    "groupTitle": "Processo"
  },
  {
    "type": "post",
    "url": "/:unidade/processos/:processo/anexados",
    "title": "Anexar processo",
    "name": "anexarProcesso",
    "group": "Processo",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Anexar um processo.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI.</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "processo",
            "description": "<p>Número do processo.</p>"
          }
        ],
        "Request Body": [
          {
            "group": "Request Body",
            "type": "ProcessoAnexado",
            "optional": false,
            "field": "processoAnexado",
            "description": "<p>Objeto representando o processo a ser anexado.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "processoAnexado.numero",
            "description": "<p>Número do processo a ser anexado.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: [POST] https://<host>/sei-broker/service/COSAP/processos/33910003114201754/anexados\n\nbody:\n{\n\t\"numero\":\"33910003093201777\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/ProcessoResource.java",
    "groupTitle": "Processo"
  },
  {
    "type": "post",
    "url": "/:unidade/processos/bloqueados",
    "title": "Bloquear processo",
    "name": "bloquearProcesso",
    "group": "Processo",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Bloquear um processo.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI.</p>"
          }
        ],
        "Request Body": [
          {
            "group": "Request Body",
            "type": "ProcessoBloqueado",
            "optional": false,
            "field": "processoBloqueado",
            "description": "<p>Objeto com o número do processo a ser bloqueado.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "processoBloqueado.numero",
            "description": "<p>Número do processo a ser bloqueado.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: [POST] https://<host>/sei-broker/service/COSAP/processos/bloqueados\n\nbody:\n{\n\t\"numero\":\"33910003093201777\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/ProcessoResource.java",
    "groupTitle": "Processo"
  },
  {
    "type": "post",
    "url": "/:unidade/processos/concluidos",
    "title": "Concluir processo",
    "name": "concluirProcesso",
    "group": "Processo",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Conclui o processo informado.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          }
        ],
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "String",
            "allowedValues": [
              "\"S (sim), N (não)\""
            ],
            "optional": true,
            "field": "auto-formatacao",
            "defaultValue": "S",
            "description": "<p>O broker utilizará a mascara padrão para formatar o número do processo</p>"
          }
        ],
        "Request Body": [
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "processo",
            "description": "<p>Numero do processo a ser concluído</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: [POST] https://<host>/sei-broker/service/COSAP/processos/concluidos\n\nbody:\n33910000029201653",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "Boolean",
            "optional": false,
            "field": "resultado",
            "description": "<p>Booleano informando sucesso da requisição</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/ProcessoResource.java",
    "groupTitle": "Processo"
  },
  {
    "type": "get",
    "url": "/processos/:processo/documentos/:documento",
    "title": "Consultar documento",
    "name": "consultarDocumentoDoProcesso",
    "group": "Processo",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Consulta um documento de determinado processo.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "processo",
            "description": "<p>Número do processo.</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "documento",
            "description": "<p>Número do documento.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -i https://<host>/sei-broker/service/processos/33910002924201874/documentos/55737058",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso Response Body - 200": [
          {
            "group": "Sucesso Response Body - 200",
            "type": "DocumentoResumido",
            "optional": false,
            "field": "documentoResumido",
            "description": "<p>Resumo do documento encontrado no SEI.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "documentoResumido.numero",
            "description": "<p>Número do documento.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "documentoResumido.numeroInformado",
            "description": "<p>Número informado na inclusão do documento, também conhecido como número de árvore.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "allowedValues": [
              "\"GERADO\"",
              "\"RECEBIDO\""
            ],
            "optional": false,
            "field": "documentoResumido.origem",
            "description": "<p>Origem do documento, se o mesmo é um documento &quot;GERADO&quot; internamente ou &quot;RECEBIDO&quot; de uma fonte externa.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "Data",
            "optional": false,
            "field": "documentoResumido.dataGeracao",
            "description": "<p>Data de geração do documento.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "Tipo",
            "optional": false,
            "field": "documentoResumido.tipo",
            "description": "<p>Objeto representando o tipo do documento.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "documentoResumido.tipo.codigo",
            "description": "<p>Identificados do tipo do documento, também conhecido como série.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "documentoResumido.tipo.nome",
            "description": "<p>Nome do tipo do documento.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "documentoResumido.tipoConferencia",
            "description": "<p>Tipo de conferência do documento.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "boolean",
            "optional": false,
            "field": "documentoResumido.assinado",
            "description": "<p>Boolean indicando se o documento foi assinado.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n  \"numero\": \"0670949\",\n  \"numeroInformado\": \"594\",\n  \"origem\": \"RECEBIDO\",\n  \"dataGeracao\": \"2015-08-10T00:00:00-03:00\",\n  \"tipo\": {\n  \t\"codigo\": \"629\",\n  \t\"nome\": \"Relatório de Arquivamento-SIF\"\n  }\n  \"tipoConferencia\": \"4\",\n  \"assinado\": true\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/ProcessoResource.java",
    "groupTitle": "Processo"
  },
  {
    "type": "get",
    "url": "/:unidade/processos/:processo",
    "title": "Consultar processo",
    "name": "consultarProcesso",
    "group": "Processo",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Este método realiza uma consulta a processos no SEI e no SIPAR.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "processo",
            "description": "<p>Número do processo que deseja consultar</p>"
          }
        ],
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "String",
            "allowedValues": [
              "\"S (sim), N (não)\""
            ],
            "optional": true,
            "field": "assuntos",
            "defaultValue": "N",
            "description": "<p>Exibir assuntos do processo</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "allowedValues": [
              "\"S (sim), N (não)\""
            ],
            "optional": true,
            "field": "interessados",
            "defaultValue": "N",
            "description": "<p>Exibir interessados no processo</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "allowedValues": [
              "\"S (sim), N (não)\""
            ],
            "optional": true,
            "field": "observacoes",
            "defaultValue": "N",
            "description": "<p>Exibir observações feitas no processo</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "allowedValues": [
              "\"S (sim), N (não)\""
            ],
            "optional": true,
            "field": "andamento",
            "defaultValue": "N",
            "description": "<p>Exibir andamento do processo</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "allowedValues": [
              "\"S (sim), N (não)\""
            ],
            "optional": true,
            "field": "andamento-conclusao",
            "defaultValue": "N",
            "description": "<p>Exibir o andamento da conclusão do processo</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "allowedValues": [
              "\"S (sim), N (não)\""
            ],
            "optional": true,
            "field": "ultimo-andamento",
            "defaultValue": "N",
            "description": "<p>Exibir o último andamento dado ao processo</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "allowedValues": [
              "\"S (sim), N (não)\""
            ],
            "optional": true,
            "field": "unidades",
            "defaultValue": "N",
            "description": "<p>Exibir unidades onde o processo está aberto</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "allowedValues": [
              "\"S (sim), N (não)\""
            ],
            "optional": true,
            "field": "relacionados",
            "defaultValue": "N",
            "description": "<p>Exibir processos relacionados</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "allowedValues": [
              "\"S (sim), N (não)\""
            ],
            "optional": true,
            "field": "anexados",
            "defaultValue": "N",
            "description": "<p>Exibir processos anexados</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "allowedValues": [
              "\"S (sim), N (não)\""
            ],
            "optional": true,
            "field": "auto-formatacao",
            "defaultValue": "S",
            "description": "<p>O broker utilizará a mascara padrão para formatar o número do processo</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:",
        "content": "curl -i https://<host>/sei-broker/service/COSAP/processos/33910000029201653",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "ResultadoConsultaProcesso",
            "optional": false,
            "field": "resultadoConsultaProcesso",
            "description": "<p>Objeto de retorno da consulta aos processos, pode um conter processo do SEI ou do SIPAR</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "RetornoConsultaProcedimento",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei",
            "description": "<p>Resultado de processo do SEI</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Andamento",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoConclusao",
            "description": "<p>Andamento da conclusão do processo (opcional)</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Data",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoConclusao.dataHora",
            "description": "<p>Data e hora do registro de andamento</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoConclusao.descricao",
            "description": "<p>Descrição do andamento</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Unidade",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoConclusao.unidade",
            "description": "<p>Unidade responsável pelo andamento</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoConclusao.unidade.descricao",
            "description": "<p>Nome da unidade</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoConclusao.unidade.idUnidade",
            "description": "<p>Código da unidade</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoConclusao.unidade.sigla",
            "description": "<p>Sigla da unidade</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Usuario",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoConclusao.usuario",
            "description": "<p>Usuário responsável pela ação</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoConclusao.usuario.idUsuario",
            "description": "<p>Código do usuário</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoConclusao.usuario.nome",
            "description": "<p>Nome do usuário</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoConclusao.usuario.sigla",
            "description": "<p>Login do usuário</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Andamento",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoGeracao",
            "description": "<p>Andamento da geração do processo (opcional)</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Data",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoGeracao.dataHora",
            "description": "<p>Data e hora do registro de andamento</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoGeracao.descricao",
            "description": "<p>Descrição do andamento</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Unidade",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoGeracao.unidade",
            "description": "<p>Unidade responsável pelo andamento</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoGeracao.unidade.descricao",
            "description": "<p>Nome da unidade</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoGeracao.unidade.idUnidade",
            "description": "<p>Código da unidade</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoGeracao.unidade.sigla",
            "description": "<p>Sigla da unidade</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Usuario",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoGeracao.usuario",
            "description": "<p>Usuário responsável pela ação</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoGeracao.usuario.idUsuario",
            "description": "<p>Código do usuário</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoGeracao.usuario.nome",
            "description": "<p>Nome do usuário</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoGeracao.usuario.sigla",
            "description": "<p>Login do usuário</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Assunto",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.assuntos",
            "description": "<p>Lista de assuntos</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.assuntos.codigoEstruturado",
            "description": "<p>Código do assunto</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.descricao",
            "description": "<p>Descrição do assunto</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Data",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.dataAutuacao",
            "description": "<p>Data de autuação do processo</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.especificacao",
            "description": "<p>Especificação do processo</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.idProcedimento",
            "description": "<p>Id interno do processo no SEI</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Interessado",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.interessados",
            "description": "<p>Lista de interessados no processo (opcional)</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.interessados.nome",
            "description": "<p>Nome do interessado</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.interessados.sigla",
            "description": "<p>Login do interessado</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.linkAcesso",
            "description": "<p>Link para acesso ao processo</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Observacao",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.observacoes",
            "description": "<p>Observações feitas sobre o processo (opcional)</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.observacoes.descricao",
            "description": "<p>Descrição da obsevação</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Unidade",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.observacoes.unidade",
            "description": "<p>Unidade responsável pela observação</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.observacoes.unidade.descricao",
            "description": "<p>Nome da unidade</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.observacoes.unidade.idUnidade",
            "description": "<p>Código da unidade</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.observacoes.unidade.sigla",
            "description": "<p>Sigla da unidade</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "ProcedimentoResumido",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.procedimentosAnexados",
            "description": "<p>Lista com os processos anexados</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.procedimentosAnexados.idProcedimento",
            "description": "<p>Identificador do processo</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.procedimentosAnexados.procedimentoFormatado",
            "description": "<p>Número do processo visível para o usuário</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.procedimentosAnexados.tipoProcedimento",
            "description": "<p>Tipo do processo</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "ProcedimentoResumido",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.procedimentosRelacionados",
            "description": "<p>Lista com os processos relacionados</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.procedimentosRelacionados.idProcedimento",
            "description": "<p>Identificador do processo</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.procedimentosRelacionados.procedimentoFormatado",
            "description": "<p>Número do processo visível para o usuário</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.procedimentosRelacionados.tipoProcedimento",
            "description": "<p>Tipo do processo</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.procedimentoFormatado",
            "description": "<p>Número do processo visível para o usuário</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "TipoProcedimento",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.tipoProcedimento",
            "description": "<p>Tipo de procedimento</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.tipoProcedimento.idTipoProcedimento",
            "description": "<p>Identificador do tipo de procedimento</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.tipoProcedimento.nome",
            "description": "<p>Nome do tipo de procedimento</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Andamento",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.ultimoAndamento",
            "description": "<p>Ultimo andamento do processo (opcional)</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Data",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.ultimoAndamento.dataHora",
            "description": "<p>Data e hora do registro de andamento</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.ultimoAndamento.descricao",
            "description": "<p>Descrição do andamento</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Unidade",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.ultimoAndamento.unidade",
            "description": "<p>Unidade responsável pelo andamento</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.ultimoAndamento.unidade.descricao",
            "description": "<p>Nome da unidade</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.ultimoAndamento.unidade.idUnidade",
            "description": "<p>Código da unidade</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.ultimoAndamento.unidade.sigla",
            "description": "<p>Sigla da unidade</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Usuario",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.ultimoAndamento.usuario",
            "description": "<p>Usuário responsável pela ação</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.ultimoAndamento.usuario.idUsuario",
            "description": "<p>Código do usuário</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.ultimoAndamento.usuario.nome",
            "description": "<p>Nome do usuário</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.ultimoAndamento.usuario.sigla",
            "description": "<p>Login do usuário</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "UnidadeProcedimentoAberto",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.unidadesProcedimentoAberto",
            "description": "<p>Unidades onde o processo está aberto (opcional)</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Unidade",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.unidadesProcedimentoAberto.unidade",
            "description": "<p>Unidade onde o processo está aberto</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.unidadesProcedimentoAberto.unidade.descricao",
            "description": "<p>Nome da unidade</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.unidadesProcedimentoAberto.unidade.idUnidade",
            "description": "<p>Código da unidade</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.unidadesProcedimentoAberto.unidade.sigla",
            "description": "<p>Sigla da unidade</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "DocumentoSIPAR",
            "optional": false,
            "field": "resultadoConsultaProcesso.sipar",
            "description": "<p>Resultado de processo do SIPAR</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sipar.digito",
            "description": "<p>Digito do processo</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sipar.operadora",
            "description": "<p>Operadora relacionada ao processo</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Data",
            "optional": false,
            "field": "resultadoConsultaProcesso.sipar.emissao",
            "description": "<p>Data de emissão</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Data",
            "optional": false,
            "field": "resultadoConsultaProcesso.sipar.registro",
            "description": "<p>Data de registro</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sipar.tipo",
            "description": "<p>Tipo do processo</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sipar.resumo",
            "description": "<p>Resumo sobre o processo</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Long",
            "optional": false,
            "field": "resultadoConsultaProcesso.sipar.orgaoPosse",
            "description": "<p>Código do orgão que tem a posse do processo</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Long",
            "optional": false,
            "field": "resultadoConsultaProcesso.sipar.orgaoOrigem",
            "description": "<p>Código do orgão de origem do processo</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Long",
            "optional": false,
            "field": "resultadoConsultaProcesso.sipar.orgaoRegistro",
            "description": "<p>Código do orgão de registro do processo</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Long",
            "optional": false,
            "field": "resultadoConsultaProcesso.sipar.assunto",
            "description": "<p>Código do assunto</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/ProcessoResource.java",
    "groupTitle": "Processo"
  },
  {
    "type": "get",
    "url": "/processos",
    "title": "Listar processos",
    "name": "consultarProcessos",
    "group": "Processo",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Lista os processos conforme os filtros informados.</p>",
    "parameter": {
      "fields": {
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "Boolean",
            "optional": true,
            "field": "crescente",
            "defaultValue": "false",
            "description": "<p>Ordenar em ordem crescente, processos mais antigos primeiro</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "interessado",
            "description": "<p>Identificador do interessado</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "unidade",
            "description": "<p>Unidade da qual deseja filtrar os processos</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "pagina",
            "defaultValue": "1",
            "description": "<p>Número da página</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "qtdRegistros",
            "defaultValue": "50",
            "description": "<p>Quantidade de registros retornados por página</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "tipo",
            "description": "<p>Identificador do tipo de processo que deseja filtrar</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -i https://<host>/sei-broker/service/processos",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso Response Body - 200": [
          {
            "group": "Sucesso Response Body - 200",
            "type": "List",
            "optional": false,
            "field": "processos",
            "description": "<p>Lista com os processos encontrados</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "ProcessoResumido",
            "optional": false,
            "field": "processos.processoResumido",
            "description": "<p>Resumo do processo encontrado no SEI</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "processos.processoResumido.numero",
            "description": "<p>Número do processo</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "processos.processoResumido.numeroFormatado",
            "description": "<p>Número do processo formatado</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "processos.processoResumido.descricao",
            "description": "<p>Descrição do processo</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "processos.processoResumido.unidade",
            "description": "<p>Unidade responsável pelo processo</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "Data",
            "optional": false,
            "field": "processos.processoResumido.dataGeracao",
            "description": "<p>Data de geração do processo</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "Tipo",
            "optional": false,
            "field": "processos.processoResumido.tipo",
            "description": "<p>Objeto com os dados do tipo de processo</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "processos.processoResumido.tipo.codigo",
            "description": "<p>Código do tipo</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "processos.processoResumido.tipo.nome",
            "description": "<p>Nome do tipo</p>"
          }
        ],
        "Sucesso Response Header - 200": [
          {
            "group": "Sucesso Response Header - 200",
            "type": "header",
            "optional": false,
            "field": "total_registros",
            "description": "<p>quantidade de registros que existem para essa consulta.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n  \"numero\": \"33910007118201710\",\n  \"numeroFormatado\": \"33910.007118/2017-10\",\n  \"descricao\": \"D:2237021 - SUL AMÉRICA SEGURO SAÚDE S/A\",\n  \"unidade\": \"NÚCLEO-RJ\",\n  \"dataGeracao\": \"2017-10-09T03:00:00.000+0000\",\n  \"tipo\": {\n  \t\"codigo\": \"100000882\",\n  \t\"nome\": \"Fiscalização: Sancionador\"\n  }\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/ProcessoResource.java",
    "groupTitle": "Processo"
  },
  {
    "type": "delete",
    "url": "/:unidade/processos/:processo/anexados/:processoAnexado",
    "title": "Desanexar processo",
    "name": "desanexarProcesso",
    "group": "Processo",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Remove um processo anexado.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI.</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "processo",
            "description": "<p>Número do processo.</p>"
          }
        ],
        "Request Body": [
          {
            "group": "Request Body",
            "type": "Motivo",
            "optional": false,
            "field": "motivo",
            "description": "<p>Objeto com o motivo.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "motivo.motivo",
            "description": "<p>Descrição do motivo para remoção do processo em anexo.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -X DELETE https://<host>/sei-broker/service/COSAP/processos/33910003114201754/anexados/33910003093201777",
        "type": "json"
      }
    ],
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/ProcessoResource.java",
    "groupTitle": "Processo"
  },
  {
    "type": "delete",
    "url": "/:unidade/processos/bloqueados/:processo",
    "title": "Desbloquear processo",
    "name": "desbloquearProcesso",
    "group": "Processo",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Desbloquear um processo.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI.</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "processo",
            "description": "<p>Número do processo.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: [DELETE] https://<host>/sei-broker/service/COSAP/processos/bloqueados/33910003093201777",
        "type": "json"
      }
    ],
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/ProcessoResource.java",
    "groupTitle": "Processo"
  },
  {
    "type": "delete",
    "url": "/:unidade/processos/:processo/relacionados/:processoRelacionado",
    "title": "Desrelacionar processo",
    "name": "desrelacionarProcesso",
    "group": "Processo",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Desrelacionar processos.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI.</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "processo",
            "description": "<p>Número do processo.</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "processoRelacionado",
            "description": "<p>Número do processo relacionado.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: [DELETE] https://<host>/sei-broker/service/COSAP/processos/33910000086201632/relacionados/33910003107201752",
        "type": "json"
      }
    ],
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/ProcessoResource.java",
    "groupTitle": "Processo"
  },
  {
    "type": "post",
    "url": "/:unidade/processos/enviados",
    "title": "Enviar processo",
    "name": "enviarProcesso",
    "group": "Processo",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Envia processos a outras unidades.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI. Representa a unidade de localização atual do processo.</p>"
          }
        ],
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "String",
            "allowedValues": [
              "\"S (sim), N (não)\""
            ],
            "optional": true,
            "field": "reabir",
            "defaultValue": "N",
            "description": "<p>Reabrir automaticamente caso esteja concluído na unidade</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "allowedValues": [
              "\"S (sim), N (não)\""
            ],
            "optional": true,
            "field": "auto-formatacao",
            "defaultValue": "S",
            "description": "<p>O broker utilizará a mascara padrão para formatar o número do processo</p>"
          }
        ],
        "Request Body": [
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "processo",
            "description": "<p>Numero do processo a ser enviado. Em caso de processo apensado, o processo a ser enviado deve ser o processo PAI. Não é possível tramitar através do processo FILHO.</p>"
          },
          {
            "group": "Request Body",
            "type": "String[]",
            "optional": false,
            "field": "unidadesDestino",
            "description": "<p>Lista com os identificadores das unidades de destino do processo, código ou nome da unidade.</p>"
          },
          {
            "group": "Request Body",
            "type": "Boolean",
            "optional": false,
            "field": "manterAbertoOrigem",
            "defaultValue": "false",
            "description": "<p>Informa se o processo deve continuar aberto na unidade de origem .</p>"
          },
          {
            "group": "Request Body",
            "type": "Boolean",
            "optional": false,
            "field": "removerAnotacoes",
            "defaultValue": "false",
            "description": "<p>Informa se as anotações do processo devem ser removidas.</p>"
          },
          {
            "group": "Request Body",
            "type": "Boolean",
            "optional": false,
            "field": "enviarEmailNotificacao",
            "defaultValue": "false",
            "description": "<p>Informa se deve ser enviado um e-mail de notificação.</p>"
          },
          {
            "group": "Request Body",
            "type": "Date",
            "optional": false,
            "field": "dataRetornoProgramado",
            "defaultValue": "null",
            "description": "<p>Data para retorno programado do processo a unidade (padrão ISO-8601).</p>"
          },
          {
            "group": "Request Body",
            "type": "Integer",
            "optional": false,
            "field": "qtdDiasAteRetorno",
            "defaultValue": "null",
            "description": "<p>Quantidade de dias até o retorno do processo.</p>"
          },
          {
            "group": "Request Body",
            "type": "Boolean",
            "optional": false,
            "field": "somenteDiasUteis",
            "defaultValue": "false",
            "description": "<p>Informa se só serão contabilizados dias úteis.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: [POST] https://<host>/sei-broker/service/COSAP/processos/enviados\n\nbody:\n{\n\t\"numeroDoProcesso\":\"1600000000098\",\n\t\"unidadesDestino\":[\"110000934\",\"110000934\"],\n\t\"manterAbertoOrigem\":false,\n\t\"removerAnotacoes\":false,\n\t\"enviarEmailNotificacao\":true,\n\t\"dataRetornoProgramado\":2016-04-14T19:39:22.292+0000,\n\t\"qtdDiasAteRetorno\":5,\n\t\"somenteDiasUteis\":true}\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "Boolean",
            "optional": false,
            "field": "resultado",
            "description": "<p>Booleano informando sucesso da requisição</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/ProcessoResource.java",
    "groupTitle": "Processo"
  },
  {
    "type": "post",
    "url": "/:unidade/processos/:processo/andamentos",
    "title": "Lançar andamento",
    "name": "lancarAndamento",
    "group": "Processo",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Lança um andamento ao processo.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI.</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "processo",
            "description": "<p>Número do processo.</p>"
          }
        ],
        "Request Body": [
          {
            "group": "Request Body",
            "type": "NovoAndamento",
            "optional": false,
            "field": "novoAndamento",
            "description": "<p>Objeto representando o novo andamento.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "novoAndamento.tarefa",
            "description": "<p>Identificador da tarefa a qual o andamento se refere (consultar lista de tarefas).</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "novoAndamento.tarefaModulo",
            "description": "<p>Identificadoe da tarefa módulo a qual o andamento se refere.</p>"
          },
          {
            "group": "Request Body",
            "type": "HashMap",
            "optional": false,
            "field": "novoAndamento.atributos",
            "description": "<p>Mapa chave-valor, identificando como serão preenchidos os atributos da tarefa.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: [POST] https://<host>/sei-broker/service/COSAP/processos/33910003114201754/andamentos\n\nbody:\n{\n\t\"tarefa\":\"65\",\n\t\"atributos\":{\"DESCRICAO\":\"Novo andamento adicionado pelo SEI-Broker\"}\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 201": [
          {
            "group": "Sucesso - 201",
            "type": "Andamento",
            "optional": false,
            "field": "andamento",
            "description": "<p>Andamento criado.</p>"
          },
          {
            "group": "Sucesso - 201",
            "type": "String",
            "optional": false,
            "field": "andamento.idAndamento",
            "description": "<p>Identificador do andamento.</p>"
          },
          {
            "group": "Sucesso - 201",
            "type": "String",
            "optional": false,
            "field": "andamento.idTarefa",
            "description": "<p>Identificador da tarefa.</p>"
          },
          {
            "group": "Sucesso - 201",
            "type": "String",
            "optional": false,
            "field": "andamento.descricao",
            "description": "<p>Descrição do andamento.</p>"
          },
          {
            "group": "Sucesso - 201",
            "type": "String",
            "optional": false,
            "field": "andamento.dataHora",
            "description": "<p>Data e hora do andamento.</p>"
          },
          {
            "group": "Sucesso - 201",
            "type": "Unidade",
            "optional": false,
            "field": "andamento.unidade",
            "description": "<p>Unidade onde o andamento ocorreu.</p>"
          },
          {
            "group": "Sucesso - 201",
            "type": "String",
            "optional": false,
            "field": "andamento.unidade.idUnidade",
            "description": "<p>Identificador da unidade.</p>"
          },
          {
            "group": "Sucesso - 201",
            "type": "String",
            "optional": false,
            "field": "andamento.unidade.sigla",
            "description": "<p>Sigla da unidade.</p>"
          },
          {
            "group": "Sucesso - 201",
            "type": "String",
            "optional": false,
            "field": "andamento.unidade.descricao",
            "description": "<p>Descrição da unidade.</p>"
          },
          {
            "group": "Sucesso - 201",
            "type": "Usuario",
            "optional": false,
            "field": "andamento.usuario",
            "description": "<p>Usuário responsável pelo andamento.</p>"
          },
          {
            "group": "Sucesso - 201",
            "type": "String",
            "optional": false,
            "field": "andamento.usuario.idUsuario",
            "description": "<p>Identificador do usuário.</p>"
          },
          {
            "group": "Sucesso - 201",
            "type": "String",
            "optional": false,
            "field": "andamento.usuario.sigla",
            "description": "<p>Login do usuário.</p>"
          },
          {
            "group": "Sucesso - 201",
            "type": "String",
            "optional": false,
            "field": "andamento.usuario.nome",
            "description": "<p>Nome do usuário.</p>"
          },
          {
            "group": "Sucesso - 201",
            "type": "AtributoAndamento[]",
            "optional": false,
            "field": "andamento.atributos",
            "description": "<p>Lista com os atributos relacionados ao andamento.</p>"
          },
          {
            "group": "Sucesso - 201",
            "type": "String",
            "optional": false,
            "field": "andamento.atributos.nome",
            "description": "<p>Nome do atributo.</p>"
          },
          {
            "group": "Sucesso - 201",
            "type": "String",
            "optional": false,
            "field": "andamento.atributos.valor",
            "description": "<p>Valor do atributo.</p>"
          },
          {
            "group": "Sucesso - 201",
            "type": "String",
            "optional": false,
            "field": "andamento.atributos.idOrigem",
            "description": "<p>Identificador de origem do atributo.</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/ProcessoResource.java",
    "groupTitle": "Processo"
  },
  {
    "type": "get",
    "url": "/:unidade/processos/:processo/andamentos",
    "title": "Listar andamentos",
    "name": "listarAndamentos",
    "group": "Processo",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Lista as andamentos do processo.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI.</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "processo",
            "description": "<p>Número do processo.</p>"
          }
        ],
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "String",
            "allowedValues": [
              "\"S (sim), N (não)\""
            ],
            "optional": true,
            "field": "exibir-atributos",
            "defaultValue": "N",
            "description": "<p>Sinalizador para retorno dos atributos associados.</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String[]",
            "optional": true,
            "field": "andamento",
            "description": "<p>Filtra andamentos pelos identificadores informados.</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String[]",
            "optional": true,
            "field": "tarefa",
            "defaultValue": "1,48,65",
            "description": "<p>Filtra andamentos pelos identificadores de tarefas informados (consultar lista de tarefas).</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String[]",
            "optional": true,
            "field": "tarefa-modulo",
            "description": "<p>Filtra andamentos pelos identificadores de tarefas de módulo informados.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:",
        "content": "curl -i https://<host>/sei-broker/service/cosap/processos/33910003114201754/andamentos",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "Andamento[]",
            "optional": false,
            "field": "andamentos",
            "description": "<p>Lista dos andamentos do processo.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "andamentos.idAndamento",
            "description": "<p>Identificador do andamento.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "andamentos.idTarefa",
            "description": "<p>Identificador da tarefa.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "andamentos.descricao",
            "description": "<p>Descrição do andamento.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "andamentos.dataHora",
            "description": "<p>Data e hora do andamento.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Unidade",
            "optional": false,
            "field": "andamentos.unidade",
            "description": "<p>Unidade onde o andamento ocorreu.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "andamentos.unidade.idUnidade",
            "description": "<p>Identificador da unidade.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "andamentos.unidade.sigla",
            "description": "<p>Sigla da unidade.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "andamentos.unidade.descricao",
            "description": "<p>Descrição da unidade.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Usuario",
            "optional": false,
            "field": "andamentos.usuario",
            "description": "<p>Usuário responsável pelo andamento.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "andamentos.usuario.idUsuario",
            "description": "<p>Identificador do usuário.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "andamentos.usuario.sigla",
            "description": "<p>Login do usuário.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "andamentos.usuario.nome",
            "description": "<p>Nome do usuário.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "AtributoAndamento[]",
            "optional": false,
            "field": "andamentos.atributos",
            "description": "<p>Lista com os atributos relacionados ao andamento.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "andamentos.atributos.nome",
            "description": "<p>Nome do atributo.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "andamentos.atributos.valor",
            "description": "<p>Valor do atributo.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "andamentos.atributos.idOrigem",
            "description": "<p>Identificador de origem do atributo.</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/ProcessoResource.java",
    "groupTitle": "Processo"
  },
  {
    "type": "get",
    "url": "/processos/:processo/documentos",
    "title": "Listar documentos",
    "name": "listarDocumentosPorProcesso",
    "group": "Processo",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Retorna os documentos de um determinado processo.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "processo",
            "description": "<p>Número do processo.</p>"
          }
        ],
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "tipo",
            "defaultValue": "null",
            "description": "<p>Identificador do tipo do documento, caso seja necessário filtrar pelo tipo</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "allowedValues": [
              "\"G (gerado/interno), R (recebido/externo)\""
            ],
            "optional": true,
            "field": "origem",
            "defaultValue": "null",
            "description": "<p>Filtra os documentos por gerados ou recebidos</p>"
          },
          {
            "group": "Query Parameters",
            "type": "boolean",
            "optional": true,
            "field": "somenteAssinados",
            "defaultValue": "false",
            "description": "<p>Exibir somente documentos assinados</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "pagina",
            "defaultValue": "1",
            "description": "<p>Número da página</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "qtdRegistros",
            "defaultValue": "50",
            "description": "<p>Quantidade de registros que serão exibidos por página</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -i https://<host>/sei-broker/service/processos/33910003149201793/documentos",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso Response Body - 200": [
          {
            "group": "Sucesso Response Body - 200",
            "type": "List",
            "optional": false,
            "field": "documentos",
            "description": "<p>Lista com os documentos encontrados.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "DocumentoResumido",
            "optional": false,
            "field": "documentos.documentoResumido",
            "description": "<p>Resumo do documento encontrado no SEI.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "documentos.documentoResumido.numero",
            "description": "<p>Número do documento.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "documentos.documentoResumido.numeroInformado",
            "description": "<p>Número informado na inclusão do documento, também conhecido como número de árvore.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "allowedValues": [
              "\"GERADO\"",
              "\"RECEBIDO\""
            ],
            "optional": false,
            "field": "documentos.documentoResumido.origem",
            "description": "<p>Origem do documento, se o mesmo é um documento &quot;GERADO&quot; internamente ou &quot;RECEBIDO&quot; de uma fonte externa.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "Data",
            "optional": false,
            "field": "documentos.documentoResumido.dataGeracao",
            "description": "<p>Data de geração do documento.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "Tipo",
            "optional": false,
            "field": "documentos.documentoResumido.tipo",
            "description": "<p>Objeto representando o tipo do documento.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "documentos.documentoResumido.tipo.codigo",
            "description": "<p>Identificados do tipo do documento, também conhecido como série.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "documentos.documentoResumido.tipo.nome",
            "description": "<p>Nome do tipo do documento.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "String",
            "optional": false,
            "field": "documentos.documentoResumido.tipoConferencia",
            "description": "<p>Tipo de conferência do documento.</p>"
          },
          {
            "group": "Sucesso Response Body - 200",
            "type": "boolean",
            "optional": false,
            "field": "documentos.documentoResumido.assinado",
            "description": "<p>Boolean indicando se o documento foi assinado.</p>"
          }
        ],
        "Sucesso Response Header- 200": [
          {
            "group": "Sucesso Response Header- 200",
            "type": "header",
            "optional": false,
            "field": "total_registros",
            "description": "<p>Quantidade de registros que existem para essa consulta</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n  \"numero\": \"0670949\",\n  \"numeroInformado\": \"594\",\n  \"origem\": \"RECEBIDO\",\n  \"dataGeracao\": \"2015-08-10T00:00:00-03:00\",\n  \"tipo\": {\n  \t\"codigo\": \"629\",\n  \t\"nome\": \"Relatório de Arquivamento-SIF\"\n  }\n  \"tipoConferencia\": \"4\",\n  \"assinado\": true\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/ProcessoResource.java",
    "groupTitle": "Processo"
  },
  {
    "type": "get",
    "url": "/:unidade/processos/tipos",
    "title": "Tipos de processo",
    "name": "listarTiposProcesso",
    "group": "Processo",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Consulta os tipos de processo.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          }
        ],
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "serie",
            "description": "<p>Tipo do documento cadastrado no serviço</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -i https://<host>/sei-broker/service/COSAP/processos/tipos",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "Boolean",
            "optional": false,
            "field": "resultado",
            "description": "<p>Booleano informando sucesso da requisição</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/ProcessoResource.java",
    "groupTitle": "Processo"
  },
  {
    "type": "delete",
    "url": "/:unidade/processos/concluidos/:processo",
    "title": "Reabrir processo",
    "name": "reabrirProcesso",
    "group": "Processo",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Reabre um processo.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "processo",
            "description": "<p>Numero do processo a ser reaberto</p>"
          }
        ],
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "String",
            "allowedValues": [
              "\"S (sim), N (não)\""
            ],
            "optional": true,
            "field": "auto-formatacao",
            "defaultValue": "S",
            "description": "<p>O broker utilizará a mascara padrão para formatar o número do processo</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -X DELETE https://<host>/sei-broker/service/COSAP/processos/concluidos/33910000029201653",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "Boolean",
            "optional": false,
            "field": "resultado",
            "description": "<p>Booleano informando sucesso da requisição</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/ProcessoResource.java",
    "groupTitle": "Processo"
  },
  {
    "type": "post",
    "url": "/:unidade/processos/:processo/relacionados",
    "title": "Relacionar processo",
    "name": "relacionarProcesso",
    "group": "Processo",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Relacionar processos.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI.</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "processo",
            "description": "<p>Número do processo.</p>"
          }
        ],
        "Request Body": [
          {
            "group": "Request Body",
            "type": "ProcessoRelacionado",
            "optional": false,
            "field": "processoRelacionado",
            "description": "<p>Objeto com o número do processo a ser relacionado.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "processoRelacionado.numero",
            "description": "<p>Número do processo a ser relacionado.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: [POST] https://<host>/sei-broker/service/COSAP/processos/33910003093201777/relacionados\n\nbody:\n{\n\t\"numero\":\"33910000086201632\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/ProcessoResource.java",
    "groupTitle": "Processo"
  },
  {
    "type": "delete",
    "url": "/:unidade/processos/sobrestados/:processo",
    "title": "Remover sobrestamento",
    "name": "removerSobrestamentoProcesso",
    "group": "Processo",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Remover sobrestamento de processo.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI.</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "processo",
            "description": "<p>Número do processo.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: [DELETE] https://<host>/sei-broker/service/COSAP/processos/sobrestados/33910003093201777",
        "type": "json"
      }
    ],
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/ProcessoResource.java",
    "groupTitle": "Processo"
  },
  {
    "type": "post",
    "url": "/:unidade/processos/sobrestados",
    "title": "Sobrestar processo",
    "name": "sobrestarProcesso",
    "group": "Processo",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Sobrestar processo.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI.</p>"
          }
        ],
        "Request Body": [
          {
            "group": "Request Body",
            "type": "SobrestamentoProcesso",
            "optional": false,
            "field": "sobrestamento",
            "description": "<p>Objeto com o motivo do sobrestamento.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "sobrestamento.processo",
            "description": "<p>Número do processo a ser sobrestado.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "sobrestamento.motivo",
            "description": "<p>Motivo do sobrestamento.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "sobrestamento.processoVinculado",
            "description": "<p>Número do processo vinculado.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: [POST] https://<host>/sei-broker/service/COSAP/processos/sobrestados\n\nbody:\n{\n\t\"processo\":\"33910003093201777\",\n\t\"motivo\":\"Sobrestando através da camada de serviços.\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/ProcessoResource.java",
    "groupTitle": "Processo"
  },
  {
    "type": "delete",
    "url": "/sipar/importados/:processo",
    "title": "Cancelar Importação Processo",
    "name": "cancelarImportacaoProcesso",
    "group": "SIPAR",
    "version": "2.0.0",
    "description": "<p>Desmarca um processo físico (SIPAR) como importado para um processo eletrônico (SEI).</p>",
    "parameter": {
      "fields": {
        "Path Parameter": [
          {
            "group": "Path Parameter",
            "type": "String",
            "optional": false,
            "field": "processo",
            "description": "<p>Número do processo físico existente no SIPAR contendo 17 dígitos e iniciado com 33902. Ex. 33902111111111111</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:",
        "content": "curl -X DELETE http://<host>/sei-broker/service/sipar/importados/33902112492200241",
        "type": "curl"
      }
    ],
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 204 No Content",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\t\"error\":\"Mensagem de erro.\"\n\t\t\"code\":\"código do erro\"\n}",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n\t\t\"error\":\"Mensagem de erro.\"\n\t\t\"code\":\"código do erro\"\n}",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 404 Not Found\n{\n\t\t\"error\":\"Mensagem de erro.\"\n\t\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/SiparResource.java",
    "groupTitle": "SIPAR"
  },
  {
    "type": "post",
    "url": "/sipar/importados",
    "title": "Importar Processo",
    "name": "importarProcesso",
    "group": "SIPAR",
    "version": "2.0.0",
    "description": "<p>Marca um processo físico (SIPAR) como importado para um processo eletrônico (SEI).</p>",
    "parameter": {
      "fields": {
        "Header Parameters": [
          {
            "group": "Header Parameters",
            "type": "String",
            "optional": false,
            "field": "content-type",
            "description": "<p>Informar text/plain</p>"
          }
        ],
        "Request Body": [
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "processo",
            "description": "<p>Número do processo a ser importado</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: [POST] http://<host>/sei-broker/service/sipar/importados\n\nbody:\n33902112492200241",
        "type": "json"
      }
    ],
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 201 Created",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\t\"error\":\"Mensagem de erro.\"\n\t\t\"code\":\"código do erro\"\n}",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n\t\t\"error\":\"Mensagem de erro.\"\n\t\t\"code\":\"código do erro\"\n}",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict\n{\n\t\t\"error\":\"Mensagem de erro.\"\n\t\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/SiparResource.java",
    "groupTitle": "SIPAR"
  },
  {
    "type": "get",
    "url": "/:unidade/series",
    "title": "Listar séries",
    "name": "listarSeries",
    "group": "Serie",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Este método realiza uma consulta às séries.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI.</p>"
          }
        ],
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "filtro",
            "description": "<p>Para filtrar por series que contenham o trecho no nome.</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "tipo-processo",
            "defaultValue": "null",
            "description": "<p>Para filtrar por determinado tipo de processo.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:",
        "content": "curl -i https://<host>/sei-broker/service/COSAP/series",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "Serie[]",
            "optional": false,
            "field": "series",
            "description": "<p>Lista de séries.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "series.idSerie",
            "description": "<p>Identificador do tipo de documento</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "series.nome",
            "description": "<p>Nome do tipo de documento</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "series.aplicabilidade",
            "description": "<p>T = Documentos internos e externos, I = documentos internos, E = documentos externos e F = formulários</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/SeriesResource.java",
    "groupTitle": "Serie"
  },
  {
    "type": "get",
    "url": "/tarefas",
    "title": "Listar tarefas",
    "name": "listarTarefas",
    "group": "Tarefa",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Lista os tipos de tarefas existentes no SEI.</p>",
    "parameter": {
      "fields": {
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "nome",
            "description": "<p>Filtro para o nome da tarefa.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:",
        "content": "curl -i https://<host>/sei-broker/service/tarefas",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "List",
            "optional": false,
            "field": "tarefas",
            "description": "<p>Lista com as tarefas</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "tarefas.identificados",
            "description": "<p>Identificador da tarefa.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "tarefas.nome",
            "description": "<p>Nome da tarefa.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "tarefas.historicoResumido",
            "description": "<p>S/N - Sinalizador indica se a tarefa aparecerá no histórico resumido.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "tarefas.historicoCompleto",
            "description": "<p>S/N - Sinalizador indica se a tarefa aparecerá no histórico completo.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "tarefas.fecharAndamentosAbertos",
            "description": "<p>S/N - Sinalizador indica se a tarefa fecha andamentos abertos.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "tarefas.lancarAndamentoFechado",
            "description": "<p>S/N - Sinalizador indica que a tarefa encerra o andamento.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "tarefas.permiteProcessoFechado",
            "description": "<p>S/N - Sinalizador indica se é permitida essa tarefa em processo fechado.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "tarefas.identicadorTarefaModulo",
            "description": "<p>Identificador de tarefa módulo.</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/TarefaResource.java",
    "groupTitle": "Tarefa"
  },
  {
    "type": "get",
    "url": "/:unidade/tipos-conferencia",
    "title": "Listar tipos de conferência",
    "name": "listarTiposConferencia",
    "group": "Tipos_Confer_ncia",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Lista os tipos de conferência.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:",
        "content": "curl -i https://<host>/sei-broker/service/cosap/tipos-conferencia",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "TipoConferencia[]",
            "optional": false,
            "field": "tipos",
            "description": "<p>Lista de tipos de conferência.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "tipos.idTipoConferencia",
            "description": "<p>Identificador do tipo de conferência.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "tipos.descricao",
            "description": "<p>Descrição do tipo de conferência.</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/TipoConferenciaResource.java",
    "groupTitle": "Tipos_Confer_ncia"
  },
  {
    "type": "get",
    "url": "/unidades/{unidade}/codigo",
    "title": "Consultar código",
    "name": "consultarCodigo",
    "group": "Unidade",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Retorna o código da Unidade pesquisada.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade que deseja consultar o código</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:",
        "content": "curl -i https://<host>/sei-broker/service/unidades/COSAP/codigo",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "codigo",
            "description": "<p>Código da unidade.</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/UnidadeResource.java",
    "groupTitle": "Unidade"
  },
  {
    "type": "get",
    "url": "/unidades",
    "title": "Listar unidades",
    "name": "listarUnidades",
    "group": "Unidade",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Retorna as Unidades cadastradas no SEI.</p>",
    "examples": [
      {
        "title": "Exemplo de requisição:",
        "content": "curl -i https://<host>/sei-broker/service/unidades/",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "Unidade[]",
            "optional": false,
            "field": "unidades",
            "description": "<p>Lista de unidades</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "unidades.idUnidade",
            "description": "<p>Identificador da unidade</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "unidades.sigla",
            "description": "<p>Sigla da unidade</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "unidades.descricao",
            "description": "<p>Descrição da unidade</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/UnidadeResource.java",
    "groupTitle": "Unidade"
  },
  {
    "type": "post",
    "url": "/usuarios/ativos",
    "title": "Ativar usuário",
    "name": "ativarUsuario",
    "group": "Usuario",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER_ADM"
      }
    ],
    "description": "<p>Este método reativa usuários.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "codigo",
            "description": "<p>Código que deseja atribuir ao usuário</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "nome",
            "description": "<p>Nome do usuário</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "login",
            "description": "<p>Login que será atribuído ao usuário</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: http://<host>/sei-broker/service/usuarios/ativos\n\nbody:\n{\n\t\"codigo\":\"1234\",\n\t\"nome\":\"André Luís Fernandes Guimarães\",\n\t\"login\":\"andre.guimaraes\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Boolean",
            "optional": false,
            "field": "resultado",
            "description": "<p>Booleano informando sucesso da requisição</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/UsuarioResource.java",
    "groupTitle": "Usuario"
  },
  {
    "type": "post",
    "url": "/:unidade/:usuario/processos",
    "title": "Atribuir processo",
    "name": "atribuirProcesso",
    "group": "Usuario",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER"
      }
    ],
    "description": "<p>Este método atribui o processo a um usuário.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "usuario",
            "description": "<p>Login do usuário a quem deseja atribuir o processo</p>"
          }
        ],
        "Request Body": [
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "processo",
            "description": "<p>Numero do processo a ser atribuído</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": true,
            "field": "reabrir-processo",
            "defaultValue": "N",
            "description": "<p>S ou N para reabrir o processo</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: [POST] http://<host>/sei-broker/service/COSAP/usuarios/andre.guimaraes/processos\n\nbody:\n{\n\t\"processo\":\"33910000029201653\",\n\t\"reabrir\":false\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Boolean",
            "optional": false,
            "field": "resultado",
            "description": "<p>Booleano informando sucesso da requisição</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/UsuarioResource.java",
    "groupTitle": "Usuario"
  },
  {
    "type": "get",
    "url": ":unidade/usuarios/:usuario",
    "title": "Buscar usuário",
    "name": "buscarUsuario",
    "group": "Usuario",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Este método realiza a uma busca pelo login do usuário.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI.</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "usuario",
            "description": "<p>Login do usuário</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -i http://<host>/sei-broker/service/cosap/usuarios/andre.guimaraes",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Usuario",
            "optional": false,
            "field": "usuario",
            "description": "<p>Informações do usuário encontrado.</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/UsuarioResource.java",
    "groupTitle": "Usuario"
  },
  {
    "type": "delete",
    "url": "/usuarios/ativos",
    "title": "Desativar usuário",
    "name": "desativarUsuario",
    "group": "Usuario",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER_ADM"
      }
    ],
    "description": "<p>Este método desativa usuários.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "codigo",
            "description": "<p>Código que deseja atribuir ao usuário</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "nome",
            "description": "<p>Nome do usuário</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "login",
            "description": "<p>Login que será atribuído ao usuário</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: [DELETE] http://<host>/sei-broker/service/usuarios/ativos/andre.guimaraes\n\nbody:\n{\n\t\"codigo\":\"1234\",\n\t\"nome\":\"André Luís Fernandes Guimarães\",\n\t\"login\":\"andre.guimaraes\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Boolean",
            "optional": false,
            "field": "resultado",
            "description": "<p>Booleano informando sucesso da requisição</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/UsuarioResource.java",
    "groupTitle": "Usuario"
  },
  {
    "type": "delete",
    "url": "/usuarios/:login",
    "title": "Excluir usuário",
    "name": "excluirUsuario",
    "group": "Usuario",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER_ADM"
      }
    ],
    "description": "<p>Este método realiza a exclusão de usuários.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "codigo",
            "description": "<p>Código que deseja atribuir ao usuário</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "nome",
            "description": "<p>Nome do usuário</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "login",
            "description": "<p>Login que será atribuído ao usuário</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: [DELETE] http://<host>/sei-broker/service/usuarios/andre.guimaraes\n\nbody:\n{\n\t\"codigo\":\"1234\",\n\t\"nome\":\"André Luís Fernandes Guimarães\",\n\t\"login\":\"andre.guimaraes\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Boolean",
            "optional": false,
            "field": "resultado",
            "description": "<p>Booleano informando sucesso da requisição</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/UsuarioResource.java",
    "groupTitle": "Usuario"
  },
  {
    "type": "post",
    "url": "/usuarios",
    "title": "Incluir usuário",
    "name": "incluirUsuario",
    "group": "Usuario",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER_ADM"
      }
    ],
    "description": "<p>Este método realiza a inclusão de novos usuários ou alterarações nos usuários existentes.</p>",
    "parameter": {
      "fields": {
        "Request Body": [
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "codigo",
            "description": "<p>Código que deseja atribuir ao usuário</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "nome",
            "description": "<p>Nome do usuário</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "login",
            "description": "<p>Login que será atribuído ao usuário</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: http://<host>/sei-broker/service/usuarios\n\nbody:\n{\n\t\"codigo\":\"1234\",\n\t\"nome\":\"André Luís Fernandes Guimarães\",\n\t\"login\":\"andre.guimaraes\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Boolean",
            "optional": false,
            "field": "resultado",
            "description": "<p>Booleano informando sucesso da requisição</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/UsuarioResource.java",
    "groupTitle": "Usuario"
  },
  {
    "type": "get",
    "url": "/:unidade/usuarios",
    "title": "Listar usuários",
    "name": "listarUsuarios",
    "group": "Usuario",
    "version": "2.0.0",
    "permission": [
      {
        "name": "RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA"
      }
    ],
    "description": "<p>Este método realiza uma consulta aos usuários cadastrados que possuem o perfil &quot;Básico&quot;.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI.</p>"
          }
        ],
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "usuario",
            "defaultValue": "null",
            "description": "<p>Id do usuário que deseja recuperar as informações</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:",
        "content": "curl -i http://<host>/sei-broker/service/usuarios/COSAP",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Usuario[]",
            "optional": false,
            "field": "usuarios",
            "description": "<p>Lista de usuários</p>"
          }
        ]
      }
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "sei-broker/src/main/java/br/gov/ans/integracao/sei/rest/UsuarioResource.java",
    "groupTitle": "Usuario"
  }
] });
