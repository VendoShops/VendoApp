package com.example.vendoapp.ui.filter

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.vendoapp.databinding.FragmentFilterBinding
import com.example.vendoapp.data.model.filter.FilterData
import com.example.vendoapp.data.model.filter.FilterSection
import com.example.vendoapp.ui.adapter.filter.ColorAdapter
import com.example.vendoapp.ui.base.BaseFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment : BaseFragment<FragmentFilterBinding>(FragmentFilterBinding::inflate) {

    private val viewModel: FilterViewModel by viewModels()
    private lateinit var colorAdapter: ColorAdapter

    override fun onViewCreateFinish() {
        setupViews()
        setupObservers()
        setupClickListeners()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        // Setup color RecyclerView
        colorAdapter = ColorAdapter(emptyList()) { colorItem ->
            viewModel.updateColorSelection(colorItem)
        }

        binding.recyclerViewColors.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = colorAdapter
        }

        // Setup price range slider
        binding.priceRangeSlider.setValues(0f, 20000f)
        binding.priceRangeSlider.addOnChangeListener { slider, _, _ ->
            val values = slider.values
            binding.tvMinPrice.text = "$${values[0].toInt()}"
            binding.tvMaxPrice.text = "$${values[1].toInt()}"
            viewModel.updatePriceRange(values[0], values[1])
        }
    }

    private fun setupObservers() {
        viewModel.expandedSections.observe(viewLifecycleOwner) { expandedSections ->
            updateSectionVisibility(expandedSections)
        }

        viewModel.colorList.observe(viewLifecycleOwner) { colors ->
            colorAdapter.updateColors(colors)
        }

        viewModel.filterData.observe(viewLifecycleOwner) { filterData ->
            updateFilterSelections(filterData)
        }
    }

    private fun setupClickListeners() {
        // Back button
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Section header clicks
        binding.layoutGenderHeader.setOnClickListener {
            viewModel.toggleSection(FilterSection.GENDER)
        }

        binding.layoutCategoriesHeader.setOnClickListener {
            viewModel.toggleSection(FilterSection.CATEGORIES)
        }

        binding.layoutSizeHeader.setOnClickListener {
            viewModel.toggleSection(FilterSection.SIZE)
        }

        binding.layoutBrandHeader.setOnClickListener {
            viewModel.toggleSection(FilterSection.BRAND)
        }

        binding.layoutColorHeader.setOnClickListener {
            viewModel.toggleSection(FilterSection.COLOR)
        }

        binding.layoutPriceHeader.setOnClickListener {
            viewModel.toggleSection(FilterSection.PRICE)
        }

        // Chip group listeners
        setupChipGroupListeners()

//        binding.btnApplyFilter.setOnClickListener {
//            val filterData = viewModel.applyFilters()
//
//            val bundle = Bundle().apply {
//                putParcelable("filterData", filterData)
//            }
//
//            parentFragmentManager.setFragmentResult("filterResult", bundle)
//
//            parentFragmentManager.popBackStack()
//        }
    }

    private fun setupChipGroupListeners() {
        // Gender chips
        binding.chipGroupGender.setOnCheckedStateChangeListener { group, checkedIds ->
            val selectedGenders = getSelectedChipTexts(group, checkedIds)
            viewModel.updateGenderSelection(selectedGenders)
        }

        // Category chips
        binding.chipGroupCategories.setOnCheckedStateChangeListener { group, checkedIds ->
            val selectedCategories = getSelectedChipTexts(group, checkedIds)
            viewModel.updateCategorySelection(selectedCategories)
        }

        // Size chips
        binding.chipGroupSizes.setOnCheckedStateChangeListener { group, checkedIds ->
            val selectedSizes = getSelectedChipTexts(group, checkedIds)
            viewModel.updateSizeSelection(selectedSizes)
        }

        // Brand chips
        binding.chipGroupBrands.setOnCheckedStateChangeListener { group, checkedIds ->
            val selectedBrands = getSelectedChipTexts(group, checkedIds)
            viewModel.updateBrandSelection(selectedBrands)
        }
    }

    private fun getSelectedChipTexts(chipGroup: ChipGroup, checkedIds: List<Int>): List<String> {
        return checkedIds.mapNotNull { id ->
            chipGroup.findViewById<Chip>(id)?.text?.toString()
        }
    }

    private fun updateSectionVisibility(expandedSections: Set<FilterSection>) {
        // Update Gender section
        val isGenderExpanded = expandedSections.contains(FilterSection.GENDER)
        binding.layoutGenderOptions.visibility = if (isGenderExpanded) View.VISIBLE else View.GONE
        rotateArrow(binding.ivGenderArrow, isGenderExpanded)

        // Update Categories section
        val isCategoriesExpanded = expandedSections.contains(FilterSection.CATEGORIES)
        binding.chipGroupCategories.visibility =
            if (isCategoriesExpanded) View.VISIBLE else View.GONE
        rotateArrow(binding.ivCategoriesArrow, isCategoriesExpanded)

        // Update Size section
        val isSizeExpanded = expandedSections.contains(FilterSection.SIZE)
        binding.chipGroupSizes.visibility = if (isSizeExpanded) View.VISIBLE else View.GONE
        rotateArrow(binding.ivSizeArrow, isSizeExpanded)

        // Update Brand section
        val isBrandExpanded = expandedSections.contains(FilterSection.BRAND)
        binding.chipGroupBrands.visibility = if (isBrandExpanded) View.VISIBLE else View.GONE
        rotateArrow(binding.ivBrandArrow, isBrandExpanded)

        // Update Color section
        val isColorExpanded = expandedSections.contains(FilterSection.COLOR)
        binding.recyclerViewColors.visibility = if (isColorExpanded) View.VISIBLE else View.GONE
        rotateArrow(binding.ivColorArrow, isColorExpanded)

        // Update Price section
        val isPriceExpanded = expandedSections.contains(FilterSection.PRICE)
        binding.layoutPriceOptions.visibility = if (isPriceExpanded) View.VISIBLE else View.GONE
        rotateArrow(binding.ivPriceArrow, isPriceExpanded)
    }

    private fun rotateArrow(arrow: View, isExpanded: Boolean) {
        val rotation = if (isExpanded) 180f else 0f
        arrow.animate()
            .rotation(rotation)
            .setDuration(200)
            .start()
    }

    @SuppressLint("SetTextI18n")
    private fun updateFilterSelections(filterData: FilterData) {
        // Update chip selections based on filter data
        updateChipGroupSelection(binding.chipGroupGender, filterData.selectedGenders)
        updateChipGroupSelection(binding.chipGroupCategories, filterData.selectedCategories)
        updateChipGroupSelection(binding.chipGroupSizes, filterData.selectedSizes)
        updateChipGroupSelection(binding.chipGroupBrands, filterData.selectedBrands)

        // Update price range
        binding.priceRangeSlider.setValues(filterData.minPrice, filterData.maxPrice)
        binding.tvMinPrice.text = "$${filterData.minPrice.toInt()}"
        binding.tvMaxPrice.text = "$${filterData.maxPrice.toInt()}"
    }

    private fun updateChipGroupSelection(chipGroup: ChipGroup, selectedItems: List<String>) {
        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as? Chip
            chip?.let {
                it.isChecked = selectedItems.contains(it.text.toString())
            }
        }
    }
}