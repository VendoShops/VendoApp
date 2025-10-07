package com.example.vendoapp.ui.favorite

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vendoapp.databinding.FragmentFavouriteBinding
import com.example.vendoapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavouriteBinding>(
    FragmentFavouriteBinding::inflate
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