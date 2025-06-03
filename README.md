# Projeto de Estudo - Desafio Itaú

Este projeto foi desenvolvido com o objetivo exclusivo de estudo, com base no [desafio proposto pelo Itaú](https://github.com/rafaellins-itau/desafio-itau-vaga-99-junior).

---

## Tecnologias Utilizadas

- Java 21
- Spring Boot 3.2.5
- Maven
- JUnit 5
- Springdoc OpenAPI (Swagger)
- Docker & Docker Compose

---

## Como executar o projeto

### Pré-requisitos

- Java 21 instalado
- Maven 3.9.9 instalado
- Docker e Docker Compose instalados

### Execução via Maven

```bash
# Instalar as dependências e compilar o projeto
mvn install

# Executar o projeto
mvn spring-boot:run
```

### Execução via Docker Compose

```bash
docker compose up --build
```

---

## Documentação da API

Após a aplicação estar em execução, acesse:

```
http://localhost:8080/swagger-ui.html
```

ou

```
http://localhost:8080/swagger-ui/index.html
```

---

## Testes

Os testes estão localizados em `src/test/java`. Para executá-los:

```bash
mvn test
```

---

## Containerização

O projeto conta com Docker e Docker Compose, sendo que a imagem do serviço será construída automaticamente, com base no Dockerfile, e exposta na porta `8080`.

### Estrutura do `docker-compose.yml`

```yaml
services:
  app:
    build: .
    ports:
      - "8080:8080"
```

