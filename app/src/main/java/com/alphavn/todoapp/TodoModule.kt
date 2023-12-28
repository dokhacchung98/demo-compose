package com.alphavn.todoapp

import android.content.Context
import androidx.room.Room
import com.alphavn.todoapp.repository.TodoDao
import com.alphavn.todoapp.repository.TodoDatabase
import com.alphavn.todoapp.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoModule {
    @Singleton
    @Provides
    fun getRepository(@ApplicationContext context: Context, todoDao: TodoDao): TodoRepository {
        return TodoRepository(context = context, todoDao)
    }

    @Singleton
    @Provides
    fun getDao(database: TodoDatabase): TodoDao {
        return database.todoDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): TodoDatabase {
        return Room.databaseBuilder(
            context.applicationContext, TodoDatabase::class.java, "todo_database"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }
}