# Installation Guide

## المتطلبات المسبقة

### Hardware
- جهاز كمبيوتر بـ 4GB RAM بحد أدنى
- 5GB مساحة تخزين

### Software
- Android Studio 2022.1.1 أو أحدث
- JDK 17 أو أحدث
- Kotlin 1.9.20+

### SDK
- Minimum SDK: 26 (Android 8.0)
- Target SDK: 34 (Android 14)

## الخطوات

### 1. تحميل المشروع

```bash
# استنساخ المستودع
git clone https://github.com/charfeddineAZ/book-.git
cd book-

# أو تحميل كملف ZIP
wget https://github.com/charfeddineAZ/book-/archive/refs/heads/main.zip
unzip main.zip
cd book--main
```

### 2. فتح في Android Studio

1. افتح Android Studio
2. اختر `File` → `Open`
3. اختر مجلد المشروع
4. انتظر تحميل Gradle

### 3. تحديث التبعيات

```bash
# تنزيل جميع التبعيات
./gradlew build

# أو من Android Studio
# Build → Rebuild Project
```

### 4. إعداد جهاز المحاكاة أو جهاز فعلي

#### جهاز محاكاة:
```bash
# فتح Device Manager
# Tools → Device Manager

# إنشاء جهاز افتراضي جديد
# اختر الإصدار المناسب
# انقر Create Device
```

#### جهاز فعلي:
```bash
# تفعيل Developer Mode
# Settings → About Phone → Build Number (انقر 7 مرات)

# تفعيل USB Debugging
# Settings → Developer Options → USB Debugging

# توصيل الجهاز بـ USB
```

### 5. تشغيل التطبيق

```bash
# من سطر الأوامر
./gradlew installDebug
./gradlew installRelease

# أو من Android Studio
# Run → Run 'app'
# أو اضغط Shift + F10
```

## Configuration

### API Configuration

أنشئ ملف `local.properties`:

```properties
sdk.dir=/path/to/android-sdk
api.baseUrl=https://api.flowauto.com
api.key=your-api-key-here
```

### Build Configuration

معدل في `app/build.gradle`:

```gradle
buildTypes {
    debug {
        debuggable true
        buildConfigField "String", "API_BASE_URL", '"https://api-dev.flowauto.com"'
    }
    release {
        minifyEnabled true
        debuggable false
        buildConfigField "String", "API_BASE_URL", '"https://api.flowauto.com"'
    }
}
```

## Troubleshooting

### مشكلة: Gradle Sync Failed

**الحل:**
```bash
# حذف cache
rm -rf ~/.gradle
rm -rf .gradle

# إعادة Sync
./gradlew clean build
```

### مشكلة: Build Error

**الحل:**
```bash
# تحديث Gradle
./gradlew wrapper --gradle-version latest

# إعادة البناء
./gradlew clean build
```

### مشكلة: Device Not Found

**الحل:**
```bash
# التحقق من الأجهزة المتصلة
adb devices

# إعادة تشغيل ADB
adb kill-server
adb start-server
```

### مشكلة: Low Storage

**الحل:**
```bash
# حذف البيانات المؤقتة
rm -rf build/
rm -rf .gradle/

# تنظيف Android Studio
File → Invalidate Caches → Invalidate
```

## Testing

### تشغيل الاختبارات

```bash
# اختبارات الوحدة
./gradlew test

# اختبارات التكامل
./gradlew connectedAndroidTest

# اختبارات معينة
./gradlew test --tests TestClass
```

## Debugging

### تفعيل Debugging

1. ضع breakpoint في الكود
2. اضغط Debug 'app'
3. استخدم Debug panel

### Logcat

```bash
# عرض السجلات
adb logcat

# فلترة حسب Tag
adb logcat | grep "FlowaAuto"
```

## Performance Profiling

```bash
# Profile CPU
Run → Profile 'app'

# Monitor Memory
Android Profiler → Memory

# Network Traffic
Android Profiler → Network
```

---

**آخر تحديث**: 2026-07-17
