package com.example.vendoapp.ui.adapter.addaddress

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vendoapp.R
import com.example.vendoapp.databinding.ItemAddAddressBinding
import com.example.vendoapp.databinding.ItemAddressesBinding

data class AddressItem(
    val id: String,
    val title: String,
    val address: String,
    val iconRes: Int = R.drawable.add_address
)

class AddAddressAdapter(
    private var items: MutableList<AddressItem> = mutableListOf(),
    private val onItemClick: ((AddressItem, Int) -> Unit)? = null,
    private val onRemoveClick: ((AddressItem, Int) -> Unit)? = null
) : RecyclerView.Adapter<AddAddressAdapter.AddressViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val binding = ItemAddAddressBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, position)
    }

    override fun getItemCount(): Int = items.size

    fun submitList(newList: List<AddressItem>) {
        items = newList.toMutableList()
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        if (position in 0 until items.size) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun add(item: AddressItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    inner class AddressViewHolder(
        private val binding: ItemAddAddressBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AddressItem, position: Int) {
            binding.apply {
                tvTitle.text = item.title
                tvAddress.text = item.address

                root.setOnClickListener {
                    onItemClick?.invoke(item, position)
                }

                tvRemove.setOnClickListener {
                    onRemoveClick?.invoke(item, position)
                }
            }
        }
    }
}