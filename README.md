# testebackend.quarkus

Este projeto usa Quarkus, o Supersonic Subatomic Java Framework.

Se você quiser saber mais sobre o Quarkus, visite seu site: https://quarkus.io/ .

## Informações do Projeto

- **Java JDK**: 21.0.2
- **Quarkus**: 3.10.2

## Executando o aplicativo no modo dev

Você pode executar seu aplicativo no modo de desenvolvimento que permite codificação ao vivo usando:
```shell script
./mvnw compile quarkus:dev
```

## Regras do Projeto

O projeto foi desenvolvido pensando primeiramente na criação da lista de pessoas, tarefas e os departamentos (possuindo método para inclusão de departamento).
Após a criação é necessário incluir e alocar tarefas, departamentos e pessoas. Baseando-se no principio de que uma pessoa precisa estar em um departamento para ter uma tarefa, 
uma tarefa precisa estar em um departamento para ser alocada a alguém.
Após a inclusão e alocação de tarefas e depertamentos para as pessoas e tarefas, as requisições podem ser usadas normalmente (caso falte algo a ser cadastro ou alocado, as requisições retornarão as instruções).

## Exemplos de execução
1. Requisição para criação de pessoa: Post:
   {
   "nome": "Roberto"
   }
2. Requisição para criação de tarefa: Post:
   {
   "titulo": "Implementar Ouvido",
   "descricao": "Desenvolver a API de gerenciamento de tarefas",
   "duracao": 5,
   "completado": false,
   "prazo": "2022-06-01"
   }
3. Requisição para alocar tarefa e pessoa: Put: http://localhost:8080/api/tarefas/alocar/{tarefaId}/{pessoaId}
4. Requisição para excluir pessoa: Delete: http://localhost:8080/api/pessoas/{id}
5. Requisição para alterar pessoa: Put: http://localhost:8080/api/pessoas/{pessoaId} - Body json: {
   "nome": "Matheus"
   }
6. Requisição para listar 3 tarefas pendentes mais antigas: GET: http://localhost:8080/api/tarefas/pendentes
7. Requisição para listar nome, total de horas e departamento: GET: http://localhost:8080/api/pessoas/

E algumas outras requisições.

## Guias Relacionados

- REST resources for Hibernate ORM with Panache ([guide](https://quarkus.io/guides/rest-data-panache)): Generate Jakarta REST resources for your Hibernate Panache entities and repositories
- RESTEasy Classic JSON-B ([guide](https://quarkus.io/guides/rest-json)): JSON-B serialization support for RESTEasy Classic
- SmallRye OpenAPI ([guide](https://quarkus.io/guides/openapi-swaggerui)): Document your REST APIs with OpenAPI - comes with Swagger UI
- Hibernate ORM with Panache ([guide](https://quarkus.io/guides/hibernate-orm-panache)): Simplify your persistence code for Hibernate ORM via the active record or the repository pattern
- RESTEasy Classic ([guide](https://quarkus.io/guides/resteasy)): REST endpoint framework implementing Jakarta REST and more
- JDBC Driver - PostgreSQL ([guide](https://quarkus.io/guides/datasource)): Connect to the PostgreSQL database via JDBC

## Provided Code

### Hibernate ORM

Create your first JPA entity

[Related guide section...](https://quarkus.io/guides/hibernate-orm)

[Related Hibernate with Panache section...](https://quarkus.io/guides/hibernate-orm-panache)


### REST Data with Panache

Generating Jakarta REST resources with Panache

[Related guide section...](https://quarkus.io/guides/rest-data-panache)


### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)
