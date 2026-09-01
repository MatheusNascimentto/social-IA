# Prompt para desenvolvimento do projeto — Nexel Social AI

Atue como um **Desenvolvedor Back-end Java Sênior, Arquiteto de Software e Tech Lead**, especializado em **Java, Spring Boot, APIs REST, integração com Inteligência Artificial, arquitetura em camadas, Clean Code, SOLID e boas práticas de desenvolvimento corporativo**.

Quero desenvolver um projeto chamado **Nexel Social AI**, uma plataforma back-end que utiliza Inteligência Artificial para ajudar empresas, social medias e criadores de conteúdo a gerar conteúdo para redes sociais.

O objetivo não é apenas criar um CRUD simples. O projeto deve ser desenvolvido com uma arquitetura profissional, organizada, escalável e adequada para servir como projeto de portfólio de um Desenvolvedor Back-end Java.

---

# 1. Objetivo do sistema

O sistema permitirá que usuários cadastrem suas empresas e configurem informações sobre cada marca.

A Inteligência Artificial deverá utilizar essas informações para gerar conteúdos personalizados para redes sociais.

O sistema inicialmente deverá ser capaz de:

* Gerar legendas para Instagram e outras redes sociais.
* Gerar hashtags relacionadas ao conteúdo.
* Gerar ideias de conteúdo.
* Gerar roteiros para Reels.
* Gerar calendários de conteúdo.
* Melhorar ou reescrever uma legenda existente.
* Adaptar conteúdos para diferentes tons de comunicação.
* Salvar o histórico de conteúdos gerados.
* Permitir que um usuário possua uma ou mais empresas/marcas.
* Permitir que a IA utilize o contexto da empresa para personalizar suas respostas.

Exemplo:

Uma empresa possui as seguintes informações:

* Nome: RJ Vision Terraplanagem.
* Segmento: Terraplanagem.
* Serviços: Escavação, aterro, abertura de piscinas e perfuração de brocas.
* Público-alvo: Proprietários de terrenos, construtoras e empresas.
* Tom de comunicação: Profissional e direto.
* Objetivo: Gerar mais orçamentos.

O usuário solicita:

> Crie uma legenda para um Reels mostrando uma máquina realizando uma escavação.

A IA deverá receber automaticamente o contexto da empresa e gerar uma resposta personalizada.

---

# 2. Stack tecnológica obrigatória

Utilize preferencialmente as seguintes tecnologias:

## Backend

* Java 25.
* Spring Boot.
* Spring Web.
* Spring Data JPA.
* Spring Security.
* Spring Validation.
* Springdoc OpenAPI / Swagger.
* PostgreSQL.
* Lombok, quando fizer sentido.
* Maven.
* JUnit 5.
* Mockito.
* Docker e Docker Compose para infraestrutura local.

## Banco de dados

Utilizar **PostgreSQL** como banco de dados principal.

Justificar a modelagem das entidades e seus relacionamentos.

Utilizar:

* UUID como identificador, preferencialmente.
* `createdAt`.
* `updatedAt`.
* Auditoria quando fizer sentido.
* Constraints no banco de dados.
* Índices para campos frequentemente pesquisados.
* Migrations com Flyway.

Não utilizar `ddl-auto=create` ou `update` como solução principal em ambiente profissional.

---

# 3. Cliente para testar API

Utilizar **Bruno como ferramenta principal para testar os endpoints**, pois as collections podem ser versionadas junto com o projeto no Git.

Criar uma collection organizada por domínio, por exemplo:

```text
/bruno
    /auth
    /users
    /companies
    /content
    /ai
```

Cada requisição deve possuir:

* Nome descritivo.
* Método HTTP.
* URL.
* Headers necessários.
* Body de exemplo.
* Variáveis de ambiente quando necessário.

O projeto também deverá possuir Swagger para documentação e testes dos endpoints.

O Swagger deve ser acessível em ambiente local.

---

# 4. API de Inteligência Artificial

