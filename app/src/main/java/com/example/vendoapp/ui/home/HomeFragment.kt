package com.example.vendoapp.ui.home

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vendoapp.databinding.FragmentHomeBinding
import com.example.vendoapp.ui.adapter.home.BrandAdapter
import com.example.vendoapp.ui.adapter.home.ProductAdapter
import com.example.vendoapp.ui.base.BaseFragment
import com.example.vendoapp.ui.viewmodel.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var brandAdapter: BrandAdapter
    private lateinit var productAdapter: ProductAdapter

    override fun onViewCreateFinish() {
        setupUi()
        setupRecyclerViews()
        setupSearchFunctionality()
        observeViewModel()
    }

    private fun setupRecyclerViews() {
        // Setup Brand RecyclerView (horizontal scroll)
        brandAdapter = BrandAdapter { brand ->
            viewModel.onBrandClick(brand)
        }

        binding.rvTopBrands.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = brandAdapter
        }

        // Setup Product RecyclerView (vertical)
        productAdapter = ProductAdapter(
            onProductClick = { product -> viewModel.onProductClick(product) },
            onFavoriteClick = { product -> viewModel.onFavoriteClick(product) }
        )

        binding.rvForYou.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = productAdapter
            isNestedScrollingEnabled = false
        }
    }

    private fun setupUi() {
        binding.let {
            ViewCompat.setOnApplyWindowInsetsListener(it.main) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(0, systemBars.top, 0, 0)
                insets
            }

            // Click listeners
            it.ivNotification.setOnClickListener { viewModel.onNotificationClick() }
            it.ivFilter.setOnClickListener { viewModel.onSearchClick() }
            it.btnShopNow.setOnClickListener { viewModel.onShopNowClick() }
            it.tvTopBrandsMore.setOnClickListener { /* Navigate to brands page */ }
            it.tvForYouMore.setOnClickListener { /* Navigate to products page */ }

            it.etSearch.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    showKeyboard(it.etSearch)
                }
            }

            it.main.setOnClickListener {
                hideKeyboardAndClearFocus()
            }

            it.ivQrCode.setOnClickListener {
                viewModel.onQrCodeClick()
            }
        }
    }

    private fun setupSearchFunctionality() {
        binding.let {
            // Clear button click listener
//            it.ivClearSearch.setOnClickListener {
//                it.etSearch.setText("")
//                it.ivClearSearch.visibility = View.GONE
//                it.ivSearchIcon.visibility = View.VISIBLE
//                hideKeyboardAndClearFocus()
//            }

            it.ivQrCode.setOnClickListener {
                viewModel.onQrCodeClick()
            }

            // EditText text change listener
            it.etSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.isNullOrEmpty()) {
                        it.ivClearSearch.visibility = View.GONE
                        it.ivSearchIcon.visibility = View.VISIBLE
                    } else {
                        it.ivSearchIcon.visibility = View.GONE
                        it.ivClearSearch.visibility = View.VISIBLE
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    viewModel.onSearchQueryChanged(s.toString())
                }
            })

            // EditText focus change listener
            it.etSearch.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    hideKeyboard(it.etSearch)
                }
            }

            it.etSearch.setOnEditorActionListener { _, _, _ ->
                hideKeyboardAndClearFocus()
                true
            }
        }
    }

    private fun showKeyboard(view: View) {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideKeyboard(view: View) {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun hideKeyboardAndClearFocus() {
        binding.etSearch.clearFocus()
        val currentFocus = requireActivity().currentFocus
        if (currentFocus != null) {
            hideKeyboard(currentFocus)
        }
    }

    private fun observeViewModel() {
        viewModel.location.observe(viewLifecycleOwner) { location ->
            binding.tvLocation.text = location
        }

        viewModel.topBrands.observe(viewLifecycleOwner) { brands ->
            brandAdapter.submitList(brands)
        }

        viewModel.products.observe(viewLifecycleOwner) { products ->
            productAdapter.submitList(products)
        }
    }
}