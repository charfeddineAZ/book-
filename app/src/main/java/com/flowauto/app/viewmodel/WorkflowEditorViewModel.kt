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
        _editorState.value = _editorState.value.addNode(node)
    }

    fun removeNode(nodeId: String) {
        _editorState.value = _editorState.value.removeNode(nodeId)
    }

    fun updateNode(node: WorkflowNode) {
        _editorState.value = _editorState.value.updateNode(node)
    }

    fun addConnection(connection: Connection) {
        _editorState.value = _editorState.value.addConnection(connection)
    }

    fun removeConnection(connectionId: String) {
        _editorState.value = _editorState.value.removeConnection(connectionId)
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