A integração inicial deverá utilizar a **OpenAI API**, através de um serviço isolado do restante da aplicação.

A integração com a IA não deve ficar diretamente dentro do Controller.

Criar uma abstração, por exemplo:

```text
AiProvider
```

Com métodos relacionados à geração de conteúdo.

Criar uma implementação inicial:

```text
OpenAiProviderImpl
```

A arquitetura deve permitir, futuramente, trocar o provedor de IA sem alterar os serviços principais.

Exemplo futuro:

```text
AiProvider
├── OpenAiProviderImpl
├── GeminiProviderImpl
└── ClaudeProviderImpl
```

Os serviços de negócio devem depender da abstração `AiProvider`, e não diretamente da implementação da OpenAI.

---

# 5. Como consumir a API de IA

Utilizar `WebClient` para integração HTTP com APIs externas.

Não utilizar `RestTemplate` em novas implementações.

Criar uma classe de configuração responsável pelo `WebClient` da IA.

Exemplo conceitual:

```text
OpenAiWebClientConfig
```

As configurações devem utilizar:

```properties
OPENAI_API_KEY
OPENAI_BASE_URL
OPENAI_MODEL
```

As credenciais nunca devem ficar diretamente no código.

Utilizar:

* Variáveis de ambiente.
* Arquivo `.env` apenas para ambiente local quando necessário.
* `application.yml`.
* Profiles como `local`, `dev` e `prod`.

O `.env` nunca deve ser enviado para o Git.

Criar também:

```text
.env.example
```

Explicar detalhadamente:

1. Onde criar e configurar a chave da API.
2. Como configurar as variáveis de ambiente.
3. Como a aplicação envia uma requisição para a API.
4. Como é enviado o prompt.
5. Como a resposta da IA é convertida para DTO.
6. Como tratar erros de timeout.
7. Como tratar rate limit.
8. Como tratar respostas inválidas da API externa.
9. Como registrar erros sem expor informações sensíveis.

Sempre que possível, a resposta da IA deve ser solicitada em um formato estruturado para facilitar o processamento no Java.

---

# 6. Arquitetura do projeto

Utilizar arquitetura em camadas, organizada inicialmente por domínio ou funcionalidade, evitando um pacote gigantesco contendo todas as classes de Controller, Service e Repository.

Preferir uma estrutura semelhante a:

```text
src/main/java/com/nexel/socialai

├── config
├── common
│   ├── exception
│   ├── response
│   └── util
│
├── auth
│   ├── controller
│   ├── dto
│   ├── service
│   │   └── impl
│   └── ...
│
├── user
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── repository
│   ├── service
│   │   └── impl
│   └── mapper
│
├── company
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── repository
│   ├── service
│   │   └── impl
│   └── mapper
│
├── content
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── repository
│   ├── service
│   │   └── impl
│   └── mapper
│
├── ai
│   ├── config
│   ├── client
│   ├── dto
│   ├── provider
│   │   └── impl
│   ├── service
│   │   └── impl
│   └── exception
│
└── NexelSocialAiApplication
```

Essa estrutura pode ser adaptada caso exista uma justificativa arquitetural melhor.

---

# 7. Controllers

Os Controllers devem possuir apenas responsabilidades HTTP.

Os Controllers não devem conter regras de negócio complexas.

Devem ser responsáveis por:

* Receber requisições.
* Validar DTOs com `@Valid`.
* Chamar interfaces de Service.
* Retornar respostas HTTP adequadas.

Exemplo:

```java
@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
@Tag(name = "Companies", description = "Gerenciamento de empresas")
public class CompanyController {

    private final CompanyService companyService;

}
```

Utilizar versionamento de API:

```text
/api/v1
```

Utilizar códigos HTTP corretos:

* `200 OK`
* `201 Created`
* `204 No Content`
* `400 Bad Request`
* `401 Unauthorized`
* `403 Forbidden`
* `404 Not Found`
* `409 Conflict`
* `422 Unprocessable Entity`
* `429 Too Many Requests`
* `500 Internal Server Error`
* `502 Bad Gateway`
* `503 Service Unavailable`

