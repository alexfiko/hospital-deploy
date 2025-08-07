#!/bin/bash

echo "🏥 Iniciando Hospital System con Elasticsearch..."

# Detener contenedores existentes
echo "🛑 Deteniendo contenedores existentes..."
docker-compose down

# Limpiar volúmenes (opcional)
echo "🧹 Limpiando volúmenes..."
docker-compose down -v

# Construir y levantar servicios
echo "🔨 Construyendo servicios..."
docker-compose build

echo "🚀 Iniciando servicios..."
docker-compose up -d

# Esperar a que los servicios estén listos
echo "⏳ Esperando a que los servicios estén listos..."
sleep 30

# Verificar estado de los servicios
echo "🔍 Verificando estado de los servicios..."

# Verificar Eureka Server
echo "📡 Verificando Eureka Server..."
curl -f http://localhost:8761/actuator/health || echo "❌ Eureka Server no está listo"

# Verificar Doctor Service
echo "👨‍⚕️ Verificando Doctor Service..."
curl -f http://localhost:8090/actuator/health || echo "❌ Doctor Service no está listo"

# Verificar API Gateway
echo "🌐 Verificando API Gateway..."
curl -f http://localhost:8080/actuator/health || echo "❌ API Gateway no está listo"

# Probar endpoints de Elasticsearch
echo "🔍 Probando endpoints de Elasticsearch..."

# Verificar Elasticsearch
echo "📊 Verificando Elasticsearch..."
curl -f http://localhost:8090/doctors/search-index || echo "❌ Elasticsearch no está disponible"

# Probar búsqueda avanzada
echo "🔎 Probando búsqueda avanzada..."
curl -f "http://localhost:8090/doctors/search/advanced?query=cardio&page=0&size=5" || echo "❌ Búsqueda avanzada falló"

# Probar autocompletado
echo "💡 Probando autocompletado..."
curl -f "http://localhost:8090/doctors/search/autocomplete?prefix=car" || echo "❌ Autocompletado falló"

# Probar búsqueda por especialidad
echo "🏥 Probando búsqueda por especialidad..."
curl -f "http://localhost:8090/doctors/search/specialty/Cardiología" || echo "❌ Búsqueda por especialidad falló"

# Sincronizar datos con Elasticsearch
echo "🔄 Sincronizando datos con Elasticsearch..."
curl -X POST http://localhost:8090/doctors/sync/elasticsearch || echo "❌ Sincronización falló"

echo ""
echo "✅ Pruebas completadas!"
echo ""
echo "📊 URLs de los servicios:"
echo "   - Eureka Server: http://localhost:8761"
echo "   - API Gateway: http://localhost:8080"
echo "   - Doctor Service: http://localhost:8090"
echo "   - Swagger UI: http://localhost:8090/swagger-ui.html"
echo ""
echo "🔍 Endpoints de Elasticsearch:"
echo "   - Búsqueda avanzada: http://localhost:8090/doctors/search/advanced"
echo "   - Autocompletado: http://localhost:8090/doctors/search/autocomplete?prefix=car"
echo "   - Sugerencias: http://localhost:8090/doctors/search/suggestions?query=cardio"
echo "   - Sincronización: POST http://localhost:8090/doctors/sync/elasticsearch"
echo ""
echo "📝 Logs del Doctor Service:"
docker-compose logs doctor-service
