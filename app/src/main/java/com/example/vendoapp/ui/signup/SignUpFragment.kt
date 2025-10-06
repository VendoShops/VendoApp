package com.example.vendoapp.ui.signup

import android.util.Log
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.vendoapp.R
import com.example.vendoapp.ui.base.BaseFragment
import com.example.vendoapp.databinding.FragmentSignUpBinding
import com.example.vendoapp.utils.Resource
import com.example.vendoapp.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(
    FragmentSignUpBinding::inflate
) {

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

        signUpViewModel.register(fullName, email, password)

    }

    private fun observes() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            signUpViewModel.registerState.collect { resource ->
                when (resource) {
                    is Resource.Idle -> showLoading(false)
                    is Resource.Loading -> {
                        showLoading(true)
                        Log.d("SignUpFragment", "State: Loading")

                    }
                    is Resource.Success -> {
                        showLoading(false)
                        Log.d("SignUpFragment", "State: Success, data=${resource.data}")
                        Toast.makeText(requireContext(), "Qeydiyyat uğurlu!", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
                    }

                    is Resource.Error -> {
                        showLoading(false)
                        handleError(resource.message)
                        Log.d("SignUpFragment", "State: Error, message=${resource.message}")
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
        Toast.makeText(requireContext(), message ?: "Xəta baş verdi", Toast.LENGTH_SHORT).show()
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