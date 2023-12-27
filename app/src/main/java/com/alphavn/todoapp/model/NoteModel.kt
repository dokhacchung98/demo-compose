package com.alphavn.todoapp.model

import java.util.Calendar

data class NoteModel(
    val id: String,
    val title: String,
    val content: String,
    val createAt: Calendar,
    val updateAt: Calendar,
    val status: NoteStatus,
    val colorIndex: Int,
)