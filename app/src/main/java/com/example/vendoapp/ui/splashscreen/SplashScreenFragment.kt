package com.example.vendoapp.ui.splashscreen

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.vendoapp.R
import com.example.vendoapp.ui.base.BaseFragment
import com.example.vendoapp.databinding.FragmentSplashScreenBinding
import com.example.vendoapp.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : BaseFragment<FragmentSplashScreenBinding>(
    FragmentSplashScreenBinding::inflate
) {

    @Inject lateinit var tokenManager: TokenManager

    override fun onViewCreateFinish() {
        val fadeInAnimator = ObjectAnimator.ofFloat(binding.ivSplash, View.ALPHA, 0f, 1f)
        fadeInAnimator.duration = 1500
        fadeInAnimator.start()

        fadeInAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                lifecycleScope.launch {
                    delay(500)
                    if (!isAdded) return@launch
                    val token = tokenManager.getAccessToken()
                    val remember = tokenManager.isRememberMeChecked()

                    if (!token.isNullOrEmpty() && remember) {
                        findNavController().navigate(
                            R.id.homeFragment,
                            null,
                            NavOptions.Builder().setPopUpTo(R.id.splashScreenFragment, true).build()
                        )
                    } else {
                        findNavController().navigate(
                            R.id.loginFragment,
                            null,
                            NavOptions.Builder().setPopUpTo(R.id.splashScreenFragment, true).build()
                        )
                    }

                }
            }
        })
    }
}