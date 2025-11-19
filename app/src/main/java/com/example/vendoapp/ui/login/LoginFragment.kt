package com.example.vendoapp.ui.login

import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.vendoapp.R
import com.example.vendoapp.ui.base.BaseFragment
import com.example.vendoapp.databinding.FragmentLoginBinding
import com.example.vendoapp.utils.Resource
import com.example.vendoapp.utils.TokenManager
import com.example.vendoapp.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {

    @Inject lateinit var tokenManager: TokenManager

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreateFinish() {
        setupUi()
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
        binding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
        binding.btnLogin.setOnClickListener {

            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val remember = binding.cbRememberMe.isChecked

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Email and password cannot be empty",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            viewModel.login(email, password, remember)

            if (remember) {
                tokenManager.saveRememberMe(true)
            } else {
                tokenManager.saveRememberMe(false)
            }
        }
        observeLoginState()
    }

    private fun observeLoginState() {
        lifecycleScope.launch {
            viewModel.loginState.collectLatest { state ->
                when (state) {
                    is Resource.Idle -> showLoading(false)
                    is Resource.Loading -> showLoading(true)
                    is Resource.Success -> {
                        showLoading(false)
                        Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()

                        val token = state.data?.accessToken
                        val refresh = state.data?.refreshToken
                        val accessExpiry = state.data?.accessTokenExpiryDate
                        val refreshExpiry = state.data?.refreshTokenExpiryDate

                        if (binding.cbRememberMe.isChecked) {
                            viewModel.saveLoginData(
                                email = binding.etEmail.text.toString(),
                                password = binding.etPassword.text.toString(),
                                accessToken = token,
                                refreshToken = refresh,
                                accessExpiry = accessExpiry,
                                refreshExpiry = refreshExpiry
                            )
                        }

                        findNavController().navigate(
                            R.id.homeFragment,
                            null,
                            NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build()
                        )
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
            binding.btnLogin.isEnabled = !isLoading
        }
    }

    private fun handleError(message: String?) {
        showLoading(false)
        Toast.makeText(requireContext(), message ?: "Something went error", Toast.LENGTH_SHORT)
            .show()
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