# Architecture Guide

## 🏗️ البنية الكلية

```
FlowaAuto Application
│
├── 📱 Presentation Layer (UI)
│   ├── Screens (6 شاشات)
│   ├── Components (مكونات قابلة للإعادة)
│   ├── Theme (نظام الألوان)
│   └── Navigation (نظام التنقل)
│
├── 🎯 Business Logic Layer (ViewModel)
│   ├── WorkflowEditorViewModel
│   ├── RunnerViewModel
│   ├── WorkflowListViewModel
│   └── ProfileViewModel
│
├── 💾 Data Layer
│   ├── Local Data (Room Database)
│   ├── Remote Data (Retrofit API)
│   ├── Repository (واجهة موحدة)
│   └── Preferences (SharedPreferences)
│
└── 🔧 Infrastructure Layer
    ├── DI (Hilt)
    ├── Services (WorkflowExecutionService)
    ├── Utilities (FileManager, Executor...)
    └── Interceptors (Auth, Logging)
```

## 📦 Layers Detail

### 1. Presentation Layer

**الملفات:**
- `screens/` - الشاشات الرئيسية
- `ui/` - مكونات واجهة المستخدم
- `navigation/` - نظام التنقل

**المسؤوليات:**
- عرض البيانات للمستخدم
- معالجة المدخلات
- إدارة حالة الواجهة

### 2. ViewModel Layer

**الملفات:**
- `viewmodel/` - ViewModels

**المسؤوليات:**
- إدارة حالة الشاشة
- التفاعل مع Repository
- معالجة الأحداث من الواجهة

### 3. Data Layer

**الملفات:**
- `data/local/` - قاعدة البيانات المحلية
- `data/remote/` - API البعيد
- `data/repository/` - Repository
- `models/` - نماذج البيانات

**المسؤوليات:**
- الحصول على البيانات من مصادر مختلفة
- تخزين البيانات محلياً
- مزامنة البيانات

### 4. Infrastructure Layer

**الملفات:**
- `di/` - Dependency Injection
- `service/` - الخدمات
- `utils/` - الأدوات المساعدة

**المسؤوليات:**
- إدارة التبعيات
- تقديم الخدمات المشتركة
- المنطق المساعد

## 🔄 Data Flow

### Simple Flow
```
User Interface
    ↓
ViewModel (Handle Events)
    ↓
Repository (Get Data)
    ↓
Data Source (Local/Remote)
    ↓
Repository (Process Data)
    ↓
ViewModel (Update State)
    ↓
User Interface (Display)
```

### Example: Execute Workflow
```
1. User clicks Execute button
   ↓
2. RunnerViewModel.startExecution(workflowId)
   ↓
3. ExecutionRepository.executeWorkflow(id)
   ↓
4. FlowautoApiService.executeWorkflow(payload)
   ↓
5. API Response
   ↓
6. Repository updates local database
   ↓
7. ViewModel updates state
   ↓
8. UI displays execution results
```

## 🔧 Design Patterns

### 1. MVVM (Model-View-ViewModel)
- **View**: Composable functions
- **ViewModel**: StateFlow for state management
- **Model**: Data classes

### 2. Repository Pattern
```kotlin
interface IRepository {
    suspend fun getWorkflow(id: String): Result<Workflow>
    suspend fun saveWorkflow(workflow: Workflow): Result<Unit>
}

class WorkflowRepository : IRepository {
    // Implementation
}
```

### 3. Dependency Injection
```kotlin
@HiltViewModel
class WorkflowEditorViewModel @Inject constructor(
    private val repository: WorkflowRepository
) : ViewModel()
```

### 4. Observer Pattern (Flow)
```kotlin
val workflows: StateFlow<List<Workflow>> = repository
    .getAllWorkflows()
    .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
```

## 📊 State Management

### ViewModel State
```kotlin
data class EditorState(
    val nodes: List<WorkflowNode> = emptyList(),
    val connections: List<Connection> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
```

### State Updates
```kotlin
private val _state = MutableStateFlow(EditorState())
val state: StateFlow<EditorState> = _state.asStateFlow()

fun addNode(node: WorkflowNode) {
    _state.value = _state.value.copy(
        nodes = _state.value.nodes + node
    )
}
```

## 🔐 Data Security

### Encryption
```kotlin
// Encrypted Local Storage
val preferences = EncryptedSharedPreferences.create(...)

// Secure Database
@Entity
data class SecureData(
    @PrimaryKey val id: String,
    val encryptedValue: String
)
```

### Network Security
```kotlin
// SSL Pinning
val client = OkHttpClient.Builder()
    .certificatePinner(certificatePinner)
    .build()

// Authentication
val interceptor = AuthInterceptor(apiKey)
```

## 🚀 Performance Optimization

### Memory
- استخدام `Lazy` للتهيئة الكسول
- تنظيف الموارد في `onCleared()`
- استخدام `WeakReference` عند الحاجة

### Network
- استخدام Caching
- Request Batching
- Connection Pooling

### Database
- Pagination
- Indexing
- Query Optimization

## 🧪 Testing Architecture

### Unit Tests
```kotlin
@Test
fun testAddNode() {
    // Arrange
    val viewModel = WorkflowEditorViewModel()
    val node = WorkflowNode(...)
    
    // Act
    viewModel.addNode(node)
    
    // Assert
    assert(viewModel.state.value.nodes.contains(node))
}
```

### Integration Tests
```kotlin
@Test
fun testWorkflowExecution() {
    // Test full workflow execution
}
```

---

**آخر تحديث**: 2026-07-17
