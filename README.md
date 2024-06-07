# Gerenciamento de Tarefas com Quarkus

Este projeto utiliza Quarkus, o Supersonic Subatomic Java Framework, para gerenciar tarefas de maneira eficiente e organizada.

Se você quiser saber mais sobre o Quarkus, visite seu site: [Quarkus](https://quarkus.io/).

## Índice

- [Informações do Projeto](#informações-do-projeto)
- [Executando o Aplicativo no Modo Dev](#executando-o-aplicativo-no-modo-dev)
- [Regras do Projeto](#regras-do-projeto)
- [Exemplos de Execução](#exemplos-de-execução)
  - [Criação de Pessoa](#criação-de-pessoa)
  - [Criação de Tarefa](#criação-de-tarefa)
  - [Alocar Tarefa e Pessoa](#alocar-tarefa-e-pessoa)
  - [Excluir Pessoa](#excluir-pessoa)
  - [Alterar Pessoa](#alterar-pessoa)
  - [Listar 3 Tarefas Pendentes Mais Antigas](#listar-3-tarefas-pendentes-mais-antigas)
  - [Listar Nome, Total de Horas e Departamento](#listar-nome-total-de-horas-e-departamento)
  - [Criar Departamento](#criar-departamento)
  - [Finalizar Tarefa](#finalizar-tarefa)
- [Guias Relacionados](#guias-relacionados)
- [Código Fornecido](#código-fornecido)

## Informações do Projeto

- **Java JDK**: 21.0.2
- **Quarkus**: 3.10.2

## Executando o Aplicativo no Modo Dev

Você pode executar seu aplicativo no modo de desenvolvimento, permitindo codificação ao vivo usando:

```bash
./mvnw compile quarkus:dev
```

## Regras do Projeto

O projeto foi desenvolvido com foco na criação de listas de pessoas, tarefas e departamentos. As principais regras incluem:

- **Criação de Listas**: Primeiramente, é necessário criar listas de pessoas, tarefas e departamentos.
- **Alocação de Recursos**: Após a criação, é preciso incluir e alocar tarefas, departamentos e pessoas.
  - **Pessoa**: Deve estar em um departamento para ter uma tarefa.
  - **Tarefa**: Precisa estar em um departamento para ser alocada a alguém.

Caso algum recurso não esteja cadastrado ou alocado corretamente, as requisições retornarão instruções específicas.

## Exemplos de Execução

### Criação de Pessoa

- **Requisição**: `POST`
- **Endpoint**: `http://localhost:8080/pessoas/`
- **Body**:
  ```json
  {
    "nome": "Roberto"
  }
  ```

### Criação de Tarefa

- **Requisição**: `POST`
- **Body**:
  ```json
  {
    "titulo": "Implementar Ouvido",
    "descricao": "Desenvolver a API de gerenciamento de tarefas",
    "duracao": 5,
    "completado": false,
    "prazo": "2022-06-01"
  }
  ```

### Alocar Tarefa e Pessoa

- **Requisição**: `PUT`
- **Endpoint**: `http://localhost:8080/tarefas/alocar/{tarefaId}/{pessoaId}`

### Excluir Pessoa

- **Requisição**: `DELETE`
- **Endpoint**: `http://localhost:8080/pessoas/{id}`

### Alterar Pessoa

- **Requisição**: `PUT`
- **Endpoint**: `http://localhost:8080/pessoas/{pessoaId}`
- **Body**:
  ```json
  {
    "nome": "Matheus"
  }
  ```

### Listar 3 Tarefas Pendentes Mais Antigas

- **Requisição**: `GET`
- **Endpoint**: `http://localhost:8080/tarefas/pendentes`

### Listar Nome, Total de Horas e Departamento

- **Requisição**: `GET`
- **Endpoint**: `http://localhost:8080/pessoas/`

### Criar Departamento

- **Requisição**: `POST`
- **Endpoint**: `http://localhost:8080/departamentos`
- **Body**:
  ```json
  {
    "nome": "Finanças"
  }
  ```

### Finalizar Tarefa

- **Requisição**: `PUT`
- **Endpoint**: `http://localhost:8080/tarefas/finalizar/{tarefaId}`

## Guias Relacionados

- **[REST resources for Hibernate ORM with Panache (guide)](https://quarkus.io/guides/hibernate-orm-panache)**: Gere recursos Jakarta REST para suas entidades e repositórios Hibernate Panache.
- **[RESTEasy Classic JSON-B (guide)](https://quarkus.io/guides/resteasy-jsonb)**: Suporte à serialização JSON-B para RESTEasy Classic.
- **[SmallRye OpenAPI (guide)](https://quarkus.io/guides/openapi-swaggerui)**: Documente suas APIs REST com OpenAPI - vem com Swagger UI.
- **[Hibernate ORM with Panache (guide)](https://quarkus.io/guides/hibernate-orm-panache)**: Simplifique seu código de persistência para Hibernate ORM via active record ou padrão de repositório.
- **[RESTEasy Classic (guide)](https://quarkus.io/guides/resteasy)**: Framework de endpoint REST implementando Jakarta REST e mais.
- **[JDBC Driver - PostgreSQL (guide)](https://quarkus.io/guides/datasource)**: Conecte-se ao banco de dados PostgreSQL via JDBC.

## Código Fornecido

- **Hibernate ORM**: Crie sua primeira entidade JPA.
  - Seção relacionada no guia.
- **REST Data with Panache**: Gerando recursos Jakarta REST com Panache.
  - Seção relacionada no guia.
- **RESTEasy JAX-RS**: Inicie facilmente seus Serviços Web RESTful.
  - Seção relacionada no guia.
