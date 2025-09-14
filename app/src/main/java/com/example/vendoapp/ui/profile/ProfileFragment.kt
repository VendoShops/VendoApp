package com.example.vendoapp.ui.profile

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.findNavController
import com.example.vendoapp.R
import com.example.vendoapp.base.BaseFragment
import com.example.vendoapp.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {
    override fun onViewCreateFinish() {
        setupUi()
        tvLogoutClick()
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
    }


    private fun tvLogoutClick() {
        binding.tvLogOut.setOnClickListener {
            showLogoutDialog(requireContext()) {
                // Hesabdan cixis
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
            onLogoutConfirmed()
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            dialog.dismiss()
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