package com.example.vendoapp.ui.adapter.cart

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vendoapp.R
import com.example.vendoapp.data.model.cartModel.CartTestModel
import com.example.vendoapp.databinding.ItemCartBinding
import com.example.vendoapp.ui.cart.CartViewModel

class CartAdapter(
    private val viewModel: CartViewModel,
    private val onItemToggle: (CartTestModel) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartAdapterViewHolder>() {

    private var cartItems: List<CartTestModel> = emptyList()

    fun submitList(list: List<CartTestModel>) {
        cartItems = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapterViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapterViewHolder, position: Int) {
        holder.bind(cartItems[position], position)
    }

    override fun getItemCount(): Int = cartItems.size

    inner class CartAdapterViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartTestModel, position: Int) {
            binding.apply {
                title.text = item.title
                price.text = item.price
                oldPrice.text = item.oldPrice
                oldPrice.paintFlags = oldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                size.text = item.size

                count.text = viewModel.countMap.value?.get(position)?.toString() ?: item.count.toString()

                Glide.with(itemView.context).load(item.imageRes).into(imgCart)

                checkbox.setImageResource(
                    if (item.isSelected) R.drawable.checkbox_selected
                    else R.drawable.checkbox_unselected
                )

                checkbox.setOnClickListener {
                    onItemToggle(item)
                }

                plusIcon.setOnClickListener { viewModel.incrementCount(position) }
                minusIcon.setOnClickListener { viewModel.decrementCount(position) }
            }
        }

    }

}
