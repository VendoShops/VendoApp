package com.example.vendoapp.ui.login.signup

import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.vendoapp.R
import com.example.vendoapp.ui.base.BaseFragment
import com.example.vendoapp.databinding.FragmentSignUpBinding
import com.example.vendoapp.utils.Resource
import com.example.vendoapp.utils.TokenManager
import com.example.vendoapp.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(
    FragmentSignUpBinding::inflate
) {

    @Inject lateinit var tokenManager: TokenManager

    private val signUpViewModel : SignUpViewModel by viewModels()

    override fun onViewCreateFinish() {
        setupUi()
        observes()

        binding.tvLogIn.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
        binding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_forgotPasswordFragment)
        }
        binding.btnSignUp.setOnClickListener {
            signUpClick()
        }
    }

    private fun signUpClick() {
        val fullName = binding.etFullName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val remember = binding.cbRememberMe.isChecked

        if (fullName.isEmpty()){
            Toast.makeText(requireContext(),
                getString(R.string.fullname_can_t_be_empty), Toast.LENGTH_SHORT).show()
        } else if (email.isEmpty()) {
            Toast.makeText(requireContext(),
                getString(R.string.email_can_t_be_empty), Toast.LENGTH_SHORT).show()
        } else if (password.isEmpty()) {
            Toast.makeText(requireContext(),
                getString(R.string.password_can_t_be_empty), Toast.LENGTH_SHORT).show()
        } else {
            signUpViewModel.register(fullName, email, password, remember)
        }
    }

    private fun observes() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            signUpViewModel.registerState.collect { state ->
                when (state) {
                    is Resource.Idle -> showLoading(false)
                    is Resource.Loading -> showLoading(true)
                    is Resource.Success -> {
                        showLoading(false)
                        Toast.makeText(requireContext(),
                            getString(R.string.qeydiyyat_u_urlu), Toast.LENGTH_SHORT).show()

                        val token = state.data?.accessToken
                        val refresh = state.data?.refreshToken
                        val accessExpiry = state.data?.accessTokenExpiryDate
                        val refreshExpiry = state.data?.refreshTokenExpiryDate

                        if (binding.cbRememberMe.isChecked) {
                            signUpViewModel.saveLoginData(
                                email = binding.etEmail.text.toString(),
                                password = binding.etPassword.text.toString(),
                                accessToken = token,
                                refreshToken = refresh,
                                accessExpiry = accessExpiry,
                                refreshExpiry = refreshExpiry
                            )
                            tokenManager.saveRememberMe(true)
                        } else {
                            tokenManager.saveRememberMe(false)
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
            binding.btnSignUp.isEnabled = !isLoading
        }
    }

    private fun handleError(message: String?) {
        showLoading(false)
        Toast.makeText(requireContext(), message ?: getString(R.string.something_went_error), Toast.LENGTH_SHORT).show()
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