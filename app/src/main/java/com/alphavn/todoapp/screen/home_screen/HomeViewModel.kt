package com.alphavn.todoapp.screen.home_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.alphavn.todoapp.model.NavigationRoutes
import com.alphavn.todoapp.model.NoteModel

class HomeViewModel(navController: NavController) : ViewModel() {
    lateinit var navController: NavController;
    var isShowInfo by mutableStateOf(false)
    var isShowHeaderSearch by mutableStateOf(false)
    var valueInputSearch by mutableStateOf("")
    private var todoList by mutableStateOf<List<NoteModel>>(emptyList())
    var todoShowList by mutableStateOf<List<NoteModel>>(emptyList())

    init {
        this.navController = navController
    }

    fun getTodoList() {
        // todo: get data
        // todoList = todoRepository
    }

    fun goToNoteDetail(item: NoteModel) {
        navController.navigate(NavigationRoutes.DetailScreen.name + "/${item.id}")
    }

    fun filterTodo() {
        val keyword = valueInputSearch.trim().lowercase()
        if (keyword.isEmpty() && isShowHeaderSearch) {
            todoShowList = emptyList()
            return
        }
        todoShowList = todoList.filter {
            it.content.lowercase().contains(keyword)
        }
    }

    fun onSearchChange(value: String) {
        valueInputSearch = value
    }

    fun showInputSearch() {
        isShowHeaderSearch = true
    }

    fun closeInputSearch() {
        isShowHeaderSearch = false
    }

    fun openDialogDetail() {
        isShowInfo = true
    }

    fun closeDialogDetail() {
        isShowInfo = false
    }
}