# Social AI

 Social AI é uma API backend em Java + Spring Boot para auxiliar empresas e profissionais de social media na criação de conteúdo com suporte de Inteligência Artificial.

## Pré-requisitos

- Java 25
- Docker e Docker Compose
- Maven Wrapper (`./mvnw`)

## Ambiente local

1. Copie `.env.example` para `.env` e ajuste os valores.
2. Inicie os serviços de infraestrutura:

   ```bash
   docker compose up -d
   ```

3. Execute a aplicação:

   ```bash
   ./mvnw spring-boot:run
   ```

## Swagger / OpenAPI

Após iniciar a aplicação, acesse:

- Swagger UI: http://localhost:8080/swagger-ui/index.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

Se estiver usando o profile `test`, o Swagger também pode ser acessado a partir do mesmo endpoint quando a aplicação subir em ambiente local com as configurações corretas.

## Profiles

- `local`: perfil padrão para desenvolvimento local
- `dev`: perfil para ambiente de desenvolvimento
- `prod`: perfil de produção, com Swagger desabilitado por padrão
- `test`: perfil para testes automáticos, usando H2

## Exemplos de uso da API

### 1) Registrar usuário

```bash
curl --location 'http://localhost:8080/auth/register' \
  --header 'Content-Type: application/json' \
  --data '{
    "fullName": "Maria Silva",
    "email": "maria@example.com",
    "password": "P@ssw0rd123"
  }'
```

### 2) Fazer login

```bash
curl --location 'http://localhost:8080/auth/login' \
  --header 'Content-Type: application/json' \
  --data '{
    "email": "maria@example.com",
    "password": "P@ssw0rd123"
  }'
```

### 3) Obter perfil do usuário autenticado

```bash
curl --location 'http://localhost:8080/users/me' \
  --header 'Authorization: Bearer <SEU_JWT>'
```

### 4) Atualizar perfil do usuário autenticado

```bash
curl --location --request PUT 'http://localhost:8080/users/me' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: Bearer <SEU_JWT>' \
  --data '{
    "fullName": "Maria Silva",
    "password": "NovaSenha123"
  }'
```

### 5) Criar empresa

```bash
curl --location 'http://localhost:8080/companies' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: Bearer <SEU_JWT>' \
  --data '{
    "name": "Nexel Labs",
    "segment": "Technology"
  }'
```

### 6) Listar empresas do usuário autenticado

```bash
curl --location 'http://localhost:8080/companies?page=0&size=10' \
  --header 'Authorization: Bearer <SEU_JWT>'
```

### 7) Buscar empresa por ID

```bash
curl --location 'http://localhost:8080/companies/<COMPANY_ID>' \
  --header 'Authorization: Bearer <SEU_JWT>'
```

### 8) Atualizar empresa

```bash
curl --location --request PUT 'http://localhost:8080/companies/<COMPANY_ID>' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: Bearer <SEU_JWT>' \
  --data '{
    "name": "Nexel Labs Updated",
    "segment": "Marketing"
  }'
```

### 9) Excluir empresa

```bash
curl --location --request DELETE 'http://localhost:8080/companies/<COMPANY_ID>' \
  --header 'Authorization: Bearer <SEU_JWT>'
```

## Build e testes

```bash
./mvnw clean test
```

## Observações

- O token JWT deve ser enviado no header `Authorization` com o prefixo `Bearer`.
- Reis e campos sensíveis devem ficar fora do código-fonte.
- Para ambiente de produção, utilize variáveis de ambiente ou secret manager.

