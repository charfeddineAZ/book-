package com.flowauto.app.screens.runner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.flowauto.app.models.execution.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RunnerScreen(navController: NavHostController) {
    val runnerState = remember { mutableStateOf(RunnerState()) }
    val selectedTab = remember { mutableStateOf(0) }
    val logs = remember { mutableStateOf(emptyList<LogEntry>()) }
    val selectedLog = remember { mutableStateOf<LogEntry?>(null) }
    val showDetailPanel = remember { mutableStateOf(false) }
    val cronExpression = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        // شريط علوي
        TopAppBar(
            title = { Text("مشغل السيناريوهات") },
            actions = {
                IconButton(onClick = { /* Toggle all */ }) {
                    Icon(Icons.Default.PowerSettingsNew, contentDescription = "تشغيل/إيقاف الكل")
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
            // تبويبات أنماط التشغيل
            TabRow(selectedTabIndex = selectedTab.value) {
                Tab(
                    selected = selectedTab.value == 0,
                    onClick = { selectedTab.value = 0 },
                    text = { Text("تشغيل كامل") }
                )
                Tab(
                    selected = selectedTab.value == 1,
                    onClick = { selectedTab.value = 1 },
                    text = { Text("انتقائي") }
                )
                Tab(
                    selected = selectedTab.value == 2,
                    onClick = { selectedTab.value = 2 },
                    text = { Text("تتبع (Debug)") }
                )
                Tab(
                    selected = selectedTab.value == 3,
                    onClick = { selectedTab.value = 3 },
                    text = { Text("جدولة") }
                )
            }

            when (selectedTab.value) {
                0 -> FullRunTab(runnerState, logs)
                1 -> SelectiveRunTab(runnerState, logs)
                2 -> DebugTab(runnerState, logs)
                3 -> ScheduleTab(cronExpression)
            }

            // السجل
            LogSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                logs = logs.value,
                onLogSelected = {
                    selectedLog.value = it
                    showDetailPanel.value = true
                }
            )

            // شريط ملخص التشغيل
            SummaryBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                runnerState = runnerState.value
            )
        }

        // لوحة تفاصيل العقدة
        if (showDetailPanel.value && selectedLog.value != null) {
            NodeDetailPanel(
                log = selectedLog.value!!,
                onDismiss = { showDetailPanel.value = false },
                onOpenInEditor = { navController.navigate("editor") }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullRunTab(
    runnerState: MutableState<RunnerState>,
    logs: MutableState<List<LogEntry>>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("اختر عقدة البداية:", style = MaterialTheme.typography.titleSmall)
        
        var selectedTrigger by remember { mutableStateOf("") }
        
        ExposedDropdownMenuBox(
            expanded = false,
            onExpandedChange = { }
        ) {
            TextField(
                value = selectedTrigger,
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                readOnly = true,
                placeholder = { Text("المشغّلات المتاحة") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
        }

        Button(
            onClick = {
                // تشغيل السيناريو
                logs.value = logs.value + LogEntry(
                    timestamp = LocalDateTime.now(),
                    message = "بدء التشغيل",
                    level = LogLevel.INFO,
                    nodeName = "البداية",
                    duration = 0
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "تشغيل",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("تشغيل")
        }
    }
}

@Composable
fun SelectiveRunTab(
    runnerState: MutableState<RunnerState>,
    logs: MutableState<List<LogEntry>>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("تشغيل جزئي - اختر نقطة البداية والنهاية", style = MaterialTheme.typography.titleSmall)
        
        TextField(
            value = "",
            onValueChange = { },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("عقدة البداية") },
            readOnly = true,
            trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null) }
        )
        
        TextField(
            value = "",
            onValueChange = { },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("عقدة النهاية") },
            readOnly = true,
            trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null) }
        )
        
        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("تشغيل محدد")
        }
    }
}

@Composable
fun DebugTab(
    runnerState: MutableState<RunnerState>,
    logs: MutableState<List<LogEntry>>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("وضع التتبع - خطوة بخطوة", style = MaterialTheme.typography.titleSmall)
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("تشغيل")
            }
            
            Button(
                onClick = { },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Pause, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("إيقاف مؤقت")
            }
            
            Button(
                onClick = { },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Stop, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("إيقاف")
            }
        }
    }
}

