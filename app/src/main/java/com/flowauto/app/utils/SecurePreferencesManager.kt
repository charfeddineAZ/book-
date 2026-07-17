package com.flowauto.app.utils

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class SecurePreferencesManager(context: Context) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val preferences = EncryptedSharedPreferences.create(
        context,
        "flowauto_secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveSecret(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    fun getSecret(key: String): String? {
        return preferences.getString(key, null)
    }

    fun deleteSecret(key: String) {
        preferences.edit().remove(key).apply()
    }

    fun saveApiKey(apiKey: String) {
        saveSecret("api_key", apiKey)
    }

    fun getApiKey(): String? {
        return getSecret("api_key")
    }
}
