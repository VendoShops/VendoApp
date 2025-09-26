package com.example.vendoapp.ui.cart

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vendoapp.base.BaseFragment
import com.example.vendoapp.databinding.FragmentCartBinding

class CartFragment : BaseFragment<FragmentCartBinding>(
    FragmentCartBinding::inflate
) {

    override fun onViewCreateFinish() {
        setupUi()
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

