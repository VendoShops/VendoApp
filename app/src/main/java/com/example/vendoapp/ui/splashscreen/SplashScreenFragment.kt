package com.example.vendoapp.ui.splashscreen

import android.annotation.SuppressLint
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.vendoapp.R
import com.example.vendoapp.ui.base.BaseFragment
import com.example.vendoapp.databinding.FragmentSplashScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
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