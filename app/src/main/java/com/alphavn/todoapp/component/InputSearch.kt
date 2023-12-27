package com.alphavn.todoapp.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alphavn.todoapp.R
import com.alphavn.todoapp.ui.theme.GreyDark
import com.alphavn.todoapp.ui.theme.GreyLight

@Composable
fun InputSearch(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    onPressClose: () -> Unit,
    valueInput: String,
) {
    val styleText = TextStyle(
        color = GreyLight, fontSize = 20.sp, fontWeight = FontWeight.Light
    )

    TextField(
        value = valueInput,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        modifier = modifier.padding(horizontal = 34.dp, vertical = 7.dp).fillMaxWidth(),
        shape = CircleShape,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = GreyDark,
            unfocusedContainerColor = GreyDark,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        textStyle = styleText,
        placeholder = {
            Text(
                "Search by the keyword...",
                style = styleText,
            )
        },
        trailingIcon = {
            Image(painter = painterResource(id = R.drawable.close),
                contentDescription = "img_clear",
                modifier = Modifier.clickable {
                    onPressClose()
                })
        })
}