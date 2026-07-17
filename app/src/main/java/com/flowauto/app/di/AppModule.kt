package com.flowauto.app.di

import android.content.Context
import com.flowauto.app.data.local.db.FlowautoDatabase
import com.flowauto.app.data.local.dao.WorkflowDao
import com.flowauto.app.data.local.dao.LogDao
import com.flowauto.app.data.local.dao.LibraryDao
import com.flowauto.app.data.remote.api.FlowautoApiService
import com.flowauto.app.data.remote.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): FlowautoDatabase {
        return FlowautoDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideWorkflowDao(database: FlowautoDatabase): WorkflowDao {
        return database.workflowDao()
    }

    @Singleton
    @Provides
    fun provideLogDao(database: FlowautoDatabase): LogDao {
        return database.logDao()
    }

    @Singleton
    @Provides
    fun provideLibraryDao(database: FlowautoDatabase): LibraryDao {
        return database.libraryDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor("your-api-key"))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.flowauto.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): FlowautoApiService {
        return retrofit.create(FlowautoApiService::class.java)
    }
}
