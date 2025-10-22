package com.example.vendoapp.ui.login.forgotpassword

import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.vendoapp.R
import com.example.vendoapp.ui.base.BaseFragment
import com.example.vendoapp.databinding.FragmentForgotPasswordBinding
import com.example.vendoapp.utils.Resource
import com.example.vendoapp.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>(
    FragmentForgotPasswordBinding::inflate
) {

    private val viewModel: ForgotPasswordViewModel by viewModels()

    override fun onViewCreateFinish() {
        setupUi()
        binding.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
        }
        binding.btnSendOTP.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(requireContext(), "Email cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.sendResetPassword(email)

        }
        observeForgotPasswordState()
    }

    private fun observeForgotPasswordState() {
        lifecycleScope.launch {
            viewModel.forgotPasswordState.collectLatest { state ->
                when (state) {
                    is Resource.Idle -> showLoading(false)
                    is Resource.Loading -> showLoading(true)
                    is Resource.Success -> {
                        showLoading(false)
                        Toast.makeText(requireContext(), "OTP sent successfully", Toast.LENGTH_SHORT).show()

                        val action = ForgotPasswordFragmentDirections
                            .actionForgotPasswordFragmentToOTPFragment(binding.etEmail.text.toString().trim())

                        findNavController().navigate(action)
                    }
                    is Resource.Error -> {
                        showLoading(false)
                        handleError(state.message)
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        requireActivity().runOnUiThread {
            binding.progressBar.visible(isLoading)
            binding.btnSendOTP.isEnabled = !isLoading
        }
    }

    private fun handleError(message: String?) {
        showLoading(false)
        Toast.makeText(requireContext(), message ?: "Something went wrong", Toast.LENGTH_SHORT).show()
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