---

# 8. Services com Interface e Implementation

Quero utilizar o padrão:

```text
CompanyService
CompanyServiceImpl
```

Exemplo:

```java
public interface CompanyService {

    CompanyResponseDto create(CompanyCreateDto request);

    CompanyResponseDto findById(UUID id);

    Page<CompanyResponseDto> findAll(Pageable pageable);

    CompanyResponseDto update(UUID id, CompanyUpdateDto request);

    void delete(UUID id);

}
```

E:

```java
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

}
```

Os Controllers devem depender da interface.

Aplicar o mesmo padrão nos principais domínios.

Porém, evitar criar interfaces desnecessárias para classes utilitárias simples ou abstrações artificiais.

Sempre explicar quando uma interface realmente agrega valor e quando seria excesso de arquitetura.

---

# 9. DTOs

Nunca expor diretamente as Entities da JPA nos Controllers.

Criar DTOs específicos para Request e Response.

Exemplos:

```text
CompanyCreateRequest
CompanyUpdateRequest
CompanyResponse
ContentGenerationRequest
ContentGenerationResponse
AiContentRequest
AiContentResponse
```

Os DTOs devem utilizar validação detalhada.

Exemplo:

```java
@NotBlank(message = "O nome da empresa é obrigatório")
@Size(min = 2, max = 120)
private String name;
```

Outras anotações quando aplicáveis:

* `@NotNull`
* `@NotBlank`
* `@Size`
* `@Email`
* `@Pattern`
* `@Positive`
* `@PositiveOrZero`
* `@Past`
* `@Future`

---

# 10. Documentação Swagger/OpenAPI

Todos os endpoints e DTOs públicos devem ser bem documentados.

Utilizar:

* `@Tag`
* `@Operation`
* `@ApiResponse`
* `@Parameter`
* `@Schema`

Exemplo conceitual:

```java
@Schema(
    description = "Nome da empresa",
    example = "RJ Vision Terraplanagem",
    minLength = 2,
    maxLength = 120
)
```

A documentação deve explicar:

* Objetivo do endpoint.
* Dados de entrada.
* Dados de saída.
* Possíveis erros.
* Exemplos realistas.

Documentar os DTOs para que o Swagger seja autoexplicativo.

---

# 11. Tratamento global de exceptions

Implementar tratamento global utilizando:

```text
@RestControllerAdvice
```

Criar exceptions específicas quando fizer sentido:

```text
ResourceNotFoundException
BusinessException
ConflictException
UnauthorizedException
ForbiddenException
ExternalApiException
AiProviderException
RateLimitException
```

Criar um DTO padronizado de erro.

Exemplo:

```json
{
  "timestamp": "2026-08-25T21:00:00",
  "status": 404,
  "error": "NOT_FOUND",
  "message": "Empresa não encontrada",
  "path": "/api/v1/companies/...",
  "traceId": "..."
}
```

Nunca retornar stack trace para o cliente em produção.

Diferenciar:

* Erros de validação.
* Recurso não encontrado.
* Regra de negócio.
* Conflito de dados.
* Erro de autenticação.
* Erro de autorização.
* Erro da API de IA.
* Timeout.
* Rate limit.
* Erro interno inesperado.

---

# 12. Uso de try/catch nos Services

Utilizar `try/catch` com critério.

Não envolver todo método apenas por padrão.

Usar `try/catch` principalmente quando existir uma responsabilidade real de:

* Traduzir uma exception externa.
* Adicionar contexto.
* Executar compensação.
* Registrar um erro relevante.
* Transformar erros de integração em exceptions da aplicação.

Exemplo conceitual:

```java
try {
    // chamada para API externa
} catch (WebClientResponseException ex) {
    log.error("Erro ao consumir a API de IA. Status: {}", ex.getStatusCode());

    throw new AiProviderException(
        "Não foi possível gerar o conteúdo no momento",
        ex
    );
}
```

