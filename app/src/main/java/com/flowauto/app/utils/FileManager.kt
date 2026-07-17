package com.flowauto.app.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

class FileManager(private val context: Context) {
    private val workspaceDir = File(context.filesDir, "workspace")

    init {
        if (!workspaceDir.exists()) {
            workspaceDir.mkdirs()
        }
    }

    fun saveFile(fileName: String, content: String): Boolean {
        return try {
            val file = File(workspaceDir, fileName)
            FileOutputStream(file).use { it.write(content.toByteArray()) }
            true
        } catch (e: Exception) {
            false
        }
    }

    fun readFile(fileName: String): String? {
        return try {
            val file = File(workspaceDir, fileName)
            file.readText()
        } catch (e: Exception) {
            null
        }
    }

    fun deleteFile(fileName: String): Boolean {
        return try {
            val file = File(workspaceDir, fileName)
            file.delete()
        } catch (e: Exception) {
            false
        }
    }

    fun listFiles(): List<String> {
        return workspaceDir.listFiles()?.map { it.name } ?: emptyList()
    }

    fun exportWorkspace(uri: Uri): Boolean {
        return try {
            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                workspaceDir.listFiles()?.forEach { file ->
                    file.inputStream().use { input ->
                        outputStream.write(input.readBytes())
                    }
                }
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}
