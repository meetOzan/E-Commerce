package com.meetozan.e_commerce.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.meetozan.e_commerce.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomBar()
    }

    private fun bottomBar() {
        val bottomBar = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomBar.menu.getItem(2).isEnabled = false

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        NavigationUI.setupWithNavController(
            bottomBar,
            navHostFragment.navController
        )

        val fab = findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener {
            navHostFragment.navController.navigate(R.id.shoppingCartFragment2)
        }

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            if ((destination.id == R.id.signInFragment) || (destination.id == R.id.signUpFragment)) {
                bottomBar.visibility = View.GONE
                fab.visibility = View.GONE
            } else {
                bottomBar.visibility = View.VISIBLE
                fab.visibility = View.VISIBLE
            }
        }
    }
}