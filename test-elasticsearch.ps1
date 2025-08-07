# Script de PowerShell para probar Hospital System con Elasticsearch

Write-Host "🏥 Iniciando Hospital System con Elasticsearch..." -ForegroundColor Green

# Detener contenedores existentes
Write-Host "🛑 Deteniendo contenedores existentes..." -ForegroundColor Yellow
docker-compose down

# Limpiar volúmenes (opcional)
Write-Host "🧹 Limpiando volúmenes..." -ForegroundColor Yellow
docker-compose down -v

# Construir y levantar servicios
Write-Host "🔨 Construyendo servicios..." -ForegroundColor Yellow
docker-compose build

Write-Host "🚀 Iniciando servicios..." -ForegroundColor Green
docker-compose up -d

# Esperar a que los servicios estén listos
Write-Host "⏳ Esperando a que los servicios estén listos..." -ForegroundColor Yellow
Start-Sleep -Seconds 30

# Verificar estado de los servicios
Write-Host "🔍 Verificando estado de los servicios..." -ForegroundColor Cyan

# Verificar Eureka Server
Write-Host "📡 Verificando Eureka Server..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8761/actuator/health" -UseBasicParsing
    Write-Host "✅ Eureka Server está listo" -ForegroundColor Green
} catch {
    Write-Host "❌ Eureka Server no está listo" -ForegroundColor Red
}

# Verificar Doctor Service
Write-Host "👨‍⚕️ Verificando Doctor Service..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8081/actuator/health" -UseBasicParsing
    Write-Host "✅ Doctor Service está listo" -ForegroundColor Green
} catch {
    Write-Host "❌ Doctor Service no está listo" -ForegroundColor Red
}

# Verificar Citas Service
Write-Host "📅 Verificando Citas Service..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8083/actuator/health" -UseBasicParsing
    Write-Host "✅ Citas Service está listo" -ForegroundColor Green
} catch {
    Write-Host "❌ Citas Service no está listo" -ForegroundColor Red
}

# Verificar API Gateway
Write-Host "🌐 Verificando API Gateway..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/actuator/health" -UseBasicParsing
    Write-Host "✅ API Gateway está listo" -ForegroundColor Green
} catch {
    Write-Host "❌ API Gateway no está listo" -ForegroundColor Red
}

# Probar endpoints de Elasticsearch
Write-Host "🔍 Probando endpoints de Elasticsearch..." -ForegroundColor Cyan

# Verificar Elasticsearch
Write-Host "📊 Verificando Elasticsearch..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8081/doctors/search-index" -UseBasicParsing
    Write-Host "✅ Elasticsearch está disponible" -ForegroundColor Green
} catch {
    Write-Host "❌ Elasticsearch no está disponible" -ForegroundColor Red
}

# Probar búsqueda avanzada
Write-Host "🔎 Probando búsqueda avanzada..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8081/doctors/search/advanced?query=cardio&page=0&size=5" -UseBasicParsing
    Write-Host "✅ Búsqueda avanzada funcionando" -ForegroundColor Green
} catch {
    Write-Host "❌ Búsqueda avanzada falló" -ForegroundColor Red
}

# Probar autocompletado
Write-Host "💡 Probando autocompletado..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8081/doctors/search/autocomplete?prefix=car" -UseBasicParsing
    Write-Host "✅ Autocompletado funcionando" -ForegroundColor Green
} catch {
    Write-Host "❌ Autocompletado falló" -ForegroundColor Red
}

# Probar búsqueda por especialidad
Write-Host "🏥 Probando búsqueda por especialidad..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8081/doctors/search/specialty/Cardiología" -UseBasicParsing
    Write-Host "✅ Búsqueda por especialidad funcionando" -ForegroundColor Green
} catch {
    Write-Host "❌ Búsqueda por especialidad falló" -ForegroundColor Red
}

# Sincronizar datos con Elasticsearch
Write-Host "🔄 Sincronizando datos con Elasticsearch..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8081/doctors/sync/elasticsearch" -Method POST -UseBasicParsing
    Write-Host "✅ Sincronización exitosa" -ForegroundColor Green
} catch {
    Write-Host "❌ Sincronización falló" -ForegroundColor Red
}

# Probar integración Citas-Doctor
Write-Host "🔗 Probando integración Citas-Doctor..." -ForegroundColor Cyan

# Verificar salud del doctor service desde citas
Write-Host "🏥 Verificando doctor service desde citas..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8083/appointments/health/doctor-service" -UseBasicParsing
    Write-Host "✅ Integración Citas-Doctor funcionando" -ForegroundColor Green
} catch {
    Write-Host "❌ Integración Citas-Doctor falló" -ForegroundColor Red
}

# Probar obtención de doctores desde citas
Write-Host "👨‍⚕️ Probando obtención de doctores desde citas..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8083/appointments/doctors" -UseBasicParsing
    Write-Host "✅ Obtención de doctores desde citas funcionando" -ForegroundColor Green
} catch {
    Write-Host "❌ Obtención de doctores desde citas falló" -ForegroundColor Red
}

Write-Host ""
Write-Host "✅ Pruebas completadas!" -ForegroundColor Green
Write-Host ""
Write-Host "📊 URLs de los servicios:" -ForegroundColor Cyan
Write-Host "   - Eureka Server: http://localhost:8761" -ForegroundColor White
Write-Host "   - API Gateway: http://localhost:8080" -ForegroundColor White
Write-Host "   - Doctor Service: http://localhost:8081" -ForegroundColor White
Write-Host "   - Citas Service: http://localhost:8083" -ForegroundColor White
Write-Host "   - Especialidad Service: http://localhost:8084" -ForegroundColor White
Write-Host "   - H2 Console: http://localhost:8082/console" -ForegroundColor White
Write-Host "   - Swagger UI Doctor: http://localhost:8081/swagger-ui.html" -ForegroundColor White
Write-Host "   - Swagger UI Citas: http://localhost:8083/swagger-ui.html" -ForegroundColor White
Write-Host ""
Write-Host "🔍 Endpoints de Elasticsearch:" -ForegroundColor Cyan
Write-Host "   - Búsqueda avanzada: http://localhost:8081/doctors/search/advanced" -ForegroundColor White
Write-Host "   - Autocompletado: http://localhost:8081/doctors/search/autocomplete?prefix=car" -ForegroundColor White
Write-Host "   - Sugerencias: http://localhost:8081/doctors/search/suggestions?query=cardio" -ForegroundColor White
Write-Host "   - Sincronización: POST http://localhost:8081/doctors/sync/elasticsearch" -ForegroundColor White
Write-Host ""
Write-Host "🔗 Endpoints de Integración Citas-Doctor:" -ForegroundColor Cyan
Write-Host "   - Doctores: http://localhost:8083/appointments/doctors" -ForegroundColor White
Write-Host "   - Doctor por ID: http://localhost:8083/appointments/doctors/{id}" -ForegroundColor White
Write-Host "   - Doctores por especialidad: http://localhost:8083/appointments/doctors/specialty/{specialty}" -ForegroundColor White
Write-Host "   - Doctores disponibles: http://localhost:8083/appointments/doctors/available" -ForegroundColor White
Write-Host "   - Validar doctor: http://localhost:8083/appointments/validate-doctor/{doctorId}" -ForegroundColor White
Write-Host ""
Write-Host "📝 Logs del Doctor Service:" -ForegroundColor Cyan
docker-compose logs doctor-service
Write-Host ""
Write-Host "📝 Logs del Citas Service:" -ForegroundColor Cyan
docker-compose logs citas-service
