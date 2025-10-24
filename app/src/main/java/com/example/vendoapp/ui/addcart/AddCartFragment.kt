package com.example.vendoapp.ui.addcart

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vendoapp.data.model.home.ProductDetail
import com.example.vendoapp.databinding.FragmentAddCartBinding
import com.example.vendoapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCartFragment : BaseFragment<FragmentAddCartBinding>(FragmentAddCartBinding::inflate) {

    private val viewModel: ProductDetailViewModel by viewModels()
    private lateinit var imageAdapter: ProductImageAdapter
    private lateinit var colorAdapter: ProductColorAdapter

    override fun onViewCreateFinish() {
        setupViews()
        setupObservers()
        setupClickListeners()
    }

    private fun setupViews() {
        // Setup image ViewPager
        imageAdapter = ProductImageAdapter(emptyList()) { position ->
            // Image click handler - şəkil böyüdülə bilər
        }
        binding.viewPagerImages.adapter = imageAdapter

        // Setup color RecyclerView
        colorAdapter = ProductColorAdapter(emptyList()) { colorItem ->
            viewModel.selectColor(colorItem)
        }
        binding.recyclerViewColors.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = colorAdapter
        }

        binding.viewPagerImages.registerOnPageChangeCallback(object :
            androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updatePageIndicator(position)
            }
        })
    }

    private fun setupObservers() {
        // Product data observer
        viewModel.productDetail.observe(viewLifecycleOwner) { product ->
            updateProductUI(product)
        }

        // Selected color observer
        viewModel.selectedColor.observe(viewLifecycleOwner) { color ->
            colorAdapter.updateSelection(color)
        }

        // Selected size observer
        viewModel.selectedSize.observe(viewLifecycleOwner) { size ->
            updateSizeSelection(size)
        }

        // Favorite status observer
        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            updateFavoriteIcon(isFavorite)
        }
    }

    private fun setupClickListeners() {
        // Back button
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Favorite button
        binding.btnFavorite.setOnClickListener {
            viewModel.toggleFavorite()
        }

        // Share button
        binding.btnShare.setOnClickListener {
            shareProduct()
        }

        binding.tvStoreName.setOnClickListener {
            // Navigate to store page
        }

        // Ask question click
        binding.tvAskQuestion.setOnClickListener {
            // Open Q&A dialog
        }

        // Reviews click
        binding.layoutReviews.setOnClickListener {
            // Navigate to reviews page
        }

        // Q&A click
        binding.layoutQA.setOnClickListener {
            // Navigate to Q&A page
        }

        // Size selection clicks
        setupSizeClickListeners()

        // Add to cart button
        binding.btnAddToCart.setOnClickListener {
            addToCart()
        }
    }

    private fun setupSizeClickListeners() {
        binding.chipSizeM.setOnClickListener { viewModel.selectSize("M") }
        binding.chipSizeL.setOnClickListener { viewModel.selectSize("L") }
        binding.chipSizeXL.setOnClickListener { viewModel.selectSize("XL") }
        binding.chipSizeXXL.setOnClickListener { viewModel.selectSize("XXL") }
    }

    @SuppressLint("SetTextI18n")
    private fun updateProductUI(product: ProductDetail) {
        binding.apply {
            // Images
            imageAdapter.updateImages(product.images)
            updatePageIndicator(0)

            // Product info
            tvProductName.text = product.name
            tvProductDescription.text = product.description
            tvRating.text = product.rating.toString()
            tvReviewCount.text = "${product.reviewCount} Reviews"
            tvQACount.text = "${product.qaCount} Q&A"

            // Store info
            tvStoreName.text = product.storeName

            // Selected color
            tvSelectedColor.text = product.defaultColor

            // Colors
            colorAdapter.updateColors(product.colors)

            // Selected size
            tvSelectedSize.text = product.defaultSize

            // Shipping info
            tvDeliveryDate.text = product.estimatedDelivery
            tvShippingInfo.text = product.shippingInfo

            // Return info
            tvReturnInfo.text = product.returnInfo

            // Price
            tvPrice.text = "$${product.price}"
            tvShippingPrice.text = product.shippingPrice
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updatePageIndicator(position: Int) {
        val totalPages = imageAdapter.itemCount
        binding.tvPageIndicator.text = "${position + 1}/$totalPages"
    }

    private fun updateSizeSelection(size: String) {
        // Reset all chips
        binding.chipSizeM.isChecked = false
        binding.chipSizeL.isChecked = false
        binding.chipSizeXL.isChecked = false
        binding.chipSizeXXL.isChecked = false

        // Select the chosen size
        when (size) {
            "M" -> binding.chipSizeM.isChecked = true
            "L" -> binding.chipSizeL.isChecked = true
            "XL" -> binding.chipSizeXL.isChecked = true
            "XXL" -> binding.chipSizeXXL.isChecked = true
        }
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        // Favorite icon-u dəyişdir
        // binding.btnFavorite.setImageResource(if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_outline)
    }

    private fun shareProduct() {
        // Share product link
        // Intent.createChooser() istifadə et
    }

    private fun addToCart() {
        val selectedColor = viewModel.selectedColor.value
        val selectedSize = viewModel.selectedSize.value

        if (selectedColor == null || selectedSize == null) {
            // Show error - user must select color and size
            return
        }

        viewModel.addToCart()
        // Show success message or navigate to cart
    }
}