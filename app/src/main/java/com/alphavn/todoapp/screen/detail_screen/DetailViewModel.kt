package com.alphavn.todoapp.screen.detail_screen

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class DetailViewModel(navController: NavController): ViewModel() {
    private var navController: NavController
    init {
        this.navController = navController
    }
}