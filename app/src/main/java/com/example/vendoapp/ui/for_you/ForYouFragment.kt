package com.example.vendoapp.ui.for_you

import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.vendoapp.R
import com.example.vendoapp.databinding.FragmentForYouBinding
import com.example.vendoapp.ui.adapter.home.ProductAdapter
import com.example.vendoapp.ui.base.BaseFragment
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForYouFragment : BaseFragment<FragmentForYouBinding>(FragmentForYouBinding::inflate) {

    private val viewModel: ForYouViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter

    override fun onViewCreateFinish() {
        setupRecyclerView()
        setupCategoryChips()
        observeData()
        setupUi()
    }

    private fun setupUi() {
        binding.let {
            it.ivBack.setOnClickListener {
                findNavController().popBackStack()
            }

            it.ivBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(
            onItemClick = { _ ->
                // Navigate to product detail
            },
            onProductClick = { product ->
                // Navigate to product detail
            },
            onFavoriteClick = { product ->
                viewModel.toggleFavorite(product)
            }
        )

        binding.rvForYou.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = productAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupCategoryChips() {
        val categories = listOf("T-shirt", "Sweater", "Pants", "Dress")

        categories.forEachIndexed { index, category ->
            val chip = Chip(requireContext()).apply {
                text = category
                isCheckable = true
                isChecked = index == 0 // T-shirt default selected

                setChipBackgroundColorResource(android.R.color.white)
                chipStrokeWidth = 2f
                chipStrokeColor = ContextCompat.getColorStateList(
                    requireContext(),
                    if (index == 0) R.color.black else R.color.grey_9f
                )

                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        updateCategorySelection(this)
                        viewModel.filterByCategory(category)
                    }
                }
            }

            binding.chipGroupCategories.addView(chip)
        }
    }

    private fun updateCategorySelection(selectedChip: Chip) {
        for (i in 0 until binding.chipGroupCategories.childCount) {
            val chip = binding.chipGroupCategories.getChildAt(i) as Chip
            chip.chipStrokeColor = ContextCompat.getColorStateList(
                requireContext(),
                if (chip == selectedChip) R.color.black else R.color.grey_9f
            )
        }
    }

    private fun observeData() {
        viewModel.products.observe(viewLifecycleOwner) { products ->
//            productAdapter.submitList(products)
        }
    }

}