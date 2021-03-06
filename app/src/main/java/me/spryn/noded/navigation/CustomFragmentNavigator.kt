package me.spryn.noded.navigation

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.withStyledAttributes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import me.spryn.noded.R

@Navigator.Name("custom-fragment")
class CustomFragmentNavigator(
    containerId: Int,
    context: Context,
    fragmentManager: FragmentManager
) : FragmentNavigator(context, fragmentManager, containerId) {
    override fun createDestination() = Destination(this)
    @NavDestination.ClassType(Fragment::class)
    class Destination(navigator: FragmentNavigator) : FragmentNavigator.Destination(navigator) {
        var showToolbar = true
        var showAddButton = false
        var showCheckButton = false
        var showDeleteButton = false
        var showNodeButton = false
        override fun onInflate(context: Context, attrs: AttributeSet) {
            super.onInflate(context, attrs)
            context.withStyledAttributes(attrs, R.styleable.CustomFragmentNavigator, 0, 0) {
                showToolbar = getBoolean(R.styleable.CustomFragmentNavigator_showToolbar, true)
                showAddButton = getBoolean(R.styleable.CustomFragmentNavigator_showAdd, false)
                showCheckButton = getBoolean(R.styleable.CustomFragmentNavigator_showCheck, false)
                showDeleteButton = getBoolean(R.styleable.CustomFragmentNavigator_showDelete, false)
                showNodeButton = getBoolean(R.styleable.CustomFragmentNavigator_showNode, false)

            }
        }
    }
}