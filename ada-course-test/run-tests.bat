@echo off
echo ==================================
echo 🚀 ADA Challenge - Test Runner
echo ==================================
echo.

REM Check if Maven is installed
where mvn >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ❌ Maven não encontrado. Por favor, instale o Maven primeiro.
    exit /b 1
)

REM Check if Java is installed
where java >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ❌ Java não encontrado. Por favor, instale o Java 17+ primeiro.
    exit /b 1
)

echo ✅ Maven encontrado
echo ✅ Java encontrado
echo.

echo 📦 Compilando projeto...
call mvn clean compile -q

if %ERRORLEVEL% NEQ 0 (
    echo ❌ Erro ao compilar o projeto
    exit /b 1
)

echo ✅ Compilação concluída
echo.

echo 🧪 Executando testes...
echo.

call mvn test

echo.
echo ==================================
echo ✅ Testes concluídos!
echo ==================================
pause

@REM Made with Bob
