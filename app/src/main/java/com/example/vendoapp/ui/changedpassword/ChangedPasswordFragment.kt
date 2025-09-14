package com.example.vendoapp.ui.changedpassword

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.findNavController
import com.example.vendoapp.R
import com.example.vendoapp.base.BaseFragment
import com.example.vendoapp.databinding.FragmentChangedPasswordBinding

class ChangedPasswordFragment : BaseFragment<FragmentChangedPasswordBinding>(
    FragmentChangedPasswordBinding::inflate
) {

    override fun onViewCreateFinish() {
        setupUi()
        binding.tvLogIn.setOnClickListener {
            findNavController().navigate(R.id.action_changedPasswordFragment_to_loginFragment)
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