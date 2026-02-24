# 🚀 Guia Rápido - Como Usar os Testes

## ⚡ Início Rápido (3 passos)

### 1️⃣ ⚠️ IMPORTANTE: Inicie sua aplicação primeiro!

```bash
# Em um terminal SEPARADO, inicie sua aplicação Quarkus
cd seu-projeto-quarkus
./mvnw quarkus:dev

# OU se já compilou:
java -jar target/quarkus-app/quarkus-run.jar

# A aplicação deve estar rodando em http://localhost:8081
# Aguarde até ver a mensagem "Quarkus started"
```

### 2️⃣ Execute os testes (em outro terminal)

**Linux/Mac:**
```bash
./run-tests.sh
```

**Windows:**
```cmd
run-tests.bat
```

**Ou use Maven diretamente:**
```bash
mvn clean test
```

**Para testar em outra porta:**
```bash
mvn clean test -Dtest.base.url=http://localhost:8080
```

### 3️⃣ Veja sua pontuação! 🎯

O relatório será exibido automaticamente no final dos testes.

---

## 📊 Exemplo de Relatório

```
================================================================================
📊 RELATÓRIO DE PONTUAÇÃO - DESAFIO TÉCNICO ADA
================================================================================

📦 PONTUAÇÃO POR CATEGORIA:
--------------------------------------------------------------------------------
📦 Modelo de Dados            :  15/ 15 pontos (100.0%) - 5/5 testes passaram
🔧 CRUD de Curso              :  25/ 25 pontos (100.0%) - 5/5 testes passaram
📡 Endpoints                  :  15/ 21 pontos ( 71.4%) - 5/7 testes passaram
🧾 Validações                 :  15/ 15 pontos (100.0%) - 5/5 testes passaram
🌐 Content-Type               :  10/ 10 pontos (100.0%) - 3/3 testes passaram
🌐 Status Codes - POST        :   5/  5 pontos (100.0%) - 3/3 testes passaram
🌐 Status Codes - GET         :   4/  4 pontos (100.0%) - 2/2 testes passaram
🌐 Status Codes - PUT         :   6/  6 pontos (100.0%) - 3/3 testes passaram
🌐 Status Codes - DELETE      :   5/  5 pontos (100.0%) - 2/2 testes passaram
🗂️ Persistência              :  10/ 10 pontos (100.0%) - 1/1 testes passaram
🧪 Qualidade                  :   5/  8 pontos ( 62.5%) - 5/6 testes passaram
🌟 Diferenciais               :   0/ 18 pontos (  0.0%) - 0/5 testes passaram

================================================================================
🔴 PONTOS OBRIGATÓRIOS:
--------------------------------------------------------------------------------
Total: 100/100 pontos (100.0%)

🟢 PONTOS OPCIONAIS (PLUS):
--------------------------------------------------------------------------------
Total: 0/30 pontos (0.0%)

================================================================================
🎯 PONTUAÇÃO FINAL:
--------------------------------------------------------------------------------
TOTAL: 100/130 pontos
Obrigatórios: 100/100 pontos (100.0%)
Opcionais: 0/30 pontos extras
================================================================================
```

---

## 🔍 Executar Testes Específicos

### Por categoria:

```bash
# Apenas testes de CRUD
mvn test -Dtest=CRUDOperationsTests

# Apenas testes de validação
mvn test -Dtest=ValidationTests

# Apenas testes HTTP
mvn test -Dtest=HTTPRequirementsTests

# Apenas testes de modelo
mvn test -Dtest=ModelDataTests

# Apenas testes de endpoints
mvn test -Dtest=EndpointsTests

# Apenas testes de persistência
mvn test -Dtest=PersistenceTests

# Apenas testes de qualidade
mvn test -Dtest=CodeQualityTests

# Apenas testes opcionais
mvn test -Dtest=OptionalFeaturesTests
```

### Por método específico:

```bash
# Executar apenas um teste específico
mvn test -Dtest=CRUDOperationsTests#testCreateCourse
```

---

