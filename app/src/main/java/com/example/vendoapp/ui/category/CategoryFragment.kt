package com.example.vendoapp.ui.category

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.vendoapp.ui.base.BaseFragment
import com.example.vendoapp.databinding.FragmentCategoryBinding
import com.example.vendoapp.ui.adapter.category.CategoriesAdapter
import com.example.vendoapp.ui.viewmodel.category.CategoryViewModel
import com.google.android.material.internal.ViewUtils.hideKeyboard

class CategoryFragment : BaseFragment<FragmentCategoryBinding>(FragmentCategoryBinding::inflate) {

    private lateinit var categoryAdapter: CategoriesAdapter
    private val viewModel: CategoryViewModel by viewModels()

    override fun onViewCreateFinish() {
        setupUi()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        categoryAdapter = CategoriesAdapter()
        binding.recyclerCategories.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerCategories.adapter = categoryAdapter

        viewModel.categories.observe(viewLifecycleOwner) { list ->
            categoryAdapter.submitList(list)
        }
    }

    @SuppressLint("RestrictedApi")
    private fun setupSearchFunctionality() {
        binding.let {
            // Clear button click listener
//            it.ivClearSearch.setOnClickListener {
//                it.etSearch.setText("")
//                it.ivClearSearch.visibility = View.GONE
//                it.ivSearchIcon.visibility = View.VISIBLE
//                hideKeyboardAndClearFocus()
//            }

            it.qrScannerCategory.setOnClickListener {
                viewModel.onQrCodeClick()
            }

            // EditText text change listener
            it.searchEditText.addTextChangedListener(object : TextWatcher {
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
                        it.searchCategoryIcon.visibility = View.VISIBLE
                    } else {
                        it.searchCategoryIcon.visibility = View.GONE
                        it.ivClearSearch.visibility = View.VISIBLE
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    viewModel.onSearchQueryChanged(s.toString())
                }
            })

            // EditText focus change listener
            it.searchEditText.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    hideKeyboard(it.searchEditText)
                }
            }

            it.searchEditText.setOnEditorActionListener { _, _, _ ->
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
        binding.searchEditText.clearFocus()
        val currentFocus = requireActivity().currentFocus
        if (currentFocus != null) {
            hideKeyboard(currentFocus)
        }
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