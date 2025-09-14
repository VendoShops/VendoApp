package com.example.vendoapp.ui.otp

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.findNavController
import com.example.vendoapp.R
import com.example.vendoapp.base.BaseFragment
import com.example.vendoapp.databinding.FragmentOTPBinding

class OTPFragment : BaseFragment<FragmentOTPBinding>(
    FragmentOTPBinding::inflate
) {
    override fun onViewCreateFinish() {
        setupUi()
        binding.btnSendOTP.setOnClickListener {
            findNavController().navigate(R.id.action_OTPFragment_to_newPasswordFragment)
        }
    }

    private fun setupUi() {
        binding.let {
            ViewCompat.setOnApplyWindowInsetsListener(it.main) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(0, systemBars.top, 0, systemBars.bottom)
                insets
            }
        }
    }
}