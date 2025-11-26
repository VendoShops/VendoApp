package com.example.vendoapp.ui.cart

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vendoapp.R
import com.example.vendoapp.databinding.FragmentCartBinding
import com.example.vendoapp.ui.adapter.cart.CartAdapter
import com.example.vendoapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate) {

    private val viewModel: CartViewModel by activityViewModels()
    private lateinit var adapter: CartAdapter

    override fun onViewCreateFinish() {
        setUpNavigate()
        setupRecyclerView()
        viewModel.customerId = 1
        viewModel.cartId = 1
        viewModel.fetchCartItems()
        observeCartItems()
    }

    private fun setupRecyclerView() {
        adapter = CartAdapter(viewModel)
        binding.cartRecycler.apply {
            adapter = this@CartFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observeCartItems() {
        viewModel.cartItems.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }

        viewModel.itemsCount.observe(viewLifecycleOwner) { count ->
            binding.textItemsCount.text = count.toString()
        }

        viewModel.subtotal.observe(viewLifecycleOwner) { sub ->
            binding.textSubtotal.text = formatPrice(sub)
        }

        viewModel.discount.observe(viewLifecycleOwner) { disc ->
            binding.textDiscount.text = if (disc > 0) "-${formatPrice(disc)}" else formatPrice(0.0)
        }

        viewModel.shipping.observe(viewLifecycleOwner) { ship ->
            binding.textShipping.text = formatPrice(ship)
        }

        viewModel.total.observe(viewLifecycleOwner) { tot ->
            binding.textTotal.text = formatPrice(tot)
        }
    }

    private fun formatPrice(value: Double): String {
        return "$${"%.2f".format(value)}"
    }


    private fun setUpNavigate() {
        binding.apply {
            btnCheckOut.setOnClickListener {
                findNavController().navigate(R.id.action_cartFragment_to_addNewAddressFragment)
            }
            btnBack.setOnClickListener { findNavController().navigateUp() }
        }
    }
}
    