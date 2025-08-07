# Script para probar la conexión a Bonsai Elasticsearch localmente
Write-Host "🧪 Probando conexión a Bonsai Elasticsearch..." -ForegroundColor Cyan

# Probar conectividad directa
Write-Host "`n1️⃣ Probando conectividad directa a Bonsai..." -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "https://j78qu5rxgf:c3y0l8bm9z@hospital-cluster-5303791460.us-east-1.bonsaisearch.net:443" -Method GET -TimeoutSec 10
    Write-Host "✅ Conexión exitosa a Bonsai" -ForegroundColor Green
    Write-Host "Cluster: $($response.cluster_name)" -ForegroundColor Green
    Write-Host "Version: $($response.version.number)" -ForegroundColor Green
} catch {
    Write-Host "❌ Error conectando a Bonsai: $($_.Exception.Message)" -ForegroundColor Red
}

# Probar con curl si está disponible
Write-Host "`n2️⃣ Probando con curl..." -ForegroundColor Yellow
try {
    curl -v "https://j78qu5rxgf:c3y0l8bm9z@hospital-cluster-5303791460.us-east-1.bonsaisearch.net:443" --connect-timeout 10
} catch {
    Write-Host "❌ curl no disponible o error: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n3️⃣ Verificando configuración de red..." -ForegroundColor Yellow
Write-Host "DNS Servers:" -ForegroundColor Cyan
Get-DnsClientServerAddress | Where-Object {$_.InterfaceAlias -like "*Ethernet*" -or $_.InterfaceAlias -like "*Wi-Fi*"} | Select-Object InterfaceAlias, ServerAddresses

Write-Host "`n4️⃣ Probando resolución DNS..." -ForegroundColor Yellow
try {
    $dnsResult = Resolve-DnsName "hospital-cluster-5303791460.us-east-1.bonsaisearch.net" -Type A
    Write-Host "✅ DNS resuelto: $($dnsResult.IPAddress)" -ForegroundColor Green
} catch {
    Write-Host "❌ Error resolviendo DNS: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n🎯 Prueba completada" -ForegroundColor Cyan
