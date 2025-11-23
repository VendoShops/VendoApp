package com.example.vendoapp.ui.profile.personalinformation

import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.vendoapp.ui.base.BaseFragment
import com.example.vendoapp.databinding.FragmentPersonalInformationBinding
import com.example.vendoapp.utils.TokenManager
import com.example.vendoapp.utils.visible
import com.example.vendoapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PersonalInformationFragment : BaseFragment<FragmentPersonalInformationBinding>(
    FragmentPersonalInformationBinding::inflate
) {

    @Inject
    lateinit var tokenManager: TokenManager
    private val viewModel: PersonalInfoViewModel by viewModels()

    override fun onViewCreateFinish() {
        setupUi()
        observes()
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnUpdate.setOnClickListener {
            val fullName = binding.etFullName.text.toString().trim()
            val userId = tokenManager.getUserId()
            if (userId != null) {
                if (fullName.isEmpty()) {
                    Toast.makeText(requireContext(), "Full name cannot be empty", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                viewModel.updateProfile(userId, fullName, null)
            }
        }
    }

    private fun observes() {
        viewModel.updateState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Resource.Idle -> showLoading(true)
                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    showLoading(false)
                    Toast.makeText(requireContext(), "Profile updated", Toast.LENGTH_SHORT).show()
                }

                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(requireContext(), state.message ?: "Error", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        requireActivity().runOnUiThread {
            binding.progressBar.visible(isLoading)
            binding.btnUpdate.isEnabled = !isLoading
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