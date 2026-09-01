# Nexel Social AI — Architecture

## 1. Visão geral

O Nexel Social AI é uma aplicação backend desenvolvida em Java e Spring Boot para auxiliar empresas e profissionais de social media na criação de conteúdos para redes sociais utilizando Inteligência Artificial.

O sistema será desenvolvido inicialmente como um **monólito modular**, mantendo separação clara entre os domínios e responsabilidades.

A arquitetura deverá priorizar:

* Clean Code
* SOLID
* Baixo acoplamento
* Alta coesão
* Testabilidade
* Segurança
* Facilidade de manutenção
* Evolução futura para integrações e serviços adicionais

Não utilizar microsserviços no MVP.

---

# 2. Stack tecnológica

## Backend

* Java 25
* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Security
* Spring Validation
* Springdoc OpenAPI
* Maven
* Lombok
* MapStruct, quando fizer sentido
* JUnit 5
* Mockito
* AssertJ

## Banco

* PostgreSQL
* Flyway

## Infraestrutura

* Docker
* Docker Compose

## Testes de API

* Bruno

## Documentação

* Swagger / OpenAPI

## Inteligência Artificial

* OpenAI API

---

# 3. Arquitetura geral

Fluxo principal:

```text
Client
   │
   ▼
Controller
   │
   ▼
Service Interface
   │
   ▼
ServiceImpl
   │
   ├──────────────► Repository
   │                    │
   │                    ▼
   │               PostgreSQL
   │
   └──────────────► External Provider
                         │
                         ▼
                     OpenAI API
```

Controllers são responsáveis exclusivamente pela camada HTTP.

As regras de negócio ficam nos Services.

Repositories são responsáveis pelo acesso aos dados.

Integrações externas devem ser isoladas por abstrações.

---

# 4. Estrutura de pacotes

Estrutura inicial:

```text
com.nexel.socialai

├── config
│
├── common
│   ├── exception
│   ├── response
│   ├── security
│   └── util
│
├── auth
│   ├── controller
│   ├── dto
│   ├── service
│   │   └── impl
│   └── security
│
├── user
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── mapper
│   ├── repository
│   └── service
│       └── impl
│
├── company
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── mapper
│   ├── repository
│   └── service
│       └── impl
│
├── content
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── mapper
│   ├── repository
│   └── service
│       └── impl
│
├── ai
│   ├── client
│   ├── config
│   ├── dto
│   ├── exception
│   ├── provider
│   │   └── impl
│   ├── service
│   │   └── impl
│   └── prompt
│
└── NexelSocialAiApplication
```

---

# 5. Camadas

## Controller

Responsabilidades:

* Receber requisições HTTP.
* Validar DTOs.
* Delegar para Services.
* Retornar HTTP Response.

Não deve conter:

* Regras de negócio.
* Acesso direto ao Repository.
* Chamadas diretas para APIs externas.
* Tratamento de regras complexas.

---

## Service

Responsabilidades:

* Regras de negócio.
* Orquestração dos componentes.
* Validações de negócio.
* Controle do fluxo da aplicação.

Utilizar:

```text
Service
ServiceImpl
```

Controllers devem depender das interfaces.

---

## Repository

Responsável pelo acesso ao banco de dados.

Utilizar Spring Data JPA.

Não colocar regras de negócio nos Repositories.

---

## Entity

Representa o modelo persistido no banco.

Entities não devem ser expostas diretamente pela API.

---

## DTO

Os DTOs representam os contratos da API.

Separar:

```text
Request DTO
Response DTO
```

Nunca utilizar Entity diretamente como Request ou Response.

---

# 6. Integração com IA

A aplicação não deve depender diretamente da implementação da OpenAI.

Utilizar uma abstração:

```java
public interface AiProvider {
}
```

Implementação:

```text
OpenAiProviderImpl
```

Fluxo:

```text
ContentController
        ↓
ContentService
        ↓
AiProvider
        ↓
OpenAiProviderImpl
        ↓
OpenAI API
```

Isso permite futuramente adicionar:

```text
GeminiProviderImpl
ClaudeProviderImpl
```

sem alterar a regra de negócio principal.

---

# 7. Banco de dados

O banco principal será PostgreSQL.

Todas as alterações de estrutura devem ser realizadas através do Flyway.

Não utilizar:

```properties
spring.jpa.hibernate.ddl-auto=update
```

como mecanismo principal de evolução do banco.

Preferir:

```text
V1__create_users.sql
V2__create_companies.sql
V3__create_content_generation.sql
```

---

# 8. Identificadores

Utilizar UUID como identificador das entidades principais.

Exemplo:

```java
@Id
@GeneratedValue
private UUID id;
```

A decisão poderá ser revisada caso exista necessidade técnica específica.

---

# 9. Auditoria

Entidades persistentes importantes devem possuir:

```text
createdAt
updatedAt
```

Quando necessário, utilizar Spring Data Auditing.

---

# 10. Segurança

A autenticação será realizada utilizando:

* Spring Security
* JWT
* BCrypt

Regras importantes:

* Senhas nunca são armazenadas em texto puro.
* Tokens não devem aparecer nos logs.
* API Keys nunca devem estar no código.
* Usuários somente podem acessar seus próprios recursos.
* O backend não deve confiar em `userId` enviado pelo cliente para autorização.

O usuário autenticado deve ser obtido através do contexto de segurança.

---

# 11. Tratamento de erros

Utilizar:

```java
@RestControllerAdvice
```

para tratamento global.

Exceptions específicas devem representar situações relevantes do domínio.

Exemplos:

```text
ResourceNotFoundException
BusinessException
ConflictException
ExternalApiException
AiProviderException
RateLimitException
```

Não utilizar `catch (Exception)` indiscriminadamente.

`try/catch` deve ser utilizado principalmente em integrações externas ou situações onde exista necessidade de traduzir uma exception.

---

# 12. Java moderno

Utilizar recursos modernos do Java quando agregarem valor:

* Streams
* map
* filter
* flatMap
* Optional
* Method References
* Records
* switch expressions
* `toList()`

Não utilizar recursos modernos apenas para tornar o código mais complexo.

Legibilidade sempre deve ser prioridade.

---

# 13. Testes

Testes unitários deverão cobrir principalmente:

* Services
* Regras de negócio
* Integrações com providers utilizando mocks
* Tratamento de exceptions

Tecnologias:

* JUnit 5
* Mockito
* AssertJ

Testes de integração serão adicionados posteriormente.

---

# 14. Evolução futura

Possíveis evoluções:

* Redis
* Resilience4j
* Mensageria
* RAG
* Embeddings
* Banco vetorial
* Upload de documentos
* Leitor inteligente de documentos
* Integração com redes sociais
* Agendamento de posts
* Multi-tenancy
* Observabilidade
* Métricas
* CI/CD

Essas funcionalidades não fazem parte do MVP inicial.

---

# 15. Princípio arquitetural principal

O projeto deve começar simples e evoluir conforme surgirem necessidades reais.

Evitar overengineering.

A arquitetura deve permitir crescimento sem obrigar o projeto a possuir complexidade desnecessária desde o início.
