package com.example.vendoapp.ui.addcart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vendoapp.data.model.home.ProductColorItem
import com.example.vendoapp.databinding.ItemProductColorBinding

class ProductColorAdapter(
    private var colors: List<ProductColorItem>,
    private val onColorSelected: (ProductColorItem) -> Unit,
) : RecyclerView.Adapter<ProductColorAdapter.ColorViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION // Start with no selection

    @SuppressLint("NotifyDataSetChanged")
    fun updateColors(newColors: List<ProductColorItem>) {
        colors = newColors
        selectedPosition = RecyclerView.NO_POSITION // Reset selection when colors update
        notifyDataSetChanged()
    }

    fun updateSelection(colorName: String?) {
        if (colorName.isNullOrEmpty()) {
            // Clear selection
            val oldPosition = selectedPosition
            selectedPosition = RecyclerView.NO_POSITION
            if (oldPosition != RecyclerView.NO_POSITION) {
                notifyItemChanged(oldPosition)
            }
        } else {
            // Find and select new color
            val newPosition = colors.indexOfFirst { it.colorName == colorName }
            if (newPosition != -1 && newPosition != selectedPosition) {
                val oldPosition = selectedPosition
                selectedPosition = newPosition
                if (oldPosition != RecyclerView.NO_POSITION) {
                    notifyItemChanged(oldPosition)
                }
                notifyItemChanged(selectedPosition)
            }
        }
    }

    inner class ColorViewHolder(private val binding: ItemProductColorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(colorItem: ProductColorItem, isSelected: Boolean) {
            // Load color image with Glide
            Glide.with(binding.root.context)
                .load(colorItem.imageUrl)
                .placeholder(android.R.color.darker_gray) // Placeholder rəng
                .into(binding.ivColorImage)

            // Show/hide selection border
            binding.viewSelectionBorder.visibility = if (isSelected) View.VISIBLE else View.GONE

            // Click listener
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onColorSelected(colorItem)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val binding = ItemProductColorBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ColorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(colors[position], position == selectedPosition)
    }

    override fun getItemCount(): Int = colors.size
}
