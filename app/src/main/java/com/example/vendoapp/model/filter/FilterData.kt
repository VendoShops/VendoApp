package com.example.vendoapp.model.filter

data class FilterData(
    val selectedGenders: List<String> = emptyList(),
    val selectedCategories: List<String> = emptyList(),
    val selectedSizes: List<String> = emptyList(),
    val selectedBrands: List<String> = emptyList(),
    val selectedColors: List<ColorItem> = emptyList(),
    val minPrice: Float = 0f,
    val maxPrice: Float = 20000f
)

data class ColorItem(
    val name: String,
    val colorHex: String,
    var isSelected: Boolean = false
)

enum class FilterSection {
    GENDER,
    CATEGORIES,
    SIZE,
    BRAND,
    COLOR,
    PRICE
}
