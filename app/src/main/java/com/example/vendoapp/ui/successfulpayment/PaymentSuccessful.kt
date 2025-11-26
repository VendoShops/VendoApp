package com.example.vendoapp.ui.successfulpayment

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.findNavController
import com.example.vendoapp.R
import com.example.vendoapp.databinding.FragmentPaymentSuccesfulBinding
import com.example.vendoapp.ui.base.BaseFragment

class PaymentSuccessful : BaseFragment<FragmentPaymentSuccesfulBinding>(
    FragmentPaymentSuccesfulBinding::inflate
) {
    override fun onViewCreateFinish() {
        setupUi()
        setUpListeners()
    }

    private fun setUpListeners(){
        binding.apply {
            homePage.setOnClickListener {
                findNavController().navigate(R.id.action_paymentSuccessful_to_homeFragment)
            }
        }
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