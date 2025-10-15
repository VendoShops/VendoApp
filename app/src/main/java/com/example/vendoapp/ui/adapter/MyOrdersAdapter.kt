package com.example.vendoapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.vendoapp.R
import com.example.vendoapp.data.model.myorderstestmodel.MyOrdersTestModel
import com.example.vendoapp.databinding.ItemMyOrdersBinding

class MyOrdersAdapter : Adapter<MyOrdersAdapter.MyOrderViewHolder>() {

    private val myOrders = arrayListOf<MyOrdersTestModel>()

    class MyOrderViewHolder(val itemMyOrdersBinding: ItemMyOrdersBinding) : ViewHolder(itemMyOrdersBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOrderViewHolder {
        val view = ItemMyOrdersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyOrderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return myOrders.size
    }

    override fun onBindViewHolder(holder: MyOrderViewHolder, position: Int) {
        val myOrder = myOrders[position]

        holder.itemMyOrdersBinding.tvOrderPrice.text= myOrder.price
        holder.itemMyOrdersBinding.tvOrderDate.text= myOrder.date
        holder.itemMyOrdersBinding.ivProduct1.setImageResource(R.drawable.testorder)
        holder.itemMyOrdersBinding.ivProduct2.setImageResource(R.drawable.testorder)
        holder.itemMyOrdersBinding.ivProduct3.setImageResource(R.drawable.testorder)
    }

    fun updateList(newList: ArrayList<MyOrdersTestModel>) {
        myOrders.clear()
        myOrders.addAll(newList)
        notifyDataSetChanged()
    }
}