# Project Structure

```
FlowaAuto/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── AndroidManifest.xml
│   │   │   ├── java/com/flowauto/app/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── FlowautoApplication.kt
│   │   │   │   │
│   │   │   │   ├── ui/
│   │   │   │   │   ├── theme/
│   │   │   │   │   │   ├���─ Theme.kt
│   │   │   │   │   │   ├── Color.kt
│   │   │   │   │   │   └── Type.kt
│   │   │   │   │   ├── components/
│   │   │   │   │   │   ├── EditorComponents.kt
│   │   │   │   │   │   └── CommonComponents.kt
│   │   │   │   │   └── screens/
│   │   │   │   │       ├── editor/
│   │   │   │   │       │   ├── WorkflowEditorScreen.kt
│   │   │   │   │       │   ├── AddNodeSheet.kt
│   │   │   │   │       │   └── NodeEditorPanel.kt
│   │   │   │   │       ├── runner/
│   │   │   │   │       │   └── RunnerScreen.kt
│   │   │   │   │       ├── browser/
│   │   │   │   │       │   └── BrowserScreen.kt
│   │   │   │   │       ├── workspace/
│   │   │   │   │       │   └── WorkspaceScreen.kt
│   │   │   │   │       ├── libraries/
│   │   │   │   │       │   └── LibrariesScreen.kt
│   │   │   │   │       ├── settings/
│   │   │   │   │       │   └── SettingsScreen.kt
│   │   │   │   │       ├── home/
│   │   │   │   │       │   └── HomeScreen.kt
│   │   │   │   │       ├── search/
│   │   │   │   │       │   └── SearchScreen.kt
│   │   │   │   │       ├── mybooks/
│   │   │   │   │       │   └── MyBooksScreen.kt
│   │   │   │   │       └── profile/
│   │   │   │   │           └── ProfileScreen.kt
│   │   │   │   │
│   │   │   │   ├── navigation/
│   │   │   │   │   └── AppNavigation.kt
│   │   │   │   │
│   │   │   │   ├── viewmodel/
│   │   │   │   │   ├── WorkflowEditorViewModel.kt
│   │   │   │   │   ├── RunnerViewModel.kt
│   │   │   │   │   ├── WorkflowListViewModel.kt
│   │   │   │   │   ├── HomeViewModel.kt
│   │   │   │   │   ├── SearchViewModel.kt
│   │   │   │   │   └── ProfileViewModel.kt
│   │   │   │   │
│   │   │   │   ├── models/
│   │   │   │   │   ├── workflow/
│   │   │   │   │   │   ├── WorkflowModels.kt
│   │   │   │   │   │   └── WorkflowEntity.kt
│   │   │   │   │   ├── execution/
│   │   │   │   │   │   ├── ExecutionModels.kt
│   │   │   │   │   │   └── LogEntity.kt
│   │   │   │   │   ├── browser/
│   │   │   │   │   │   └── BrowserModels.kt
│   │   │   │   │   ├── workspace/
│   │   │   │   │   │   └── WorkspaceModels.kt
│   │   │   │   │   ├── libraries/
│   │   │   │   │   │   ├── LibrariesModels.kt
│   │   │   │   │   │   └── LibraryEntity.kt
│   │   │   │   │   ├── settings/
│   │   │   │   │   │   └── SettingsModels.kt
│   │   │   │   │   ├── api/
│   │   │   │   │   │   └── ApiResponse.kt
│   │   │   │   │   └── Book.kt
│   │   │   │   │
│   │   │   │   ├── data/
│   │   │   │   │   ├── local/
│   │   │   │   │   │   ├── db/
│   │   │   │   │   │   │   └── FlowautoDatabase.kt
│   │   │   │   │   │   ├── dao/
│   │   │   │   │   │   │   └── Daos.kt
│   │   │   │   │   │   └── converters/
│   │   │   │   │   │       └── DateTimeConverter.kt
│   │   │   │   │   ├── remote/
│   │   │   │   │   │   ├── api/
│   │   │   │   │   │   │   └── FlowautoApiService.kt
│   │   │   │   │   │   └── interceptor/
│   │   │   │   │   │       └── AuthInterceptor.kt
│   │   │   │   │   └── repository/
│   │   │   │   │       └── Repository.kt
│   │   │   │   │
│   │   │   │   ├── service/
│   │   │   │   │   └── WorkflowExecutionService.kt
│   │   │   │   │
│   │   │   │   ├── di/
│   │   │   │   │   └── AppModule.kt
│   │   │   │   │
│   │   │   │   └── utils/
│   │   │   │       ├── SecurePreferencesManager.kt
│   │   │   │       ├── FileManager.kt
│   │   │   │       ├── WorkflowExecutor.kt
│   │   │   │       ├── Constants.kt
│   │   │   │       └── Extensions.kt
│   │   │   │
│   │   │   └── res/
│   │   │       ├── values/
│   │   │       │   ├── strings.xml
│   │   │       │   ├── colors.xml
│   │   │       │   └── dimens.xml
│   │   │       └── drawable/
│   │   │
│   │   ├── test/
│   │   │   └── java/com/flowauto/app/
│   │   │       ├── viewmodel/
│   │   │       ├── data/
│   │   │       └── utils/
│   │   │
│   │   └── androidTest/
│   │       └── java/com/flowauto/app/
│   │           ├── ui/
│   │           └── integration/
│   │
│   └── build.gradle
│
├── build.gradle
├── settings.gradle
├── gradle.properties
├── gradlew
├── gradlew.bat
│
├── docs/
│   ├── README.md
│   ├── CHANGELOG.md
│   ├── CONTRIBUTING.md
│   ├── API_DOCUMENTATION.md
│   ├── ARCHITECTURE.md
│   └── PROJECT_STRUCTURE.md
│
└── .gitignore
```

## 📂 شرح الهيكل

### `app/`
المجلد الرئيسي للتطبيق

### `ui/`
طبقة العرض:
- `theme/` - نظام الألوان والأنماط
- `components/` - مكونات واجهة قابلة للإعادة
- `screens/` - الشاشات الرئيسية

### `navigation/`
نظام التنقل بين ��لشاشات

### `viewmodel/`
طبقة إدارة الحالة

### `models/`
نماذج البيانات:
- كل مجلد يمثل مجال معين (workflow, execution, إلخ)
- يحتوي على data classes و entities

### `data/`
طبقة البيانات:
- `local/` - قاعدة البيانات المحلية
- `remote/` - API البعيد
- `repository/` - واجهة موحدة للوصول للبيانات

### `service/`
الخدمات المشتركة

### `di/`
إدارة التبعيات (Dependency Injection)

### `utils/`
دوال ومساعدات

### `res/`
موارد التطبيق

### `test/` و `androidTest/`
الاختبارات

---

**آخر تحديث**: 2026-07-17
