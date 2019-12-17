package me.spryn.noded.ui

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
    deleteButtonClick: (() -> Unit)? = null,
    nodeButtonClick: (() -> Unit)? = null
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
    //set check button visibility
    setImageButtonVisibility(
        mainActivity.findViewById(R.id.check_button),
        checkButtonClick
    )
    //set delete button visibility
    setImageButtonVisibility(
        mainActivity.findViewById(R.id.delete_button),
        deleteButtonClick
    )
    //set node button visibility
    setImageButtonVisibility(
        mainActivity.findViewById(R.id.node_button),
        nodeButtonClick
    )
}

private fun setImageButtonVisibility(button: ImageButton, onClick: (() -> Unit)?) {
    button.visibility = View.GONE
    onClick?.let {
        button.visibility = View.VISIBLE
        button.setOnClickListener { onClick() }
    }
}