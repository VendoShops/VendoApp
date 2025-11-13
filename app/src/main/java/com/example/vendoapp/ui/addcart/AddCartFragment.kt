package com.example.vendoapp.ui.addcart

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.vendoapp.R
import com.example.vendoapp.data.model.home.ProductDetail
import com.example.vendoapp.databinding.FragmentAddCartBinding
import com.example.vendoapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCartFragment : BaseFragment<FragmentAddCartBinding>(FragmentAddCartBinding::inflate) {

    private val viewModel: ProductDetailViewModel by viewModels()
    private lateinit var imageAdapter: ProductImageAdapter
    private lateinit var colorAdapter: ProductColorAdapter

    // Size buttons map
    private val sizeButtons = mutableMapOf<String, TextView>()

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

        // Setup ViewPager page change callback for indicator
        binding.viewPagerImages.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val totalPages = imageAdapter.itemCount
                binding.tvPageIndicator.text = "${position + 1}/$totalPages"
            }
        })

        // Setup color RecyclerView
        colorAdapter = ProductColorAdapter(emptyList()) { colorItem ->
            viewModel.selectColor(colorItem)
        }

        binding.rvColors.adapter = colorAdapter

        // Map size buttons
        sizeButtons["M"] = binding.btnSizeM
        sizeButtons["L"] = binding.btnSizeL
        sizeButtons["XL"] = binding.btnSizeXL
        sizeButtons["XXL"] = binding.btnSizeXXL
    }

    private fun setupObservers() {
        // Observe product detail
        viewModel.productDetail.observe(viewLifecycleOwner) { product ->
            bindProductData(product)
        }

        // Observe selected color
        viewModel.selectedColor.observe(viewLifecycleOwner) { colorName ->
            // Update color adapter selection
            colorAdapter.updateSelection(colorName)

            // Update selected color text - only show when color is selected
            if (colorName.isNotEmpty()) {
                binding.tvSelectedColor.text = colorName
                binding.tvSelectedColor.visibility = View.VISIBLE
            } else {
                binding.tvSelectedColor.text = ""
                binding.tvSelectedColor.visibility = View.GONE
            }
        }

        // Observe selected size
        viewModel.selectedSize.observe(viewLifecycleOwner) { size ->
            binding.tvSelectedSize.text = size
            updateSizeSelection(size)
        }

        // Observe favorite status
        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            binding.btnFavorite.setImageResource(
                if (isFavorite) R.drawable.like_dark else R.drawable.like_normal
            )
        }

        // Observe add to cart success
        viewModel.addToCartSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Product added to cart", Toast.LENGTH_SHORT).show()
                // Reset the flag
                // viewModel.resetAddToCartFlag()
            }
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

        // Ask question button
        binding.btnAskQuestion.setOnClickListener {
            // Navigate to Q&A screen or show dialog
            Toast.makeText(requireContext(), "Ask Question feature", Toast.LENGTH_SHORT).show()
        }

        // Size button listeners
        binding.btnSizeM.setOnClickListener { viewModel.selectSize("M") }
        binding.btnSizeL.setOnClickListener { viewModel.selectSize("L") }
        binding.btnSizeXL.setOnClickListener { viewModel.selectSize("XL") }
        binding.btnSizeXXL.setOnClickListener { viewModel.selectSize("XXL") }

        // Add to cart button
        binding.btnAddToCart.setOnClickListener {
            viewModel.addToCart()
        }

        // Reviews click
        binding.layoutReviews.setOnClickListener {
            // Navigate to reviews page
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindProductData(product: ProductDetail) {
        with(binding) {
            // Product info
            tvProductName.text = product.name
            tvProductDescription.text = product.description
            tvRating.text = product.rating.toString()
            tvReviewCount.text = "${product.reviewCount} Reviews"
            tvQA.text = "${product.qaCount} Q&A"

            // Store info
            tvStoreName.text = product.storeName
            ivVerifiedBadge.visibility = if (product.isVerified) View.VISIBLE else View.GONE

            // Price
            tvPrice.text = "$${product.price}"

            // Shipping info
            tvEstimatedDelivery.text = "Estimared delivery: ${product.estimatedDelivery}"
            tvShippingPrice.text = product.shippingInfo
            tvFreeShipping.text = product.shippingPrice

            // Return info
            tvReturnInfo.text = product.returnInfo

            // Update images
            imageAdapter.updateImages(product.images)
            binding.tvPageIndicator.text = "1/${product.images.size}"

            // Update colors
            colorAdapter.updateColors(product.colors)

            // Set initial selected color (empty initially, will be set when user selects)
            binding.tvSelectedColor.text = ""
            binding.tvSelectedColor.visibility = View.GONE
        }
    }

    private fun updateSizeSelection(selectedSize: String) {
        // Reset all size buttons
        sizeButtons.values.forEach { button ->
            button.isSelected = false
        }

        // Select the chosen size
        sizeButtons[selectedSize]?.isSelected = true
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        // Favorite icon-u dəyişdir
        // binding.btnFavorite.setImageResource(if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_outline)
    }

    private fun shareProduct() {
        // Share product link
        // Intent.createChooser() istifadə et
    }
}