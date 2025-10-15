package com.example.vendoapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.vendoapp.databinding.ItemPaymentMethodsBinding
import com.example.vendoapp.data.model.payment.PaymentMethodModelTest

class PaymentMethodAdapter : Adapter<PaymentMethodAdapter.PaymentMethodViewHolder>() {

    private val paymentMethods = ArrayList<PaymentMethodModelTest>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: ArrayList<PaymentMethodModelTest>) {
        paymentMethods.clear()
        paymentMethods.addAll(newList)
        notifyDataSetChanged()
    }

    class PaymentMethodViewHolder(val itemPaymentMethodsBinding: ItemPaymentMethodsBinding) :
        ViewHolder(itemPaymentMethodsBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentMethodViewHolder {
        val view = ItemPaymentMethodsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PaymentMethodViewHolder(view)
    }

    override fun getItemCount(): Int {
        return paymentMethods.size
    }

    override fun onBindViewHolder(holder: PaymentMethodViewHolder, position: Int) {
        val itemPaymentMethod = paymentMethods[position]

        holder.itemPaymentMethodsBinding.ivCard.setImageResource(itemPaymentMethod.ivCard)
        holder.itemPaymentMethodsBinding.tvCardNumber.text = itemPaymentMethod.cardNumBer
    }
}