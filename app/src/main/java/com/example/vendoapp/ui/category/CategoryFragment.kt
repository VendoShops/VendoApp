package com.example.vendoapp.ui.category

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vendoapp.base.BaseFragment
import com.example.vendoapp.databinding.FragmentCategoryBinding

class CategoryFragment : BaseFragment<FragmentCategoryBinding>(
    FragmentCategoryBinding::inflate
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