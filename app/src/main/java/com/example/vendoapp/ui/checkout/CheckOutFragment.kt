package com.example.vendoapp.ui.checkout

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vendoapp.R
import com.example.vendoapp.data.model.cartModel.CartItems
import com.example.vendoapp.databinding.FragmentCheckOutBinding
import com.example.vendoapp.ui.adapter.checkout.CheckOutAdapter
import com.example.vendoapp.ui.base.BaseFragment

class CheckOutFragment : BaseFragment<FragmentCheckOutBinding>(
    FragmentCheckOutBinding::inflate
) {

    private lateinit var adapter: CheckOutAdapter

    override fun onViewCreateFinish() {
        setupUi()
        setUpButtons()
        setupRecycler()
        loadDemoData()
    }

    private fun setupRecycler() {
        adapter = CheckOutAdapter(mutableListOf(), onItemClick = { position, item ->
        })

        binding.orderRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.orderRecycler.adapter = adapter
    }

    private fun loadDemoData() {
        val demoList = listOf(
            CartItems(
                title = "Women manfi fashion fashinon fashion fash...",
                variant = "Brown/L",
                currentPrice = "$9",
                originalPrice = "$12",
                imageResId = R.drawable.fake_girl,
                badge = 1
            ), CartItems(
                title = "Women manfi fashion fashinon fashion fash...",
                variant = "Brown/L",
                currentPrice = "$9",
                originalPrice = "$12",
                imageResId = com.example.vendoapp.R.drawable.fake_girl,
                badge = 1
            ), CartItems(
                title = "Women manfi fashion fashinon fashion fash...",
                variant = "Brown/L",
                currentPrice = "$9",
                originalPrice = "$12",
                imageResId = com.example.vendoapp.R.drawable.fake_girl,
                badge = 1
            ), CartItems(
                title = "Women manfi fashion fashinon fashion fash...",
                variant = "Brown/L",
                currentPrice = "$9",
                originalPrice = "$12",
                imageResId = com.example.vendoapp.R.drawable.fake_girl,
                badge = 1
            )
        )

        adapter.submitList(demoList)
    }

    private fun setUpButtons() {
        binding.apply {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnCheckOut.setOnClickListener {
                findNavController().navigate(R.id.action_checkOutFragment_to_paymentSuccessful)
            }
        }
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
