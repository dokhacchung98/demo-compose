package com.alphavn.todoapp.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alphavn.todoapp.screen.home_screen.HomeViewModel
import com.alphavn.todoapp.ui.theme.BlackDark
import com.alphavn.todoapp.ui.theme.White

@Composable
fun DialogDetail() {
    val homeViewModel: HomeViewModel = viewModel()
    if (!homeViewModel.isShowInfo) {
        return
    }

    Dialog(onDismissRequest = { homeViewModel.closeDialogDetail() }) {
        Surface(
            color = BlackDark,
            modifier = Modifier
                .width(330.dp)
                .height(236.dp)
                .clip(
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            Text(
                "Designed by - \n" + "Redesigned by - \n" + "Illustrations - \n" + "Icons - \n" + "Font -\n",
                style = TextStyle(
                    fontSize = 15.sp,
                    color = White
                ),
                modifier = Modifier
                    .padding(horizontal = 38.dp, vertical = 28.dp)

            )
        }
    }
}