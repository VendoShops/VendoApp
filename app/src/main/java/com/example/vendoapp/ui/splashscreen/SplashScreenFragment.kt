package com.example.vendoapp.ui.splashscreen

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.vendoapp.R
import com.example.vendoapp.base.BaseFragment
import com.example.vendoapp.databinding.FragmentSplashScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenFragment : BaseFragment<FragmentSplashScreenBinding>(
    FragmentSplashScreenBinding::inflate
) {
    override fun onViewCreateFinish() {
        lifecycleScope.launch {
            delay(2000)
            findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
        }
    }
}