Não utilizar:

```java
catch (Exception e)
```

sem uma justificativa.

Exceções inesperadas devem ser tratadas centralmente pelo `GlobalExceptionHandler`.

Sempre explicar por que determinado `try/catch` foi utilizado.

---

# 13. Java moderno

Utilizar recursos modernos do Java de forma adequada.

Utilizar quando fizer sentido:

* Stream API.
* `map`.
* `filter`.
* `flatMap`.
* `Optional`.
* `orElseThrow`.
* Method references.
* Records para DTOs imutáveis, quando apropriado.
* `Collectors`.
* `toList()`.
* `switch expressions`.
* `var` apenas quando melhorar a legibilidade.
* `sealed classes` apenas quando houver uma necessidade real.

Exemplo:

```java
return companies.stream()
    .filter(Company::isActive)
    .map(companyMapper::toResponse)
    .toList();
```

Não utilizar Streams apenas para parecer moderno.

Para operações simples, `for` pode ser mais legível.

Evitar:

* `Optional` como campo de Entity.
* `Optional` como parâmetro.
* Cadeias de Stream excessivamente complexas.

Priorizar legibilidade.

---

# 14. Lombok

Utilizar Lombok com responsabilidade.

Exemplos permitidos:

* `@Getter`
* `@Setter`
* `@RequiredArgsConstructor`
* `@Builder`
* `@NoArgsConstructor`
* `@AllArgsConstructor`

Evitar `@Data` indiscriminadamente em Entities JPA, pois pode gerar problemas com:

* `equals`.
* `hashCode`.
* relações bidirecionais.
* lazy loading.
* `toString`.

---

# 15. Entidades iniciais

Inicialmente, pensar nas seguintes entidades:

## User

Campos sugeridos:

* id
* name
* email
* passwordHash
* role
* active
* createdAt
* updatedAt

## Company

Campos sugeridos:

* id
* name
* segment
* description
* targetAudience
* communicationTone
* mainObjective
* createdAt
* updatedAt
* user

Uma empresa pertence inicialmente a um usuário.

O modelo deve permitir futura evolução para múltiplos usuários por empresa.

## ContentGeneration

Representa um conteúdo solicitado pelo usuário.

Campos possíveis:

* id
* company
* contentType
* topic
* userPrompt
* generatedContent
* model
* provider
* status
* createdAt

Criar enums quando fizer sentido.

Exemplo:

```text
ContentType
- CAPTION
- HASHTAGS
- CONTENT_IDEA
- REELS_SCRIPT
- CONTENT_CALENDAR
- IMPROVE_CONTENT
```

E:

```text
GenerationStatus
- PENDING
- PROCESSING
- COMPLETED
- FAILED
```

Avaliar quais campos devem ser persistidos.

---

# 16. Funcionalidades iniciais

Implementar na seguinte ordem:

## Autenticação

Endpoints:

```text
POST /api/v1/auth/register
POST /api/v1/auth/login
POST /api/v1/auth/refresh
```

Utilizar:

* Spring Security.
* JWT.
* PasswordEncoder.
* BCrypt ou algoritmo adequado.

Nunca salvar senha em texto puro.

---

## Usuário

```text
GET /api/v1/users/me
PUT /api/v1/users/me
```

O usuário autenticado deve acessar apenas seus próprios dados.

---

## Empresas

```text
POST /api/v1/companies
GET /api/v1/companies
GET /api/v1/companies/{id}
PUT /api/v1/companies/{id}
DELETE /api/v1/companies/{id}
```

Garantir que um usuário não consiga acessar ou alterar empresas pertencentes a outro usuário.

---

## Geração de conteúdo com IA

Criar inicialmente:

```text
POST /api/v1/ai/generate
```

Exemplo de request:

