package com.flowauto.app.models.settings

data class AppSettings(
    val isDarkMode: Boolean = false,
    val language: String = "ar",
    val appLockEnabled: Boolean = false,
    val workspaceEncrypted: Boolean = false,
    val nodeTimeout: Int = 30,
    val parallelLimit: Int = 5,
    val batteryMode: BatteryMode = BatteryMode.BALANCED,
    val completionNotification: Boolean = true,
    val errorNotification: Boolean = true,
    val persistentNotification: Boolean = false,
    val debugWebView: Boolean = false,
    val experimentalFeatures: Boolean = false
)

enum class BatteryMode {
    HIGH_PERFORMANCE, BALANCED, BATTERY_SAVER
}

data class Secret(
    val id: String,
    val key: String,
    val value: String,
    val isEncrypted: Boolean = true,
    val createdAt: String = ""
)

data class CloudSync(
    val service: String,
    val enabled: Boolean = false,
    val autoSync: Boolean = false,
    val syncInterval: Int = 3600,
    val lastSyncTime: String? = null
)
