package com.example.vendoapp.ui.adapter.home

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vendoapp.R
import com.example.vendoapp.databinding.ItemProductBinding
import com.example.vendoapp.data.model.home.Product

class ProductAdapter(
    private val onItemClick: (Product) -> Unit,
    private val onProductClick: (Product) -> Unit,
    private val onFavoriteClick: (Product) -> Unit,
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    inner class ProductViewHolder(
        private val binding: ItemProductBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                tvProductTitle.text = product.productName ?: "Unknown Product"
                tvPrice.text = "$${"%.2f".format(product.price ?: 0.0)}"
                tvRating.text = product.rating?.toString() ?: "0.0"

                // Favorite icon
                val favoriteIcon = if (product.isFavorite) {
                    R.drawable.favorite_like // TODO: filled icon əlavə edin
                } else {
                    R.drawable.favorite_like
                }
                ivFavorite.setImageResource(favoriteIcon)

                llColorIndicators.visibility = View.GONE

                // Click listeners
                root.setOnClickListener { onProductClick(product) }
                ivFavorite.setOnClickListener { onFavoriteClick(product) }
            }

            Glide.with(binding.root.context)
                .load(product.imageUrl)
                .placeholder(R.drawable.parfume)
                .error(R.drawable.parfume)
                .into(binding.ivProductImage)
        }
    }

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

    private class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}