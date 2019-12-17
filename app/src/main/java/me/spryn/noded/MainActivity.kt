package me.spryn.noded

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
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

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        val customFragment = destination as? CustomFragmentNavigator.Destination ?: return
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        //toolbar title
        findViewById<TextView>(R.id.toolbar_title).text = customFragment.label
        toolbar.visibility = if (customFragment.showToolbar) View.VISIBLE else View.GONE
        findViewById<ImageButton>(R.id.add_button).visibility =
            if (customFragment.showAddButton) View.VISIBLE else View.GONE
        findViewById<ImageButton>(R.id.check_button).visibility =
            if (customFragment.showCheckButton) View.VISIBLE else View.GONE
        findViewById<ImageButton>(R.id.node_button).visibility =
            if (customFragment.showNodeButton) View.VISIBLE else View.GONE

    }
}