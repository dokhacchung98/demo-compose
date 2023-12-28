package com.alphavn.todoapp.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alphavn.todoapp.model.NoteModel

@Database(entities = [NoteModel::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}