package com.flowauto.app.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.flowauto.app.models.settings.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController) {
    val selectedCategory = remember { mutableStateOf(0) }
    val isDarkMode = remember { mutableStateOf(false) }
    val language = remember { mutableStateOf("ar") }
    val appLock = remember { mutableStateOf(false) }
    val encryptWorkspace = remember { mutableStateOf(false) }
    val showSecrets = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        TopAppBar(
            title = { Text("الإعدادات") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "رجوع")
                }
            }
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 56.dp)
        ) {
            // قائمة الفئات
            Column(
                modifier = Modifier
                    .width(150.dp)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                    .verticalScroll(rememberScrollState())
            ) {
                val categories = listOf(
                    "الحساب" to Icons.Default.AccountCircle,
                    "الأمان" to Icons.Default.Lock,
                    "الأداء" to Icons.Default.Speed,
                    "التخصيص" to Icons.Default.Palette,
                    "الإشعارات" to Icons.Default.Notifications,
                    "المطور" to Icons.Default.Code,
                    "النسخ الاحتياطي" to Icons.Default.Backup,
                    "حول" to Icons.Default.Info
                )

                categories.forEachIndexed { index, (label, icon) ->
                    NavigationDrawerItem(
                        label = { Text(label, style = MaterialTheme.typography.labelSmall) },
                        icon = { Icon(icon, contentDescription = label) },
                        selected = selectedCategory.value == index,
                        onClick = { selectedCategory.value = index },
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }

            // محتوى الفئة المختارة
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                when (selectedCategory.value) {
                    0 -> AccountSettings()
                    1 -> SecuritySettings(appLock, encryptWorkspace, showSecrets)
                    2 -> PerformanceSettings()
                    3 -> CustomizationSettings(isDarkMode, language)
                    4 -> NotificationSettings()
                    5 -> DeveloperSettings()
                    6 -> BackupSettings()
                    7 -> AboutSettings()
                }
            }
        }
    }
}

@Composable
fun AccountSettings() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("الحساب والمزامنة", style = MaterialTheme.typography.titleMedium)

        Card {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("خدمات التخزين السحابي", style = MaterialTheme.typography.labelSmall)

                listOf(
                    "Google Drive" to Icons.Default.CloudDownload,
                    "Dropbox" to Icons.Default.CloudQueue,
                    "AWS S3" to Icons.Default.Cloud,
                    "WebDAV" to Icons.Default.FolderOpen
                ).forEach { (service, icon) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { }
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(icon, contentDescription = null, modifier = Modifier.size(24.dp))
                            Text(service)
                        }
                        Switch(checked = false, onCheckedChange = { })
                    }
                }
            }
        }

        Card {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("إدارة المزامنة", style = MaterialTheme.typography.labelSmall)

                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("اختبار الاتصال")
                }

                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors()
                ) {
                    Text("جدولة المزامنة")
                }
            }
        }
    }
}

@Composable
fun SecuritySettings(
    appLock: MutableState<Boolean>,
    encryptWorkspace: MutableState<Boolean>,
    showSecrets: MutableState<Boolean>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("الأمان والخصوصية", style = MaterialTheme.typography.titleMedium)

        Card {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("قفل التطبيق", style = MaterialTheme.typography.labelSmall)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("تفعيل البصمة")
                    Switch(checked = appLock.value, onCheckedChange = { appLock.value = it })
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("تشفير Workspace")
                    Switch(checked = encryptWorkspace.value, onCheckedChange = { encryptWorkspace.value = it })
                }
            }
        }

        Card(
            modifier = Modifier.clickable { showSecrets.value = true }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("إدارة الأسرار", style = MaterialTheme.typography.labelSmall)
                    Text("تخزين مفاتيح API بأمان", style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.outline
                    ))
                }
                Icon(Icons.Default.ChevronRight, contentDescription = null)
            }
        }
    }
}