```json
{
  "companyId": "UUID",
  "contentType": "CAPTION",
  "topic": "Máquina realizando escavação",
  "additionalInstructions": "Criar uma legenda profissional e persuasiva para Instagram"
}
```

O backend deve:

1. Validar o usuário autenticado.
2. Buscar a empresa.
3. Garantir que a empresa pertence ao usuário.
4. Montar o contexto da empresa.
5. Montar o prompt para a IA.
6. Chamar o `AiProvider`.
7. Tratar possíveis erros externos.
8. Validar a resposta.
9. Salvar o histórico.
10. Retornar a resposta ao cliente.

---

# 17. Construção do prompt

Criar um componente ou serviço específico responsável pela construção dos prompts.

Exemplo:

```text
PromptBuilderService
```

Não espalhar textos gigantes de prompt dentro de Controllers.

O prompt deve conter:

```text
Você é um especialista em marketing digital e redes sociais.

Contexto da empresa:

Nome: {companyName}
Segmento: {segment}
Descrição: {description}
Público-alvo: {targetAudience}
Tom de comunicação: {communicationTone}
Objetivo principal: {mainObjective}

Tarefa solicitada:

Tipo de conteúdo: {contentType}
Tema: {topic}
Instruções adicionais: {additionalInstructions}

Gere uma resposta relevante para a empresa.
Não invente informações específicas sobre produtos ou serviços que não foram fornecidas.
Respeite o tom de comunicação informado.
```

O `PromptBuilderService` deve ser facilmente testável.

---

# 18. Mapeamento

Avaliar a utilização de **MapStruct** para mapeamentos entre:

```text
Entity → Response DTO
Request DTO → Entity
```

Caso o projeto seja pequeno no início, o mapeamento manual também pode ser utilizado.

Explicar a decisão.

Não colocar lógica de negócio complexa dentro dos Mappers.

---

# 19. Paginação e filtros

Endpoints de listagem devem suportar paginação.

Exemplo:

```text
GET /api/v1/companies?page=0&size=10&sort=name,asc
```

Criar um padrão consistente para respostas paginadas.

Futuramente permitir filtros no histórico de conteúdo:

* Tipo.
* Empresa.
* Status.
* Data inicial.
* Data final.

---

# 20. Logs

Utilizar SLF4J.

Não utilizar `System.out.println`.

Os logs devem ser úteis para debugging.

Exemplo:

```java
log.info("Solicitação de geração de conteúdo recebida. CompanyId: {}", companyId);
```

Em caso de erro:

```java
log.error(
    "Erro ao gerar conteúdo para empresa {}",
    companyId,
    exception
);
```

Nunca registrar:

* Senhas.
* Tokens JWT completos.
* API Keys.
* Dados sensíveis desnecessários.

---

# 21. Resiliência da integração com IA

Implementar gradualmente mecanismos para integração externa:

* Timeout.
* Tratamento de erro HTTP.
* Rate limit.
* Retry apenas para erros adequados.
* Circuit breaker futuramente, utilizando Resilience4j.

Não fazer retry para qualquer erro indiscriminadamente.

Exemplos de erros que precisam ser diferenciados:

```text
400 → requisição inválida
401 → credencial inválida
429 → limite excedido
5xx → erro do provedor
timeout → indisponibilidade ou lentidão
```

---

# 22. Testes

Criar testes unitários para os principais Services.

Utilizar:

* JUnit 5.
* Mockito.
* AssertJ quando fizer sentido.

Testar principalmente:

### CompanyService

* Criar empresa com sucesso.
* Buscar empresa existente.
* Empresa não encontrada.
* Usuário tentando acessar empresa de outro usuário.

### AiContentService

* Geração com sucesso.
* Empresa inexistente.
* Empresa de outro usuário.
* Erro do provedor.
* Rate limit.
* Timeout.
* Falha ao salvar histórico.

Não testar apenas getters, setters ou código sem regra de negócio.

Para evoluir o projeto, adicionar testes de integração.

---

# 23. Segurança

Implementar:

