package me.spryn.noded.ui

import android.graphics.Color
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import me.spryn.noded.MainActivity
import me.spryn.noded.R

fun updateToolbar(
    mainActivity: MainActivity,
    toolbarColor: Int = ContextCompat.getColor(mainActivity, R.color.colorPrimary),
    toolbarElevation: Float = 8F,
    statusBarColor: Int = ContextCompat.getColor(mainActivity, R.color.colorPrimaryDark),
    addButtonClick: (() -> Unit)? = null,
    checkButtonClick: (() -> Unit)? = null,
    deleteButtonClick: (() -> Unit)? = null
) {
    //toolbar color
    val toolbar: Toolbar? = mainActivity.findViewById(R.id.toolbar)
    toolbar?.setBackgroundColor(toolbarColor)
    //toolbar elevation
    toolbar?.elevation = toolbarElevation
    //status bar color
    mainActivity.window.statusBarColor = statusBarColor
    //set add button visibility
    setImageButtonVisibility(
        mainActivity.findViewById(R.id.add_button),
        addButtonClick
    )
    //set check button
    setImageButtonVisibility(
        mainActivity.findViewById(R.id.check_button),
        checkButtonClick
    )
    //set delete button visibility
    setImageButtonVisibility(
        mainActivity.findViewById(R.id.delete_button),
        deleteButtonClick
    )
}

private fun setImageButtonVisibility(button: ImageButton, onClick: (() -> Unit)?) {
    button.visibility = View.GONE
    onClick?.let {
        button.visibility = View.VISIBLE
        button.setOnClickListener { onClick() }
    }
}

fun statusBarColorBlend(toolbarColor: Int): Int {
    val opacity = 0.4f
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

fun statusBarColorBlendTwice(toolbarColor: Int): Int = statusBarColorBlend(statusBarColorBlend(toolbarColor))