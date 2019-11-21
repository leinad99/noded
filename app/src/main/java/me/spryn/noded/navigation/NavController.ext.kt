package me.spryn.noded.navigation

import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import me.spryn.noded.R

fun NavController.clearBackStackAndNavigateTo(@IdRes destination: Int) {
    val navOptions = NavOptions.Builder()
        .setPopUpTo(R.id.navigation, true)
        .build()

    navigate(destination, null, navOptions)
}