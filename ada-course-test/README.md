# 🚀 Desafio Técnico ADA - Quarkus REST API - Suite de Testes

Este projeto contém uma **suite completa de testes automatizados** para avaliar a implementação do desafio técnico de criação de uma API REST com Quarkus para gerenciar Cursos e Aulas.

## 📊 Sistema de Pontuação

O sistema de testes avalia automaticamente:

- **100 pontos obrigatórios** - Requisitos essenciais
- **30 pontos opcionais** - Funcionalidades extras (Plus)
- **Total possível: 130 pontos**

### Distribuição de Pontos

| Categoria | Pontos Obrigatórios | Pontos Opcionais |
|-----------|---------------------|------------------|
| 📦 Modelo de Dados | 15 | 0 |
| 🔧 CRUD de Curso | 25 | 0 |
| 📡 Endpoints | 15 | 6 |
| 🧾 Validações | 15 | 0 |
| 🌐 Requisitos HTTP | 27 | 3 |
| 🗂️ Persistência | 10 | 0 |
| 🧪 Qualidade | 5 | 3 |
| 🌟 Diferenciais | 0 | 18 |

## 🎯 O que é Testado

### Requisitos Obrigatórios (100 pontos)

#### 📦 Modelo de Dados (15 pontos)
- ✅ Course com campos: id, name, lessons
- ✅ Lesson com campos: id, name

#### 🔧 CRUD de Curso (25 pontos)
- ✅ Criar curso (POST /courses)
- ✅ Listar todos os cursos (GET /courses)
- ✅ Buscar curso por ID (GET /courses/{id})
- ✅ Atualizar curso (PUT /courses/{id})
- ✅ Remover curso (DELETE /courses/{id})

#### 📡 Endpoints (15 pontos)
- ✅ POST /courses
- ✅ GET /courses
- ✅ GET /courses/{id}
- ✅ PUT /courses/{id}
- ✅ DELETE /courses/{id}

#### 🧾 Validações (15 pontos)
- ✅ Course.name: obrigatório, não vazio, mínimo 3 caracteres
- ✅ Lesson.name: obrigatório, não vazio

#### 🌐 Requisitos HTTP (27 pontos)
- ✅ Content-Type: application/json
- ✅ Rejeita outros Content-Types (415)
- ✅ Status codes corretos:
  - 201 Created (POST)
  - 200 OK (GET, PUT)
  - 204 No Content (DELETE)
  - 400 Bad Request (validação)
  - 404 Not Found (recurso não existe)

#### 🗂️ Persistência (10 pontos)
- ✅ Dados persistidos (H2 ou PostgreSQL/MySQL)

#### 🧪 Qualidade (5 pontos)
- ✅ Anotações REST corretas
- ✅ Códigos HTTP corretos
- ✅ Headers corretos
- ✅ Bean Validation

### Requisitos Opcionais (30 pontos)

#### 📡 Endpoints de Lessons (6 pontos)
- 🟢 POST /courses/{courseId}/lessons
- 🟢 GET /courses/{courseId}/lessons

#### 🌐 HTTP Avançado (3 pontos)
- 🟢 Header Location no POST

#### 🧪 Qualidade Avançada (3 pontos)
- 🟢 Organização do código (Resource, DTO, Service, Repository)

#### 🌟 Diferenciais (18 pontos)
- 🟢 Testes com @QuarkusTest (4 pontos)
- 🟢 Tratamento global de exceção (4 pontos)
- 🟢 Paginação em GET /courses (3 pontos)
- 🟢 Uso de DTOs (4 pontos)
- 🟢 Health Check (3 pontos)

## 🛠️ Como Usar

### Pré-requisitos

- Java 17 ou superior
- Maven 3.8+
- **Sua aplicação Quarkus DEVE estar rodando** antes de executar os testes
- Por padrão, os testes conectam em `http://localhost:8081`

### ⚠️ IMPORTANTE: Inicie sua aplicação primeiro!

```bash
# Em um terminal separado, inicie sua aplicação Quarkus
cd seu-projeto-quarkus
./mvnw quarkus:dev
# ou
java -jar target/quarkus-app/quarkus-run.jar
```

### Executar os Testes

#### Opção 1: Maven (Recomendado)

```bash
# Executar todos os testes
mvn clean test

# Executar com relatório detalhado
mvn clean test -Dquarkus.log.level=INFO

# Testar em URL diferente
mvn clean test -Dtest.base.url=http://localhost:8080
```

#### Opção 2: Executar classe específica

```bash
# Executar apenas testes de CRUD
mvn test -Dtest=CRUDOperationsTests

# Executar apenas testes de validação
mvn test -Dtest=ValidationTests

# Executar apenas testes HTTP
mvn test -Dtest=HTTPRequirementsTests
```

#### Opção 3: Runner personalizado (com relatório de pontuação)

```bash
mvn exec:java -Dexec.mainClass="com.ada.challenge.TestSuiteRunner"
```

### Estrutura do Projeto

