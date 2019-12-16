package me.spryn.noded.ui

import android.graphics.Color
import java.lang.Integer.parseInt

fun colorBlendDark(toolbarColor: Int, opacity: Float = 0.4f): Int {
    val red = (1.0f - opacity) * Color.red(toolbarColor)
    val green = (1.0f - opacity) * Color.green(toolbarColor)
    val blue = (1.0f - opacity) * Color.blue(toolbarColor)
    return Color.argb(
        255,
        red.toInt(),
        green.toInt(),
        blue.toInt()
    )
}

fun colorBlendDarker(toolbarColor: Int): Int =
    colorBlendDark(toolbarColor, 0.16f)

fun pickTextColorBasedOnBgColorSimple(bgColor: String): String {
    val color = if (bgColor[0] == '#') bgColor.substring(1, 7) else bgColor
    val r = parseInt(color.substring(0, 2), 16) // hexToR
    val g = parseInt(color.substring(2, 4), 16) // hexToG
    val b = parseInt(color.substring(4, 6), 16) // hexToB
    return if (((r * 0.299) + (g * 0.587) + (b * 0.114)) > 186) "#000000" else "#ffffff"
}