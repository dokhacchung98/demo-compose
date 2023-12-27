package com.alphavn.todoapp.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alphavn.todoapp.R
import com.alphavn.todoapp.ui.theme.GreyDark
import com.alphavn.todoapp.ui.theme.White

@Composable
fun IconButton(onClick: () -> Unit, icon: Painter) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = GreyDark
        ),
        shape = RoundedCornerShape(15),
        contentPadding = PaddingValues(13.dp),
    ) {
        Image(
            painter = icon,
            contentDescription = "icon_button",
            modifier = Modifier.fillMaxSize(),
            colorFilter = ColorFilter.tint(color = White)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewButton() {
    IconButton(onClick = {}, icon = painterResource(id = R.drawable.close))
}