package com.example.vendoapp.ui.cart

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vendoapp.R
import com.example.vendoapp.data.model.cartModel.CartTestModel
import com.example.vendoapp.databinding.FragmentCartBinding
import com.example.vendoapp.ui.adapter.cart.CartAdapter
import com.example.vendoapp.ui.base.BaseFragment

class CartFragment : BaseFragment<FragmentCartBinding>(
    FragmentCartBinding::inflate
) {

    private lateinit var adapter: CartAdapter

    override fun onViewCreateFinish() {
        setupUi()
        setUpNavigate()
        setupRecyclerView()
        loadTestData()
    }

    private fun setupRecyclerView() {
        adapter = CartAdapter()
        binding.cartRecycler.apply {
            adapter = this@CartFragment.adapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun loadTestData() {
        val testList = listOf(
            CartTestModel(
                title = "Nike Air Max",
                price = "$120",
                oldPrice = "$150",
                imageRes = R.drawable.test_girl,
                size = "US 10",
                isFavorite = true
            ),
            CartTestModel(
                title = "Adidas Superstar",
                price = "$85",
                oldPrice = "$110",
                imageRes = R.drawable.test_girl,
                size = "US 9",
                isFavorite = false
            ),
            CartTestModel(
                title = "Puma RS-X",
                price = "$95",
                oldPrice = "$130",
                imageRes = R.drawable.test_girl,
                size = "US 11",
                isFavorite = true
            )
        )

        adapter.submitList(testList)
    }

    private fun setupUi() {
        binding.let {
            ViewCompat.setOnApplyWindowInsetsListener(it.main) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(0, systemBars.top, 0, 0)
                insets
            }
        }
    }

    private fun setUpNavigate() {
        binding.apply {
            btnCheckOut.setOnClickListener {
                // CheckOut Fragment navigate
//                findNavController().navigate(R.id.action_cartFragment_to_checkOutFragment)
            }
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }

    }
}

