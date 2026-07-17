# FlowaAuto - منصة أتمتة السيناريوهات المتقدمة

## 📱 نظرة عامة

FlowaAuto هي منصة أتمتة سيناريوهات متقدمة وقوية على نظام Android، توفر تجربة فريدة وسهلة الاستخدام لبناء واختبار وتنفيذ سيناريوهات معقدة بدون الحاجة للبرمجة.

## 🎯 الميزات الرئيسية

### 1. محرر سير العمل (Workflow Editor)
- ✅ قماس لا نهائي قابل للتكبير والتصغير
- ✅ نظام عقد متقدم مع أنواع مختلفة
- ✅ ربط ذكي للعقد (Tap-to-Connect)
- ✅ تعيين بيانات متقدم (Data Mapping)
- ✅ تراجع/إعادة (Undo/Redo)
- ✅ خريطة مصغرة (Minimap)
- ✅ تحرير متقدم للعقد مع 4 تبويبات

### 2. مشغل السيناريوهات (Runner)
- ✅ 4 أوضاع تشغيل:
  - تشغيل كامل
  - تشغيل انتقائي (Selective)
  - وضع التتبع (Debug)
  - الجدولة (Cron Scheduling)
- ✅ سجل ملون مع تصفية
- ✅ عرض تفاصيل العقدة
- ✅ إحصائيات التشغيل
- ✅ إدارة قائمة الانتظار

### 3. المتصفح المدمج (Browser)
- ✅ WebView كامل مع دعم JavaScript
- ✅ تسجيل الموصلات (Recording)
- ✅ التقاط المحددات (Selector Capture)
- ✅ أدوات استخراج البيانات
- ✅ إدارة التبويبات
- ✅ حفظ الصفحات

### 4. مساحة العمل (Workspace)
- ✅ مستكشف ملفات شجري
- ✅ محرر متعدد الصيغ (JSON, CSV, JS, Python, Markdown...)
- ✅ معاينة فورية
- ✅ دمج مع محرر السيناريوهات
- ✅ سلة محذوفات
- ✅ مزامنة سحابية

### 5. المكتبات (Libraries)
- ✅ مكتبة JavaScript
- ✅ مكتبة Python
- ✅ مكتبة الموصلات (Raccords)
- ✅ مكتبة المحددات (Selectors)
- ✅ محرر متقدم لكل عنصر
- ✅ متجر المجتمع
- ✅ نظام الإصدارات

### 6. الإعدادات (Settings)
- ✅ إدارة الحساب والمزامنة السحابية
- ✅ الأمان والخصوصية
- ✅ إدارة الأسرار (Vault)
- ✅ إعدادات الأداء
- ✅ التخصيص (ثيم، لغة، حجم خط)
- ✅ الإشعارات
- ✅ خيارات المطور
- ✅ النسخ الاحتياطي والاستعادة

## 🏗️ البنية المعمارية

```
FlowaAuto
├── 📱 UI Layer (Jetpack Compose)
│   ├── Screens (6 شاشات رئيسية)
│   ├── Components (مكونات قابلة للإعادة)
│   ├── Theme (نظام الألوان والتصاميم)
│   └── Navigation (نظام التنقل)
│
├── 🎯 ViewModel Layer
│   ├── WorkflowEditorViewModel
│   ├── RunnerViewModel
│   ├── WorkflowListViewModel
│   └── ProfileViewModel
│
├── 💾 Data Layer
│   ├── Local (Room Database)
│   ├── Remote (Retrofit API)
│   ├── Repository (البيانات المحلية والبعيدة)
│   └── Preferences (SharedPreferences الآمنة)
│
├── 🔧 Utilities
│   ├── WorkflowExecutor
│   ├── FileManager
│   ├── SecurePreferencesManager
│   └── Extensions
│
└── 🚀 Services
    └── WorkflowExecutionService
```

## 📦 التبعيات الرئيسية

### Android & Jetpack
- androidx.compose (UI)
- androidx.navigation (Navigation)
- androidx.room (Database)
- androidx.datastore (Preferences)
- androidx.security (Encryption)
- androidx.work (Background Tasks)
- androidx.biometric (Biometric Auth)

### Network
- Retrofit (HTTP Client)
- OkHttp (HTTP Interceptor)
- Gson (JSON Serialization)

### Dependency Injection
- Hilt (DI Framework)

### Logging
- Timber (Logging)

### Testing
- JUnit
- Espresso
- Compose UI Tests

## 🚀 البدء السريع

### المتطلبات
- Android 8.0+ (API 26)
- Android Studio Arctic Fox أو أحدث
- Kotlin 1.9.20+

### التثبيت

1. **استنساخ المستودع**
```bash
git clone https://github.com/charfeddineAZ/book-.git
cd book-
```

2. **فتح المشروع في Android Studio**
```bash
android-studio .
```

3. **تحديث التبعيات**
```bash
./gradlew build
```

4. **تشغيل التطبيق**
```bash
./gradlew installDebug
```

## 📋 الشاشات المتاحة

### 🏠 الرئيسية (Home)
- عرض الكتب المقترحة
- روابط سريعة للميزات

### 🔍 البحث (Search)
- البحث عن الكتب
- التصفية المتقدمة

