package com.example.vendoapp.ui.profile.myreturns

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.findNavController
import com.example.vendoapp.databinding.FragmentMyReturnsBinding
import com.example.vendoapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyReturnsFragment : BaseFragment<FragmentMyReturnsBinding>(
    FragmentMyReturnsBinding::inflate
) {
    override fun onViewCreateFinish() {
        setupUi()
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
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