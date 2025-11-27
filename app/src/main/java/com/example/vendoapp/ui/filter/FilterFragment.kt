package com.example.vendoapp.ui.filter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.vendoapp.data.model.filter.FilterData
import com.example.vendoapp.data.model.filter.FilterSection
import com.example.vendoapp.databinding.FragmentFilterBinding
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
        setupPriceSlider()
        setupObservers()
        setupClickListeners()

        // Initially hide the Apply Filter button
        binding.btnApplyFilter.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        // Setup color RecyclerView
        colorAdapter = ColorAdapter(emptyList()) { colorItem ->
            viewModel.updateColorSelection(colorItem)
            checkAndShowApplyButton()
        }

        binding.recyclerViewColors.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = colorAdapter
        }

        // Setup price range slider values and listener
        binding.priceRangeSlider.setValues(0f, 20000f)
        binding.priceRangeSlider.addOnChangeListener { slider, _, _ ->
            val values = slider.values
            binding.tvMinPrice.text = "$${values[0].toInt()}"
            binding.tvMaxPrice.text = "$${values[1].toInt()}"

            binding.etMinPrice.setText(values[0].toInt().toString())
            binding.etMaxPrice.setText(values[1].toInt().toString())

            viewModel.updatePriceRange(values[0], values[1])
            checkAndShowApplyButton()
        }

        binding.etMinPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val minValue = s?.toString()?.toFloatOrNull() ?: 0f
                val currentMax = binding.priceRangeSlider.values[1]
                if (minValue <= currentMax) {
                    binding.priceRangeSlider.setValues(minValue, currentMax)
                }
            }
        })

        binding.etMaxPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val maxValue = s?.toString()?.toFloatOrNull() ?: 20000f
                val currentMin = binding.priceRangeSlider.values[0]
                if (maxValue >= currentMin) {
                    binding.priceRangeSlider.setValues(currentMin, maxValue)
                }
            }
        })
    }

    private fun setupPriceSlider() {
        binding.priceRangeSlider.apply {
            trackActiveTintList = ColorStateList.valueOf(Color.BLACK)
            trackInactiveTintList = ColorStateList.valueOf(Color.parseColor("#E5E7EB"))

            trackHeight = 4.dpToPx(requireContext())

            val thumbDrawable = GradientDrawable().apply {
                shape = GradientDrawable.OVAL
                setColor(Color.WHITE)
                setStroke(2.dpToPx(requireContext()), Color.BLACK)
                setSize(5.dpToPx(requireContext()), 9.dpToPx(requireContext()))
            }

            setCustomThumbDrawable(thumbDrawable)

            thumbElevation = 0f

            labelBehavior = com.google.android.material.slider.LabelFormatter.LABEL_GONE
        }
    }

    private fun setupObservers() {
        viewModel.expandedSections.observe(viewLifecycleOwner) { expandedSections ->
            updateSectionVisibility(expandedSections)

            if (expandedSections.isNotEmpty()) {
                binding.btnApplyFilter.visibility = View.VISIBLE
            }
        }

        viewModel.colorList.observe(viewLifecycleOwner) { colors ->
            colorAdapter.updateColors(colors)
        }

        viewModel.filterData.observe(viewLifecycleOwner) { filterData ->
            updateFilterSelections(filterData)
        }
    }

    private fun setupClickListeners() {
        binding.let { filterBinding ->
            filterBinding.btnBack.setOnClickListener {
                parentFragmentManager.popBackStack()
            }

            // Section header clicks
            filterBinding.layoutGenderHeader.setOnClickListener {
                viewModel.toggleSection(FilterSection.GENDER)
            }

            filterBinding.layoutCategoriesHeader.setOnClickListener {
                viewModel.toggleSection(FilterSection.CATEGORIES)
            }

            filterBinding.layoutSizeHeader.setOnClickListener {
                viewModel.toggleSection(FilterSection.SIZE)
            }

            filterBinding.layoutBrandHeader.setOnClickListener {
                viewModel.toggleSection(FilterSection.BRAND)
            }

            filterBinding.layoutColorHeader.setOnClickListener {
                viewModel.toggleSection(FilterSection.COLOR)
            }

            filterBinding.layoutPriceHeader.setOnClickListener {
                viewModel.toggleSection(FilterSection.PRICE)
            }
        }

        // Chip group listeners
        setupChipGroupListeners()

        // Apply filter button
        binding.btnApplyFilter.setOnClickListener {
            val filterData = viewModel.applyFilters()

            // val bundle = Bundle().apply {
            //     putParcelable("filterData", filterData)
            // }
            // parentFragmentManager.setFragmentResult("filterResult", bundle)

            parentFragmentManager.popBackStack()
        }
    }

    private fun setupChipGroupListeners() {
        binding.let { chipGroup ->
            // Gender chips
            chipGroup.chipGroupGender.setOnCheckedStateChangeListener { group, checkedIds ->
                val selectedGenders = getSelectedChipTexts(group, checkedIds)
                viewModel.updateGenderSelection(selectedGenders)
            }

            // Category chips
            chipGroup.chipGroupCategories.setOnCheckedStateChangeListener { group, checkedIds ->
                val selectedCategories = getSelectedChipTexts(group, checkedIds)
                viewModel.updateCategorySelection(selectedCategories)
            }

            // Size chips
            chipGroup.chipGroupSizes.setOnCheckedStateChangeListener { group, checkedIds ->
                val selectedSizes = getSelectedChipTexts(group, checkedIds)
                viewModel.updateSizeSelection(selectedSizes)
            }

            // Brand chips
            chipGroup.chipGroupBrands.setOnCheckedStateChangeListener { group, checkedIds ->
                val selectedBrands = getSelectedChipTexts(group, checkedIds)
                viewModel.updateBrandSelection(selectedBrands)
            }
        }
    }

    private fun getSelectedChipTexts(chipGroup: ChipGroup, checkedIds: List<Int>): List<String> {
        return checkedIds.mapNotNull { id ->
            chipGroup.findViewById<Chip>(id)?.text?.toString()
        }
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

        binding.etMinPrice.setText(filterData.minPrice.toInt().toString())
        binding.etMaxPrice.setText(filterData.maxPrice.toInt().toString())
    }

    private fun updateChipGroupSelection(chipGroup: ChipGroup, selectedItems: List<String>) {
        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as? Chip
            chip?.let {
                it.isChecked = selectedItems.contains(it.text.toString())
            }
        }
    }

    private fun rotateArrow(arrow: View, isExpanded: Boolean) {
        val rotation = if (isExpanded) 180f else 0f
        arrow.animate()
            .rotation(rotation)
            .setDuration(200)
            .start()
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

    private fun checkAndShowApplyButton() {
        val filterData = viewModel.filterData.value ?: return

        val hasSelection = filterData.selectedGenders.isNotEmpty() ||
                filterData.selectedCategories.isNotEmpty() ||
                filterData.selectedSizes.isNotEmpty() ||
                filterData.selectedBrands.isNotEmpty() ||
                filterData.selectedColors.isNotEmpty() ||
                (filterData.minPrice != 0f || filterData.maxPrice != 20000f)

        if (hasSelection) {
            binding.btnApplyFilter.visibility = View.VISIBLE
        }
    }

    private fun Int.dpToPx(context: Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }
}