* JWT.
* Senhas criptografadas.
* Endpoints públicos e privados claramente definidos.
* Autorização baseada no usuário autenticado.
* Nunca confiar no `userId` enviado pelo frontend.
* Obter o usuário autenticado pelo contexto de segurança.
* Validação de ownership.

Exemplo de regra:

```text
Usuário A não pode acessar dados da Empresa B pertencente ao Usuário B.
```

Essa regra deve ser validada no backend.

---

# 24. Padrão de desenvolvimento

Sempre seguir estes princípios:

## SOLID

Aplicar quando fizer sentido, sem criar abstrações artificiais.

## Clean Code

Priorizar:

* Métodos pequenos.
* Nomes claros.
* Responsabilidades bem definidas.
* Baixo acoplamento.
* Código fácil de testar.

## DRY

Evitar duplicação desnecessária.

Mas não criar abstrações prematuras apenas para eliminar poucas linhas duplicadas.

## KISS

A solução deve ser simples antes de ser complexa.

Não implementar microsserviços no MVP.

O projeto deve começar como um **monólito modular bem estruturado**.

---

# 25. O que NÃO quero

Não quero:

* Controller com regra de negócio.
* Entity sendo retornada diretamente pela API.
* `try/catch` em todos os métodos sem necessidade.
* `catch (Exception)` usado como padrão.
* `@Data` indiscriminadamente em Entities.
* API Key escrita no código.
* Senhas sem criptografia.
* Classes gigantescas com muitas responsabilidades.
* Dependências circulares.
* Streams desnecessárias.
* Abstrações exageradas.
* Microsserviços prematuros.
* Código complexo apenas para parecer “enterprise”.

---

# 26. Organização no Trello — PBI e Tasks

Quero que o projeto seja dividido em **PBIs e Tasks**, como em um projeto Scrum.

Para cada PBI, informar:

* Nome do PBI.
* Objetivo.
* User Story.
* Critérios de aceite.
* Prioridade.
* Dependências.
* Estimativa de complexidade.
* Lista detalhada de Tasks.
* Definition of Done.

Exemplo:

## PBI 01 — Configuração inicial do projeto

### User Story

Como desenvolvedor, quero configurar a estrutura inicial do projeto para que seja possível iniciar o desenvolvimento em um ambiente padronizado.

### Critérios de aceite

* Projeto Spring Boot criado.
* PostgreSQL configurado.
* Docker Compose funcionando.
* Flyway configurado.
* Swagger funcionando.
* Profiles configurados.
* Variáveis sensíveis fora do código.

### Tasks

* Criar projeto Spring Boot.
* Configurar Java.
* Configurar Maven.
* Configurar PostgreSQL.
* Criar Docker Compose.
* Configurar `application.yml`.
* Criar profile local.
* Configurar Swagger.
* Criar `.env.example`.
* Criar `.gitignore`.

---

# 27. Backlog inicial esperado

Criar e detalhar inicialmente PBIs semelhantes aos seguintes:

### PBI 01

Setup e estrutura inicial.

### PBI 02

Configuração do banco PostgreSQL e Flyway.

### PBI 03

Autenticação e segurança com JWT.

### PBI 04

Gerenciamento de usuário.

### PBI 05

Gerenciamento de empresas.

### PBI 06

Arquitetura de integração com IA.

### PBI 07

Integração com OpenAI.

### PBI 08

Geração de legendas.

### PBI 09

Geração de hashtags.

### PBI 10

Geração de ideias de conteúdo.

### PBI 11

Geração de roteiros para Reels.

### PBI 12

Geração de calendário de conteúdo.

### PBI 13

Histórico de conteúdos gerados.

### PBI 14

Tratamento global de exceptions.

### PBI 15

Logs e observabilidade básica.

### PBI 16

Testes unitários.

### PBI 17

Testes de integração.

### PBI 18

Dockerização completa.

### PBI 19

Documentação do projeto e README.

### PBI 20

