package com.flowauto.app.models.workflow

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Construction
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import java.util.UUID

enum class NodeType(val displayName: String) {
    TRIGGER_MANUAL("تشغيل يدوي"),
    TRIGGER_WEBHOOK("Webhook"),
    TRIGGER_SCHEDULE("جدولة"),
    BROWSER_OPEN("فتح متصفح"),
    BROWSER_NAVIGATE("التنقل"),
    BROWSER_CLICK("نقرة"),
    CODE_JAVASCRIPT("JavaScript"),
    CODE_PYTHON("Python"),
    DATA_TRANSFORM("تحويل"),
    DATA_MERGE("دمج"),
    DATA_FILTER("تصفية"),
    FLOW_IF("شرط"),
    FLOW_LOOP("حلقة"),
    FLOW_SWITCH("التبديل"),
    FILE_READ("قراءة ملف"),
    FILE_WRITE("كتابة ملف"),
    FILE_WATCH("مراقبة ملف"),
    RACCORD_RUN("تشغيل موصل"),
    SELECTOR_USE("استخدام محدد")
}

data class WorkflowNode(
    val id: String = UUID.randomUUID().toString(),
    val type: NodeType,
    val name: String,
    val description: String = "",
    val x: Float = 0f,
    val y: Float = 0f,
    val width: Float = 120f,
    val height: Float = 80f,
    val inputPorts: List<Port> = emptyList(),
    val outputPorts: List<Port> = emptyList(),
    val config: Map<String, Any> = emptyMap(),
    val enabled: Boolean = true,
    val color: Color = getColorForType(type),
    val icon: ImageVector = getIconForType(type),
    val executionTime: Long = 0,
    val lastError: String? = null
) {
    companion object {
        fun createFromType(type: NodeType): WorkflowNode {
            return WorkflowNode(
                type = type,
                name = type.displayName,
                inputPorts = getInputPortsForType(type),
                outputPorts = getOutputPortsForType(type)
            )
        }
    }
}

data class Port(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val type: PortType,
    val dataType: DataType = DataType.ANY
)

enum class PortType {
    INPUT, OUTPUT
}

enum class DataType {
    STRING, NUMBER, BOOLEAN, OBJECT, ARRAY, ANY
}

data class Connection(
    val id: String = UUID.randomUUID().toString(),
    val sourceNodeId: String,
    val sourcePortId: String,
    val targetNodeId: String,
    val targetPortId: String,
    val dataMapping: DataMapping? = null
)

data class DataMapping(
    val sourceFields: List<String> = emptyList(),
    val targetFields: List<String> = emptyList(),
    val transformations: Map<String, String> = emptyMap()
)

data class EditorState(
    val nodes: List<WorkflowNode> = emptyList(),
    val connections: List<Connection> = emptyList(),
    val selectedNodeId: String? = null,
    val selectedConnectionId: String? = null,
    val zoomLevel: Float = 1f,
    val panX: Float = 0f,
    val panY: Float = 0f
) {
    fun addNode(node: WorkflowNode) = copy(nodes = nodes + node)
    fun removeNode(nodeId: String) = copy(
        nodes = nodes.filter { it.id != nodeId },
        connections = connections.filter { it.sourceNodeId != nodeId && it.targetNodeId != nodeId }
    )
    fun updateNode(node: WorkflowNode) = copy(
        nodes = nodes.map { if (it.id == node.id) node else it }
    )
    fun addConnection(connection: Connection) = copy(
        connections = connections + connection
    )
    fun removeConnection(connectionId: String) = copy(
        connections = connections.filter { it.id != connectionId }
    )
}

fun getColorForType(type: NodeType): Color = when (type) {
    NodeType.TRIGGER_MANUAL, NodeType.TRIGGER_WEBHOOK, NodeType.TRIGGER_SCHEDULE -> Color(0xFF4CAF50)
    NodeType.BROWSER_OPEN, NodeType.BROWSER_NAVIGATE, NodeType.BROWSER_CLICK -> Color(0xFF2196F3)
    NodeType.CODE_JAVASCRIPT, NodeType.CODE_PYTHON -> Color(0xFFFFC107)
    NodeType.DATA_TRANSFORM, NodeType.DATA_MERGE, NodeType.DATA_FILTER -> Color(0xFF9C27B0)
    NodeType.FLOW_IF, NodeType.FLOW_LOOP, NodeType.FLOW_SWITCH -> Color(0xFFF44336)
    NodeType.FILE_READ, NodeType.FILE_WRITE, NodeType.FILE_WATCH -> Color(0xFF00BCD4)
    NodeType.RACCORD_RUN, NodeType.SELECTOR_USE -> Color(0xFF673AB7)
}

fun getIconForType(type: NodeType): ImageVector = when (type) {
    NodeType.TRIGGER_MANUAL -> Icons.Default.PlayArrow
    NodeType.TRIGGER_WEBHOOK -> Icons.Default.Webhook
    NodeType.TRIGGER_SCHEDULE -> Icons.Default.Schedule
    NodeType.BROWSER_OPEN -> Icons.Default.OpenInBrowser
    NodeType.BROWSER_NAVIGATE -> Icons.Default.Navigation
    NodeType.BROWSER_CLICK -> Icons.Default.TouchApp
    NodeType.CODE_JAVASCRIPT, NodeType.CODE_PYTHON -> Icons.Default.Code
    NodeType.DATA_TRANSFORM -> Icons.Default.Edit
    NodeType.DATA_MERGE -> Icons.Default.Merge
    NodeType.DATA_FILTER -> Icons.Default.FilterList
    NodeType.FLOW_IF -> Icons.Default.Call
    NodeType.FLOW_LOOP -> Icons.Default.Loop
    NodeType.FLOW_SWITCH -> Icons.Default.SwapHoriz
    NodeType.FILE_READ -> Icons.Default.FileOpen
    NodeType.FILE_WRITE -> Icons.Default.Save
    NodeType.FILE_WATCH -> Icons.Default.Visibility
    NodeType.RACCORD_RUN -> Icons.Default.Extension
    NodeType.SELECTOR_USE -> Icons.Default.SelectAll
}

fun getInputPortsForType(type: NodeType): List<Port> {
    return when (type) {
        NodeType.TRIGGER_MANUAL, NodeType.TRIGGER_WEBHOOK, NodeType.TRIGGER_SCHEDULE -> emptyList()
        else -> listOf(Port(name = "المدخل", type = PortType.INPUT))
    }
}

fun getOutputPortsForType(type: NodeType): List<Port> {
    return when (type) {
        NodeType.FILE_READ, NodeType.CODE_JAVASCRIPT, NodeType.CODE_PYTHON -> {
            listOf(
                Port(name = "النجاح", type = PortType.OUTPUT),
                Port(name = "خطأ", type = PortType.OUTPUT)
            )
        }
        else -> listOf(Port(name = "المخرج", type = PortType.OUTPUT))
    }
}
