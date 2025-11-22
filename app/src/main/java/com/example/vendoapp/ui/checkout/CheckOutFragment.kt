package com.example.vendoapp.ui.checkout

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vendoapp.R
import com.example.vendoapp.databinding.FragmentCheckOutBinding
import com.example.vendoapp.ui.adapter.checkout.CartItem
import com.example.vendoapp.ui.adapter.checkout.CheckOutAdapter
import com.example.vendoapp.ui.base.BaseFragment

class CheckOutFragment : BaseFragment<FragmentCheckOutBinding>(
    FragmentCheckOutBinding::inflate
) {

    private lateinit var adapter: CheckOutAdapter

    override fun onViewCreateFinish() {
        setUpButtons()
        setupRecycler()
        loadDemoData()
    }

    private fun setupRecycler() {
        adapter = CheckOutAdapter(mutableListOf(), onItemClick = { position, item ->
            Toast.makeText(requireContext(), "Clicked: ${item.title}", Toast.LENGTH_SHORT).show()
        })

        binding.orderRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.orderRecycler.adapter = adapter
    }

    private fun loadDemoData() {
        val demoList = listOf(
            CartItem(
                title = "Women manfi fashion fashinon fashion fash...",
                variant = "Brown/L",
                currentPrice = "$9",
                originalPrice = "$12",
                imageResId = com.example.vendoapp.R.drawable.fake_girl,
                badge = 1
            ), CartItem(
                title = "Women manfi fashion fashinon fashion fash...",
                variant = "Brown/L",
                currentPrice = "$9",
                originalPrice = "$12",
                imageResId = com.example.vendoapp.R.drawable.fake_girl,
                badge = 1
            ), CartItem(
                title = "Women manfi fashion fashinon fashion fash...",
                variant = "Brown/L",
                currentPrice = "$9",
                originalPrice = "$12",
                imageResId = com.example.vendoapp.R.drawable.fake_girl,
                badge = 1
            ), CartItem(
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
}
