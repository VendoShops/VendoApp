package com.example.vendoapp.ui.addcart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vendoapp.R
import com.example.vendoapp.databinding.ItemProductImageBinding

class ProductImageAdapter(
    private var images: List<String>,
    private val onImageClick: (Int) -> Unit,
) : RecyclerView.Adapter<ProductImageAdapter.ImageViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateImages(newImages: List<String>) {
        images = newImages
        notifyDataSetChanged()
    }

    inner class ImageViewHolder(private val binding: ItemProductImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String, position: Int) {
            // Load image with Glide
            Glide.with(binding.root.context)
                .load(imageUrl)
                .placeholder(R.drawable.example_girl) // Placeholder
                .into(binding.ivProductImage)

            binding.root.setOnClickListener {
                onImageClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemProductImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position], position)
    }

    override fun getItemCount(): Int = images.size
}
