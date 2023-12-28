package com.alphavn.todoapp.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.alphavn.todoapp.R
import com.alphavn.todoapp.screen.detail_screen.DetailViewModel
import com.alphavn.todoapp.ui.theme.BlackDark
import com.alphavn.todoapp.ui.theme.Green
import com.alphavn.todoapp.ui.theme.Red
import com.alphavn.todoapp.ui.theme.White

@Composable
fun DialogDiscard(onTapDiscard: () -> Unit) {
    val detailViewModel: DetailViewModel = hiltViewModel()
    if (!detailViewModel.isShowDiscardChange) {
        return
    }

    Dialog(onDismissRequest = { detailViewModel.onCloseDiscard() }) {
        Surface(
            color = BlackDark,
            modifier = Modifier
                .width(330.dp)
                .height(236.dp)
                .clip(
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.info), contentDescription = "",
                    modifier = Modifier.size(40.dp)
                )
                Text(
                    "Are your sure you want\ndiscard your changes?",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = White,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .padding(horizontal = 38.dp, vertical = 12.dp),
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            detailViewModel.onCloseDiscard()
                            onTapDiscard()
                        },
                        modifier = Modifier
                            .width(112.dp)
                            .height(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Red,
                            contentColor = White
                        ),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text("Discard")
                    }
                    Button(
                        onClick = { detailViewModel.onCloseDiscard() },
                        modifier = Modifier
                            .width(112.dp)
                            .height(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Green,
                            contentColor = White
                        ),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text("Keep")
                    }
                }
            }
        }
    }
}

