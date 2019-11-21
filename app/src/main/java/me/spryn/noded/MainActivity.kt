package me.spryn.noded

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration


class MainActivity : AppCompatActivity() {

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
}