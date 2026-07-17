package com.flowauto.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flowauto.app.data.repository.WorkflowRepository
import com.flowauto.app.models.workflow.WorkflowEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkflowListViewModel @Inject constructor(
    private val repository: WorkflowRepository
) : ViewModel() {
    private val _workflows = MutableStateFlow<List<WorkflowEntity>>(emptyList())
    val workflows: StateFlow<List<WorkflowEntity>> = _workflows

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadWorkflows()
    }

    private fun loadWorkflows() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getAllWorkflows().collect { workflows ->
                _workflows.value = workflows
                _isLoading.value = false
            }
        }
    }

    fun deleteWorkflow(id: String) {
        viewModelScope.launch {
            try {
                repository.deleteWorkflow(id)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun executeWorkflow(id: String) {
        viewModelScope.launch {
            try {
                val result = repository.executeWorkflow(id)
                result.onSuccess {
                    _error.value = "تم التنفيذ بنجاح"
                }.onFailure {
                    _error.value = it.message
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
