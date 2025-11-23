package com.example.vendoapp.ui.adapter.like

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vendoapp.R
import com.example.vendoapp.data.model.home.Product
import com.example.vendoapp.databinding.ItemProductBinding

class LikeAdapter(
    private val onProductClick: (Product) -> Unit,
    private val onFavoriteClick: (Product) -> Unit
) : ListAdapter<Product, LikeAdapter.LikeViewHolder>(LikeDiffCallback()) {

    inner class LikeViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                tvProductTitle.text = product.productName ?: "Unknown Product"
                tvPrice.text = "$${"%.2f".format(product.price ?: 0.0)}"
                tvRating.text = product.rating?.toString() ?: "0.0"

                ivFavorite.setImageResource(
                    if (product.isFavorite) R.drawable.like_dark
                    else R.drawable.like
                )

                llColorIndicators.visibility = View.GONE

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LikeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LikeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class LikeDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
    }
}
