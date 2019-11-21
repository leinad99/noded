package me.spryn.noded.navigation

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.plusAssign

class CustomNavHostFragment : NavHostFragment() {
    override fun onCreateNavController(navController: NavController) {
        super.onCreateNavController(navController)
        activity?.let {
            navController.navigatorProvider += CustomFragmentNavigator(id, it, childFragmentManager)
        }
    }
}
