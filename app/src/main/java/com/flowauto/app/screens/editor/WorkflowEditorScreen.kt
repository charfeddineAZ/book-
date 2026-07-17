package com.flowauto.app.screens.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.flowauto.app.models.workflow.*
import com.flowauto.app.ui.components.*

@Composable
fun WorkflowEditorScreen(navController: NavHostController) {
    val editorState = remember { mutableStateOf(EditorState()) }
    val showAddNodeSheet = remember { mutableStateOf(false) }
    val selectedNode = remember { mutableStateOf<WorkflowNode?>(null) }
    val selectedConnection = remember { mutableStateOf<Connection?>(null) }
    val showNodeEditor = remember { mutableStateOf(false) }
    val undoStack = remember { mutableStateOf(emptyList<EditorState>()) }
    val redoStack = remember { mutableStateOf(emptyList<EditorState>()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        // شريط الأدوات العلوي
        EditorTopBar(
            onUndo = { /* Undo logic */ },
            onRedo = { /* Redo logic */ },
            onSave = { /* Save logic */ },
            onSettings = { navController.navigate("settings") }
        )

        // القماش الرئيسي
        WorkflowCanvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 56.dp, bottom = 56.dp),
            editorState = editorState.value,
            onNodeSelected = { node -> selectedNode.value = node },
            onConnectionSelected = { conn -> selectedConnection.value = conn },
            onEmptySpaceClick = {
                selectedNode.value = null
                selectedConnection.value = null
            }
        )

        // شريط الأدوات السفلي الديناميكي
        BottomToolbar(
            modifier = Modifier.align(Alignment.BottomCenter),
            selectedNode = selectedNode.value,
            selectedConnection = selectedConnection.value,
            onAddNode = { showAddNodeSheet.value = true },
            onDeleteNode = {
                selectedNode.value?.let {
                    editorState.value = editorState.value.removeNode(it.id)
                    selectedNode.value = null
                }
            },
            onEditNode = { showNodeEditor.value = true },
            onDataMapping = { /* Open data mapping */ }
        )

        // زر عائم لإضافة عقدة
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp, 72.dp),
            onClick = { showAddNodeSheet.value = true },
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(Icons.Default.Add, contentDescription = "إضافة عقدة")
        }

        // لوحة اختيار العقدة
        if (showAddNodeSheet.value) {
            AddNodeSheet(
                onNodeSelected = { nodeType ->
                    val newNode = WorkflowNode.createFromType(nodeType)
                    editorState.value = editorState.value.addNode(newNode)
                    showAddNodeSheet.value = false
                },
                onDismiss = { showAddNodeSheet.value = false }
            )
        }

        // لوحة تحرير العقدة
        if (showNodeEditor.value && selectedNode.value != null) {
            NodeEditorPanel(
                node = selectedNode.value!!,
                onNodeUpdated = { updatedNode ->
                    editorState.value = editorState.value.updateNode(updatedNode)
                },
                onDismiss = { showNodeEditor.value = false }
            )
        }
    }
}

@Composable
fun EditorTopBar(
    onUndo: () -> Unit,
    onRedo: () -> Unit,
    onSave: () -> Unit,
    onSettings: () -> Unit
) {
    TopAppBar(
        title = { Text("محرر سير العمل") },
        actions = {
            IconButton(onClick = onUndo) {
                Icon(Icons.Default.Undo, contentDescription = "تراجع")
            }
            IconButton(onClick = onRedo) {
                Icon(Icons.Default.Redo, contentDescription = "إعادة")
            }
            IconButton(onClick = onSave) {
                Icon(Icons.Default.Save, contentDescription = "حفظ")
            }
            IconButton(onClick = onSettings) {
                Icon(Icons.Default.Settings, contentDescription = "إعدادات")
            }
        }
    )
}