@Composable
fun PerformanceSettings() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("الأداء والموارد", style = MaterialTheme.typography.titleMedium)

        Card {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                var nodeTimeout by remember { mutableStateOf(30) }
                var parallelLimit by remember { mutableStateOf(5) }
                var batteryMode by remember { mutableStateOf("متوازن") }

                Text("مهلة العقدة (ثانية):", style = MaterialTheme.typography.labelSmall)
                Slider(
                    value = nodeTimeout.toFloat(),
                    onValueChange = { nodeTimeout = it.toInt() },
                    valueRange = 10f..120f,
                    steps = 10
                )
                Text("$nodeTimeout ثانية", style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.outline
                ))

                Spacer(modifier = Modifier.height(16.dp))

                Text("حد التنفيذ المتوازي:", style = MaterialTheme.typography.labelSmall)
                Slider(
                    value = parallelLimit.toFloat(),
                    onValueChange = { parallelLimit = it.toInt() },
                    valueRange = 1f..10f,
                    steps = 8
                )
                Text("$parallelLimit سيناريوهات", style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.outline
                ))
            }
        }
    }
}

@Composable
fun CustomizationSettings(
    isDarkMode: MutableState<Boolean>,
    language: MutableState<String>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("التخصيص", style = MaterialTheme.typography.titleMedium)

        Card {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("الوضع الداكن")
                    Switch(checked = isDarkMode.value, onCheckedChange = { isDarkMode.value = it })
                }

                Divider()

                Text("اللغة:", style = MaterialTheme.typography.labelSmall)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("ar" to "العربية", "en" to "English").forEach { (code, name) ->
                        Button(
                            onClick = { language.value = code },
                            modifier = Modifier.weight(1f),
                            colors = if (language.value == code) ButtonDefaults.buttonColors()
                            else ButtonDefaults.outlinedButtonColors()
                        ) {
                            Text(name)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationSettings() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("الإشعارات", style = MaterialTheme.typography.titleMedium)

        Card {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                var completionNotif by remember { mutableStateOf(true) }
                var errorNotif by remember { mutableStateOf(true) }
                var persistentNotif by remember { mutableStateOf(false) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("إشعار انتهاء السيناريو")
                    Switch(checked = completionNotif, onCheckedChange = { completionNotif = it })
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("إشعار الأخطاء")
                    Switch(checked = errorNotif, onCheckedChange = { errorNotif = it })
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("إشعار دائم أثناء التشغيل")
                    Switch(checked = persistentNotif, onCheckedChange = { persistentNotif = it })
                }
            }
        }
    }
}

@Composable
fun DeveloperSettings() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("خيارات المطور", style = MaterialTheme.typography.titleMedium)

        Card {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                var debugWebView by remember { mutableStateOf(false) }
                var experimentalFeatures by remember { mutableStateOf(false) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("تصحيح WebView")
                    Switch(checked = debugWebView, onCheckedChange = { debugWebView = it })
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("الميزات التجريبية")
                    Switch(checked = experimentalFeatures, onCheckedChange = { experimentalFeatures = it })
                }

                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors()
                ) {
                    Text("عرض السجلات الخام")
                }

                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors()
                ) {
                    Text("إعادة تعيين")
                }
            }
        }
    }
}

@Composable
fun BackupSettings() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("النسخ الاحتياطي والاستعادة", style = MaterialTheme.typography.titleMedium)

        Card {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("تصدير البيانات")
                }

                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors()
                ) {
                    Text("استيراد من ملف")
                }
            }
        }
    }
}

@Composable
fun AboutSettings() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.Info,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Text("FlowaAuto", style = MaterialTheme.typography.titleLarge)
        Text("v1.0.0", style = MaterialTheme.typography.labelSmall)

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("تطبيق أتمتة سيناريوهات قوي وسهل الاستخدام")
                Divider()
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("الترخيص")
                }
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors()
                ) {
                    Text("المساعدة والدعم")
                }
            }
        }
    }
}
