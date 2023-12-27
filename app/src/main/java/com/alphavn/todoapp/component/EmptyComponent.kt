package com.alphavn.todoapp.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alphavn.todoapp.R
import com.alphavn.todoapp.ui.theme.White

@Composable
fun EmptyComponent(modifier: Modifier = Modifier, isSearch: Boolean = false) {
    val msg = if (isSearch) "File not found. Try searching again." else "Create your first note !"
    val painter = if (isSearch) R.drawable.cuate else R.drawable.rafiki
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(top = 100.dp).fillMaxWidth()
    ) {
        Image(painter = painterResource(id = painter), contentDescription = "img_empty")
        Text(
            text = msg,
            style = TextStyle(
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Light
            )
        )
    }
}