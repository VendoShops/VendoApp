package com.example.vendoapp.ui.adapter.cart

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vendoapp.R
import com.example.vendoapp.data.model.cartModel.CartItem
import com.example.vendoapp.data.model.cartModel.CartTestModel
import com.example.vendoapp.databinding.ItemCartBinding
import com.example.vendoapp.ui.cart.CartViewModel

class CartAdapter(
    private val viewModel: CartViewModel
) : RecyclerView.Adapter<CartAdapter.CartAdapterViewHolder>() {

    private var cartItems: List<CartItem> = emptyList()

    fun submitList(list: List<CartItem>) {
        cartItems = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapterViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapterViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount(): Int = cartItems.size

    inner class CartAdapterViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartItem) {
            binding.apply {
                title.text = item.productName
                price.text = "$${item.productPrice}"
                oldPrice.text = item.discountPrice?.let { "$$it" } ?: ""
                oldPrice.paintFlags = oldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                sizeAndColor.text = item.size ?: "-"

                count.text = item.quantity.toString()
                Glide.with(itemView.context).load(item.image).into(imgCart)

                checkbox.setImageResource(
                    if (item.isSelected()) R.drawable.checkbox_selected
                    else R.drawable.checkbox_unselected
                )
                checkbox.setOnClickListener { viewModel.toggleSelection(item.id) }

                plusIcon.setOnClickListener { viewModel.incrementQuantity(item.id) }
                minusIcon.setOnClickListener { viewModel.decrementQuantity(item.id) }

                txtRemove.setOnClickListener { viewModel.removeItem(item.id) }
            }
        }
    }
}
