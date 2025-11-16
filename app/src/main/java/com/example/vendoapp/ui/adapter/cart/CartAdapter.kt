package com.example.vendoapp.ui.adapter.cart

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.vendoapp.data.model.cartModel.CartTestModel
import com.example.vendoapp.databinding.ItemCartBinding
import com.example.vendoapp.ui.adapter.cart.CartAdapter.CartAdapterViewHolder

class CartAdapter : Adapter<CartAdapter.CartAdapterViewHolder>() {

    private var cartItems: List<CartTestModel> = emptyList()

    fun submitList(list: List<CartTestModel>) {
        cartItems = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartAdapterViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CartAdapterViewHolder,
        position: Int
    ) {
        val item = cartItems[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    class CartAdapterViewHolder(val itemCartBinding: ItemCartBinding) :
        RecyclerView.ViewHolder(itemCartBinding.root) {

        fun bind(item: CartTestModel) {
            itemCartBinding.apply {
                title.text = item.title
                price.text = item.price
                oldPrice.text = item.oldPrice
                oldPrice.paintFlags = oldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                size.text = item.size

                Glide.with(itemView.context)
                    .load(item.imageRes)
                    .into(imgCart)

            }
        }
    }
}