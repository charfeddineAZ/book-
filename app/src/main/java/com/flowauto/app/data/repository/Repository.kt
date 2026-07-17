package com.flowauto.app.data.repository

import com.flowauto.app.data.local.dao.WorkflowDao
import com.flowauto.app.data.local.dao.LogDao
import com.flowauto.app.data.local.dao.LibraryDao
import com.flowauto.app.data.remote.api.FlowautoApiService
import com.flowauto.app.models.workflow.WorkflowEntity
import com.flowauto.app.models.execution.LogEntity
import com.flowauto.app.models.libraries.LibraryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkflowRepository @Inject constructor(
    private val workflowDao: WorkflowDao,
    private val apiService: FlowautoApiService
) {
    suspend fun saveWorkflow(workflow: WorkflowEntity) {
        workflowDao.insertWorkflow(workflow)
    }

    suspend fun getWorkflow(id: String): WorkflowEntity? {
        return workflowDao.getWorkflowById(id)
    }

    fun getAllWorkflows(): Flow<List<WorkflowEntity>> {
        return workflowDao.getAllWorkflows()
    }

    suspend fun updateWorkflow(workflow: WorkflowEntity) {
        workflowDao.updateWorkflow(workflow)
    }

    suspend fun deleteWorkflow(id: String) {
        workflowDao.deleteWorkflowById(id)
    }

    suspend fun executeWorkflow(id: String): Result<String> {
        return try {
            val response = apiService.executeWorkflow(id)
            Result.success(response.data ?: "")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

@Singleton
class ExecutionRepository @Inject constructor(
    private val logDao: LogDao,
    private val apiService: FlowautoApiService
) {
    suspend fun saveLog(log: LogEntity) {
        logDao.insertLog(log)
    }

    fun getExecutionLogs(scenarioId: String): Flow<List<LogEntity>> {
        return logDao.getLogsByScenario(scenarioId)
    }

    suspend fun getRecentLogs(limit: Int = 100): List<LogEntity> {
        return logDao.getRecentLogs(limit)
    }

    suspend fun clearLogs() {
        logDao.clearAllLogs()
    }
}

@Singleton
class LibraryRepository @Inject constructor(
    private val libraryDao: LibraryDao
) {
    suspend fun saveLibrary(library: LibraryEntity) {
        libraryDao.insertLibrary(library)
    }

    suspend fun getLibrary(id: String): LibraryEntity? {
        return libraryDao.getLibraryById(id)
    }

    fun getLibrariesByType(type: String): Flow<List<LibraryEntity>> {
        return libraryDao.getLibrariesByType(type)
    }

    fun getAllLibraries(): Flow<List<LibraryEntity>> {
        return libraryDao.getAllLibraries()
    }

    suspend fun updateLibrary(library: LibraryEntity) {
        libraryDao.updateLibrary(library)
    }

    suspend fun deleteLibrary(id: String) {
        libraryDao.deleteLibrary(LibraryEntity(id, "", "", "", ""))
    }
}
