package com.alphavn.todoapp.screen.detail_screen

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphavn.todoapp.model.ModeEnum
import com.alphavn.todoapp.model.NoteModel
import com.alphavn.todoapp.repository.TodoRepository
import com.alphavn.todoapp.repository.TodoRepository.Companion.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val todoRepository: TodoRepository) :
    ViewModel() {
    var modeDetail by mutableStateOf(ModeEnum.Preview)
    private var noteModel by mutableStateOf(
        NoteModel(
            id = "",
            title = "",
            content = "",
            createAt = "",
            status = 0,
            updateAt = "",
            colorIndex = 0
        )
    )
    var titleValue by mutableStateOf("")
    var contentValue by mutableStateOf("")
    var isShowDiscardChange by mutableStateOf(false)
    private var isChangeValue = false

    fun getNoteModel(id: String) {
        Log.e(TAG, "getNoteModel $id")
        viewModelScope.launch {
            if (id.isNotEmpty() && id != "a") {
                val noteModelExist = todoRepository.getTodoById(id)
                if (noteModelExist != null) {
                    titleValue = noteModelExist.title
                    contentValue = noteModelExist.content
                    noteModel.title = noteModelExist.title
                    noteModel.content = noteModelExist.content
                    noteModel.content = noteModelExist.content
                    noteModel.createAt = noteModelExist.createAt
                    noteModel.updateAt = noteModelExist.updateAt
                    noteModel.id = noteModelExist.id
                    noteModel.status = noteModelExist.status
                    noteModel.colorIndex = noteModelExist.colorIndex
                    modeDetail = ModeEnum.Preview
                    return@launch
                }
            }
            modeDetail = ModeEnum.Editing
        }
    }

    fun saveNote() {
        if (titleValue.trim().isEmpty() || contentValue.trim().isEmpty()) {
            Toast.makeText(
                todoRepository.context, "Please enter title and content.", Toast.LENGTH_SHORT
            ).show()
            return
        }

        noteModel.content = contentValue.trim()
        noteModel.title = titleValue.trim()

        viewModelScope.launch {
            if (noteModel.id.isNotEmpty()) {
                todoRepository.editTodo(noteModel)
            } else {
                todoRepository.createTodo(noteModel)
            }
        }
    }

    fun toEdit() {
        Log.e(TAG, "toEdit")
        modeDetail = ModeEnum.Editing
    }

    fun onChangeTitle(str: String) {
        titleValue = str
        isChangeValue = true
    }

    fun onChangeContent(str: String) {
        contentValue = str
        isChangeValue = true
    }

    fun onTapBack(onTapBack: () -> Unit) {
        if (isChangeValue) {
            isShowDiscardChange = true
        } else {
            onTapBack()
        }
    }

    fun onCloseDiscard() {
        isShowDiscardChange = false
    }
}