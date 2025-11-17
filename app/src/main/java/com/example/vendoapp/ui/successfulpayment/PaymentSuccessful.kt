package com.example.vendoapp.ui.successfulpayment

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vendoapp.databinding.FragmentPaymentSuccesfulBinding
import com.example.vendoapp.ui.base.BaseFragment

class PaymentSuccessful : BaseFragment<FragmentPaymentSuccesfulBinding>(
    FragmentPaymentSuccesfulBinding::inflate
) {
    override fun onViewCreateFinish() {
        setupUi()
    }


    private fun setupUi() {
        binding.let {
            ViewCompat.setOnApplyWindowInsetsListener(it.root) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(14, systemBars.top, 14, systemBars.bottom)
                insets
            }
        }
    }
}