Melhorias de resiliência e integração externa.

Para cada PBI, criar Tasks pequenas e executáveis, evitando uma Task genérica como:

```text
Fazer autenticação
```

Preferir:

```text
Criar entidade User
Criar migration da tabela users
Criar UserRepository
Criar DTO de registro
Adicionar validações
Criar UserService
Implementar UserServiceImpl
Criar PasswordEncoder
Criar endpoint de registro
Criar testes do serviço
Documentar endpoint no Swagger
Criar requisição no Bruno
```

---

# 28. Forma de resposta durante o desenvolvimento

Não quero que você gere o projeto inteiro de uma vez.

Quero desenvolver **PBI por PBI**.

Sempre seguir este fluxo:

## Primeiro

Explicar o objetivo do PBI.

## Segundo

Explicar as decisões arquiteturais.

## Terceiro

Listar todas as Tasks.

## Quarto

Implementar uma Task por vez ou um pequeno grupo de Tasks relacionadas.

## Quinto

Para cada código gerado:

* Informar o caminho completo do arquivo.
* Explicar a responsabilidade da classe.
* Explicar decisões importantes.
* Não omitir imports relevantes.
* Utilizar código compilável.
* Não inventar APIs ou métodos inexistentes.

## Sexto

Ao finalizar o PBI:

* Revisar os critérios de aceite.
* Informar o que foi implementado.
* Informar o que ainda falta.
* Sugerir o próximo PBI.

---

# 29. Regra importante para decisões técnicas

Sempre que existir mais de uma alternativa técnica, não escolher automaticamente a mais complexa.

Apresentar brevemente:

* Alternativa recomendada.
* Motivo da escolha.
* Quando outra alternativa seria melhor.

Exemplo:

```text
WebClient vs RestTemplate
MapStruct vs Mapper manual
Record vs Class
UUID vs Long
Interface vs classe concreta
Try/catch local vs GlobalExceptionHandler
```

Priorizar decisões adequadas para um projeto de portfólio de Back-end Java moderno.

---

# 30. Primeira resposta esperada

Como primeira etapa, NÃO implemente código.

Primeiro faça uma análise completa da arquitetura proposta e entregue:

1. Visão geral da arquitetura.
2. Diagrama textual do fluxo da aplicação.
3. Estrutura de pacotes recomendada.
4. Modelo inicial das entidades e relacionamentos.
5. Escolha e justificativa do banco de dados.
6. Estratégia para integração com IA.
7. Como a OpenAI API será abstraída.
8. Estratégia de tratamento de exceptions.
9. Estratégia de autenticação e autorização.
10. Estratégia para DTOs e validações.
11. Estratégia para Swagger.
12. Estratégia para Bruno.
13. Estratégia de testes.
14. Lista completa dos PBIs.
15. Tasks detalhadas do PBI 01.
16. Critérios de aceite do PBI 01.

Após isso, aguarde minha aprovação antes de gerar o código do PBI 01.

O projeto deve ser tratado como um sistema real, desenvolvido gradualmente, com foco em qualidade de código, boas práticas e aprendizado profissional de Java Back-end.

Nunca gere várias funcionalidades ou PBIs de uma única vez. Sempre analise a tarefa atual, verifique as instruções e a estrutura existente do projeto antes de alterar arquivos. Implemente apenas o escopo solicitado. Não sobrescreva código existente sem necessidade.

Antes de criar uma nova classe, verifique se já existe uma classe, DTO, Service, Interface ou Exception com responsabilidade semelhante no projeto. Reutilize abstrações existentes quando apropriado.

# Language and Naming Conventions

## Código

**Todo o código-fonte do projeto deve ser escrito exclusivamente em inglês.**

Isso inclui:

* Classes.
* Interfaces.
* Enums.
* Methods.
* Variables.
* Constants.
* Packages.
* DTOs.
* Entities.
* Repositories.
* Services.
* Controllers.
* Exceptions.
* Database tables.
* Database columns.
* Migration names.
* API endpoint names.
* JSON request/response fields.
* Log messages.
* Test names.
* Test methods.

