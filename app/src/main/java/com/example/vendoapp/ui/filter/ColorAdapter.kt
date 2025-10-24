package com.example.vendoapp.ui.filter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vendoapp.databinding.ItemColorBinding
import com.example.vendoapp.data.model.filter.ColorItem

class ColorAdapter(
    private var colors: List<ColorItem>,
    private val onColorSelected: (ColorItem) -> Unit,
) : RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateColors(newColors: List<ColorItem>) {
        this.colors = newColors
        notifyDataSetChanged()
    }

    inner class ColorViewHolder(private val binding: ItemColorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("UseKtx")
        fun bind(colorItem: ColorItem) {
            // Create circular background with color
            val drawable = GradientDrawable().apply {
                shape = GradientDrawable.OVAL

                setColor(Color.parseColor(colorItem.colorHex))

                setStroke(
                    if (colorItem.isSelected) 6 else 2,
                    if (colorItem.isSelected) Color.BLACK else Color.GRAY
                )
            }

            binding.colorCircle.background = drawable

            // Show/hide check mark based on selection
            binding.checkMark.visibility = if (colorItem.isSelected) View.VISIBLE else View.GONE

            binding.colorName.text = colorItem.name

            binding.root.setOnClickListener {
                colorItem.isSelected = !colorItem.isSelected
                onColorSelected(colorItem)
                notifyItemChanged(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val binding = ItemColorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ColorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(colors[position])
    }

    override fun getItemCount(): Int = colors.size
}