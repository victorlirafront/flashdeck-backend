# Flashcards API - Backend

API REST para sistema de flashcards com autenticação JWT.

## 🚀 Tecnologias

- **Java 17**
- **Spring Boot 4.0.0**
- **Spring Security** - Autenticação e autorização
- **Spring Data JPA** - Persistência de dados
- **H2 Database** - Banco de dados em memória
- **JWT (Auth0)** - Tokens de autenticação
- **Maven** - Gerenciamento de dependências

## 📁 Estrutura do Projeto

```
src/
 └── main/
      ├── java/
      │    └── com/flashcards/
      │          ├── application/          # Camada de Aplicação
      │          │     ├── dto/           # Data Transfer Objects
      │          │     │     └── auth/
      │          │     │           ├── LoginRequest.java
      │          │     │           ├── LoginResponse.java
      │          │     │           └── RegisterRequest.java
      │          │     ├── mapper/        # Mappers
      │          │     │     └── UserMapper.java
      │          │     └── usecases/      # Casos de Uso
      │          │           └── auth/
      │          │                 ├── LoginUseCase.java
      │          │                 └── RegisterUserUseCase.java
      │          │
      │          ├── domain/              # Camada de Domínio
      │          │     ├── entity/        # Entidades
      │          │     │     └── User.java
      │          │     ├── valueobject/    # Value Objects
      │          │     │     ├── Email.java
      │          │     │     └── Password.java
      │          │     ├── repository/    # Interfaces de Repositório
      │          │     │     └── UserRepository.java
      │          │     └── exception/     # Exceções de Domínio
      │          │           ├── InvalidEmailException.java
      │          │           ├── InvalidCredentialsException.java
      │          │           ├── UserAlreadyExistsException.java
      │          │           └── UserNotFoundException.java
      │          │
      │          ├── infrastructure/       # Camada de Infraestrutura
      │          │     ├── controller/   # Controllers REST
      │          │     │     └── AuthController.java
      │          │     ├── persistence/   # Persistência JPA
      │          │     │     └── jpa/
      │          │     │           ├── entity/
      │          │     │           │     └── UserEntity.java
      │          │     │           ├── repository/
      │          │     │           │     └── JpaUserRepository.java
      │          │     │           ├── mapper/
      │          │     │           │     └── UserEntityMapper.java
      │          │     │           └── adapter/
      │          │     │                 └── UserRepositoryAdapter.java
      │          │     ├── security/      # Segurança
      │          │     │     ├── JwtTokenProvider.java
      │          │     │     └── JwtAuthenticationFilter.java
      │          │     ├── config/        # Configurações
      │          │     │     ├── SecurityConfig.java
      │          │     │     └── CorsConfig.java
      │          │     └── exception/     # Exception Handlers
      │          │           └── GlobalExceptionHandler.java
      │          │
      │          └── FlashcardsApplication.java
      │
      └── resources/
            └── application.properties
```

## 🔧 Configuração

### application.properties

```properties
# Database
spring.datasource.url=jdbc:h2:mem:flashcards
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true

# JWT Configuration
api.security.token.secret=your-secret-key-change-in-production-minimum-256-bits
api.security.token.expiration-hours=2
```

## 🚀 Como Executar

### Pré-requisitos

- Java 17 ou superior
- Maven 3.6+ (ou use o Maven Wrapper incluído)

### Executar a aplicação

```bash
# Usando Maven Wrapper
./mvnw spring-boot:run

# Ou usando Maven instalado
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## 📡 Endpoints

### Autenticação

#### POST /auth/register
Registra um novo usuário.

**Request:**
```json
{
    "name": "Test User",
    "email": "test@example.com",
    "password": "password123"
}
```

**Response (201 Created):**
```json
{
    "name": "Test User",
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### POST /auth/login
Faz login e retorna token JWT.

**Request:**
```json
{
    "email": "test@example.com",
    "password": "password123"
}
```

**Response (200 OK):**
```json
{
    "name": "Test User",
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

## 🔐 Autenticação

Após fazer login ou registro, você receberá um token JWT. Use este token em requisições autenticadas:

```
Authorization: Bearer <seu-token-jwt>
```

O token tem validade de 2 horas (configurável).

## 🧪 Testando a API

### Usando Postman

1. Importe a collection: `docs/Flashcards_API.postman_collection.json`
2. Execute as requisições na ordem: Register → Login
3. O token JWT será salvo automaticamente nas variáveis

### Usando cURL

**Registro:**
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User",
    "email": "test@example.com",
    "password": "password123"
  }'
```

**Login:**
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123"
  }'
```

## 📋 Códigos de Status

- **200 OK** - Requisição bem-sucedida
- **201 Created** - Recurso criado com sucesso
- **400 Bad Request** - Dados inválidos
- **401 Unauthorized** - Credenciais inválidas
- **404 Not Found** - Recurso não encontrado
- **409 Conflict** - Email já cadastrado
- **500 Internal Server Error** - Erro interno do servidor

## 🏗️ Arquitetura

O projeto segue os princípios de **Clean Architecture** e **DDD (Domain-Driven Design)**:

- **Domain Layer**: Contém as regras de negócio e entidades
- **Application Layer**: Contém os casos de uso e DTOs
- **Infrastructure Layer**: Contém implementações técnicas (JPA, Security, Controllers)

## 📝 Notas

- O banco de dados H2 é em memória, então os dados são perdidos ao reiniciar a aplicação
- Para produção, altere o secret do JWT em `application.properties`
- Configure CORS adequadamente para produção

## 📄 Licença

Este projeto é privado.
