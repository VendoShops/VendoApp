package com.example.vendoapp.ui.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.vendoapp.databinding.ItemBrandBinding
import com.example.vendoapp.model.home.Brand

class BrandAdapter(
    private val onBrandClick: (Brand) -> Unit
) : ListAdapter<Brand, BrandAdapter.BrandViewHolder>(BrandDiffCallback()) {

    inner class BrandViewHolder(private val binding: ItemBrandBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(brand: Brand) {
            binding.ivBrandLogo.setImageResource(brand.logoRes)
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

    private class BrandDiffCallback : DiffUtil.ItemCallback<Brand>() {
        override fun areItemsTheSame(oldItem: Brand, newItem: Brand): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Brand, newItem: Brand): Boolean {
            return oldItem == newItem
        }
    }
}