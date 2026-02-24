#!/bin/bash

echo "=================================="
echo "🚀 ADA Challenge - Test Runner"
echo "=================================="
echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null
then
    echo "❌ Maven não encontrado. Por favor, instale o Maven primeiro."
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null
then
    echo "❌ Java não encontrado. Por favor, instale o Java 17+ primeiro."
    exit 1
fi

echo "✅ Maven encontrado: $(mvn --version | head -n 1)"
echo "✅ Java encontrado: $(java -version 2>&1 | head -n 1)"
echo ""

echo "📦 Compilando projeto..."
mvn clean compile -q

if [ $? -ne 0 ]; then
    echo "❌ Erro ao compilar o projeto"
    exit 1
fi

echo "✅ Compilação concluída"
echo ""

echo "🧪 Executando testes..."
echo ""

mvn test

echo ""
echo "=================================="
echo "✅ Testes concluídos!"
echo "=================================="

# Made with Bob