### 📚 مكتبتي (My Books)
- الكتب المضافة
- إدارة القائمة

### 👤 الملف الشخصي (Profile)
- معلومات المستخدم
- الإحصائيات
- الإعدادات الشخصية

### 🎨 محرر سير العمل (Workflow Editor)
- بناء السيناريوهات بصرياً
- إدارة العقد والاتصالات
- تحرير متقدم

### ▶️ المشغل (Runner)
- تنفيذ السيناريوهات
- تتبع التنفيذ
- عرض السجلات

### 🌐 المتصفح (Browser)
- تصفح الويب
- تسجيل الموصلات
- التقاط المحددات

### 📁 مساحة العمل (Workspace)
- إدارة الملفات
- تحرير المحتوى
- مزامنة سحابية

### 📚 المكتبات (Libraries)
- إدارة المكتبات
- إنشاء عناصر جديدة
- متجر المجتمع

### ⚙️ الإعدادات (Settings)
- إدارة الحساب
- الأمان والخصوصية
- إعدادات الأداء

## 🔐 الأمان

- ✅ تشفير البيانات المحلية (AES-256)
- ✅ إدارة آمنة للأسرار (Vault)
- ✅ مصادقة بيومترية
- ✅ اتصالات HTTP آمنة (HTTPS)
- ✅ Interceptor للمصادقة

## 📊 قاعدة البيانات

### الجداول الرئيسية
- **workflows**: تخزين السيناريوهات
- **logs**: سجلات التنفيذ
- **libraries**: المكتبات المحفوظة

## 🔌 API المتاح

```kotlin
// GET - الحصول على السيناريوهات
GET /api/workflows

// GET - الحصول على سيناريو محدد
GET /api/workflows/{id}

// POST - إنشاء سيناريو
POST /api/workflows

// PUT - تحديث سيناريو
PUT /api/workflows/{id}

// DELETE - حذف سيناريو
DELETE /api/workflows/{id}

// POST - تنفيذ سيناريو
POST /api/execute

// GET - الحصول على السجلات
GET /api/logs/{scenarioId}
```

## 🛠️ أدوات التطوير

### ViewModels المتاحة
- `WorkflowEditorViewModel` - إدارة محرر السيناريوهات
- `RunnerViewModel` - إدارة مشغل السيناريوهات
- `WorkflowListViewModel` - إدارة قائمة السيناريوهات
- `HomeViewModel` - إدارة الصفحة الرئيسية
- `SearchViewModel` - إدارة البحث
- `ProfileViewModel` - إدارة الملف الشخصي

### Utilities المتاحة
- `WorkflowExecutor` - تنفيذ السيناريوهات
- `FileManager` - إدارة الملفات
- `SecurePreferencesManager` - إدارة البيانات الآمنة
- `Constants` - الثوابت والإعدادات
- `Extensions` - دوال مساعدة

## 📝 أمثلة الاستخدام

### إنشاء سيناريو جديد
```kotlin
val newNode = WorkflowNode.createFromType(NodeType.TRIGGER_MANUAL)
editorState.value = editorState.value.addNode(newNode)
```

### ربط عقدتين
```kotlin
val connection = Connection(
    sourceNodeId = node1.id,
    sourcePortId = "output",
    targetNodeId = node2.id,
    targetPortId = "input"
)
editorState.value = editorState.value.addConnection(connection)
```

### تنفيذ سيناريو
```kotlin
viewModelScope.launch {
    val result = repository.executeWorkflow(workflowId)
    result.onSuccess { output ->
        // معالجة النتيجة
    }.onFailure { error ->
        // معالجة الخطأ
    }
}
```

## 🧪 الاختبار

### اختبارات الوحدة
```bash
./gradlew test
```

### اختبارات التكامل
```bash
./gradlew connectedAndroidTest
```

## 📈 الأداء

- تحميل الشاشات: < 500ms
- تنفيذ السيناريوهات: متوازي مع Coroutines
- استهلاك الذاكرة: محسّن مع Compose
- استهلاك البطارية: موفر مع WorkManager

## 🌍 اللغات المدعومة

- ✅ العربية (RTL Support)
- ✅ الإنجليزية

## 📱 المتطلبات

- **نظام التشغيل**: Android 8.0+
- **RAM**: 2GB بحد أدنى
- **التخزين**: 100MB
- **الاتصال**: الإنترنت للميزات السحابية

## 🤝 المساهمة

نرحب بالمساهمات! يرجى:
1. Fork المستودع
2. إنشاء فرع للميزة الجديدة
3. Commit التغييرات
4. Push إلى الفرع
5. فتح Pull Request

## 📄 الترخيص

هذا المشروع مرخص تحت MIT License

## 👥 المؤلفون

- **charfeddineAZ** - المطور الرئيسي

## 📞 التواصل

- البريد الإلكتروني: charfedine68@gmail.com
- GitHub: [@charfeddineAZ](https://github.com/charfeddineAZ)

## 🙏 شكر خاص

شكراً لاستخدام FlowaAuto! نتمنى لك تجربة ممتعة في بناء وتنفيذ السيناريوهات الخاصة بك.

---

**آخر تحديث**: 2026-07-17
**الإصدار**: 1.0.0
