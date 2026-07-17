package com.flowauto.app.models.execution

import java.time.LocalDateTime

enum class LogLevel {
    ERROR, WARNING, INFO, SUCCESS
}

data class LogEntry(
    val timestamp: LocalDateTime,
    val message: String,
    val level: LogLevel,
    val nodeName: String,
    val duration: Long,
    val inputData: String = "",
    val outputData: String = ""
)

data class RunnerState(
    val isRunning: Boolean = false,
    val isPaused: Boolean = false,
    val currentNodeId: String? = null,
    val executedNodes: Int = 0,
    val totalTime: Long = 0,
    val status: ExecutionStatus = ExecutionStatus.IDLE,
    val queue: List<ScenarioExecution> = emptyList()
)

enum class ExecutionStatus {
    IDLE, RUNNING, PAUSED, COMPLETED, FAILED
}

data class ScenarioExecution(
    val id: String,
    val name: String,
    val status: ExecutionStatus,
    val progress: Float = 0f,
    val startTime: LocalDateTime? = null,
    val endTime: LocalDateTime? = null,
    val logs: List<LogEntry> = emptyList()
)
