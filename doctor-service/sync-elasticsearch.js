// Script para sincronizar datos con Elasticsearch
const fetch = require('node-fetch');

const BASE_URL = 'http://localhost:8081';

async function syncElasticsearch() {
  try {
    console.log('🔄 Iniciando sincronización con Elasticsearch...');
    
    // Endpoint de sincronización
    const response = await fetch(`${BASE_URL}/doctors/sync/elasticsearch`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      }
    });
    
    if (response.ok) {
      const result = await response.text();
      console.log('✅ Sincronización exitosa:', result);
    } else {
      console.error('❌ Error en sincronización:', response.status, response.statusText);
    }
    
  } catch (error) {
    console.error('❌ Error de conexión:', error.message);
  }
}

async function checkElasticsearchStatus() {
  try {
    console.log('🔍 Verificando estado de Elasticsearch...');
    
    const response = await fetch(`${BASE_URL}/doctors/search-index`);
    
    if (response.ok) {
      const doctors = await response.json();
      console.log('✅ Elasticsearch funcionando. Doctores encontrados:', doctors.length);
      return true;
    } else {
      console.log('❌ Elasticsearch no disponible');
      return false;
    }
    
  } catch (error) {
    console.error('❌ Error verificando estado:', error.message);
    return false;
  }
}

async function main() {
  console.log('🚀 Iniciando verificación y sincronización...\n');
  
  // Verificar estado
  const isWorking = await checkElasticsearchStatus();
  
  if (!isWorking) {
    console.log('\n🔄 Intentando sincronizar datos...');
    await syncElasticsearch();
    
    // Verificar nuevamente
    console.log('\n🔍 Verificando estado después de sincronización...');
    await checkElasticsearchStatus();
  }
  
  console.log('\n✨ Proceso completado');
}

main();
