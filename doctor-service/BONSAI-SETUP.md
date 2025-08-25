# 🚀 Configuración de Bonsai para Elasticsearch

## 📋 Pasos para configurar el índice en Bonsai:

### **1. Acceder a la consola de Bonsai:**
- Ve a tu dashboard de Bonsai
- Haz clic en tu cluster
- Busca la pestaña **"Console"** o **"Dev Tools"**

### **2. Crear el índice "doctores":**

#### **Opción A: Usar el archivo JSON (recomendado)**
1. Copia el contenido del archivo `create-index-bonsai.json`
2. En la consola de Bonsai, ejecuta:
```json
PUT /doctores
```
3. Pega el contenido del archivo JSON
4. Presiona `Ctrl + Enter` para ejecutar

#### **Opción B: Comando directo**
```json
PUT /doctores
{
  "settings": {
    "number_of_shards": 1,
    "number_of_replicas": 0
  },
  "mappings": {
    "properties": {
      "id": {"type": "keyword"},
      "name": {"type": "text"},
      "specialty": {"type": "text"},
      "hospital": {"type": "text"},
      "description": {"type": "text"},
      "experienceYears": {"type": "integer"},
      "rating": {"type": "float"},
      "available": {"type": "boolean"},
      "tags": {"type": "keyword"},
      "diasLaborales": {"type": "keyword"},
      "searchText": {"type": "text"}
    }
  }
}
```

### **3. Verificar que el índice se creó:**
```json
GET _cat/indices?v
```

Deberías ver algo como:
```
health status index     uuid                   pri rep docs.count docs.deleted store.size pri.store.size
green  open   doctores  abc123def456ghi789jkl  1   0   0          0            208b       208b
```

### **4. Verificar el mapeo del índice:**
```json
GET /doctores/_mapping
```

### **5. Probar la sincronización:**
Una vez que el índice esté creado, ejecuta:
```bash
curl -X GET 'http://doctor-service-dev.up.railway.app/doctors/sync/elasticsearch'
```

### **6. Verificar que los datos se sincronizaron:**
```json
GET /doctores/_count
```

Deberías ver algo como:
```json
{
  "count": 50,
  "_shards": {
    "total": 1,
    "successful": 1,
    "skipped": 0,
    "failed": 0
  }
}
```

### **7. Probar una búsqueda:**
```json
GET /doctores/_search
{
  "query": {
    "match": {
      "specialty": "Cardiología"
    }
  }
}
```

## ⚠️ **Posibles errores y soluciones:**

### **Error: "index_already_exists_exception"**
- El índice ya existe, puedes saltarte el paso 2
- O eliminar el índice existente: `DELETE /doctores`

### **Error: "mapper_parsing_exception"**
- Verifica que el JSON esté bien formateado
- Asegúrate de que no haya comas extra al final

### **Error: "cluster_block_exception"**
- El cluster puede estar en modo de solo lectura
- Contacta al soporte de Bonsai

## 🎯 **Orden de ejecución recomendado:**

1. ✅ Crear índice en Bonsai
2. ✅ Verificar que existe
3. ✅ Desplegar doctor-service en Railway
4. ✅ Ejecutar sincronización
5. ✅ Verificar datos en Bonsai
6. ✅ Probar búsquedas

## 📞 **Si algo falla:**

- Revisa los logs de Railway
- Verifica la conexión a Bonsai
- Confirma que las credenciales sean correctas
- Asegúrate de que el índice esté creado antes de sincronizar
