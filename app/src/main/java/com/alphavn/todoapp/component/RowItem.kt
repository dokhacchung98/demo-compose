package com.alphavn.todoapp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alphavn.todoapp.model.ColorEnum
import com.alphavn.todoapp.model.NoteModel

@Composable
fun RowItem(
    modifier: Modifier = Modifier, item: NoteModel
) {
    val backgroundColor = ColorEnum.fromId(item.colorIndex).color

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .padding(horizontal = 45.dp, vertical = 27.dp)
    ) {
        Text(
            item.title,
            style = TextStyle(
                fontSize = 25.sp,
            )
        )
    }
}