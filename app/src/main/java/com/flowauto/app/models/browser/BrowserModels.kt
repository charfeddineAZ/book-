package com.flowauto.app.models.browser

import java.time.LocalDateTime

data class SelectorCapture(
    val id: String,
    val elementName: String,
    val cssSelector: String,
    val xpathSelector: String,
    val attributes: Map<String, String> = emptyMap(),
    val createdAt: LocalDateTime = LocalDateTime.now()
)

data class Connector(
    val id: String,
    val name: String,
    val steps: List<RecordedStep>,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val tags: List<String> = emptyList(),
    val description: String = ""
)

data class RecordedStep(
    val id: String,
    val type: StepType,
    val description: String,
    val selector: String? = null,
    val value: String? = null,
    val waitTime: Long = 0,
    val screenshot: String? = null
)

enum class StepType(val displayName: String) {
    CLICK("نقرة"),
    INPUT("إدخال نص"),
    SCROLL("تمرير"),
    WAIT("انتظار"),
    NAVIGATE("التنقل"),
    SELECT("اختيار"),
    SCREENSHOT("لقطة شاشة")
}
