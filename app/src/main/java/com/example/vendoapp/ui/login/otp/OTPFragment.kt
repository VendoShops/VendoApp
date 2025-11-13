package com.example.vendoapp.ui.login.otp

import android.view.View
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.vendoapp.R
import com.example.vendoapp.ui.base.BaseFragment
import com.example.vendoapp.databinding.FragmentOTPBinding
import com.example.vendoapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OTPFragment : BaseFragment<FragmentOTPBinding>(
    FragmentOTPBinding::inflate
) {

    private val viewModel: OtpViewModel by viewModels()
    private val args: OTPFragmentArgs by navArgs()

    override fun onViewCreateFinish() {
        setupUi()
        observes()

        binding.btnConfirmOTP.setOnClickListener {
            val otpCode = binding.otpView.otp
            val target = args.email

            if (otpCode.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Please enter OTP code", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.verifyOtp(target, otpCode)
        }
    }

    private fun observes() {
        lifecycleScope.launch {
            viewModel.otpState.collectLatest { state ->
                when (state) {
                    is Resource.Loading -> showLoading(true)
                    is Resource.Success -> {
                        showLoading(false)
                        Toast.makeText(requireContext(), "OTP verified!", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_OTPFragment_to_newPasswordFragment)
                    }

                    is Resource.Error -> {
                        showLoading(false)
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit
                }
            }
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnConfirmOTP.isEnabled = !isLoading
    }

}