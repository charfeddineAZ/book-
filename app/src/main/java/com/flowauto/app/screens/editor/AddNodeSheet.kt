package com.flowauto.app.screens.editor

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.flowauto.app.models.workflow.NodeType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNodeSheet(
    onNodeSelected: (NodeType) -> Unit,
    onDismiss: () -> Unit
) {
    val categories = remember {
        listOf(
            "المشغّلات" to listOf(
                NodeType.TRIGGER_MANUAL,
                NodeType.TRIGGER_WEBHOOK,
                NodeType.TRIGGER_SCHEDULE
            ),
            "المتصفح" to listOf(
                NodeType.BROWSER_OPEN,
                NodeType.BROWSER_NAVIGATE,
                NodeType.BROWSER_CLICK
            ),
            "الكود" to listOf(
                NodeType.CODE_JAVASCRIPT,
                NodeType.CODE_PYTHON
            ),
            "البيانات" to listOf(
                NodeType.DATA_TRANSFORM,
                NodeType.DATA_MERGE,
                NodeType.DATA_FILTER
            ),
            "التحكم" to listOf(
                NodeType.FLOW_IF,
                NodeType.FLOW_LOOP,
                NodeType.FLOW_SWITCH
            ),
            "الملفات" to listOf(
                NodeType.FILE_READ,
                NodeType.FILE_WRITE,
                NodeType.FILE_WATCH
            ),
            "الملحقات" to listOf(
                NodeType.RACCORD_RUN,
                NodeType.SELECTOR_USE
            )
        )
    }

    val selectedTab = remember { mutableStateOf(0) }
    val searchQuery = remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxHeight(0.9f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // شريط البحث
            TextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                placeholder = { Text("ابحث عن عقدة...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true
            )

            // تبويبات الفئات
            ScrollableTabRow(
                selectedTabIndex = selectedTab.value,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                categories.forEachIndexed { index, (category, _) ->
                    Tab(
                        selected = selectedTab.value == index,
                        onClick = { selectedTab.value = index },
                        text = { Text(category) }
                    )
                }
            }

            // شبكة العقد
            val currentNodes = categories[selectedTab.value].second
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(currentNodes) { nodeType ->
                    NodeCard(
                        nodeType = nodeType,
                        onClick = { onNodeSelected(nodeType) }
                    )
                }
            }
        }
    }
}

@Composable
fun NodeCard(
    nodeType: NodeType,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = getNodeIcon(nodeType),
                contentDescription = nodeType.displayName,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = nodeType.displayName,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 2
            )
        }
    }
}

fun getNodeIcon(type: NodeType) = when (type) {
    NodeType.TRIGGER_MANUAL -> Icons.Default.PlayArrow
    NodeType.TRIGGER_WEBHOOK -> Icons.Default.Webhook
    NodeType.TRIGGER_SCHEDULE -> Icons.Default.Schedule
    NodeType.BROWSER_OPEN -> Icons.Default.OpenInBrowser
    NodeType.BROWSER_NAVIGATE -> Icons.Default.Navigation
    NodeType.BROWSER_CLICK -> Icons.Default.TouchApp
    NodeType.CODE_JAVASCRIPT -> Icons.Default.Code
    NodeType.CODE_PYTHON -> Icons.Default.Code
    NodeType.DATA_TRANSFORM -> Icons.Default.Transform
    NodeType.DATA_MERGE -> Icons.Default.MergeType
    NodeType.DATA_FILTER -> Icons.Default.FilterList
    NodeType.FLOW_IF -> Icons.Default.IfElse
    NodeType.FLOW_LOOP -> Icons.Default.Loop
    NodeType.FLOW_SWITCH -> Icons.Default.SwapHoriz
    NodeType.FILE_READ -> Icons.Default.FileOpen
    NodeType.FILE_WRITE -> Icons.Default.Save
    NodeType.FILE_WATCH -> Icons.Default.Visibility
    NodeType.RACCORD_RUN -> Icons.Default.Extension
    NodeType.SELECTOR_USE -> Icons.Default.SelectAll
    else -> Icons.Default.Help
}
