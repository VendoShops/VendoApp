package com.example.vendoapp.ui.paymentmethod

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.findNavController
import com.example.vendoapp.R
import com.example.vendoapp.ui.adapter.PaymentMethodAdapter
import com.example.vendoapp.ui.base.BaseFragment
import com.example.vendoapp.databinding.FragmentPaymentMethodBinding
import com.example.vendoapp.data.model.payment.PaymentMethodModelTest

class PaymentMethodFragment : BaseFragment<FragmentPaymentMethodBinding>(
FragmentPaymentMethodBinding::inflate
) {

    private val paymentMethodAdapter = PaymentMethodAdapter()

    override fun onViewCreateFinish() {
        setupUi()
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.rvPaymentMethods.adapter = paymentMethodAdapter

        val items = ArrayList<PaymentMethodModelTest>()
        items.add(PaymentMethodModelTest(R.drawable.mastercard, "**************2109"))

        paymentMethodAdapter.updateList(items)
    }

    private fun setupUi() {
        binding.let {
            ViewCompat.setOnApplyWindowInsetsListener(it.main) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(0, systemBars.top, 0, systemBars.bottom)
                insets
            }
        }
    }
}