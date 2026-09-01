# Nexel Social AI — Backlog

## Objetivo

Desenvolver uma plataforma backend capaz de utilizar Inteligência Artificial para auxiliar empresas e profissionais de social media na criação de conteúdos para redes sociais.

---

# PBI 01 — Setup inicial

### Objetivo

Criar a estrutura base do projeto.

### Tasks

* [ ] Criar projeto Spring Boot.
* [ ] Configurar Java 25.
* [ ] Configurar Maven.
* [ ] Configurar estrutura de pacotes.
* [ ] Configurar Lombok.
* [ ] Configurar Spring Web.
* [ ] Configurar Spring Validation.
* [ ] Configurar Swagger.
* [ ] Configurar profiles.
* [ ] Criar `.gitignore`.
* [ ] Criar `.env.example`.
* [ ] Criar Docker Compose inicial.
* [ ] Criar README inicial.

### Critérios de aceite

* Projeto inicia sem erros.
* Swagger disponível.
* Estrutura de pacotes criada.
* Configurações sensíveis não estão no código.

---

# PBI 02 — Banco de dados

### Objetivo

Configurar PostgreSQL e Flyway.

### Tasks

* [ ] Configurar PostgreSQL.
* [ ] Configurar Docker Compose.
* [ ] Configurar datasource.
* [ ] Configurar Flyway.
* [ ] Criar primeira migration.
* [ ] Validar conexão.
* [ ] Testar execução das migrations.

---

# PBI 03 — Autenticação

### Objetivo

Implementar autenticação segura utilizando JWT.

### Tasks

* [ ] Criar User Entity.
* [ ] Criar User Repository.
* [ ] Criar User DTOs.
* [ ] Criar User Mapper.
* [ ] Criar User Service.
* [ ] Implementar password encoder.
* [ ] Criar endpoint de registro.
* [ ] Criar autenticação.
* [ ] Implementar geração de JWT.
* [ ] Implementar filtro JWT.
* [ ] Configurar SecurityFilterChain.
* [ ] Criar endpoint `/auth/login`.
* [ ] Testar autenticação.
* [ ] Documentar endpoints no Swagger.
* [ ] Criar requests no Bruno.

---

# PBI 04 — Usuário

### Tasks

* [ ] Criar endpoint `/users/me`.
* [ ] Implementar busca do usuário autenticado.
* [ ] Implementar atualização de dados.
* [ ] Criar validações.
* [ ] Criar testes.
* [ ] Documentar Swagger.
* [ ] Criar requests Bruno.

---

# PBI 05 — Empresas

### Objetivo

Permitir que usuários cadastrem suas empresas e marcas.

### Tasks

* [ ] Criar Company Entity.
* [ ] Criar migration.
* [ ] Criar Company Repository.
* [ ] Criar Create DTO.
* [ ] Criar Update DTO.
* [ ] Criar Response DTO.
* [ ] Criar Mapper.
* [ ] Criar CompanyService.
* [ ] Implementar criação.
* [ ] Implementar busca.
* [ ] Implementar listagem paginada.
* [ ] Implementar atualização.
* [ ] Implementar exclusão.
* [ ] Validar ownership.
* [ ] Criar testes.
* [ ] Documentar Swagger.
* [ ] Criar requests Bruno.

---

# PBI 06 — Arquitetura de IA

### Objetivo

Criar abstração para providers de Inteligência Artificial.

### Tasks

* [ ] Criar `AiProvider`.
* [ ] Criar DTOs da integração.
* [ ] Criar configuração do WebClient.
* [ ] Configurar variáveis da OpenAI.
* [ ] Criar `OpenAiProviderImpl`.
* [ ] Implementar comunicação HTTP.
* [ ] Criar tratamento de timeout.
* [ ] Criar tratamento de rate limit.
* [ ] Criar exceptions de integração.
* [ ] Criar testes utilizando mocks.

---

# PBI 07 — Geração de legendas

### Tasks

* [ ] Criar ContentGeneration Entity.
* [ ] Criar migration.
* [ ] Criar ContentGeneration Repository.
* [ ] Criar ContentGeneration DTOs.
* [ ] Criar PromptBuilder.
* [ ] Criar ContentService.
* [ ] Integrar AiProvider.
* [ ] Implementar geração de legenda.
* [ ] Persistir histórico.
* [ ] Criar tratamento de erros.
* [ ] Criar testes.
* [ ] Documentar Swagger.
* [ ] Criar request Bruno.

