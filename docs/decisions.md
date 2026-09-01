# Nexel Social AI — Architecture Decisions

Este documento registra decisões técnicas importantes do projeto.

O objetivo é evitar que decisões arquiteturais sejam esquecidas ou alteradas sem justificativa.

---

# ADR-001 — Utilização do Java 25

## Status

Accepted

## Decisão

Utilizar Java 25.

## Motivo

Java 25 é a versão atual do ambiente de desenvolvimento adotado para o projeto e fornece recursos modernos da linguagem e da plataforma.

---

# ADR-002 — Spring Boot

## Status

Accepted

## Decisão

Utilizar Spring Boot como framework principal do backend.

## Motivo

Spring Boot fornece recursos maduros para:

* APIs REST.
* Segurança.
* Persistência.
* Validação.
* Injeção de dependências.
* Configuração.
* Testes.

---

# ADR-003 — PostgreSQL

## Status

Accepted

## Decisão

Utilizar PostgreSQL como banco de dados principal.

## Motivo

PostgreSQL é robusto, open source, amplamente utilizado no mercado e adequado para o modelo relacional do sistema.

Também permite futuras funcionalidades relacionadas a dados estruturados e extensões avançadas.

---

# ADR-004 — Flyway

## Status

Accepted

## Decisão

Utilizar Flyway para controle das migrations.

## Motivo

Permite versionar alterações do banco junto ao código.

Exemplo:

```text
V1__create_users.sql
V2__create_companies.sql
V3__create_content_generation.sql
```

Evitar depender de `ddl-auto=update`.

---

# ADR-005 — UUID

## Status

Accepted

## Decisão

Utilizar UUID como identificador das entidades principais.

## Motivo

Evita IDs sequenciais previsíveis expostos pela API e facilita futuras estratégias de distribuição.

---

# ADR-006 — Monólito modular

## Status

Accepted

## Decisão

O projeto será inicialmente desenvolvido como monólito modular.

## Motivo

O sistema ainda está em estágio inicial.

Microsserviços adicionariam complexidade operacional sem benefício proporcional.

A arquitetura deverá permitir separação futura caso exista necessidade real.

---

# ADR-007 — Service Interface + Implementation

## Status

Accepted

## Decisão

Utilizar interfaces para os principais Services e implementações separadas.

Exemplo:

```text
CompanyService
CompanyServiceImpl
```

## Motivo

Permite separar contrato e implementação e facilita testes e futuras substituições quando houver necessidade real.

Não criar interfaces para componentes que não se beneficiem da abstração.

---

# ADR-008 — WebClient

## Status

Accepted

## Decisão

Utilizar WebClient para comunicação HTTP com APIs externas.

## Motivo

É a abordagem moderna dentro do ecossistema Spring para novas integrações HTTP.

Evitar RestTemplate em novas implementações.

---

# ADR-009 — OpenAI como primeiro AI Provider

## Status

Accepted

## Decisão

Utilizar OpenAI como primeiro provedor de Inteligência Artificial.

## Motivo

O projeto possui como objetivo principal estudar integração entre Java/Spring Boot e modelos de IA generativa.

A implementação será abstraída através de:

```text
AiProvider
```

permitindo futuros providers.

---

# ADR-010 — Abstração AiProvider

## Status

Accepted

## Decisão

A regra de negócio não deve depender diretamente da OpenAI.

Estrutura:

```text
AiProvider
    ↓
OpenAiProviderImpl
```

## Motivo

Permitir troca de fornecedor sem modificar o domínio principal da aplicação.

---

# ADR-011 — DTOs

## Status

Accepted

## Decisão

Não expor Entities diretamente através da API.

Utilizar Request DTOs e Response DTOs.

## Motivo

Permite controlar o contrato externo da API e evitar acoplamento entre banco e API.

---

# ADR-012 — Tratamento global de Exceptions

## Status

Accepted

## Decisão

Utilizar `@RestControllerAdvice`.

## Motivo

Centralizar o tratamento de erros HTTP e manter Controllers limpos.

Exceptions específicas deverão ser utilizadas para representar erros relevantes.

---

# ADR-013 — Try/Catch

## Status

Accepted

## Decisão

Não utilizar try/catch indiscriminadamente nos Services.

Utilizar principalmente para:

* Integrações externas.
* Tradução de exceptions.
* Tratamento contextual.
* Operações que exigem recuperação ou compensação.

## Motivo

Evitar código repetitivo e mascaramento de erros.

---

# ADR-014 — Bruno

## Status

Accepted

## Decisão

Utilizar Bruno para testes manuais das APIs.

## Motivo

As collections podem ser armazenadas e versionadas junto ao código do projeto.

Isso facilita compartilhamento e reprodução do ambiente.

---

# ADR-015 — Swagger/OpenAPI

## Status

Accepted

## Decisão

Utilizar Springdoc OpenAPI para documentação.

## Motivo

Permitir documentação automática e testes rápidos dos endpoints.

---

# ADR-016 — Testes

## Status

Accepted

## Decisão

Utilizar:

* JUnit 5
* Mockito
* AssertJ

Testes unitários serão prioridade para Services e regras de negócio.

Testes de integração serão adicionados progressivamente.

---

# ADR-017 — Docker

## Status

Accepted

## Decisão

Utilizar Docker para infraestrutura local.

Inicialmente:

```text
Application
PostgreSQL
```

## Motivo

Padronizar ambiente de desenvolvimento e facilitar setup.

---

# ADR-018 — Java moderno

## Status

Accepted

## Decisão

Utilizar recursos modernos do Java quando melhorarem o código.

Exemplos:

* Streams
* Optional
* Records
* Method References
* switch expressions

## Regra

Não utilizar recursos modernos apenas por estética.

Legibilidade e manutenção possuem prioridade.

---

# ADR-019 — Não utilizar microsserviços inicialmente

## Status

Accepted

## Decisão

Não utilizar arquitetura de microsserviços no MVP.

## Motivo

A complexidade operacional não é necessária neste estágio.

O sistema será estruturado para permitir evolução futura.

---

# ADR-020 — RAG e documentos

## Status

Proposed

## Decisão

Avaliar posteriormente a implementação de RAG para permitir que a IA utilize documentos e informações próprias das empresas.

Possíveis tecnologias futuras:

* Embeddings.
* Vector Database.
* PostgreSQL + pgvector.
* Pipeline de processamento de documentos.

Essa decisão será revisada após o MVP da geração de conteúdo.

---

# Como adicionar novas decisões

Novas decisões devem seguir este formato:

```text
# ADR-XXX — Título

## Status

Proposed / Accepted / Deprecated / Superseded

## Contexto

Qual problema estamos tentando resolver?

## Decisão

Qual solução foi escolhida?

## Alternativas consideradas

Quais alternativas foram avaliadas?

## Motivo

Por que essa alternativa foi escolhida?

## Consequências

Quais são os benefícios e trade-offs?
```

Não alterar uma decisão anterior silenciosamente.

Caso uma decisão seja substituída, registrar a nova decisão e marcar a anterior como `Superseded`.
