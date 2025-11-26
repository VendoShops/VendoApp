package com.example.vendoapp.ui.adapter.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.vendoapp.R
import com.example.vendoapp.data.model.cartModel.CartItem
import com.example.vendoapp.ui.cart.CartViewModel
import com.bumptech.glide.Glide

class CartAdapter(private val viewModel: CartViewModel) :
    ListAdapter<CartItem, CartAdapter.CartVH>(CartDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartVH(v)
    }

    override fun onBindViewHolder(holder: CartVH, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class CartVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val checkbox: ImageView = itemView.findViewById(R.id.checkbox)
        private val imgCart: ImageView = itemView.findViewById(R.id.imgCart)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val sizeAndColor: TextView = itemView.findViewById(R.id.sizeAndColor)
        private val txtRemove: TextView = itemView.findViewById(R.id.txtRemove)
        private val minusIcon: ImageView = itemView.findViewById(R.id.minusIcon)
        private val plusIcon: ImageView = itemView.findViewById(R.id.plusIcon)
        private val count: TextView = itemView.findViewById(R.id.count)
        private val price: TextView = itemView.findViewById(R.id.price)
        private val oldPrice: TextView = itemView.findViewById(R.id.oldPrice)

        fun bind(item: CartItem) {
            // Title / size-color
            title.text = item.productName
            val sizeColor = listOfNotNull(item.color?.trim(), item.size?.trim()).joinToString("/")
            sizeAndColor.text = if (sizeColor.isEmpty()) "-" else sizeColor

            // Quantity
            count.text = item.quantity.toString()

            // Price display: use discountPrice when available
            val effectivePrice = item.discountPrice ?: item.productPrice
            price.text = formatPrice(effectivePrice * item.quantity)

            if (item.discountPrice != null && item.discountPrice < item.productPrice) {
                oldPrice.visibility = View.VISIBLE
                oldPrice.text = formatPrice(item.productPrice)
            } else oldPrice.visibility = View.GONE

            // Image
            item.image?.let {
                Glide.with(itemView.context).load(it).into(imgCart)
            } ?: imgCart.setImageResource(R.drawable.test_girl)

            // Checkbox state
            if (item.isSelected()) {
                checkbox.setImageResource(R.drawable.checkbox_selected)
            } else {
                checkbox.setImageResource(R.drawable.checkbox_unselected)
            }

            // Click listeners
            plusIcon.setOnClickListener {
                viewModel.incrementQuantity(item.id)
            }
            minusIcon.setOnClickListener {
                viewModel.decrementQuantity(item.id)
            }
            txtRemove.setOnClickListener {
                viewModel.removeItem(item.id)
            }
            checkbox.setOnClickListener {
                viewModel.toggleSelection(item.id)
            }

            // Optional: clicking whole item could navigate to product etc.
        }
    }

    companion object {
        private val CartDiff = object : DiffUtil.ItemCallback<CartItem>() {
            override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean =
                oldItem == newItem
        }

        private fun formatPrice(value: Double): String {
            // simple formatting, localize as needed
            return "$${"%.2f".format(value)}"
        }
    }
}