```
quarkus-course-api-tests/
├── pom.xml
├── README.md
├── CHALLENGE.md
└── src/
    └── test/
        ├── java/
        │   └── com/
        │       └── ada/
        │           └── challenge/
        │               ├── scoring/
        │               │   ├── TestScore.java          # Anotação para pontuação
        │               │   └── ScoreCalculator.java    # Calculadora de pontos
        │               ├── tests/
        │               │   ├── ModelDataTests.java     # Testes do modelo
        │               │   ├── CRUDOperationsTests.java # Testes CRUD
        │               │   ├── EndpointsTests.java     # Testes de endpoints
        │               │   ├── ValidationTests.java    # Testes de validação
        │               │   ├── HTTPRequirementsTests.java # Testes HTTP
        │               │   ├── PersistenceTests.java   # Testes de persistência
        │               │   ├── CodeQualityTests.java   # Testes de qualidade
        │               │   └── OptionalFeaturesTests.java # Testes opcionais
        │               └── TestSuiteRunner.java        # Runner principal
        └── resources/
            └── application.properties                   # Configuração de testes
```

## 📋 Relatório de Pontuação

Após executar os testes, você verá um relatório detalhado como:

```
================================================================================
📊 RELATÓRIO DE PONTUAÇÃO - DESAFIO TÉCNICO ADA
================================================================================

📦 PONTUAÇÃO POR CATEGORIA:
--------------------------------------------------------------------------------
📦 Modelo de Dados            :  15/ 15 pontos (100.0%) - 5/5 testes passaram
🔧 CRUD de Curso              :  25/ 25 pontos (100.0%) - 5/5 testes passaram
📡 Endpoints                  :  21/ 21 pontos (100.0%) - 7/7 testes passaram
🧾 Validações                 :  15/ 15 pontos (100.0%) - 5/5 testes passaram
🌐 Content-Type               :  10/ 10 pontos (100.0%) - 3/3 testes passaram
🌐 Status Codes - POST        :   5/  5 pontos (100.0%) - 3/3 testes passaram
🌐 Status Codes - GET         :   4/  4 pontos (100.0%) - 2/2 testes passaram
🌐 Status Codes - PUT         :   6/  6 pontos (100.0%) - 3/3 testes passaram
🌐 Status Codes - DELETE      :   5/  5 pontos (100.0%) - 2/2 testes passaram
🗂️ Persistência              :  10/ 10 pontos (100.0%) - 1/1 testes passaram
🧪 Qualidade                  :   8/  8 pontos (100.0%) - 6/6 testes passaram
🌟 Diferenciais               :  18/ 18 pontos (100.0%) - 5/5 testes passaram

================================================================================
🔴 PONTOS OBRIGATÓRIOS:
--------------------------------------------------------------------------------
Total: 100/100 pontos (100.0%)

🟢 PONTOS OPCIONAIS (PLUS):
--------------------------------------------------------------------------------
Total: 30/30 pontos (100.0%)

================================================================================
🎯 PONTUAÇÃO FINAL:
--------------------------------------------------------------------------------
TOTAL: 130/130 pontos
Obrigatórios: 100/100 pontos (100.0%)
Opcionais: 30/30 pontos extras

================================================================================
📋 DETALHAMENTO DOS TESTES:
--------------------------------------------------------------------------------
[Lista detalhada de cada teste com status ✅ PASS ou ❌ FAIL]
================================================================================
```

## 🔧 Configuração

### URL da Aplicação

Por padrão, os testes conectam em `http://localhost:8081`.

Para alterar, você pode:

**Opção 1: Via linha de comando**
```bash
mvn clean test -Dtest.base.url=http://localhost:8080
```

**Opção 2: Editar `src/test/resources/application.properties`**
```properties
test.base.url=http://localhost:8081
test.base.path=
```

**Opção 3: Variáveis de ambiente**
```bash
export TEST_BASE_URL=http://localhost:8080
mvn clean test
```

## 📝 Notas Importantes

1. **⚠️ CRÍTICO: Sua aplicação DEVE estar rodando** antes de executar os testes
2. Os testes conectam em uma aplicação **externa** (não iniciam uma aplicação)
3. Os testes são **independentes** mas testam a mesma instância da aplicação
4. Testes marcados com 🔴 são **obrigatórios** (100 pontos)
5. Testes marcados com 🟢 são **opcionais** (30 pontos extras)
6. O relatório mostra **exatamente quantos pontos você conquistou**
7. **Certifique-se que o banco de dados está limpo** antes de executar os testes para resultados consistentes

## 🎓 Para Avaliadores

Este projeto pode ser usado para:

1. **Avaliação automatizada** de implementações do desafio
2. **Feedback imediato** aos candidatos sobre sua pontuação
3. **Identificação rápida** de requisitos não implementados
4. **Comparação objetiva** entre diferentes implementações

## 📚 Tecnologias Utilizadas

- **Quarkus** - Framework Java
- **JUnit 5** - Framework de testes
- **REST Assured** - Testes de API REST
- **Maven** - Gerenciamento de dependências
- **H2 Database** - Banco em memória para testes

## 🤝 Contribuindo

Para adicionar novos testes:

1. Crie uma nova classe em `src/test/java/com/ada/challenge/tests/`
2. Anote com `@QuarkusTest`
3. Use a anotação `@TestScore` em cada método de teste
4. Execute e veja a pontuação atualizada!

## 📄 Licença

Este projeto é fornecido como material educacional para o desafio técnico da ADA.

---

**Boa sorte com o desafio! 🚀**