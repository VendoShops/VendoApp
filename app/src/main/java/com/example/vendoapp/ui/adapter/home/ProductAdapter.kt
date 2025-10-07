package com.example.vendoapp.ui.adapter.home

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.vendoapp.R
import com.example.vendoapp.databinding.ItemProductBinding
import com.example.vendoapp.data.model.home.Product

class ProductAdapter(
    private val onItemClick: (Product) -> Unit,
    private val onProductClick: (Product) -> Unit,
    private val onFavoriteClick: (Product) -> Unit,
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductViewHolder(
        private val binding: ItemProductBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                ivProductImage.setImageResource(product.imageRes)

                tvProductTitle.text = product.title
                tvPrice.text = product.price
                tvRating.text = product.rating.toString()

                // Set favorite icon
                val favoriteIcon = if (product.isFavorite) {
                    R.drawable.favorite_like
                } else {
                    R.drawable.favorite_like
                }
                ivFavorite.setImageResource(favoriteIcon)

                // Create color indicators
                createColorIndicators(product.colors)

                // Click listeners
                root.setOnClickListener { onProductClick(product) }
                ivFavorite.setOnClickListener { onFavoriteClick(product) }
            }
        }

        private fun createColorIndicators(colors: List<String>) {
            binding.llColorIndicators.removeAllViews()

            colors.take(5).forEachIndexed { index, colorHex ->
                val colorView = ImageView(binding.root.context).apply {
                    layoutParams = LinearLayout.LayoutParams(16, 16).apply {
                        if (index > 0) setMargins(8, 0, 0, 0)
                    }

                    val drawable = GradientDrawable().apply {
                        shape = GradientDrawable.OVAL
                        setColor(Color.parseColor(colorHex))
                        setStroke(1, ContextCompat.getColor(binding.root.context, R.color.grey_9f))
                    }
                    setImageDrawable(drawable)
                }
                binding.llColorIndicators.addView(colorView)
            }
        }
    }

    private class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}