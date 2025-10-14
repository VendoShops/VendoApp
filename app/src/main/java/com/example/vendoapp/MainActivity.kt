package com.example.vendoapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.vendoapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        bottomNav()
    }

    private fun setupUI() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, 0, 0, 0)
            insets
        }
    }

    private fun bottomNav() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        // Remove background tint and ripple effect
        binding.bottomNav.itemRippleColor = null
        binding.bottomNav.itemActiveIndicatorColor = null

        binding.bottomNav.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.root.post {
                binding.bottomCardView.isVisible = when (destination.id) {
                    R.id.homeFragment,
                    R.id.categoryFragment,
                    R.id.likeFragment,
                    R.id.cartFragment,
                    R.id.profileFragment,
                    R.id.termsAndConditionsFragment -> true
                    else -> false
                }
                if (destination.id == R.id.termsAndConditionsFragment) {
                    binding.bottomNav.menu.findItem(R.id.profileFragment).isChecked = true
                }
            }
        }
    }
}