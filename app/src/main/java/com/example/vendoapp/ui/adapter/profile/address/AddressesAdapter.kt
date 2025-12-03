package com.example.vendoapp.ui.adapter.profile.address

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.vendoapp.databinding.ItemAddressesBinding
import com.example.vendoapp.data.model.address.AddressesModelTest

class AddressesAdapter : Adapter<AddressesAdapter.AddressesViewHolder>() {

    private val addresses = ArrayList<AddressesModelTest>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: ArrayList<AddressesModelTest>) {
        addresses.clear()
        addresses.addAll(newList)
        notifyDataSetChanged()
    }

    class AddressesViewHolder(val itemAddressesBinding: ItemAddressesBinding) :
        ViewHolder(itemAddressesBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressesViewHolder {
        val view = ItemAddressesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddressesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return addresses.size
    }

    override fun onBindViewHolder(holder: AddressesViewHolder, position: Int) {
        val itemAddress = addresses[position]

        holder.itemAddressesBinding.tvLocation.text = itemAddress.location
        holder.itemAddressesBinding.tvLocationName.text = itemAddress.locationName
    }
}