@Composable
fun ScheduleTab(cronExpression: MutableState<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("جدولة التشغيل", style = MaterialTheme.typography.titleSmall)
        
        TextField(
            value = cronExpression.value,
            onValueChange = { cronExpression.value = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("تعبير Cron") },
            placeholder = { Text("0 0 * * *") }
        )
        
        // أزرار جاهزة
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Button(
                onClick = { cronExpression.value = "*/1 * * * *" },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.outlinedButtonColors()
            ) {
                Text("دقيقة", style = MaterialTheme.typography.labelSmall)
            }
            Button(
                onClick = { cronExpression.value = "0 * * * *" },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.outlinedButtonColors()
            ) {
                Text("ساعة", style = MaterialTheme.typography.labelSmall)
            }
            Button(
                onClick = { cronExpression.value = "0 0 * * *" },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.outlinedButtonColors()
            ) {
                Text("يوم", style = MaterialTheme.typography.labelSmall)
            }
        }
        
        TextField(
            value = "",
            onValueChange = { },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("التنفيذ القادم") },
            readOnly = true,
            trailingIcon = { Icon(Icons.Default.Schedule, contentDescription = null) }
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { },
                modifier = Modifier.weight(1f)
            ) {
                Text("حفظ الجدولة")
            }
            
            Switch(
                checked = false,
                onCheckedChange = { },
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Composable
fun LogSection(
    modifier: Modifier,
    logs: List<LogEntry>,
    onLogSelected: (LogEntry) -> Unit
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("السجل (${logs.size})", style = MaterialTheme.typography.titleSmall)
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(onClick = { /* Copy */ }) {
                    Icon(Icons.Default.ContentCopy, contentDescription = "نسخ")
                }
                IconButton(onClick = { /* Share */ }) {
                    Icon(Icons.Default.Share, contentDescription = "مشاركة")
                }
                IconButton(onClick = { /* Clear */ }) {
                    Icon(Icons.Default.Clear, contentDescription = "مسح")
                }
            }
        }
        
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(logs) { log ->
                LogEntry(log = log, onClick = { onLogSelected(log) })
            }
        }
    }
}

@Composable
fun LogEntry(
    log: LogEntry,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = when (log.level) {
                LogLevel.ERROR -> Color(0xFFFFCDD2)
                LogLevel.WARNING -> Color(0xFFFFF9C4)
                LogLevel.SUCCESS -> Color(0xFFC8E6C9)
                LogLevel.INFO -> Color(0xFFBBDEFB)
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = log.message,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        text = log.timestamp.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        text = log.nodeName,
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        text = "${log.duration}ms",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}

@Composable
fun SummaryBar(
    modifier: Modifier,
    runnerState: RunnerState
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "عقد منفذة: ${runnerState.executedNodes}",
                    style = MaterialTheme.typography.labelSmall
                )
                Text(
                    text = "الوقت الكلي: ${runnerState.totalTime}ms",
                    style = MaterialTheme.typography.labelSmall
                )
            }
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = { }) {
                    Text("إعادة تشغيل")
                }
                Button(onClick = { }) {
                    Text("حفظ التقرير")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NodeDetailPanel(
    log: LogEntry,
    onDismiss: () -> Unit,
    onOpenInEditor: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxHeight(0.7f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "تفاصيل: ${log.nodeName}",
                    style = MaterialTheme.typography.titleSmall
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "إغلاق")
                }
            }
            
            // عرض المدخلات والمخرجات
            Text("المدخلات:", style = MaterialTheme.typography.labelSmall)
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
            ) {
                Text("{ }", modifier = Modifier.padding(12.dp))
            }
            
            Text("المخرجات:", style = MaterialTheme.typography.labelSmall)
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
            ) {
                Text("{ }", modifier = Modifier.padding(12.dp))
            }
            
            Button(
                onClick = onOpenInEditor,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("فتح في المحرر")
            }
        }
    }
}