### Exemplos corretos

```java
public class CompanyServiceImpl {

    public CompanyResponse createCompany(CompanyCreateRequest request) {
        // ...
    }
}
```

```java
public class ResourceNotFoundException extends RuntimeException {
}
```

```java
public enum ContentType {
    CAPTION,
    HASHTAGS,
    CONTENT_IDEA,
    REELS_SCRIPT
}
```

### Exemplos incorretos

```java
public class EmpresaServiceImpl {
}
```

```java
public class ServicoEmpresa {
}
```

```java
public void criarEmpresa() {
}
```

Não utilizar português em identificadores de código.

---

# API

Os endpoints também devem utilizar inglês.

### Correto

```text
/api/v1/companies
/api/v1/users/me
/api/v1/content
/api/v1/auth/login
```

### Incorreto

```text
/api/v1/empresas
/api/v1/usuarios
/api/v1/conteudos
/api/v1/autenticacao
```

Os campos JSON também devem utilizar inglês.

### Correto

```json
{
  "companyId": "UUID",
  "contentType": "CAPTION",
  "additionalInstructions": "..."
}
```

### Incorreto

```json
{
  "empresaId": "UUID",
  "tipoConteudo": "LEGENDA",
  "instrucoesAdicionais": "..."
}
```

---

# Database

Tabelas e colunas devem utilizar inglês.

Preferir `snake_case` no PostgreSQL.

Exemplo:

```text
users
companies
content_generations
created_at
updated_at
company_id
communication_tone
target_audience
```

Evitar:

```text
usuarios
empresas
geracoes_conteudo
data_criacao
nome_empresa
```

---

# Java Naming Conventions

Seguir as convenções padrão do Java:

### Classes

`PascalCase`

```text
CompanyService
CompanyServiceImpl
ContentGeneration
GlobalExceptionHandler
OpenAiProviderImpl
```

### Methods

`camelCase`

```text
createCompany()
findCompanyById()
generateContent()
buildPrompt()
```

### Variables

`camelCase`

```text
companyId
contentType
additionalInstructions
generatedContent
```

### Constants

`UPPER_SNAKE_CASE`

```java
private static final int DEFAULT_PAGE_SIZE = 10;
```

### Packages

Sempre em lowercase:

```text
com.nexel.socialai.company
com.nexel.socialai.content
com.nexel.socialai.ai
```

---

# Tests

Os testes também devem ser escritos em inglês.

Exemplo:

```java
@Test
void shouldCreateCompanySuccessfully() {
}
```

```java
@Test
void shouldThrowExceptionWhenCompanyDoesNotExist() {
}
```

Não utilizar:

```java
void deveCriarEmpresaComSucesso()
```

---

# Comments

Comentários no código também devem ser escritos em inglês.

Porém, evitar comentários desnecessários.

O código deve ser suficientemente claro através de nomes apropriados.

Preferir:

```java
// Build the company context used by the AI provider.
```

Somente adicionar comentários quando eles explicarem uma decisão ou comportamento que não seja óbvio pelo código.

---

# Logs

Logs também devem ser escritos em inglês.

Correto:

```java
log.info("Company created successfully. CompanyId: {}", companyId);
```

Incorreto:

```java
log.info("Empresa criada com sucesso. CompanyId: {}", companyId);
```

---

# Documentation

A documentação técnica do código deve ser escrita em inglês quando fizer parte do código ou documentação técnica do projeto.

O README e documentos de arquitetura podem utilizar português caso sejam destinados ao desenvolvedor brasileiro responsável pelo projeto, porém:

**qualquer elemento técnico que faça parte do sistema deve permanecer em inglês.**

---

# Golden Rule

When generating or modifying code:

> **Always use English for all technical identifiers, code elements, API contracts, database objects, logs, tests, and comments. Never create Portuguese identifiers.**

