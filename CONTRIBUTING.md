# Contributing Guide

## 🤝 كيفية المساهمة

شكراً لاهتمامك بالمساهمة في FlowaAuto! إليك الخطوات:

### 1. Fork المستودع
```bash
git clone https://github.com/your-username/book-.git
cd book-
```

### 2. إنشاء فرع جديد
```bash
git checkout -b feature/your-feature-name
```

### 3. إجراء التغييرات
- اتبع معايير الكود الموضحة أدناه
- أضف تعليقات واضحة
- اكتب اختبارات للميزات الجديدة

### 4. Commit التغييرات
```bash
git commit -m "feat: إضافة وصف الميزة الجديدة"
```

### 5. Push إلى الفرع
```bash
git push origin feature/your-feature-name
```

### 6. فتح Pull Request
- اشرح ما تفعله
- أضف لقطات شاشة إن أمكن
- اربط المشاكل المرتبطة

## 📝 معايير الكود

### Kotlin Style Guide
- اتبع [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- استخدم camelCase للمتغيرات والدوال
- استخدم PascalCase للفئات
- استخدم UPPER_CASE للثوابت

### Naming
- المتغيرات: `val userName: String`
- الدوال: `fun getUserName(): String`
- الفئات: `class UserViewModel`
- الواجهات: `interface IRepository`
- الـ Enums: `enum class UserRole`

### Comments
```kotlin
// تعليق واحد للأسطر القليلة

/**
 * تعليق متعدد الأسطر للدوال والفئات
 * @param name اسم المستخدم
 * @return النتيجة
 */
fun getUserName(name: String): String {
    // المزيد من التفاصيل
}
```

## 🧪 الاختبار

### كتابة الاختبارات
```kotlin
class WorkflowEditorViewModelTest {
    @Test
    fun testAddNode() {
        // اختبر إضافة عقدة
    }
}
```

### تشغيل الاختبارات
```bash
./gradlew test
./gradlew connectedAndroidTest
```

## 🐛 الإبلاغ عن الأخطاء

عند العثور على خطأ:
1. تحقق من أنه لم يتم الإبلاغ عنه بالفعل
2. اوصف الخطأ بوضوح
3. أضف خطوات إعادة الإنتاج
4. أرفق لقطات شاشة أو سجلات

## ✨ الميزات الجديدة

قبل بدء العمل على ميزة جديدة:
1. افتح Issue لمناقشتها
2. انتظر الموافقة
3. بدأ العمل على الفرع

## 📖 التوثيق

- حدث README.md إذا لزم الأمر
- أضف comments للكود المعقد
- حدث CHANGELOG.md

## 🎯 معايير الجودة

- ✅ الكود يجب أن يمر في جميع الاختبارات
- ✅ لا توجد تحذيرات lint
- ✅ التوثيق محدّث
- ✅ معايير الكود متبعة

---

شكراً لمساهمتك! 🎉
