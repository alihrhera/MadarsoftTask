package com.madarsoft.task.utils.errors

import android.content.res.ColorStateList
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

    fun generateReadableLightColor(): Color {
        fun component(): Float = Random.nextFloat() * 0.4f + 0.2f
        return Color(
            red = component(),
            green = component(),
            blue = component(),
            alpha = 1f
        )
    }





fun Color.toColorStateList(): ColorStateList {
    val colorInt = android.graphics.Color.argb(
        (alpha * 255).toInt(),
        (red * 255).toInt(),
        (green * 255).toInt(),
        (blue * 255).toInt()
    )
    return ColorStateList.valueOf(colorInt)
}