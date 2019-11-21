package me.spryn.noded.navigation

//import android.app.Activity
//import android.content.Context
//import android.os.Bundle
//import android.util.AttributeSet
//import androidx.annotation.ColorRes
//import androidx.core.content.withStyledAttributes
//import androidx.navigation.NavDestination
//import androidx.navigation.NavOptions
//import androidx.navigation.Navigator
//import me.spryn.noded.R
//
//@Navigator.Name("chrome")
//class ChromeCustomTabsNavigator(
//    private val context: Context
//) : Navigator<ChromeCustomTabsNavigator.Destination>() {
//
//    override fun createDestination() = Destination(this)
//
//    override fun navigate(destination: Destination, args: Bundle?, navOptions: NavOptions?, navigatorExtras: Extras?): NavDestination? {
//
//        return null // Do not add to the back stack, managed by Chrome Custom Tabs
//    }
//
//    override fun popBackStack() = true // Managed by Chrome Custom Tabs
//
//    @NavDestination.ClassType(Activity::class)
//    class Destination(navigator: Navigator<out NavDestination>) : NavDestination(navigator) {
//
//        @ColorRes
//        var toolbarColor: Int = 0
//
//        @ColorRes
//        var secondaryToolbarColor: Int = 0
//
//        override fun onInflate(context: Context, attrs: AttributeSet) {
//            super.onInflate(context, attrs)
//
//            context.withStyledAttributes(attrs, R.styleable.ChromeCustomTabsNavigator, 0, 0) {
//                toolbarColor = getResourceId(R.styleable.ChromeCustomTabsNavigator_toolbarColor, 0)
//                secondaryToolbarColor = getResourceId(R.styleable.ChromeCustomTabsNavigator_secondaryToolbarColor, 0)
//            }
//        }
//    }
//}