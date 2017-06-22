define({ "api": [
  {
    "type": "delete",
    "url": "/:unidade/blocos/disponibilizados/12",
    "title": "Indisponibilizar bloco",
    "name": "cancelarDisponibilizacaoBloco",
    "group": "Bloco",
    "version": "0.0.1",
    "description": "<p>Este método cancela a disponibilização de blocos.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Parameter",
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
        "content": "curl -X DELETE http://anshmjboss01/sei-broker/service/COSAP/blocos/disponibilizados/12",
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/BlocoResource.java",
    "groupTitle": "Bloco"
  },
  {
    "type": "get",
    "url": "/:unidade/blocos/:bloco",
    "title": "Consultar bloco",
    "name": "consultarBloco",
    "group": "Bloco",
    "version": "0.0.1",
    "description": "<p>Este método recupera as informações do bloco informado.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "bloco",
            "description": "<p>Numero do bloco que deseja consultar</p>"
          },
          {
            "group": "Parameter",
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
        "content": "curl -i http://anshmjboss01/sei-broker/service/COSAP/blocos/12",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "RetornoConsultaBloco",
            "optional": false,
            "field": "Retorno",
            "description": "<p>json ou xml com informações do bloco</p>"
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/BlocoResource.java",
    "groupTitle": "Bloco"
  },
  {
    "type": "post",
    "url": "/:unidade/blocos/disponibilizados",
    "title": "Disponibilizar bloco",
    "name": "disponibilizarBloco",
    "group": "Bloco",
    "version": "0.0.1",
    "description": "<p>Este método disponibiliza blocos.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Parameter",
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
        "content": "endpoint: [POST] http://anshmjboss01/sei-broker/service/COSAP/blocos/disponibilizados\n\nbody:\n12",
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/BlocoResource.java",
    "groupTitle": "Bloco"
  },
  {
    "type": "delete",
    "url": "/:unidade/blocos/:bloco",
    "title": "Excluir bloco",
    "name": "excluirBloco",
    "group": "Bloco",
    "version": "0.0.1",
    "description": "<p>Este método exclui um bloco criado.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Parameter",
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
        "content": "curl -X DELETE http://anshmjboss01/sei-broker/service/COSAP/blocos/12",
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/BlocoResource.java",
    "groupTitle": "Bloco"
  },
  {
    "type": "post",
    "url": "/:unidade/blocos",
    "title": "Gerar bloco",
    "name": "gerarBloco",
    "group": "Bloco",
    "version": "0.0.1",
    "description": "<p>Este método gera um novo bloco.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "allowedValues": [
              "\"ASSINATURA\"",
              "\"INTERNO\"",
              "\"REUNIAO\""
            ],
            "optional": false,
            "field": "tipo",
            "description": "<p>Tipo do bloco a ser criado</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "descricao",
            "description": "<p>Descrição do bloco</p>"
          },
          {
            "group": "Parameter",
            "type": "String[]",
            "optional": false,
            "field": "unidades",
            "description": "<p>Códigos das unidades onde o bloco deve ser disponibilizado, ou vazio para não disponibilizar</p>"
          },
          {
            "group": "Parameter",
            "type": "String[]",
            "optional": false,
            "field": "documentos",
            "description": "<p>Código dos documentos que serão incluídos no bloco</p>"
          },
          {
            "group": "Parameter",
            "type": "Boolean",
            "optional": false,
            "field": "disponibilizar",
            "defaultValue": "false",
            "description": "<p>Informa se o bloco criado deve ser disponibilizado</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: http://anshmjboss01/sei-broker/service/COSAP/blocos\n\nbody:\n{\n\t\"tipo\":\"ASSINATURA\",\n\t\"descricao\":\"Bloco demonstrativo.\",\n\t\"unidades\":[\"110000935\"],\n\t\"documentos\":[\"0000131\"],\n\t\"disponibilizar\":true\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultado",
            "description": "<p>Código do bloco criado</p>"
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/BlocoResource.java",
    "groupTitle": "Bloco"
  },
  {
    "type": "POST",
    "url": "/:unidade/blocos/:bloco/documentos",
    "title": "Incluir documento",
    "name": "incluirDocumentoNoBloco",
    "group": "Bloco",
    "version": "0.0.1",
    "description": "<p>Este método inclui um documento no bloco.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "documento",
            "description": "<p>Numero do documento que será incluído do bloco</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "bloco",
            "description": "<p>Numero do bloco onde o documento será incluído</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: [POST] http://anshmjboss01/sei-broker/service/COSAP/blocos/12/documentos\n\nbody:\n0000050",
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/BlocoResource.java",
    "groupTitle": "Bloco"
  },
  {
    "type": "post",
    "url": "/:unidade/blocos/:bloco/processos",
    "title": "Incluir processo",
    "name": "incluirProcessoNoBloco",
    "group": "Bloco",
    "version": "0.0.1",
    "description": "<p>Este método inclui um processo no bloco.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "processo",
            "description": "<p>Numero do processo que será incluído no bloco</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "bloco",
            "description": "<p>Numero do bloco onde o processo será incluído</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -X PUT http://anshmjboss01/sei-broker/service/COSAP/blocos/12/processos",
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/BlocoResource.java",
    "groupTitle": "Bloco"
  },
  {
    "type": "delete",
    "url": "/:unidade/:bloco/documentos/:documento",
    "title": "Remover documento",
    "name": "retirarDocumentoDoBloco",
    "group": "Bloco",
    "version": "0.0.1",
    "description": "<p>Este método remove o documento do bloco.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "documento",
            "description": "<p>Numero do documento que será retirado do bloco</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "bloco",
            "description": "<p>Numero do bloco de onde o documento será retirado</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -X DELETE http://anshmjboss01/sei-broker/service/COSAP/blocos/12/documentos/0000050",
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/BlocoResource.java",
    "groupTitle": "Bloco"
  },
  {
    "type": "delete",
    "url": "/:unidade/blocos/:bloco/processos/:processo",
    "title": "Remover processo",
    "name": "retirarProcessoDoBloco",
    "group": "Bloco",
    "version": "0.0.1",
    "description": "<p>Este método remove o processo do bloco.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "processo",
            "description": "<p>Numero do processo que será retirado do bloco</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "bloco",
            "description": "<p>Numero do bloco de onde o processo será retirado</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -X DELETE http://anshmjboss01/sei-broker/service/COSAP/blocos/12/processos/33910000029201653",
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/BlocoResource.java",
    "groupTitle": "Bloco"
  },
  {
    "type": "post",
    "url": "/:unidade/documentos/cancelados",
    "title": "Cancelar documento",
    "name": "cancelarDocumento",
    "group": "Documento",
    "version": "0.0.1",
    "description": "<p>Cancela um documento.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "documento",
            "description": "<p>Numero do documento que será cancelado</p>"
          },
          {
            "group": "Parameter",
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
        "content": "endpoint: http://anshmjboss01/sei-broker/service/COSAP/documentos/cancelados\n\nbody:\n{\n\t\"documento\":\"0000050\",\n\t\"motivo\":\"Motivo do cancelamento.\"\n}",
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/DocumentoResource.java",
    "groupTitle": "Documento"
  },
  {
    "type": "get",
    "url": "/:unidade/documentos/:documento",
    "title": "Consultar documento",
    "name": "consultarDocumento",
    "group": "Documento",
    "version": "0.0.1",
    "description": "<p>Este método realiza uma consulta aos documentos cadastrados no SEI.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "documento",
            "description": "<p>Id do documento que deseja recuperar as informações</p>"
          },
          {
            "group": "Parameter",
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
            "group": "Parameter",
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
            "group": "Parameter",
            "type": "String",
            "allowedValues": [
              "\"S (sim), N (não)\""
            ],
            "optional": true,
            "field": "publicacao",
            "defaultValue": "N",
            "description": "<p>exibir detalhes da publicação</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:",
        "content": "curl -i http://anshmjboss01/sei-broker/service/COSAP/documentos/0000050?assinaturas=S&andamento=S",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "RetornoConsultaDocumento",
            "optional": false,
            "field": "retornoConsultaDocumento",
            "description": "<p>Informações do documento encontrado no SEI</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.idProcedimento",
            "description": "<p>Id interno do processo no SEI</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.procedimentoFormatado",
            "description": "<p>Número do processo visível para o usuário</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.idDocumento",
            "description": "<p>Id interno do documento no SEI</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.documentoFormatado",
            "description": "<p>Número do documento visível para o usuário</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.linkAcesso",
            "description": "<p>Link para acesso ao documento</p>"
          },
          {
            "group": "Success 200",
            "type": "Serie",
            "optional": false,
            "field": "retornoConsultaDocumento.serie",
            "description": "<p>Dados do tipo do documento</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.serie.idSerie",
            "description": "<p>Identificador do tipo de documento</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.serie.nome",
            "description": "<p>Nome do tipo de documento</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.numero",
            "description": "<p>Número do documento</p>"
          },
          {
            "group": "Success 200",
            "type": "Data",
            "optional": false,
            "field": "retornoConsultaDocumento.data",
            "description": "<p>Data de geração para documentos internos e para documentos externos é a data informada na tela de cadastro</p>"
          },
          {
            "group": "Success 200",
            "type": "Unidade",
            "optional": false,
            "field": "retornoConsultaDocumento.unidadeElaboradora",
            "description": "<p>Dados da unidade que gerou o documento</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.unidadeElaboradora.descricao",
            "description": "<p>Nome da unidade</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.unidadeElaboradora.idUnidade",
            "description": "<p>Código da unidade</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.unidadeElaboradora.unidade.sigla",
            "description": "<p>Sigla da unidade</p>"
          },
          {
            "group": "Success 200",
            "type": "Andamento",
            "optional": false,
            "field": "retornoConsultaDocumento.andamentoGeracao",
            "description": "<p>Informações do andamento de geração (opcional)</p>"
          },
          {
            "group": "Success 200",
            "type": "Data",
            "optional": false,
            "field": "retornoConsultaDocumento.andamentoGeracao.dataHora",
            "description": "<p>Data e hora do registro de andamento</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.andamentoGeracao.descricao",
            "description": "<p>Descrição do andamento</p>"
          },
          {
            "group": "Success 200",
            "type": "Unidade",
            "optional": false,
            "field": "retornoConsultaDocumento.andamentoGeracao.unidade",
            "description": "<p>Unidade responsável pelo andamento</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.andamentoGeracao.unidade.descricao",
            "description": "<p>Nome da unidade</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.andamentoGeracao.unidade.idUnidade",
            "description": "<p>Código da unidade</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.andamentoGeracao.unidade.sigla",
            "description": "<p>Sigla da unidade</p>"
          },
          {
            "group": "Success 200",
            "type": "Usuario",
            "optional": false,
            "field": "retornoConsultaDocumento.andamentoGeracao.usuario",
            "description": "<p>Usuário responsável pela ação</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.andamentoGeracao.usuario.idUsuario",
            "description": "<p>Código do usuário</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.andamentoGeracao.usuario.nome",
            "description": "<p>Nome do usuário</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.andamentoGeracao.usuario.sigla",
            "description": "<p>Login do usuário</p>"
          },
          {
            "group": "Success 200",
            "type": "Assinatura",
            "optional": false,
            "field": "retornoConsultaDocumento.assinaturas",
            "description": "<p>Conjunto de assinaturas do documento</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.assinaturas.cargoFuncao",
            "description": "<p>Cargo do responsável pela assinatura</p>"
          },
          {
            "group": "Success 200",
            "type": "Data",
            "optional": false,
            "field": "retornoConsultaDocumento.assinaturas.dataHora",
            "description": "<p>Data e hora da assinatura</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "retornoConsultaDocumento.assinaturas.nome",
            "description": "<p>Nome do assinante</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n\t\"idProcedimento\":\"52\",\n\t\"procedimentoFormatado\":\"16.0.000000005-5\",\n\t\"idDocumento\":\"152\",\n\t\"documentoFormatado\":\"0000123\",\n\t\"linkAcesso\":\"https://sei-hm.ans.gov.br/controlador.php?acao=procedimento_trabalhar&id_procedimento=52&id_documento=152\",\n\t\"serie\":{\n\t\t\"idSerie\":\"12\",\n\t\t\"nome\":\"Memorando\"\n\t},\n\t\"numero\":\"6\",\n\t\"data\":\"05/04/2016\",\n\t\"unidadeElaboradora\":{\n\t\t\"idUnidade\":\"110000934\",\n\t\t\"sigla\":\"COSAP\",\n\t\t\"descricao\":\"Coordenadoria de Sistemas e Aplicativos\"\n\t},\n\t\"andamentoGeracao\":null,\n\t\"assinaturas\":[],\n\t\"publicacao\":null  \t\n}",
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/DocumentoResource.java",
    "groupTitle": "Documento"
  },
  {
    "type": "get",
    "url": "/:unidade/documentos/",
    "title": "Listar documentos",
    "name": "consultarDocumentosIncluidosBroker",
    "group": "Documento",
    "version": "0.0.1",
    "description": "<p>Retorna informações dos documentos inclusos pelo SEI-Broker.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "pagina",
            "description": "<p>Número da página</p>"
          },
          {
            "group": "Parameter",
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
        "content": "curl -i http://anshmjboss01/sei-broker/service/COSAP/documentos?pagina=1&qtdRegsitros=30",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "InclusaoDocumento",
            "optional": false,
            "field": "resultado",
            "description": "<p>Objeto com dados sobre o documento</p>"
          },
          {
            "group": "Success 200",
            "type": "Date",
            "optional": false,
            "field": "resultado.data",
            "description": "<p>Data do envio (padrão ISO-8601)</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultado.hash",
            "description": "<p>Hash SHA-256 gerado a partir do base64 enviado ao Broker</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "resultado.id",
            "description": "<p>Identificação da inclusão de documento</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultado.nome",
            "description": "<p>Nome do documento incluído</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultado.numero",
            "description": "<p>Número retonado pelo SEI, NULL caso tenha ocorrido algum problema.</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultado.processo",
            "description": "<p>Número do processo</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultado.sistema",
            "description": "<p>Sistema responsável pela inclusão</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultado.unidade",
            "description": "<p>Unidade onde foi incluído o documento</p>"
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/DocumentoResource.java",
    "groupTitle": "Documento"
  },
  {
    "type": "get",
    "url": "/:unidade/documentos/:documento/pdf",
    "title": "Exportar documento",
    "name": "exportarDocumento",
    "group": "Documento",
    "version": "0.0.1",
    "description": "<p>Exporta documentos do SEI em PDF.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Parameter",
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
        "content": "curl -i http://anshmjboss01/sei-broker/service/COSIT/documentos/0003322/pdf",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/DocumentoResource.java",
    "groupTitle": "Documento"
  },
  {
    "type": "post",
    "url": "/:unidade/documentos",
    "title": "Incluir documento",
    "name": "incluirDocumento",
    "group": "Documento",
    "version": "0.0.1",
    "description": "<p>Este método realiza a inclusão de um documento.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Parameter",
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
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "idSerie",
            "description": "<p>Identificador do tipo de documento no SEI (Consultar serviço Listar Séries)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "idProcedimento",
            "description": "<p>Número do processo onde o documento será incluído</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "numero",
            "description": "<p>Número do documento, passar null para documentos gerados com numeração controlada pelo SEI.Para documentos externos informar o número ou nome complementar a ser exibido na árvore de documentos do processo (o SEI não controla numeração de documentos externos).</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "data",
            "description": "<p>Data do documento, obrigatório para documentos externos. Passar null para documentos gerados</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "descricao",
            "description": "<p>Descrição do documento para documentos gerados. Passar null para documentos externos</p>"
          },
          {
            "group": "Parameter",
            "type": "Remetente",
            "optional": true,
            "field": "remetente",
            "description": "<p>Obrigatório para documentos externos, passar null para documentos gerados.</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "remetente.nome",
            "description": "<p>Nome do remetente</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "remetente.sigla",
            "description": "<p>Login do remetente</p>"
          },
          {
            "group": "Parameter",
            "type": "Interessado[]",
            "optional": true,
            "field": "interessados",
            "description": "<p>Informar um conjunto com os dados de interessados. Se não existirem interessados deve ser informado um conjunto vazio</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "interessados.nome",
            "description": "<p>Nome do interessado</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "interessados.sigla",
            "description": "<p>Login do interessado</p>"
          },
          {
            "group": "Parameter",
            "type": "Destinatario[]",
            "optional": true,
            "field": "destinatarios",
            "description": "<p>Informar um conjunto com os dados de destinatários. Se não existirem destinatários deve ser informado um conjunto vazio</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "destinatarios.nome",
            "description": "<p>Nome do destinatário</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "destinatarios.sigla",
            "description": "<p>Login do destinatário</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "observacao",
            "description": "<p>Texto da observação da unidade, passar null se não existir</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "nomeArquivo",
            "description": "<p>Nome do arquivo, obrigatório para documentos externos. Passar null para documentos gerados</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "conteudo",
            "description": "<p>Conteúdo do arquivo codificado em Base64. Para documentos gerados será o conteúdo da seção principal do editor HTML e para documentos externos será o conteúdo do anexo.</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "allowedValues": [
              "\"0 (público)\"",
              "\"1 (restrito)\"",
              "\"2 (sigiloso)\"",
              "\"null (herda do processo)\""
            ],
            "optional": true,
            "field": "nivelAcesso",
            "description": "<p>Nível de acesso do documento</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: http://anshmjboss01/sei-broker/service/COSAP/documentos\n\nbody:\n{\n\t\"tipo\":\"G\",\n\t\"idProcedimento\":null,\n\t\"idSerie\":null,\n\t\"numero\":null,\n\t\"data\":null,\n\t\"descricao\":\"Documento demonstrativo\",\n\t\"remetente\":null,\n\t\"interessados\":[{\"sigla\":\"andre.guimaraes\",\"nome\":\"André Luís Fernandes Guimarães\"}],\n\t\"destinatarios\":[],\n\t\"observacao\":null,\n\t\"nomeArquivo\":null,\n\t\"conteudo\":\"Conteúdo Base64\",\n\t\"nivelAcesso\":\"0\"\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "idDocumento",
            "description": "<p>número interno do documento</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "documentoFormatado",
            "description": "<p>número do documento visível para o usuário</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "linkAcesso",
            "description": "<p>link para acesso ao documento</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n  \"idDocumento\":\"1140000000872\",\n  \"documentoFormatado\":\"0003934\",\n  \"linkAcesso\":\"https://sei-hm.ans.gov.br/controlador.php?acao=arvore_visualizar&acao_origem=procedimento_visualizar&id_procedimento=267&id_documento=1017&sta_editor=I&infra_sistema=100000100&infra_unidade_atual=110000934&infra_hash=3d798777382d6ac455317f3a87ad9bd1f9650315e019ef922f388b829902a95b\"\n}",
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/DocumentoResource.java",
    "groupTitle": "Documento"
  },
  {
    "type": "get",
    "url": "/:unidade/extensoes",
    "title": "Listar extensões",
    "name": "listarExtensoesPermitidas",
    "group": "Extensao",
    "version": "0.0.1",
    "description": "<p>Este método realiza uma busca pelas extensões de arquivos permitidas.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI.</p>"
          },
          {
            "group": "Parameter",
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
        "content": "curl -i http://anshmjboss01/sei-broker/service/COSAP/extensoes/",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "ArquivoExtensao[]",
            "optional": false,
            "field": "extensoes",
            "description": "<p>Lista de extensões permitidas.</p>"
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/ExtensoesResource.java",
    "groupTitle": "Extensao"
  },
  {
    "type": "post",
    "url": "/:unidade/processos",
    "title": "Abrir processo",
    "name": "abrirProcesso",
    "group": "Processo",
    "version": "0.0.1",
    "description": "<p>Este método realiza a abertura de um processo.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI.</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "idTipoProcedimento",
            "description": "<p>Identificador do tipo de processo no SEI (Consultar tipos de processo).</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "especificacao",
            "description": "<p>Especificação do processo.</p>"
          },
          {
            "group": "Parameter",
            "type": "Assunto[]",
            "optional": false,
            "field": "assuntos",
            "description": "<p>Assuntos do processo, os assuntos informados serão adicionados aos assuntos \t\t\t\t\t\t\t\t\tsugeridos para o tipo de processo. Passar um array vazio caso nenhum outro assunto seja necessário \t\t\t\t\t\t\t\t\t(caso apenas os sugeridos para o tipo bastem para classificação).</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "assuntos.codigoEstruturado",
            "description": "<p>Código do assunto</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "assuntos.descricao",
            "description": "<p>Descrição do assunto</p>"
          },
          {
            "group": "Parameter",
            "type": "Interessado[]",
            "optional": false,
            "field": "interessados",
            "description": "<p>Informar um conjunto com os dados de interessados. Se não existirem interessados \t\t\t\t\t\t\t\tdeve ser informado um conjunto vazio.</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "interessados.nome",
            "description": "<p>Nome do interessado</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "interessados.sigla",
            "description": "<p>Login do interessado</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "observacao",
            "description": "<p>Texto da observação, passar null se não existir.</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "allowedValues": [
              "\"0 (público)\"",
              "\"1 (restrito)\"",
              "\"2 (sigiloso)\"",
              "\"null (herda do processo)\""
            ],
            "optional": false,
            "field": "nivelAcesso",
            "description": "<p>Nível de acesso do processo.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: [POST] http://anshmjboss01/sei-broker/service/COSAP/processos\n\nbody:\n{\n\t\"dadosProcesso\":{\n\t\t\t\"idTipoProcedimento\":\"100000375\",\n\t\t\t\"especificacao\":\"Documentação REST\",\n\t\t\t\"assuntos\":[{\"codigoEstruturado\":\"00.01.01.01\",\"descricao\":\"Assunto teste\"}],\n\t\t\t\"interessados\":[{\"sigla\":\"andre.guimaraes\",\"nome\":\"André Luís Fernandes Guimarães\"}],\n\t\t\t\"observacao\":\"Exemplo de requisição\",\n\t\t\t\"nivelAcesso\":0\n\t\t},\n\t\t\"documentos\":[],\n\t\t\"processosRelacionados\":[],\n\t\t\"unidadesDestino\":[\"110000935\",\"110000934\"],\n\t\t\"manterAbertoOrigem\":true,\n\t\t\"enviarEmailNotificacao\":true,\n\t\t\"qtdDiasAteRetorno\":null,\n\t\t\"somenteDiasUteis\":false\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "idProcedimento",
            "description": "<p>Número do processo gerado</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "procedimentoFormatado",
            "description": "<p>Número formatado do processo gerado</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "linkAcesso",
            "description": "<p>Link de acesso ao processo</p>"
          },
          {
            "group": "Success 200",
            "type": "RetornoInclusaoDocumento",
            "optional": false,
            "field": "retornoInclusaoDocumentos",
            "description": "<p>Retorno dos documentos inseridos no processo (opcional)</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "retornoInclusaoDocumentos.idDocumento",
            "description": "<p>Número interno do documento</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "retornoInclusaoDocumentos.documentoFormatado",
            "description": "<p>Número do documento visível para o usuário</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "retornoInclusaoDocumentos.linkAcesso",
            "description": "<p>Link para acesso ao documento</p>"
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/ProcessoResource.java",
    "groupTitle": "Processo"
  },
  {
    "type": "post",
    "url": "/:unidade/processos/concluidos",
    "title": "Concluir processo",
    "name": "concluirProcesso",
    "group": "Processo",
    "version": "0.0.1",
    "description": "<p>Este método realiza a conclusão de um processo.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Parameter",
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
        "content": "endpoint: [POST] http://anshmjboss01/sei-broker/service/COSAP/processos/concluidos\n\nbody:\n33910000029201653",
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/ProcessoResource.java",
    "groupTitle": "Processo"
  },
  {
    "type": "get",
    "url": "/:unidade/processos/:processo",
    "title": "Consultar processo",
    "name": "consultarProcesso",
    "group": "Processo",
    "version": "0.0.1",
    "description": "<p>Este método realiza uma consulta a processos no SEI e no SIPAR.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "processo",
            "description": "<p>Número do processo que deseja consultar</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "assuntos",
            "defaultValue": "N",
            "description": "<p>S ou N para exibir assuntos do processo</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "interessados",
            "defaultValue": "N",
            "description": "<p>S ou N para exibir interessados no processo</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "observacoes",
            "defaultValue": "N",
            "description": "<p>S ou N para exibir observações feitas no processo</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "andamento",
            "defaultValue": "N",
            "description": "<p>S ou N para exibir andamento do processo</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "andamento-conclusao",
            "defaultValue": "N",
            "description": "<p>S ou N para exibir o andamento da conclusão do processo</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "ultimo-andamento",
            "defaultValue": "N",
            "description": "<p>S ou N para exibir o último andamento dado ao processo</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "unidades",
            "defaultValue": "N",
            "description": "<p>S ou N para exibir unidades onde o processo está aberto</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "relacionados",
            "defaultValue": "N",
            "description": "<p>S ou N para exibir processos relacionados</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "anexados",
            "defaultValue": "N",
            "description": "<p>S ou N para exibir processos anexados</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:",
        "content": "curl -i http://anshmjboss01/sei-broker/service/COSAP/processos/33910000029201653",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "ResultadoConsultaProcesso",
            "optional": false,
            "field": "resultadoConsultaProcesso",
            "description": "<p>Objeto de retorno da consulta aos processos, pode um conter processo do SEI ou do SIPAR</p>"
          },
          {
            "group": "Success 200",
            "type": "RetornoConsultaProcedimento",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei",
            "description": "<p>Resultado de processo do SEI</p>"
          },
          {
            "group": "Success 200",
            "type": "Andamento",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoGeracao",
            "description": "<p>Andamento da geração do processo (opcional)</p>"
          },
          {
            "group": "Success 200",
            "type": "Data",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoGeracao.dataHora",
            "description": "<p>Data e hora do registro de andamento</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoGeracao.descricao",
            "description": "<p>Descrição do andamento</p>"
          },
          {
            "group": "Success 200",
            "type": "Unidade",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoGeracao.unidade",
            "description": "<p>Unidade responsável pelo andamento</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoGeracao.unidade.descricao",
            "description": "<p>Nome da unidade</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoGeracao.unidade.idUnidade",
            "description": "<p>Código da unidade</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoGeracao.unidade.sigla",
            "description": "<p>Sigla da unidade</p>"
          },
          {
            "group": "Success 200",
            "type": "Usuario",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoGeracao.usuario",
            "description": "<p>Usuário responsável pela ação</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoGeracao.usuario.idUsuario",
            "description": "<p>Código do usuário</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoGeracao.usuario.nome",
            "description": "<p>Nome do usuário</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.andamentoGeracao.usuario.sigla",
            "description": "<p>Login do usuário</p>"
          },
          {
            "group": "Success 200",
            "type": "Data",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.dataAutuacao",
            "description": "<p>Data de autuação do processo</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.especificacao",
            "description": "<p>Especificação do processo</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.idProcedimento",
            "description": "<p>Id interno do processo no SEI</p>"
          },
          {
            "group": "Success 200",
            "type": "Interessado",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.interessados",
            "description": "<p>Lista de interessados no processo (opcional)</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.interessados.nome",
            "description": "<p>Nome do interessado</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.interessados.sigla",
            "description": "<p>Login do interessado</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.linkAcesso",
            "description": "<p>Link para acesso ao processo</p>"
          },
          {
            "group": "Success 200",
            "type": "Observacao",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.observacoes",
            "description": "<p>Observações feitas sobre o processo (opcional)</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.observacoes.descricao",
            "description": "<p>Descrição da obsevação</p>"
          },
          {
            "group": "Success 200",
            "type": "Unidade",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.observacoes.unidade",
            "description": "<p>Unidade responsável pela observação</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.observacoes.unidade.descricao",
            "description": "<p>Nome da unidade</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.observacoes.unidade.idUnidade",
            "description": "<p>Código da unidade</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.observacoes.unidade.sigla",
            "description": "<p>Sigla da unidade</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.procedimentoFormatado",
            "description": "<p>Número do processo visível para o usuário</p>"
          },
          {
            "group": "Success 200",
            "type": "TipoProcedimento",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.tipoProcedimento",
            "description": "<p>Tipo de procedimento</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.tipoProcedimento.idTipoProcedimento",
            "description": "<p>Identificador do tipo de procedimento</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.tipoProcedimento.nome",
            "description": "<p>Nome do tipo de procedimento</p>"
          },
          {
            "group": "Success 200",
            "type": "Andamento",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.ultimoAndamento",
            "description": "<p>Ultimo andamento do processo (opcional)</p>"
          },
          {
            "group": "Success 200",
            "type": "Data",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.ultimoAndamento.dataHora",
            "description": "<p>Data e hora do registro de andamento</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.ultimoAndamento.descricao",
            "description": "<p>Descrição do andamento</p>"
          },
          {
            "group": "Success 200",
            "type": "Unidade",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.ultimoAndamento.unidade",
            "description": "<p>Unidade responsável pelo andamento</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.ultimoAndamento.unidade.descricao",
            "description": "<p>Nome da unidade</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.ultimoAndamento.unidade.idUnidade",
            "description": "<p>Código da unidade</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.ultimoAndamento.unidade.sigla",
            "description": "<p>Sigla da unidade</p>"
          },
          {
            "group": "Success 200",
            "type": "Usuario",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.ultimoAndamento.usuario",
            "description": "<p>Usuário responsável pela ação</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.ultimoAndamento.usuario.idUsuario",
            "description": "<p>Código do usuário</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.ultimoAndamento.usuario.nome",
            "description": "<p>Nome do usuário</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.ultimoAndamento.usuario.sigla",
            "description": "<p>Login do usuário</p>"
          },
          {
            "group": "Success 200",
            "type": "UnidadeProcedimentoAberto",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.unidadesProcedimentoAberto",
            "description": "<p>Unidades onde o processo está aberto (opcional)</p>"
          },
          {
            "group": "Success 200",
            "type": "Unidade",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.unidadesProcedimentoAberto.unidade",
            "description": "<p>Unidade onde o processo está aberto</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.unidadesProcedimentoAberto.unidade.descricao",
            "description": "<p>Nome da unidade</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.unidadesProcedimentoAberto.unidade.idUnidade",
            "description": "<p>Código da unidade</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sei.unidadesProcedimentoAberto.unidade.sigla",
            "description": "<p>Sigla da unidade</p>"
          },
          {
            "group": "Success 200",
            "type": "DocumentoSIPAR",
            "optional": false,
            "field": "resultadoConsultaProcesso.sipar",
            "description": "<p>Resultado de processo do SIPAR</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sipar.digito",
            "description": "<p>Digito do processo</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sipar.operadora",
            "description": "<p>Operadora relacionada ao processo</p>"
          },
          {
            "group": "Success 200",
            "type": "Data",
            "optional": false,
            "field": "resultadoConsultaProcesso.sipar.emissao",
            "description": "<p>Data de emissão</p>"
          },
          {
            "group": "Success 200",
            "type": "Data",
            "optional": false,
            "field": "resultadoConsultaProcesso.sipar.registro",
            "description": "<p>Data de registro</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sipar.tipo",
            "description": "<p>Tipo do processo</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultadoConsultaProcesso.sipar.resumo",
            "description": "<p>Resumo sobre o processo</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "resultadoConsultaProcesso.sipar.orgaoPosse",
            "description": "<p>Código do orgão que tem a posse do processo</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "resultadoConsultaProcesso.sipar.orgaoOrigem",
            "description": "<p>Código do orgão de origem do processo</p>"
          },
          {
            "group": "Success 200",
            "type": "Long",
            "optional": false,
            "field": "resultadoConsultaProcesso.sipar.orgaoRegistro",
            "description": "<p>Código do orgão de registro do processo</p>"
          },
          {
            "group": "Success 200",
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/ProcessoResource.java",
    "groupTitle": "Processo"
  },
  {
    "type": "post",
    "url": "/:unidade/processos/enviados",
    "title": "Enviar processo",
    "name": "enviarProcesso",
    "group": "Processo",
    "version": "0.0.1",
    "description": "<p>Este método envia processos a outras unidades.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "processo",
            "description": "<p>Numero do processo a ser enviado</p>"
          },
          {
            "group": "Parameter",
            "type": "String[]",
            "optional": false,
            "field": "unidadesDestino",
            "description": "<p>Códigos das unidades para onde o bloco será enviado</p>"
          },
          {
            "group": "Parameter",
            "type": "Boolean",
            "optional": false,
            "field": "manterAbertoOrigem",
            "defaultValue": "false",
            "description": "<p>Informa se o processo deve continuar aberto na unidade de origem</p>"
          },
          {
            "group": "Parameter",
            "type": "Boolean",
            "optional": false,
            "field": "removerAnotacoes",
            "defaultValue": "false",
            "description": "<p>Informa se as anotações do processo devem ser removidas</p>"
          },
          {
            "group": "Parameter",
            "type": "Boolean",
            "optional": false,
            "field": "enviarEmailNotificacao",
            "defaultValue": "false",
            "description": "<p>Informa se deve ser enviado um e-mail de notificação</p>"
          },
          {
            "group": "Parameter",
            "type": "Date",
            "optional": false,
            "field": "dataRetornoProgramado",
            "defaultValue": "null",
            "description": "<p>Data para retorno programado do processo a unidade (padrão ISO-8601)</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "qtdDiasAteRetorno",
            "defaultValue": "null",
            "description": "<p>Quantidade de dias até o retorno do processo</p>"
          },
          {
            "group": "Parameter",
            "type": "Boolean",
            "optional": false,
            "field": "somenteDiasUteis",
            "defaultValue": "false",
            "description": "<p>Informa se só serão contabilizados dias úteis</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "endpoint: [POST] http://anshmjboss01/sei-broker/service/COSAP/processos/enviados\n\nbody:\n{\n\t\"numeroDoProcesso\":\"1600000000098\",\n\t\"unidadesDestino\":[\"110000934\",\"110000934\"],\n\t\"manterAbertoOrigem\":false,\n\t\"removerAnotacoes\":false,\n\t\"enviarEmailNotificacao\":true,\n\t\"dataRetornoProgramado\":2016-04-14T19:39:22.292+0000,\n\t\"qtdDiasAteRetorno\":5,\n\t\"somenteDiasUteis\":true}\n}",
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/ProcessoResource.java",
    "groupTitle": "Processo"
  },
  {
    "type": "get",
    "url": "/:unidade/processos/tipos",
    "title": "Tipos de processo",
    "name": "listarTiposProcesso",
    "group": "Processo",
    "version": "0.0.1",
    "description": "<p>Este método consulta os tipos de processo.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
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
        "content": "curl -i http://anshmjboss01/sei-broker/service/COSAP/processos/tipos",
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/ProcessoResource.java",
    "groupTitle": "Processo"
  },
  {
    "type": "delete",
    "url": "/:unidade/processos/concluidos/:processo",
    "title": "Reabrir processo",
    "name": "reabrirProcesso",
    "group": "Processo",
    "version": "0.0.1",
    "description": "<p>Este método reabre um processo.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "processo",
            "description": "<p>Numero do processo a ser reaberto</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -X DELETE http://anshmjboss01/sei-broker/service/COSAP/processos/concluidos/33910000029201653",
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/ProcessoResource.java",
    "groupTitle": "Processo"
  },
  {
    "type": "get",
    "url": "/:unidade/series",
    "title": "Listar séries",
    "name": "listarSeries",
    "group": "Serie",
    "version": "0.0.1",
    "description": "<p>Este método realiza uma consulta às séries.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "tipo-processo",
            "defaultValue": "null",
            "description": "<p>Para filtrar por determinado tipo de processo.</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "unidade",
            "defaultValue": "null",
            "description": "<p>Para filtrar por séries de determinada unidade.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:",
        "content": "curl -i http://anshmjboss01/sei-broker/service/COSAP/series",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Serie[]",
            "optional": false,
            "field": "series",
            "description": "<p>Lista de séries.</p>"
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/SeriesResource.java",
    "groupTitle": "Serie"
  },
  {
    "type": "get",
    "url": "/unidades/{unidade}/codigo",
    "title": "Consultar código",
    "name": "consultarCodigo",
    "group": "Unidade",
    "version": "0.0.1",
    "description": "<p>Este método retorna o código da Unidade pesquisada.</p>",
    "examples": [
      {
        "title": "Exemplo de requisição:",
        "content": "curl -i http://anshmjboss01/sei-broker/service/unidades/COSAP/codigo",
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/UnidadeResource.java",
    "groupTitle": "Unidade"
  },
  {
    "type": "get",
    "url": "/unidades",
    "title": "Listar unidades",
    "name": "listarUnidades",
    "group": "Unidade",
    "version": "0.0.1",
    "description": "<p>Este método realiza uma consulta aos usuários cadastrados que possuem o perfil &quot;Básico&quot;.</p>",
    "examples": [
      {
        "title": "Exemplo de requisição:",
        "content": "curl -i http://anshmjboss01/sei-broker/service/unidades/",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Unidade[]",
            "optional": false,
            "field": "unidades",
            "description": "<p>Lista de unidades</p>"
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/UnidadeResource.java",
    "groupTitle": "Unidade"
  },
  {
    "type": "put",
    "url": "/usuarios/ativar",
    "title": "Ativar usuário",
    "name": "ativarUsuario",
    "group": "Usuario",
    "version": "0.0.1",
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
        "content": "endpoint: http://anshmjboss01/sei-broker/service/usuarios/ativar\n\nbody:\n{\n\t\"codigo\":\"1234\",\n\t\"nome\":\"André Luís Fernandes Guimarães\",\n\t\"login\":\"andre.guimaraes\"\n}",
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/UsuarioResource.java",
    "groupTitle": "Usuario"
  },
  {
    "type": "post",
    "url": "/:unidade/:usuario/processos",
    "title": "Atribuir processo",
    "name": "atribuirProcesso",
    "group": "Usuario",
    "version": "0.0.1",
    "description": "<p>Este método atribui o processo a um usuário.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "processo",
            "description": "<p>Numero do processo a ser atribuído</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "usuario",
            "description": "<p>Login do usuário a quem deseja atribuir o processo</p>"
          },
          {
            "group": "Parameter",
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
        "content": "endpoint: [POST] http://anshmjboss01/sei-broker/service/COSAP/usuarios/andre.guimaraes/processos\n\nbody:\n{\n\t\"processo\":\"33910000029201653\",\n\t\"reabrir\":false\n}",
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/UsuarioResource.java",
    "groupTitle": "Usuario"
  },
  {
    "type": "get",
    "url": "/usuarios/:usuario",
    "title": "Buscar usuário",
    "name": "buscarUsuario",
    "group": "Usuario",
    "version": "0.0.1",
    "description": "<p>Este método realiza a uma busca pelo login do usuário.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
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
        "content": "curl -i http://anshmjboss01/sei-broker/service/usuarios/andre.guimaraes",
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/UsuarioResource.java",
    "groupTitle": "Usuario"
  },
  {
    "type": "put",
    "url": "/usuarios/desativar",
    "title": "Desativar usuário",
    "name": "desativarUsuario",
    "group": "Usuario",
    "version": "0.0.1",
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
        "content": "endpoint: http://anshmjboss01/sei-broker/service/usuarios/desativar\n\nbody:\n{\n\t\"codigo\":\"1234\",\n\t\"nome\":\"André Luís Fernandes Guimarães\",\n\t\"login\":\"andre.guimaraes\"\n}",
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/UsuarioResource.java",
    "groupTitle": "Usuario"
  },
  {
    "type": "post",
    "url": "/usuarios/excluir",
    "title": "Excluir usuário",
    "name": "excluirUsuario",
    "group": "Usuario",
    "version": "0.0.1",
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
        "content": "endpoint: http://anshmjboss01/sei-broker/service/usuarios/excluir\n\nbody:\n{\n\t\"codigo\":\"1234\",\n\t\"nome\":\"André Luís Fernandes Guimarães\",\n\t\"login\":\"andre.guimaraes\"\n}",
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/UsuarioResource.java",
    "groupTitle": "Usuario"
  },
  {
    "type": "post",
    "url": "/usuarios/incluir-alterar",
    "title": "Incluir ou alterar usuário",
    "name": "incluirUsuario",
    "group": "Usuario",
    "version": "0.0.1",
    "description": "<p>Este método realiza a inclusão de novos usuários ou alterarações nos usuários existentes.</p>",
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
        "content": "endpoint: http://anshmjboss01/sei-broker/service/usuarios/incluir-alterar\n\nbody:\n{\n\t\"codigo\":\"1234\",\n\t\"nome\":\"André Luís Fernandes Guimarães\",\n\t\"login\":\"andre.guimaraes\"\n}",
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/UsuarioResource.java",
    "groupTitle": "Usuario"
  },
  {
    "type": "get",
    "url": "/:unidade/usuarios",
    "title": "Listar usuários",
    "name": "listarUsuarios",
    "group": "Usuario",
    "version": "0.0.1",
    "description": "<p>Este método realiza uma consulta aos usuários cadastrados que possuem o perfil &quot;Básico&quot;.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "unidade",
            "description": "<p>Sigla da Unidade cadastrada no SEI</p>"
          },
          {
            "group": "Parameter",
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
        "content": "curl -i http://anshmjboss01/sei-broker/service/usuarios/COSAP",
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/UsuarioResource.java",
    "groupTitle": "Usuario"
  },
  {
    "type": "get",
    "url": "/versao",
    "title": "Consultar versão",
    "name": "getNumeroVersao",
    "group": "Versao",
    "version": "0.0.1",
    "description": "<p>Este método realiza uma consulta para saber a versão do sei-broker que está disponível.</p>",
    "examples": [
      {
        "title": "Exemplo de requisição:",
        "content": "curl -i http://anshmjboss01/sei-broker/service/versao",
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
    "filename": "barramento-sei/src/main/java/br/gov/ans/integracao/sei/rest/VersaoResource.java",
    "groupTitle": "Versao"
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
    "filename": "barramento-sei/src/main/webapp/ajuda/main.js",
    "group": "c__ANS_ARQUITETURA_Fontes_Barramento_SEI_trunk_barramento_sei_src_main_webapp_ajuda_main_js",
    "groupTitle": "c__ANS_ARQUITETURA_Fontes_Barramento_SEI_trunk_barramento_sei_src_main_webapp_ajuda_main_js",
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
    "filename": "barramento-sei/target/sei-broker-0.10/ajuda/main.js",
    "group": "c__ANS_ARQUITETURA_Fontes_Barramento_SEI_trunk_barramento_sei_target_sei_broker_0_10_ajuda_main_js",
    "groupTitle": "c__ANS_ARQUITETURA_Fontes_Barramento_SEI_trunk_barramento_sei_target_sei_broker_0_10_ajuda_main_js",
    "name": ""
  }
] });
