package com.example.vendoapp.ui.adapter.checkout

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vendoapp.databinding.ItemCheckoutBinding

data class CartItem(
    val title: String,
    val variant: String,
    val currentPrice: String,
    val originalPrice: String?,
    val imageResId: Int,
    val badge: Int = 0
)

class CheckOutAdapter(
    private var items: MutableList<CartItem> = mutableListOf(),
    private val onItemClick: ((position: Int, item: CartItem) -> Unit)? = null
) : RecyclerView.Adapter<CheckOutAdapter.CheckOutViewHolder>() {

    inner class CheckOutViewHolder(
        private val binding: ItemCheckoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartItem) = with(binding) {

            productImage.setImageResource(item.imageResId)

            if (item.badge > 0) {
                badge.text = item.badge.toString()
                badge.visibility = android.view.View.VISIBLE
            } else {
                badge.visibility = android.view.View.GONE
            }

            productTitle.text = item.title
            productVariant.text = item.variant
            currentPrice.text = item.currentPrice

            if (!item.originalPrice.isNullOrEmpty()) {
                originalPrice.text = item.originalPrice
                originalPrice.visibility = android.view.View.VISIBLE
                originalPrice.paintFlags =
                    originalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                originalPrice.visibility = android.view.View.GONE
            }

            root.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION)
                    onItemClick?.invoke(pos, item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckOutViewHolder {
        val binding = ItemCheckoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CheckOutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CheckOutViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitList(newItems: List<CartItem>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }
}
