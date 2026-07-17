package com.flowauto.app.screens.browser

import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.flowauto.app.models.browser.*

@Composable
fun BrowserScreen(navController: NavHostController) {
    val url = remember { mutableStateOf("https://example.com") }
    val isRecording = remember { mutableStateOf(false) }
    val recordedSteps = remember { mutableStateOf(emptyList<RecordedStep>()) }
    val selectedTab = remember { mutableStateOf(0) }
    val showExtractorMenu = remember { mutableStateOf(false) }
    val showSelectorMode = remember { mutableStateOf(false) }
    val capturedSelectors = remember { mutableStateOf(emptyList<SelectorCapture>()) }
    val savedConnectors = remember { mutableStateOf(emptyList<Connector>()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        // شريط العناوين والتنقل
        TopAppBar(
            title = { Text("المتصفح المدمج") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "رجوع")
                }
            },
            actions = {
                IconButton(onClick = { /* Save page */ }) {
                    Icon(Icons.Default.Download, contentDescription = "حفظ الصفحة")
                }
                IconButton(onClick = { navController.navigate("settings") }) {
                    Icon(Icons.Default.Settings, contentDescription = "إعدادات")
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 56.dp)
        ) {
            // شريط العنوان والملاحة
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* Go back */ }, modifier = Modifier.size(40.dp)) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "رجوع")
                }
                IconButton(onClick = { /* Go forward */ }, modifier = Modifier.size(40.dp)) {
                    Icon(Icons.Default.ArrowForward, contentDescription = "تقدم")
                }
                IconButton(onClick = { /* Refresh */ }, modifier = Modifier.size(40.dp)) {
                    Icon(Icons.Default.Refresh, contentDescription = "تحديث")
                }
                IconButton(onClick = { /* Home */ }, modifier = Modifier.size(40.dp)) {
                    Icon(Icons.Default.Home, contentDescription = "الرئيسية")
                }

                TextField(
                    value = url.value,
                    onValueChange = { url.value = it },
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp),
                    placeholder = { Text("أدخل العنوان...", style = MaterialTheme.typography.labelSmall) },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.labelSmall
                )

                IconButton(onClick = { /* Go */ }, modifier = Modifier.size(40.dp)) {
                    Icon(Icons.Default.Send, contentDescription = "انتقل")
                }
            }

            // إدارة التبويبات
            TabRow(
                selectedTabIndex = selectedTab.value,
                modifier = Modifier.fillMaxWidth()
            ) {
                Tab(
                    selected = selectedTab.value == 0,
                    onClick = { selectedTab.value = 0 },
                    text = { Text("المتصفح", style = MaterialTheme.typography.labelSmall) }
                )
                Tab(
                    selected = selectedTab.value == 1,
                    onClick = { selectedTab.value = 1 },
                    text = { Text("المحددات", style = MaterialTheme.typography.labelSmall) }
                )
                Tab(
                    selected = selectedTab.value == 2,
                    onClick = { selectedTab.value = 2 },
                    text = { Text("التسجيل", style = MaterialTheme.typography.labelSmall) }
                )
            }

            // محتوى التبويبات
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                when (selectedTab.value) {
                    0 -> BrowserContent(url.value, showSelectorMode, showExtractorMenu)
                    1 -> SelectorsPanel(capturedSelectors)
                    2 -> RecordingPanel(isRecording, recordedSteps)
                }
            }
        }

        // شريط أدوات الأتمتة (سفلي)
        AutomationToolbar(
            modifier = Modifier.align(Alignment.BottomCenter),
            isRecording = isRecording.value,
            onSelectorMode = { showSelectorMode.value = true },
            onRecordToggle = { isRecording.value = !isRecording.value },
            onExtractor = { showExtractorMenu.value = true },
            onConnectors = { /* Show connectors */ }
        )
    }
}

@Composable
fun BrowserContent(
    url: String,
    showSelectorMode: MutableState<Boolean>,
    showExtractorMenu: MutableState<Boolean>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // WebView يمكن وضعها هنا في تطبيق حقيقي
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                }
            },
            modifier = Modifier.fillMaxSize(),
            update = { webView ->
                webView.loadUrl(url)
            }
        )

        // زر المحدد العائم
        if (showSelectorMode.value) {
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp),
                onClick = { showSelectorMode.value = false },
                containerColor = MaterialTheme.colorScheme.error
            ) {
                Icon(Icons.Default.Close, contentDescription = "إغلاق وضع المحدد")
            }
        }
    }
}

@Composable
fun SelectorsPanel(capturedSelectors: MutableState<List<SelectorCapture>>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "المحددات المستخرجة (${capturedSelectors.value.size})",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyColumn {
            items(capturedSelectors.value) { selector ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            text = selector.elementName,
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = "CSS: ${selector.cssSelector}",
                            style = MaterialTheme.typography.labelSmall,
                            maxLines = 1
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Button(
                                onClick = { /* Copy */ },
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(4.dp)
                            ) {
                                Icon(
                                    Icons.Default.ContentCopy,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Button(
                                onClick = { /* Save */ },
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(4.dp)
                            ) {
                                Icon(
                                    Icons.Default.Save,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RecordingPanel(isRecording: MutableState<Boolean>, recordedSteps: MutableState<List<RecordedStep>>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "الخطوات المسجلة (${recordedSteps.value.size})",
                style = MaterialTheme.typography.titleSmall
            )
            Surface(
                color = if (isRecording.value) Color(0xFFFF5252) else Color(0xFF4CAF50),
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = if (isRecording.value) "🔴 قيد التسجيل" else "⏹ متوقف",
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(recordedSteps.value) { step ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = step.type.displayName,
                                style = MaterialTheme.typography.labelSmall
                            )
                            Text(
                                text = step.description,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                        IconButton(onClick = { /* Delete step */ }) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "حذف",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }

        if (isRecording.value) {
            Button(
                onClick = { /* Save connector */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Text("حفظ الموصل")
            }
        }
    }
}

@Composable
fun AutomationToolbar(
    modifier: Modifier,
    isRecording: Boolean,
    onSelectorMode: () -> Unit,
    onRecordToggle: () -> Unit,
    onExtractor: () -> Unit,
    onConnectors: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onSelectorMode) {
                Icon(Icons.Default.TouchApp, contentDescription = "محدد")
            }
            IconButton(
                onClick = onRecordToggle,
                modifier = Modifier
                    .background(
                        color = if (isRecording) Color(0xFFFF5252).copy(alpha = 0.1f) else Color.Transparent,
                        shape = MaterialTheme.shapes.small
                    )
            ) {
                Icon(
                    if (isRecording) Icons.Default.Stop else Icons.Default.FiberManualRecord,
                    contentDescription = "تسجيل",
                    tint = if (isRecording) Color(0xFFFF5252) else MaterialTheme.colorScheme.onSurface
                )
            }
            IconButton(onClick = onExtractor) {
                Icon(Icons.Default.DataExtraction, contentDescription = "مستخرج")
            }
            IconButton(onClick = onConnectors) {
                Icon(Icons.Default.Extension, contentDescription = "موصلات")
            }
        }
    }
}

import androidx.compose.material3.Color
import androidx.compose.material3.PaddingValues

