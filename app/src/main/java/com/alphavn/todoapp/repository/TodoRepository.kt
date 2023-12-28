package com.alphavn.todoapp.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.alphavn.todoapp.model.NoteModel
import com.alphavn.todoapp.model.NoteStatus
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID
import javax.inject.Inject

class TodoRepository @Inject constructor(val context: Context, private val todoDao: TodoDao) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

    init {
        getLastIndex()
    }

    suspend fun getListTodo(): List<NoteModel> {
        return try {
            todoDao.getAllNotes(status = NoteStatus.IN_PROGRESS.value)
        } catch (error: Exception) {
            Log.e(TAG, "getListTodo error: $error")
            emptyList()
        }
    }

    suspend fun getTodoById(id: String): NoteModel? {
        try {
            return todoDao.getNoteById(id)
        } catch (error: Exception) {
            Log.e(TAG, "editTodo error: $error")
        }
        return null
    }

    suspend fun createTodo(noteModel: NoteModel) {
        try {
            val uuid = UUID.randomUUID().toString()
            val currentTime = getCurrentTime()
            noteModel.id = uuid
            noteModel.createAt = currentTime
            noteModel.status = NoteStatus.IN_PROGRESS.value
            lastIndexColor++
            if (lastIndexColor > 5) {
                lastIndexColor = 0
            }
            noteModel.colorIndex = lastIndexColor
            saveLastIndex()
            todoDao.insert(noteModel)
        } catch (error: Exception) {
            Log.e(TAG, "createTodo error: $error")
        }
    }

    suspend fun editTodo(noteModel: NoteModel) {
        try {
            val currentTime = getCurrentTime()
            noteModel.updateAt = currentTime
            todoDao.update(noteModel)
        } catch (error: Exception) {
            Log.e(TAG, "editTodo error: $error")
        }
    }

    suspend fun deleteTodo(id: String) {
        try {
            val noteModel: NoteModel? = todoDao.getNoteById(id)
            if (noteModel != null) {
                val currentTime = getCurrentTime()
                noteModel.updateAt = currentTime
                noteModel.status = NoteStatus.FINISH.value
                todoDao.update(noteModel)
            }
        } catch (error: Exception) {
            Log.e(TAG, "deleteTodo error: $error")
        }
    }

    private fun saveLastIndex() {
        with(sharedPreferences.edit()) {
            putInt(KEY_LAST_INDEX, lastIndexColor)
            apply()
        }
    }

    private fun getLastIndex() {
        lastIndexColor = sharedPreferences.getInt(KEY_LAST_INDEX, 0)
    }

    companion object {
        val KEY_LAST_INDEX = "lastIndexColor"
        val TAG = "ALPHA_VN"
        var lastIndexColor = 0

        fun getCurrentTime(): String {
            val currentDateTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("HH:mm:ss yyyy-MM-dd")
            return currentDateTime.format(formatter)
        }
    }
}