package com.flowauto.app.screens.editor

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.flowauto.app.models.workflow.Connection
import com.flowauto.app.models.workflow.EditorState
import com.flowauto.app.models.workflow.NodeType
import com.flowauto.app.models.workflow.WorkflowNode
import com.flowauto.app.viewmodel.WorkflowEditorViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkflowEditorScreen(
    navController: NavHostController,
    viewModel: WorkflowEditorViewModel = viewModel()
) {
    val editorState by viewModel.editorState.collectAsState()
    val isSaving by viewModel.isSaving.collectAsState()
    val undoStack by viewModel.undoStack.collectAsState()
    val redoStack by viewModel.redoStack.collectAsState()
    var showAddNodeSheet by remember { mutableStateOf(false) }
    var showNodeEditor by remember { mutableStateOf(false) }
    var selectedNode by remember(editorState.selectedNodeId, editorState.nodes) {
        mutableStateOf(editorState.nodes.firstOrNull { it.id == editorState.selectedNodeId })
    }
    var selectedConnection by remember(editorState.selectedConnectionId, editorState.connections) {
        mutableStateOf(editorState.connections.firstOrNull { it.id == editorState.selectedConnectionId })
    }
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            EditorTopBar(
                canUndo = undoStack.isNotEmpty(),
                canRedo = redoStack.isNotEmpty(),
                isSaving = isSaving,
                onUndo = viewModel::undo,
                onRedo = viewModel::redo,
                onSave = { viewModel.saveWorkflow("سير عمل جديد") },
                onRun = { /* Hook runner screen when execution is connected. */ },
                onSettings = { navController.navigate("settings") }
            )
        },
        bottomBar = {
            BottomToolbar(
                selectedNode = selectedNode,
                selectedConnection = selectedConnection,
                onAddNode = { showAddNodeSheet = true },
                onDelete = {
                    selectedNode?.let { viewModel.removeNode(it.id) }
                    selectedConnection?.let { viewModel.removeConnection(it.id) }
                },
                onEditNode = { showNodeEditor = true },
                onDuplicateNode = { selectedNode?.let { viewModel.duplicateNode(it.id) } },
                onAutoArrange = viewModel::autoArrange,
                onAddSample = viewModel::addStarterWorkflow
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showAddNodeSheet = true },
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("إضافة عقدة") }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            WorkflowCanvas(
                modifier = Modifier.fillMaxSize(),
                editorState = editorState,
                onNodeSelected = { viewModel.selectNode(it.id) },
                onConnectionSelected = { viewModel.selectConnection(it.id) },
                onNodeMoved = viewModel::moveNode,
                onEmptySpaceClick = { viewModel.clearSelection() }
            )

            WorkflowInspector(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp),
                editorState = editorState,
                selectedNode = selectedNode,
                selectedConnection = selectedConnection
            )
        }
    }

    if (showAddNodeSheet) {
        AddNodeSheet(
            onNodeSelected = { nodeType ->
                viewModel.addNode(WorkflowNode.createFromType(nodeType).copy(x = 48f, y = 96f))
                showAddNodeSheet = false
            },
            onDismiss = { showAddNodeSheet = false }
        )
    }

    if (showNodeEditor && selectedNode != null) {
        NodeEditorPanel(
            node = selectedNode!!,
            onNodeUpdated = viewModel::updateNode,
            onDismiss = { showNodeEditor = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorTopBar(
    canUndo: Boolean,
    canRedo: Boolean,
    isSaving: Boolean,
    onUndo: () -> Unit,
    onRedo: () -> Unit,
    onSave: () -> Unit,
    onRun: () -> Unit,
    onSettings: () -> Unit
) {
    TopAppBar(
        title = {
            Column {
                Text("محرر سير العمل", fontWeight = FontWeight.Bold)
                Text("صمّم، اربط، وشغّل الأتمتة", style = MaterialTheme.typography.labelSmall)
            }
        },
        actions = {
            IconButton(enabled = canUndo, onClick = onUndo) { Icon(Icons.Default.Undo, contentDescription = "تراجع") }
            IconButton(enabled = canRedo, onClick = onRedo) { Icon(Icons.Default.Redo, contentDescription = "إعادة") }
            IconButton(onClick = onRun) { Icon(Icons.Default.PlayArrow, contentDescription = "تشغيل") }
            IconButton(enabled = !isSaving, onClick = onSave) {
                if (isSaving) CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp) else Icon(Icons.Default.Save, contentDescription = "حفظ")
            }
            IconButton(onClick = onSettings) { Icon(Icons.Default.Settings, contentDescription = "إعدادات") }
        }
    )
}

@Composable
fun WorkflowCanvas(
    modifier: Modifier,
    editorState: EditorState,
    onNodeSelected: (WorkflowNode) -> Unit,
    onConnectionSelected: (Connection) -> Unit,
    onNodeMoved: (String, Float, Float) -> Unit,
    onEmptySpaceClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f))
            .pointerInput(Unit) { detectTapGestures { onEmptySpaceClick() } }
    ) {
        Canvas(Modifier.fillMaxSize()) {
            val grid = 32.dp.toPx()
            for (x in 0..(size.width / grid).roundToInt()) drawLine(Color.Gray.copy(alpha = .16f), Offset(x * grid, 0f), Offset(x * grid, size.height))
            for (y in 0..(size.height / grid).roundToInt()) drawLine(Color.Gray.copy(alpha = .16f), Offset(0f, y * grid), Offset(size.width, y * grid))
        }

        editorState.connections.forEach { connection ->
            ConnectionLine(connection = connection, editorState = editorState, selected = connection.id == editorState.selectedConnectionId, onClick = { onConnectionSelected(connection) })
        }

        editorState.nodes.forEach { node ->
            WorkflowNodeView(
                node = node,
                selected = node.id == editorState.selectedNodeId,
                onClick = { onNodeSelected(node) },
                onMove = { dx, dy -> onNodeMoved(node.id, dx, dy) }
            )
        }

        if (editorState.nodes.isEmpty()) EmptyCanvasState(Modifier.align(Alignment.Center))
        Minimap(Modifier.align(Alignment.BottomStart).padding(12.dp).size(132.dp), editorState.nodes, editorState.connections)
    }
}

