package me.spryn.noded

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import me.spryn.noded.navigation.CustomFragmentNavigator


class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpToolbar()
    }

    private fun setUpToolbar() {
        val controller = findNavController(R.id.navHostFragment)
        val appBarConfiguration = AppBarConfiguration(controller.graph)
        val toolbar: Toolbar = findViewById(R.id.toolbar)

        toolbar.setupWithNavController(controller, appBarConfiguration)
    }

    override fun onResume() {
        super.onResume()
        findNavController(R.id.navHostFragment).addOnDestinationChangedListener(this)
    }

    override fun onPause() {
        super.onPause()
        findNavController(R.id.navHostFragment).removeOnDestinationChangedListener(this)
    }

    // region NavController.OnDestinationChangedListener
    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        val customFragment = destination as? CustomFragmentNavigator.Destination ?: return
            findViewById<Toolbar>(R.id.toolbar).visibility =
                if (customFragment.showToolbar) View.VISIBLE else View.GONE
    }
    // endregion

}