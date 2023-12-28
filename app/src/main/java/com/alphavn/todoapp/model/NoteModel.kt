package com.alphavn.todoapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteModel(
    @PrimaryKey
    var id: String,
    var title: String,
    var content: String,
    var createAt: String,
    var updateAt: String,
    var status: Int,
    var colorIndex: Int,
)