## ⚙️ Configuração

### Alterar a URL da aplicação

**Opção 1: Via linha de comando (recomendado)**
```bash
mvn clean test -Dtest.base.url=http://localhost:8080
```

**Opção 2: Editar `src/test/resources/application.properties`**
```properties
test.base.url=http://localhost:8081
test.base.path=
```

**Opção 3: Variável de ambiente**
```bash
export TEST_BASE_URL=http://localhost:8080
mvn clean test
```

---

## 🐛 Solução de Problemas

### ❌ Erro: "Connection refused" ou "ConnectException"

**Problema:** Sua aplicação não está rodando ou não está acessível.

**Solução:**
```bash
# 1. Verifique se sua aplicação está rodando
curl http://localhost:8081/q/health/live

# 2. Se não estiver, inicie sua aplicação
cd seu-projeto-quarkus
./mvnw quarkus:dev

# 3. Aguarde até ver "Quarkus started" antes de executar os testes
```

### ❌ Erro: "Port already in use" (na sua aplicação)

**Problema:** A porta 8081 já está em uso.

**Solução 1:** Pare o processo que está usando a porta
```bash
# Linux/Mac
lsof -ti:8081 | xargs kill -9

# Windows
netstat -ano | findstr :8081
taskkill /PID <PID> /F
```

**Solução 2:** Inicie sua aplicação em outra porta e configure os testes
```bash
# Inicie sua aplicação na porta 8080
./mvnw quarkus:dev -Dquarkus.http.port=8080

# Execute os testes apontando para a nova porta
mvn clean test -Dtest.base.url=http://localhost:8080
```

### ❌ Testes falhando com 404

**Problema:** Endpoints não implementados ou URLs incorretas.

**Solução:** Verifique se sua aplicação implementou todos os endpoints:
- POST /courses
- GET /courses
- GET /courses/{id}
- PUT /courses/{id}
- DELETE /courses/{id}

### ❌ Testes falhando com 400

**Problema:** Validações não implementadas corretamente.

**Solução:** Verifique as validações:
- Course.name: @NotNull, @NotBlank, @Size(min=3)
- Lesson.name: @NotNull, @NotBlank

---

## 📈 Dicas para Maximizar sua Pontuação

### ✅ Obrigatórios (100 pontos)

1. **Implemente todos os endpoints CRUD** (25 pontos)
2. **Configure validações Bean Validation** (15 pontos)
3. **Use códigos HTTP corretos** (30 pontos)
4. **Configure Content-Type corretamente** (10 pontos)
5. **Implemente persistência** (10 pontos)
6. **Crie o modelo de dados completo** (15 pontos)

### 🌟 Opcionais (30 pontos extras)

1. **Adicione endpoints de Lessons** (6 pontos)
   - POST /courses/{courseId}/lessons
   - GET /courses/{courseId}/lessons

2. **Retorne header Location no POST** (3 pontos)
   ```java
   return Response.created(URI.create("/courses/" + id)).entity(course).build();
   ```

3. **Organize o código** (3 pontos)
   - Separe em Resource, Service, Repository
   - Use DTOs

4. **Adicione Health Check** (3 pontos)
   ```xml
   <dependency>
       <groupId>io.quarkus</groupId>
       <artifactId>quarkus-smallrye-health</artifactId>
   </dependency>
   ```

5. **Implemente tratamento global de exceção** (4 pontos)
   ```java
   @Provider
   public class GlobalExceptionHandler implements ExceptionMapper<Exception>
   ```

6. **Adicione paginação** (3 pontos)
   ```java
   @GET
   public List<Course> list(@QueryParam("page") int page, 
                           @QueryParam("size") int size)
   ```

7. **Use DTOs** (4 pontos)
   - Crie classes separadas para Request/Response

---

## 📞 Suporte

Se encontrar problemas:

1. Verifique se sua aplicação está rodando
2. Verifique os logs dos testes
3. Execute testes individuais para isolar o problema
4. Consulte o README.md completo

---

**Boa sorte! 🚀**