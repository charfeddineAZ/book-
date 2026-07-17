package com.flowauto.app.data.local.dao

import androidx.room.*
import com.flowauto.app.models.workflow.WorkflowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkflowDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkflow(workflow: WorkflowEntity)

    @Query("SELECT * FROM workflows WHERE id = :id")
    suspend fun getWorkflowById(id: String): WorkflowEntity?

    @Query("SELECT * FROM workflows")
    fun getAllWorkflows(): Flow<List<WorkflowEntity>>

    @Update
    suspend fun updateWorkflow(workflow: WorkflowEntity)

    @Delete
    suspend fun deleteWorkflow(workflow: WorkflowEntity)

    @Query("DELETE FROM workflows WHERE id = :id")
    suspend fun deleteWorkflowById(id: String)
}

@Dao
interface LogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: LogEntity)

    @Query("SELECT * FROM logs WHERE scenarioId = :scenarioId ORDER BY timestamp DESC")
    fun getLogsByScenario(scenarioId: String): Flow<List<LogEntity>>

    @Query("SELECT * FROM logs ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getRecentLogs(limit: Int = 100): List<LogEntity>

    @Delete
    suspend fun deleteLog(log: LogEntity)

    @Query("DELETE FROM logs")
    suspend fun clearAllLogs()
}

@Dao
interface LibraryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLibrary(library: LibraryEntity)

    @Query("SELECT * FROM libraries WHERE id = :id")
    suspend fun getLibraryById(id: String): LibraryEntity?

    @Query("SELECT * FROM libraries WHERE type = :type")
    fun getLibrariesByType(type: String): Flow<List<LibraryEntity>>

    @Query("SELECT * FROM libraries")
    fun getAllLibraries(): Flow<List<LibraryEntity>>

    @Update
    suspend fun updateLibrary(library: LibraryEntity)

    @Delete
    suspend fun deleteLibrary(library: LibraryEntity)
}
