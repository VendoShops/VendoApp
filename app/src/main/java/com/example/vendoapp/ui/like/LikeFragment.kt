package com.example.vendoapp.ui.like

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.vendoapp.ui.base.BaseFragment
import com.example.vendoapp.databinding.FragmentLikeBinding
import com.example.vendoapp.ui.adapter.like.LikeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LikeFragment : BaseFragment<FragmentLikeBinding>(FragmentLikeBinding::inflate) {

    private val likeViewModel: LikeViewModel by viewModels()
    private lateinit var adapter: LikeAdapter

    override fun onViewCreateFinish() {
        setUpAdapter()
        setupUi()
        observeData()
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

    private fun setUpAdapter() {
        adapter = LikeAdapter(
            onProductClick = { product -> likeViewModel.onProductClick(product) },
            onFavoriteClick = { product -> likeViewModel.onFavoriteClick(product) }
        )

        binding.recylerLike.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = this@LikeFragment.adapter
            isNestedScrollingEnabled = false
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                likeViewModel.likedProducts.collect { products ->
                    adapter.submitList(products.toList())
                }
            }
        }
    }
}
