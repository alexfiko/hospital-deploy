# 🔍 Scripts de verificación rápida para Bonsai

## **Comandos para copiar y pegar en la consola de Bonsai:**

### **1. Ver todos los índices:**
```json
GET _cat/indices?v
```

### **2. Verificar si existe el índice "doctores":**
```json
GET /doctores/_exists
```

### **3. Ver el mapeo del índice:**
```json
GET /doctores/_mapping
```

### **4. Contar documentos en el índice:**
```json
GET /doctores/_count
```

### **5. Ver un documento de ejemplo:**
```json
GET /doctores/_search
{
  "size": 1
}
```

### **6. Probar búsqueda por especialidad:**
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

### **7. Probar búsqueda por nombre:**
```json
GET /doctores/_search
{
  "query": {
    "match": {
      "name": "Dr."
    }
  }
}
```

### **8. Ver estadísticas del índice:**
```json
GET /doctores/_stats
```

### **9. Verificar la salud del índice:**
```json
GET /doctores/_cluster/health
```

### **10. Si necesitas eliminar el índice:**
```json
DELETE /doctores
```

## **📊 Respuestas esperadas:**

### **Índice creado correctamente:**
- `GET _cat/indices?v` → Debe mostrar `doctores` con status `green`
- `GET /doctores/_exists` → Debe devolver `true`

### **Sincronización exitosa:**
- `GET /doctores/_count` → Debe mostrar `count: 50` (o el número de doctores)
- `GET /doctores/_search` → Debe devolver documentos con todos los campos

### **Búsquedas funcionando:**
- Las queries deben devolver resultados relevantes
- Los campos deben estar correctamente mapeados
