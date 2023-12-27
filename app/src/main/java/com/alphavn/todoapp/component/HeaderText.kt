package com.alphavn.todoapp.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.alphavn.todoapp.ui.theme.White

@Composable
fun HeaderText(text: String, modifier: Modifier) {
    val customTextStyle = TextStyle(
        color = White,
        fontSize = 43.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.1.em
    )
    Text(
        text = text,
        style = customTextStyle,
        modifier = modifier,
    )
}