package com.example.vendoapp.ui.adapter.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vendoapp.R
import com.example.vendoapp.data.model.cartModel.CartItem
import com.example.vendoapp.databinding.ItemCartBinding
import com.example.vendoapp.ui.cart.CartViewModel

class CartAdapter(private val viewModel: CartViewModel) :
    ListAdapter<CartItem, CartAdapter.CartVH>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CartVH(ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: CartVH, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CartVH(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartItem) {
            binding.title.text = item.productName
            binding.sizeAndColor.text =
                listOfNotNull(item.color, item.size).joinToString("/") { it.ifEmpty { "-" } }
            binding.count.text = item.quantity.toString()
            binding.price.text = formatPrice(item.totalPrice)

            binding.oldPrice.apply {
                if (item.discountPrice != null && item.discountPrice < item.productPrice) {
                    visibility = View.VISIBLE
                    text = formatPrice(item.productPrice)
                } else visibility = View.GONE
            }

            Glide.with(itemView.context).load(item.image ?: R.drawable.test_girl)
                .into(binding.imgCart)

            binding.checkbox.setImageResource(
                if (item.isSelected()) R.drawable.checkbox_selected
                else R.drawable.checkbox_unselected
            )

            binding.plusIcon.setOnClickListener { viewModel.incrementQuantity(item.id) }
            binding.minusIcon.setOnClickListener { viewModel.decrementQuantity(item.id) }
            binding.txtRemove.setOnClickListener { viewModel.removeItem(item.id) }
            binding.checkbox.setOnClickListener { viewModel.toggleSelection(item.id) }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<CartItem>() {
            override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem) =
                oldItem == newItem
        }

        private fun formatPrice(value: Double) = "$${"%.2f".format(value)}"
    }
}
