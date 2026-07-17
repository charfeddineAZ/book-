package com.flowauto.app.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.flowauto.app.data.local.converters.DateTimeConverter
import com.flowauto.app.data.local.dao.*
import com.flowauto.app.models.workflow.WorkflowEntity
import com.flowauto.app.models.execution.LogEntity
import com.flowauto.app.models.libraries.LibraryEntity

@Database(
    entities = [
        WorkflowEntity::class,
        LogEntity::class,
        LibraryEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateTimeConverter::class)
abstract class FlowautoDatabase : RoomDatabase() {
    abstract fun workflowDao(): WorkflowDao
    abstract fun logDao(): LogDao
    abstract fun libraryDao(): LibraryDao

    companion object {
        @Volatile
        private var INSTANCE: FlowautoDatabase? = null

        fun getDatabase(context: Context): FlowautoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FlowautoDatabase::class.java,
                    "flowauto_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
