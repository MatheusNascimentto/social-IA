# Nexel Social AI — API

## 1. Base URL

Ambiente local:

```text
http://localhost:8080/api/v1
```

---

# 2. Padrão de resposta

As APIs devem possuir contratos consistentes.

Sucesso:

```json
{
  "data": {},
  "message": "Operação realizada com sucesso"
}
```

Erros:

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

O formato definitivo poderá ser ajustado conforme o desenvolvimento.

---

# 3. Versionamento

Todas as APIs públicas devem utilizar versionamento:

```text
/api/v1
```

---

# 4. Authentication

## POST `/auth/register`

Cria um novo usuário.

### Request

```json
{
  "name": "Matheus",
  "email": "matheus@email.com",
  "password": "Password123!"
}
```

### Response

HTTP `201 Created`.

---

## POST `/auth/login`

Autentica um usuário.

### Request

```json
{
  "email": "matheus@email.com",
  "password": "Password123!"
}
```

### Response

```json
{
  "accessToken": "JWT_TOKEN",
  "tokenType": "Bearer",
  "expiresIn": 3600
}
```

---

# 5. Users

## GET `/users/me`

Retorna os dados do usuário autenticado.

Requer:

```text
Authorization: Bearer {token}
```

---

## PUT `/users/me`

Atualiza dados do usuário autenticado.

---

# 6. Companies

## POST `/companies`

Cria uma empresa.

### Request

```json
{
  "name": "RJ Vision Terraplanagem",
  "segment": "Terraplanagem",
  "description": "Empresa especializada em serviços de terraplanagem.",
  "targetAudience": "Proprietários de terrenos e empresas",
  "communicationTone": "Profissional e direto",
  "mainObjective": "Gerar novos orçamentos"
}
```

---

## GET `/companies`

Lista empresas pertencentes ao usuário autenticado.

Suporta paginação:

```text
?page=0&size=10&sort=name,asc
```

---

## GET `/companies/{id}`

Retorna uma empresa específica.

O usuário somente poderá acessar empresas das quais possui autorização.

---

## PUT `/companies/{id}`

Atualiza uma empresa.

---

## DELETE `/companies/{id}`

Exclui uma empresa.

---

# 7. AI Content Generation

## POST `/ai/generate`

Gera conteúdo utilizando Inteligência Artificial.

### Request

```json
{
  "companyId": "UUID",
  "contentType": "CAPTION",
  "topic": "Máquina realizando escavação",
  "additionalInstructions": "Criar uma legenda profissional e persuasiva para Instagram."
}
```

### Content Types

```text
CAPTION
HASHTAGS
CONTENT_IDEA
REELS_SCRIPT
CONTENT_CALENDAR
IMPROVE_CONTENT
```

### Response

```json
{
  "id": "UUID",
  "companyId": "UUID",
  "contentType": "CAPTION",
  "content": "Texto gerado pela IA...",
  "provider": "OPENAI",
  "model": "MODEL_NAME",
  "createdAt": "2026-08-25T21:00:00"
}
```

---

# 8. Content History

## GET `/content`

Lista conteúdos gerados.

Parâmetros possíveis:

```text
page
size
sort
companyId
contentType
status
startDate
endDate
```

---

## GET `/content/{id}`

Retorna detalhes de um conteúdo gerado.

---

# 9. HTTP Status Codes

Utilizar:

| Status | Uso                      |
| ------ | ------------------------ |
| 200    | Operação realizada       |
| 201    | Recurso criado           |
| 204    | Operação sem conteúdo    |
| 400    | Request inválido         |
| 401    | Não autenticado          |
| 403    | Sem permissão            |
| 404    | Recurso não encontrado   |
| 409    | Conflito                 |
| 422    | Regra de validação       |
| 429    | Rate limit               |
| 500    | Erro interno             |
| 502    | Erro de provedor externo |
| 503    | Serviço indisponível     |

---

# 10. Swagger

Swagger/OpenAPI deve documentar:

* Endpoints.
* Request DTOs.
* Response DTOs.
* Exemplos.
* Status codes.
* Autenticação.
* Possíveis erros.

Utilizar:

```text
@Tag
@Operation
@ApiResponse
@Parameter
@Schema
```

---

# 11. Bruno

As requisições Bruno devem ser organizadas:

```text
bruno/
├── auth/
├── users/
├── companies/
├── ai/
└── content/
```

As collections devem ser versionadas junto com o projeto.

Utilizar ambientes:

```text
local
dev
```

Variáveis sensíveis não devem ser commitadas.

---

# 12. Regras de API

* Nunca expor Entity diretamente.
* Validar todos os Requests.
* Utilizar DTOs.
* Utilizar autenticação nos endpoints privados.
* Validar ownership dos recursos.
* Nunca confiar em IDs de usuário enviados pelo cliente.
* Documentar endpoints no Swagger.
* Manter compatibilidade dentro da mesma versão da API.
* Utilizar `/api/v1`.
