package com.example.vendoapp.ui.adapter.profile.morders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.vendoapp.R
import com.example.vendoapp.databinding.ItemMyOrdersBinding

class MyOrdersAdapter : ListAdapter<String, MyOrdersAdapter.MyOrderViewHolder>(DiffCallback) {

    class MyOrderViewHolder(val binding: ItemMyOrdersBinding) : RecyclerView.ViewHolder(binding.root)

    object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOrderViewHolder {
        val binding = ItemMyOrdersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyOrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyOrderViewHolder, position: Int) {
        val item = getItem(position)

        // Test meqsedli yazilib backendi
        // Imageviewlar duzelmelidi
        holder.binding.apply {
            tvOrderDate.text = item
            tvOrderPrice.text = "N/A"
            ivProduct1.setImageResource(R.drawable.testorder)
            ivProduct2.setImageResource(R.drawable.testorder)
            ivProduct3.setImageResource(R.drawable.testorder)
        }
    }

}