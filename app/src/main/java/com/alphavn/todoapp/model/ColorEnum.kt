package com.alphavn.todoapp.model

import androidx.compose.ui.graphics.Color
import com.alphavn.todoapp.ui.theme.Blue
import com.alphavn.todoapp.ui.theme.GreenLight
import com.alphavn.todoapp.ui.theme.Orange
import com.alphavn.todoapp.ui.theme.Pink
import com.alphavn.todoapp.ui.theme.Purple
import com.alphavn.todoapp.ui.theme.Yellow

enum class ColorEnum(val color: Color) {
    ValuePink(Pink),
    ValuePurple(Purple),
    ValueOrange(Orange),
    ValueGreenLight(GreenLight),
    ValueYellow(Yellow),
    ValueBlue(Blue);

    companion object {
        fun fromId(id: Int): ColorEnum {
            return when (id) {
                0 -> ValuePink
                1 -> ValuePurple
                2 -> ValueOrange
                3 -> ValueGreenLight
                4 -> ValueYellow
                5 -> ValueBlue
                else -> ValuePink
            }
        }
    }

}