@Composable
fun WorkflowNodeView(node: WorkflowNode, selected: Boolean, onClick: () -> Unit, onMove: (Float, Float) -> Unit) {
    Card(
        modifier = Modifier
            .offset { IntOffset(node.x.roundToInt(), node.y.roundToInt()) }
            .size(node.width.dp, node.height.dp)
            .border(if (selected) 2.dp else 0.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
            .pointerInput(node.id) { detectDragGestures(onDragStart = { onClick() }) { change, drag -> change.consume(); onMove(drag.x, drag.y) } }
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = node.color.copy(alpha = if (node.enabled) .92f else .45f)),
        elevation = CardDefaults.cardElevation(defaultElevation = if (selected) 10.dp else 4.dp)
    ) {
        Column(Modifier.fillMaxSize().padding(10.dp), verticalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(node.icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(22.dp))
                Spacer(Modifier.width(8.dp))
                Text(node.name, color = Color.White, style = MaterialTheme.typography.labelLarge, maxLines = 2)
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                PortDots(node.inputPorts.size, Alignment.Start)
                AssistChip(onClick = {}, label = { Text(if (node.enabled) "نشط" else "متوقف") }, modifier = Modifier.height(28.dp))
                PortDots(node.outputPorts.size, Alignment.End)
            }
        }
    }
}

@Composable
private fun PortDots(count: Int, alignment: Alignment.Horizontal) {
    Column(horizontalAlignment = alignment, verticalArrangement = Arrangement.spacedBy(3.dp)) {
        repeat(count.coerceAtLeast(1)) { Box(Modifier.size(8.dp).background(MaterialTheme.colorScheme.onPrimary, CircleShape)) }
    }
}

