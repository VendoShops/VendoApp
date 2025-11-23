package com.example.vendoapp.ui.profile

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.vendoapp.R
import com.example.vendoapp.ui.base.BaseFragment
import com.example.vendoapp.databinding.FragmentProfileBinding
import com.example.vendoapp.utils.Resource
import com.example.vendoapp.utils.TokenManager
import com.example.vendoapp.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {

    @Inject lateinit var tokenManager: TokenManager
    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreateFinish() {
        setupUi()
        tvLogoutClick()
        observes()

        val userId = tokenManager.getUserId()
        if (userId != -1) {
            viewModel.loadUserProfile(userId)
        } else {
            findNavController().navigate(R.id.loginFragment)
            Toast.makeText(requireContext(), "UserId tapilmadi", Toast.LENGTH_SHORT).show()
        }

        //  Navigations
        binding.termsAndConditionsConstraint.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_termsAndConditionsFragment)
        }
        binding.languageConstraint.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_selectLanguageFragment)
        }
        binding.personalInfoConstraint.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_personalInformationFragment)
        }
        binding.addressesConstraint.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_addressesFragment)
        }
        binding.myPaymentMethodsConstraint.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_paymentMethodFragment)
        }
        binding.myOrderConstraint.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_myOrdersFragment)
        }
        binding.myReturnsConstraint.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_myReturnsFragment)
        }
    }

    private fun observes() {
        viewModel.profile.observe(viewLifecycleOwner) { resource ->
            when(resource) {
                is Resource.Idle -> { /* Show loading */ }        //////////////////////////////
                is Resource.Loading -> { /* Show loading */ }     //////////////////////////////
                is Resource.Success -> {
                    val profile = resource.data
                    binding.tvProfileName.text = profile?.fullName ?: "No Name"
                    binding.tvProfileEmail.text = "User ID: ${profile?.userId}"
                    Glide.with(this)
                        .load(profile?.avatarUrl)
                        .placeholder(R.drawable.testprofileimage)
                        .into(binding.ivProfile)
                }
                is Resource.Error -> {
                    binding.ivProfile.setImageResource(R.drawable.testprofileimage)
                }
            }
        }
    }

    private fun tvLogoutClick() {
        binding.tvLogOut.setOnClickListener {
            showLogoutDialog(requireContext()) {
                tokenManager.clearTokens()
            }
        }
    }

    fun showLogoutDialog(context: Context, onLogoutConfirmed: () -> Unit) {
        val dialog = Dialog(context)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_logout, null)
        dialog.setContentView(view)

        val btnLogout = view.findViewById<Button>(R.id.btnLogout)
        val btnCancel = view.findViewById<Button>(R.id.btnCancel)

        btnLogout.setOnClickListener {
            dialog.dismiss()
            onLogoutConfirmed()
            findNavController().navigate(
                R.id.loginFragment,
                null,
                NavOptions.Builder().setPopUpTo(R.id.homeFragment, true).build()
            )
        }


        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    private fun setupUi() {
        binding.let {
            ViewCompat.setOnApplyWindowInsetsListener(it.main) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(0, systemBars.top, 0, 0)
                insets
            }
        }
    }
}