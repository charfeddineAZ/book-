package com.flowauto.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flowauto.app.models.workflow.WorkflowNode
import com.flowauto.app.models.workflow.Connection
import com.flowauto.app.models.workflow.EditorState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WorkflowEditorViewModel : ViewModel() {
    private val _editorState = MutableStateFlow(EditorState())
    val editorState: StateFlow<EditorState> = _editorState

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving

    private val _undoStack = MutableStateFlow(emptyList<EditorState>())
    val undoStack: StateFlow<List<EditorState>> = _undoStack

    private val _redoStack = MutableStateFlow(emptyList<EditorState>())
    val redoStack: StateFlow<List<EditorState>> = _redoStack

    fun addNode(node: WorkflowNode) {
        commitState(_editorState.value.addNode(node))
    }

    fun removeNode(nodeId: String) {
        commitState(_editorState.value.removeNode(nodeId))
    }

    fun updateNode(node: WorkflowNode) {
        commitState(_editorState.value.updateNode(node))
    }

    fun addConnection(connection: Connection) {
        commitState(_editorState.value.addConnection(connection))
    }

    fun removeConnection(connectionId: String) {
        commitState(_editorState.value.removeConnection(connectionId))
    }

    fun selectNode(nodeId: String) {
        _editorState.value = _editorState.value.copy(selectedNodeId = nodeId, selectedConnectionId = null)
    }

    fun selectConnection(connectionId: String) {
        _editorState.value = _editorState.value.copy(selectedConnectionId = connectionId, selectedNodeId = null)
    }

    fun clearSelection() {
        _editorState.value = _editorState.value.copy(selectedNodeId = null, selectedConnectionId = null)
    }

    fun moveNode(nodeId: String, deltaX: Float, deltaY: Float) {
        _editorState.value = _editorState.value.copy(
            nodes = _editorState.value.nodes.map { node ->
                if (node.id == nodeId) node.copy(x = (node.x + deltaX).coerceAtLeast(0f), y = (node.y + deltaY).coerceAtLeast(0f)) else node
            }
        )
    }

    fun duplicateNode(nodeId: String) {
        _editorState.value.nodes.firstOrNull { it.id == nodeId }?.let { node ->
            addNode(node.copy(id = java.util.UUID.randomUUID().toString(), name = "${node.name} نسخة", x = node.x + 32f, y = node.y + 32f))
        }
    }

    fun autoArrange() {
        val arranged = _editorState.value.nodes.mapIndexed { index, node ->
            node.copy(x = 40f + (index % 2) * 220f, y = 40f + (index / 2) * 140f)
        }
        commitState(_editorState.value.copy(nodes = arranged))
    }

    fun addStarterWorkflow() {
        val trigger = WorkflowNode.createFromType(com.flowauto.app.models.workflow.NodeType.TRIGGER_MANUAL).copy(x = 40f, y = 80f)
        val browser = WorkflowNode.createFromType(com.flowauto.app.models.workflow.NodeType.BROWSER_OPEN).copy(x = 260f, y = 80f)
        val transform = WorkflowNode.createFromType(com.flowauto.app.models.workflow.NodeType.DATA_TRANSFORM).copy(x = 480f, y = 80f)
        val first = Connection(sourceNodeId = trigger.id, sourcePortId = trigger.outputPorts.first().id, targetNodeId = browser.id, targetPortId = browser.inputPorts.first().id)
        val second = Connection(sourceNodeId = browser.id, sourcePortId = browser.outputPorts.first().id, targetNodeId = transform.id, targetPortId = transform.inputPorts.first().id)
        commitState(EditorState(nodes = listOf(trigger, browser, transform), connections = listOf(first, second)))
    }

    private fun commitState(newState: EditorState) {
        _undoStack.value = (_undoStack.value + _editorState.value).takeLast(50)
        _redoStack.value = emptyList()
        _editorState.value = newState
    }

    fun saveWorkflow(name: String) {
        viewModelScope.launch {
            _isSaving.value = true
            try {
                // حفظ السيناريو
                _isSaving.value = false
            } catch (e: Exception) {
                _isSaving.value = false
            }
        }
    }

    fun undo() {
        if (_undoStack.value.isNotEmpty()) {
            _redoStack.value = _redoStack.value + _editorState.value
            _editorState.value = _undoStack.value.last()
            _undoStack.value = _undoStack.value.dropLast(1)
        }
    }

    fun redo() {
        if (_redoStack.value.isNotEmpty()) {
            _undoStack.value = _undoStack.value + _editorState.value
            _editorState.value = _redoStack.value.last()
            _redoStack.value = _redoStack.value.dropLast(1)
        }
    }
}