@Composable
fun WorkflowCanvas(
    modifier: Modifier,
    editorState: EditorState,
    onNodeSelected: (WorkflowNode) -> Unit,
    onConnectionSelected: (Connection) -> Unit,
    onEmptySpaceClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            .pointerInput(Unit) {
                detectTapGestures {
                    onEmptySpaceClick()
                }
            }
    ) {
        // رسم الاتصالات
        editorState.connections.forEach { connection ->
            ConnectionLine(
                connection = connection,
                onClick = { onConnectionSelected(connection) }
            )
        }

        // رسم العقد
        editorState.nodes.forEach { node ->
            WorkflowNodeView(
                node = node,
                onClick = { onNodeSelected(node) },
                onOutputPointClick = { /* Handle connection start */ },
                onInputPointClick = { /* Handle connection end */ }
            )
        }

        // خريطة مصغرة
        Minimap(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp)
                .size(100.dp),
            nodes = editorState.nodes,
            connections = editorState.connections
        )
    }
}

@Composable
fun BottomToolbar(
    modifier: Modifier,
    selectedNode: WorkflowNode?,
    selectedConnection: Connection?,
    onAddNode: () -> Unit,
    onDeleteNode: () -> Unit,
    onEditNode: () -> Unit,
    onDataMapping: () -> Unit
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
            if (selectedNode == null && selectedConnection == null) {
                // حالة عدم التحديد
                IconButton(onClick = onAddNode) {
                    Icon(Icons.Default.Add, contentDescription = "إضافة عقدة")
                }
                IconButton(onClick = { /* Search */ }) {
                    Icon(Icons.Default.Search, contentDescription = "بحث")
                }
                IconButton(onClick = { /* Auto-arrange */ }) {
                    Icon(Icons.Default.GridView, contentDescription = "ترتيب تلقائي")
                }
            } else if (selectedNode != null) {
                // حالة تحديد عقدة
                IconButton(onClick = onEditNode) {
                    Icon(Icons.Default.Edit, contentDescription = "تحرير")
                }
                IconButton(onClick = { /* Copy */ }) {
                    Icon(Icons.Default.ContentCopy, contentDescription = "نسخ")
                }
                IconButton(onClick = { /* Cut */ }) {
                    Icon(Icons.Default.ContentCut, contentDescription = "قص")
                }
                IconButton(onClick = onDeleteNode) {
                    Icon(Icons.Default.Delete, contentDescription = "حذف")
                }
                IconButton(onClick = onDataMapping) {
                    Icon(Icons.Default.Link, contentDescription = "تخطيط البيانات")
                }
            } else if (selectedConnection != null) {
                // حالة تحديد اتصال
                IconButton(onClick = onDeleteNode) {
                    Icon(Icons.Default.Delete, contentDescription = "حذف")
                }
                IconButton(onClick = onDataMapping) {
                    Icon(Icons.Default.Link, contentDescription = "تخطيط البيانات")
                }
            }
        }
    }
}

@Composable
fun WorkflowNodeView(
    node: WorkflowNode,
    onClick: () -> Unit,
    onOutputPointClick: (String) -> Unit,
    onInputPointClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .size(120.dp, 80.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { onClick() })
            },
        colors = CardDefaults.cardColors(
            containerColor = node.color
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = node.icon,
                contentDescription = node.name,
                tint = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = node.name,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 2
            )
        }
    }
}

@Composable
fun ConnectionLine(
    connection: Connection,
    onClick: () -> Unit
) {
    // سيتم رسم خط الاتصال هنا
    // هذا مثال مبسط
    Box(
        modifier = Modifier
            .size(200.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { onClick() })
            }
    )
}

@Composable
fun Minimap(
    modifier: Modifier,
    nodes: List<WorkflowNode>,
    connections: List<Connection>
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
        shape = MaterialTheme.shapes.small,
        shadowElevation = 4.dp
    ) {
        // عرض مصغر للعقد والاتصالات
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "خريطة: ${nodes.size} عقدة",
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