---

# PBI 08 — Hashtags

### Tasks

* [ ] Criar geração de hashtags.
* [ ] Criar prompt específico.
* [ ] Validar resposta.
* [ ] Persistir histórico.
* [ ] Criar testes.
* [ ] Documentar endpoint.

---

# PBI 09 — Ideias de conteúdo

### Tasks

* [ ] Criar geração de ideias.
* [ ] Criar prompt específico.
* [ ] Definir formato da resposta.
* [ ] Validar resposta.
* [ ] Persistir histórico.
* [ ] Criar testes.

---

# PBI 10 — Roteiros para Reels

### Tasks

* [ ] Criar geração de roteiro.
* [ ] Definir estrutura do roteiro.
* [ ] Criar prompt.
* [ ] Validar resposta.
* [ ] Persistir histórico.
* [ ] Criar testes.

---

# PBI 11 — Calendário de conteúdo

### Tasks

* [ ] Definir modelo de calendário.
* [ ] Criar DTOs.
* [ ] Criar prompt.
* [ ] Implementar geração.
* [ ] Persistir calendário.
* [ ] Criar endpoint de consulta.
* [ ] Criar testes.

---

# PBI 12 — Melhoramento de conteúdo

### Tasks

* [ ] Criar request para conteúdo existente.
* [ ] Criar prompt.
* [ ] Implementar melhoria.
* [ ] Permitir escolha do tom.
* [ ] Persistir resultado.
* [ ] Criar testes.

---

# PBI 13 — Histórico

### Tasks

* [ ] Criar listagem paginada.
* [ ] Criar filtros.
* [ ] Filtrar por empresa.
* [ ] Filtrar por tipo.
* [ ] Filtrar por período.
* [ ] Criar endpoint de detalhes.
* [ ] Criar testes.

---

# PBI 14 — Tratamento de Exceptions

### Tasks

* [ ] Criar GlobalExceptionHandler.
* [ ] Criar ErrorResponse.
* [ ] Criar exception de recurso não encontrado.
* [ ] Criar exception de regra de negócio.
* [ ] Criar exception de integração.
* [ ] Criar tratamento de validação.
* [ ] Criar tratamento de erros externos.
* [ ] Padronizar respostas.

---

# PBI 15 — Logs

### Tasks

* [ ] Padronizar logs.
* [ ] Adicionar logs de operações importantes.
* [ ] Adicionar logs de integrações.
* [ ] Remover `System.out`.
* [ ] Garantir que informações sensíveis não sejam registradas.

---

# PBI 16 — Testes

### Tasks

* [ ] Criar testes dos Services.
* [ ] Criar testes dos Controllers.
* [ ] Criar testes de exceptions.
* [ ] Criar testes da integração IA.
* [ ] Criar testes de segurança.
* [ ] Criar testes de integração.

---

# PBI 17 — Docker

### Tasks

* [ ] Criar Dockerfile.
* [ ] Configurar container da aplicação.
* [ ] Configurar PostgreSQL.
* [ ] Configurar variáveis.
* [ ] Criar Docker Compose completo.
* [ ] Testar ambiente do zero.

---

# PBI 18 — Documentação

### Tasks

* [ ] Criar README completo.
* [ ] Documentar arquitetura.
* [ ] Documentar execução local.
* [ ] Documentar variáveis de ambiente.
* [ ] Documentar API.
* [ ] Documentar integração IA.
* [ ] Documentar Bruno.

---

# PBI 19 — Resiliência

### Tasks

* [ ] Configurar timeouts.
* [ ] Avaliar retry.
* [ ] Implementar rate limit handling.
* [ ] Avaliar Resilience4j.
* [ ] Implementar circuit breaker quando necessário.

---

# PBI 20 — Evoluções futuras

Possíveis funcionalidades:

* [ ] Redis.
* [ ] RAG.
* [ ] Embeddings.
* [ ] Banco vetorial.
* [ ] Upload de documentos.
* [ ] Leitor inteligente de documentos.
* [ ] Integração com Instagram.
* [ ] Agendamento de posts.
* [ ] Multi-tenancy.
* [ ] Métricas.
* [ ] CI/CD.
