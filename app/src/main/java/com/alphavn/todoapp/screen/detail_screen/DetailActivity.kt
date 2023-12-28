package com.alphavn.todoapp.screen.detail_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alphavn.todoapp.R
import com.alphavn.todoapp.component.DialogDiscard
import com.alphavn.todoapp.component.IconButton
import com.alphavn.todoapp.model.ModeEnum
import com.alphavn.todoapp.ui.theme.Black
import com.alphavn.todoapp.ui.theme.Grey

@Composable
fun DetailActivity(idNote: String, onTapBack: () -> Unit) {
    val detailViewModel: DetailViewModel = hiltViewModel<DetailViewModel>()

    LaunchedEffect(Unit) {
        detailViewModel.getNoteModel(idNote)
    }

    Surface(
        modifier = Modifier.fillMaxSize(), color = Color.Black
    ) {
        Scaffold(
            topBar = {
                HeaderDetail(onTapBack)
            },
            containerColor = Black,
        ) { innerPadding ->
            DialogDiscard(onTapDiscard = onTapBack)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    enabled = detailViewModel.modeDetail == ModeEnum.Editing,
                    value = detailViewModel.titleValue,
                    onValueChange = { detailViewModel.onChangeTitle(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 27.dp),
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 35.sp,
                        fontWeight = FontWeight(600)
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(
                            "Title",
                            style = TextStyle(
                                color = Grey,
                                fontSize = 35.sp,
                                fontWeight = FontWeight(600)
                            ),
                        )
                    },
                )
                TextField(
                    enabled = detailViewModel.modeDetail == ModeEnum.Editing,
                    value = detailViewModel.contentValue,
                    onValueChange = { detailViewModel.onChangeContent(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 27.dp),
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 23.sp,
                        fontWeight = FontWeight(400)
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(
                            "Type something...",
                            style = TextStyle(
                                color = Grey,
                                fontSize = 23.sp,
                                fontWeight = FontWeight(400)
                            ),
                        )
                    },
                )
            }
        }
    }
}

@Composable
fun HeaderDetail(onTapBack: () -> Unit) {
    val detailViewModel: DetailViewModel = hiltViewModel()

    Row(
        modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 38.dp, bottom = 20.dp)
    ) {
        IconButton(
            onClick = {
                detailViewModel.onTapBack(onTapBack = onTapBack)
            },
            icon = painterResource(id = R.drawable.back),
        )
        Spacer(modifier = Modifier.weight(1f))
        if (detailViewModel.modeDetail == ModeEnum.Editing) {
            IconButton(
                onClick = {
                    detailViewModel.saveNote()
                    onTapBack()
                },
                icon = painterResource(id = R.drawable.save),
            )
        } else {
            IconButton(
                onClick = {
                    detailViewModel.toEdit()
                },
                icon = painterResource(id = R.drawable.mode),
            )
        }
    }
}
