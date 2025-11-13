package com.example.vendoapp.ui.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vendoapp.data.model.filter.ColorItem
import com.example.vendoapp.data.model.filter.FilterData
import com.example.vendoapp.data.model.filter.FilterSection
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(): ViewModel() {

    private val _filterData = MutableLiveData<FilterData>()
    val filterData: LiveData<FilterData> = _filterData

    private val _expandedSections = MutableLiveData<Set<FilterSection>>()
    val expandedSections: LiveData<Set<FilterSection>> = _expandedSections

    private val _colorList = MutableLiveData<List<ColorItem>>()
    val colorList: LiveData<List<ColorItem>> = _colorList

    init {
        initializeData()
    }

    private fun initializeData() {
        _filterData.value = FilterData()
        _expandedSections.value = emptySet()

        // Initialize color list
        val colors = listOf(
            ColorItem("Green", "#4CAF50"),
            ColorItem("Blue", "#2196F3"),
            ColorItem("Red", "#F44336"),
            ColorItem("Purple", "#9C27B0"),
            ColorItem("Brown", "#795548"),
            ColorItem("Light Blue", "#03DAC5"),
            ColorItem("Dark Red", "#D32F2F"),
            ColorItem("Dark Purple", "#7B1FA2"),
            ColorItem("Dark Brown", "#5D4037")
        )
        _colorList.value = colors
    }

    fun toggleSection(section: FilterSection) {
        val currentExpanded = _expandedSections.value?.toMutableSet() ?: mutableSetOf()
        if (currentExpanded.contains(section)) {
            currentExpanded.remove(section)
        } else {
            currentExpanded.add(section)
        }
        _expandedSections.value = currentExpanded
    }

    fun isSectionExpanded(section: FilterSection): Boolean {
        return _expandedSections.value?.contains(section) ?: false
    }

    fun updateGenderSelection(genders: List<String>) {
        val currentData = _filterData.value ?: FilterData()
        _filterData.value = currentData.copy(selectedGenders = genders)
    }

    fun updateCategorySelection(categories: List<String>) {
        val currentData = _filterData.value ?: FilterData()
        _filterData.value = currentData.copy(selectedCategories = categories)
    }

    fun updateSizeSelection(sizes: List<String>) {
        val currentData = _filterData.value ?: FilterData()
        _filterData.value = currentData.copy(selectedSizes = sizes)
    }

    fun updateBrandSelection(brands: List<String>) {
        val currentData = _filterData.value ?: FilterData()
        _filterData.value = currentData.copy(selectedBrands = brands)
    }

    fun updateColorSelection(colorItem: ColorItem) {
        val currentColors = _colorList.value?.toMutableList() ?: mutableListOf()
        val index = currentColors.indexOfFirst { it.name == colorItem.name }
        if (index != -1) {
            currentColors[index] = colorItem
            _colorList.value = currentColors

            val selectedColors = currentColors.filter { it.isSelected }
            val currentData = _filterData.value ?: FilterData()
            _filterData.value = currentData.copy(selectedColors = selectedColors)
        }
    }

    fun updatePriceRange(minPrice: Float, maxPrice: Float) {
        val currentData = _filterData.value ?: FilterData()
        _filterData.value = currentData.copy(minPrice = minPrice, maxPrice = maxPrice)
    }

    fun applyFilters(): FilterData {
        return _filterData.value ?: FilterData()
    }

    fun clearAllFilters() {
        _filterData.value = FilterData()
        _expandedSections.value = emptySet()

        // Reset color selections
        val resetColors = _colorList.value?.map { it.copy(isSelected = false) } ?: emptyList()
        _colorList.value = resetColors
    }
}