@Composable
fun ConnectionLine(connection: Connection, editorState: EditorState, selected: Boolean, onClick: () -> Unit) {
    val source = editorState.nodes.firstOrNull { it.id == connection.sourceNodeId }
    val target = editorState.nodes.firstOrNull { it.id == connection.targetNodeId }
    if (source != null && target != null) {
        Canvas(Modifier.fillMaxSize().pointerInput(connection.id) { detectTapGestures { onClick() } }) {
            drawLine(
                color = if (selected) Color(0xFFFF9800) else Color(0xFF607D8B),
                start = Offset(source.x + source.width, source.y + source.height / 2),
                end = Offset(target.x, target.y + target.height / 2),
                strokeWidth = if (selected) 6f else 4f,
                cap = StrokeCap.Round,
                pathEffect = if (selected) PathEffect.dashPathEffect(floatArrayOf(18f, 10f)) else null
            )
        }
    }
}

@Composable
fun BottomToolbar(
    selectedNode: WorkflowNode?,
    selectedConnection: Connection?,
    onAddNode: () -> Unit,
    onDelete: () -> Unit,
    onEditNode: () -> Unit,
    onDuplicateNode: () -> Unit,
    onAutoArrange: () -> Unit,
    onAddSample: () -> Unit
) {
    Surface(tonalElevation = 8.dp) {
        LazyRow(Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            item { FilledTonalButton(onClick = onAddNode) { Icon(Icons.Default.Add, null); Spacer(Modifier.width(6.dp)); Text("عقدة") } }
            item { OutlinedButton(onClick = onAutoArrange) { Icon(Icons.Default.GridView, null); Spacer(Modifier.width(6.dp)); Text("ترتيب") } }
            item { OutlinedButton(onClick = onAddSample) { Icon(Icons.Default.AutoAwesome, null); Spacer(Modifier.width(6.dp)); Text("قالب") } }
            if (selectedNode != null) {
                item { Button(onClick = onEditNode) { Icon(Icons.Default.Edit, null); Spacer(Modifier.width(6.dp)); Text("تحرير") } }
                item { OutlinedButton(onClick = onDuplicateNode) { Icon(Icons.Default.ContentCopy, null); Spacer(Modifier.width(6.dp)); Text("نسخ") } }
            }
            if (selectedNode != null || selectedConnection != null) item { OutlinedButton(onClick = onDelete) { Icon(Icons.Default.Delete, null); Spacer(Modifier.width(6.dp)); Text("حذف") } }
        }
    }
}

@Composable
private fun EmptyCanvasState(modifier: Modifier = Modifier) {
    ElevatedCard(modifier = modifier.padding(24.dp), colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.AccountTree, contentDescription = null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.primary)
            Text("ابدأ ببناء سير عملك", style = MaterialTheme.typography.titleMedium)
            Text("أضف عقدة أو استخدم القالب الجاهز من الشريط السفلي.", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun WorkflowInspector(modifier: Modifier, editorState: EditorState, selectedNode: WorkflowNode?, selectedConnection: Connection?) {
    ElevatedCard(modifier = modifier.widthIn(min = 220.dp, max = 280.dp)) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("لوحة المعلومات", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            Text("العقد: ${editorState.nodes.size}")
            Text("الاتصالات: ${editorState.connections.size}")
            selectedNode?.let { Text("المحدد: ${it.name}", color = MaterialTheme.colorScheme.primary) }
            selectedConnection?.let { Text("اتصال محدد", color = MaterialTheme.colorScheme.primary) }
        }
    }
}

@Composable
fun Minimap(modifier: Modifier, nodes: List<WorkflowNode>, connections: List<Connection>) {
    Surface(modifier = modifier, color = MaterialTheme.colorScheme.surface.copy(alpha = 0.88f), shape = MaterialTheme.shapes.medium, shadowElevation = 4.dp) {
        Column(Modifier.padding(10.dp), verticalArrangement = Arrangement.SpaceBetween) {
            Text("خريطة مصغرة", style = MaterialTheme.typography.labelMedium)
            Text("${nodes.size} عقدة")
            Text("${connections.size} رابط")
        }
    }
}
