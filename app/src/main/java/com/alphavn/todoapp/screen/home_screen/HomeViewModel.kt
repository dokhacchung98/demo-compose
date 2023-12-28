package com.alphavn.todoapp.screen.home_screen

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphavn.todoapp.model.NoteModel
import com.alphavn.todoapp.repository.TodoRepository
import com.alphavn.todoapp.repository.TodoRepository.Companion.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val todoRepository: TodoRepository) : ViewModel() {
    var isShowInfo by mutableStateOf(false)
    var isShowHeaderSearch by mutableStateOf(false)
    var valueInputSearch by mutableStateOf("")
    private var todoList by mutableStateOf<List<NoteModel>>(emptyList())
    var todoShowList by mutableStateOf<List<NoteModel>>(emptyList())

    init {
        getTodoList()
    }

    private fun getTodoList() {
        viewModelScope.launch {
            val noteList = todoRepository.getListTodo()
            todoList += noteList
            filterTodo()
        }
    }

    fun deleteNote(noteModel: NoteModel) {
        Log.e(TAG, "deleteNote ${noteModel.id}")
        viewModelScope.launch {
            todoRepository.deleteTodo(noteModel.id)
            todoList = todoList.filter { it.id != noteModel.id }
            filterTodo()
        }
    }

    private fun filterTodo() {
        val keyword = valueInputSearch.trim().lowercase()
        Log.e(TAG, "filterTodo: $keyword")
        if (keyword.isEmpty() && isShowHeaderSearch) {
            todoShowList = emptyList()
            return
        }
        todoShowList = todoList.filter {
            it.title.lowercase().contains(keyword)
        }
    }

    private val handler = Handler(Looper.getMainLooper())

    fun onSearchChange(value: String) {
        valueInputSearch = value
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed({
            filterTodo()
        }, 500)
    }

    fun showInputSearch() {
        isShowHeaderSearch = true
    }

    fun closeInputSearch() {
        isShowHeaderSearch = false
        valueInputSearch = ""
        filterTodo()
    }

    fun openDialogDetail() {
        isShowInfo = true
    }

    fun closeDialogDetail() {
        isShowInfo = false
    }

    private var revealedItems: List<NoteModel> = emptyList()

    fun isItemRevealed(item: NoteModel): Boolean {
        return revealedItems.contains(item)
    }

    fun itemExpanded(item: NoteModel) {
        revealedItems = revealedItems.toMutableList().also { mutableList ->
            mutableList.add(item)
        }
    }

    fun itemCollapsed(item: NoteModel) {
        revealedItems = revealedItems.toMutableList().also { mutableList ->
            mutableList.remove(item)
        }
    }
}