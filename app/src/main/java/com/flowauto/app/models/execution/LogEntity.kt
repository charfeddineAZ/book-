package com.flowauto.app.models.execution

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "logs")
data class LogEntity(
    @PrimaryKey val id: String,
    val scenarioId: String,
    val nodeName: String,
    val message: String,
    val level: String, // ERROR, WARNING, INFO, SUCCESS
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val duration: Long = 0,
    val inputData: String = "",
    val outputData: String = ""
)
