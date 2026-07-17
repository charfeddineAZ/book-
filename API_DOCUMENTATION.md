# API Documentation

## Base URL
```
https://api.flowauto.com/api/v1
```

## Authentication
جميع الطلبات تتطلب رأس Authorization:
```
Authorization: Bearer {API_KEY}
```

## Workflows API

### GET /workflows
الحصول على قائمة السيناريوهات

**Response:**
```json
{
  "success": true,
  "data": [
    {
      "id": "workflow-1",
      "name": "سيناريو البحث",
      "description": "بحث متقدم",
      "createdAt": "2026-07-17",
      "updatedAt": "2026-07-17"
    }
  ]
}
```

### GET /workflows/{id}
الحصول على سيناريو محدد

**Parameters:**
- `id` (string): معرّف السيناريو

**Response:**
```json
{
  "success": true,
  "data": {
    "id": "workflow-1",
    "name": "سيناريو البحث",
    "nodes": [...],
    "connections": [...]
  }
}
```

### POST /workflows
إنشاء سيناريو جديد

**Request Body:**
```json
{
  "name": "السيناريو الجديد",
  "description": "وصف السيناريو",
  "nodes": [...],
  "connections": [...]
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "id": "workflow-new",
    "message": "تم الإنشاء بنجاح"
  }
}
```

### PUT /workflows/{id}
تحديث سيناريو

**Parameters:**
- `id` (string): معرّف السيناريو

**Request Body:**
```json
{
  "name": "اسم محدّث",
  "nodes": [...]
}
```

### DELETE /workflows/{id}
حذف سيناريو

**Parameters:**
- `id` (string): معرّف السيناريو

**Response:**
```json
{
  "success": true,
  "message": "تم الحذف بنجاح"
}
```

## Execution API

### POST /execute
تنفيذ سيناريو

**Request Body:**
```json
{
  "workflowId": "workflow-1",
  "startNodeId": "trigger-1",
  "payload": {
    "key": "value"
  }
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "executionId": "exec-123",
    "status": "running"
  }
}
```

## Logs API

### GET /logs/{scenarioId}
الحصول على سجلات السيناريو

**Parameters:**
- `scenarioId` (string): معرّف السيناريو
- `limit` (integer): عدد السجلات (افتراضي: 100)
- `offset` (integer): إزاحة الصفحة

**Response:**
```json
{
  "success": true,
  "data": [
    {
      "id": "log-1",
      "nodeName": "عقدة البحث",
      "level": "INFO",
      "message": "بدء البحث",
      "timestamp": "2026-07-17T10:30:00",
      "duration": 150
    }
  ]
}
```

## Error Handling

### Error Response
```json
{
  "success": false,
  "code": 400,
  "message": "خطأ في الطلب"
}
```

### Error Codes
- `200`: نجح
- `400`: خطأ في الطلب
- `401`: غير مصرح
- `403`: محظور
- `404`: غير موجود
- `500`: خطأ في الخادم

## Rate Limiting

- العدد الأقصى للطلبات: 1000 طلب/ساعة
- سيتم إرجاع `429 Too Many Requests` عند تجاوز الحد

## Examples

### cURL Example
```bash
curl -X POST https://api.flowauto.com/api/v1/workflows \
  -H "Authorization: Bearer YOUR_API_KEY" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "سيناريو جديد",
    "description": "وصف السيناريو"
  }'
```

### Kotlin Example
```kotlin
val apiService = retrofitService.create(FlowautoApiService::class.java)
val response = apiService.createWorkflow(workflow)
```

---

**آخر تحديث**: 2026-07-17
**الإصدار**: v1.0
