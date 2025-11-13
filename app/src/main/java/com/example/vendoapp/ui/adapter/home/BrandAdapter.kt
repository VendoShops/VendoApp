package com.example.vendoapp.ui.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vendoapp.R
import com.example.vendoapp.data.model.home.BrandResponse
import com.example.vendoapp.databinding.ItemBrandBinding

class BrandAdapter(
    private val onBrandClick: (BrandResponse) -> Unit,
) : ListAdapter<BrandResponse, BrandAdapter.BrandViewHolder>(BrandDiffCallback()) {

    inner class BrandViewHolder(private val binding: ItemBrandBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(brand: BrandResponse) {
            Glide.with(binding.root.context)
                .load(brand.logoUrl)
                .placeholder(R.drawable.ic_launcher_background) // Default placeholder
                .error(R.drawable.ic_launcher_foreground) // Error placeholder
                .into(binding.ivBrandLogo)

            binding.root.setOnClickListener {
                onBrandClick(brand)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        val binding = ItemBrandBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BrandViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class BrandDiffCallback : DiffUtil.ItemCallback<BrandResponse>() {
        override fun areItemsTheSame(oldItem: BrandResponse, newItem: BrandResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BrandResponse, newItem: BrandResponse): Boolean {
            return oldItem == newItem
        }
    }
}