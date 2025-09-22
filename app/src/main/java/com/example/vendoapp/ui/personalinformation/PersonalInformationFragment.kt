package com.example.vendoapp.ui.personalinformation


import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.findNavController
import com.example.vendoapp.ui.base.BaseFragment
import com.example.vendoapp.databinding.FragmentPersonalInformationBinding

class PersonalInformationFragment : BaseFragment<FragmentPersonalInformationBinding>(
    FragmentPersonalInformationBinding::inflate
) {

    override fun onViewCreateFinish() {
        setupUi()
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
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