package com.flowauto.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flowauto.app.models.execution.LogEntry
import com.flowauto.app.models.execution.RunnerState
import com.flowauto.app.models.execution.ExecutionStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RunnerViewModel : ViewModel() {
    private val _runnerState = MutableStateFlow(RunnerState())
    val runnerState: StateFlow<RunnerState> = _runnerState

    private val _logs = MutableStateFlow(emptyList<LogEntry>())
    val logs: StateFlow<List<LogEntry>> = _logs

    fun startExecution(scenarioId: String) {
        viewModelScope.launch {
            _runnerState.value = _runnerState.value.copy(
                isRunning = true,
                status = ExecutionStatus.RUNNING
            )
            // تنفيذ السيناريو
        }
    }

    fun pauseExecution() {
        _runnerState.value = _runnerState.value.copy(
            isPaused = true,
            status = ExecutionStatus.PAUSED
        )
    }

    fun resumeExecution() {
        _runnerState.value = _runnerState.value.copy(
            isPaused = false,
            status = ExecutionStatus.RUNNING
        )
    }

    fun stopExecution() {
        _runnerState.value = _runnerState.value.copy(
            isRunning = false,
            status = ExecutionStatus.COMPLETED
        )
    }

    fun addLog(log: LogEntry) {
        _logs.value = _logs.value + log
    }

    fun clearLogs() {
        _logs.value = emptyList()
    }
}
