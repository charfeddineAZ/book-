package com.flowauto.app.screens.editor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.flowauto.app.models.workflow.WorkflowNode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NodeEditorPanel(
    node: WorkflowNode,
    onNodeUpdated: (WorkflowNode) -> Unit,
    onDismiss: () -> Unit
) {
    val updatedNode = remember { mutableStateOf(node) }
    val selectedTab = remember { mutableStateOf(0) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxHeight(0.95f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            // شريط العنوان
            TopAppBar(
                title = { Text("تحرير: ${node.name}") },
                actions = {
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "إغلاق")
                    }
                }
            )

            // التبويبات
            TabRow(selectedTabIndex = selectedTab.value) {
                Tab(
                    selected = selectedTab.value == 0,
                    onClick = { selectedTab.value = 0 },
                    text = { Text("الإعدادات") }
                )
                Tab(
                    selected = selectedTab.value == 1,
                    onClick = { selectedTab.value = 1 },
                    text = { Text("السكريبت") }
                )
                Tab(
                    selected = selectedTab.value == 2,
                    onClick = { selectedTab.value = 2 },
                    text = { Text("البيانات") }
                )
                Tab(
                    selected = selectedTab.value == 3,
                    onClick = { selectedTab.value = 3 },
                    text = { Text("الأداء") }
                )
            }

            // محتوى التبويبات
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                when (selectedTab.value) {
                    0 -> SettingsTab(node = updatedNode.value, onNodeUpdated = { updatedNode.value = it })
                    1 -> ScriptTab(node = updatedNode.value, onNodeUpdated = { updatedNode.value = it })
                    2 -> DataTab(node = updatedNode.value)
                    3 -> PerformanceTab(node = updatedNode.value)
                }
            }

            // أزرار الحفظ والإلغاء
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors()
                ) {
                    Text("إلغاء")
                }
                Button(
                    onClick = {
                        onNodeUpdated(updatedNode.value)
                        onDismiss()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("حفظ")
                }
            }
        }
    }
}

@Composable
fun SettingsTab(
    node: WorkflowNode,
    onNodeUpdated: (WorkflowNode) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextField(
            value = node.name,
            onValueChange = { onNodeUpdated(node.copy(name = it)) },
            label = { Text("اسم العقدة") },
            modifier = Modifier.fillMaxWidth()
        )
        
        TextField(
            value = node.description,
            onValueChange = { onNodeUpdated(node.copy(description = it)) },
            label = { Text("الوصف") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Text(
            text = "معاملات خاصة بالعقدة",
            style = MaterialTheme.typography.titleSmall
        )
        // سيتم إضافة معاملات حسب نوع العقدة
    }
}

@Composable
fun ScriptTab(
    node: WorkflowNode,
    onNodeUpdated: (WorkflowNode) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "محرر الأكواد",
            style = MaterialTheme.typography.titleSmall
        )
        // محرر أكواد متقدم
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            Text("سيتم إضافة محرر أكواد متقدم هنا", modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun DataTab(node: WorkflowNode) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "مدخلات العقدة",
            style = MaterialTheme.typography.titleSmall
        )
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "لم يتم تشغيل السيناريو بعد",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun PerformanceTab(node: WorkflowNode) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "معلومات الأداء",
            style = MaterialTheme.typography.titleSmall
        )
        Text("وقت التنفيذ: -")
        Text("عدد محاولات التشغيل: 0")
    }
}
