package com.example.vendoapp.ui.cart

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vendoapp.R
import com.example.vendoapp.databinding.FragmentCartBinding
import com.example.vendoapp.ui.adapter.cart.CartAdapter
import com.example.vendoapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate) {

    private val viewModel: CartViewModel by viewModels()
    private lateinit var adapter: CartAdapter

    override fun onViewCreateFinish() {
        setupRecyclerView()
        setupObservers()
        setupNavigation()
        viewModel.fetchCartSummary()
    }

    private fun setupRecyclerView() {
        adapter = CartAdapter(viewModel)
        binding.cartRecycler.apply {
            adapter = this@CartFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupObservers() {
        viewModel.cartItems.removeObservers(viewLifecycleOwner)
        viewModel.itemsCount.removeObservers(viewLifecycleOwner)
        viewModel.subtotal.removeObservers(viewLifecycleOwner)
        viewModel.discount.removeObservers(viewLifecycleOwner)
        viewModel.shipping.removeObservers(viewLifecycleOwner)
        viewModel.total.removeObservers(viewLifecycleOwner)
        viewModel.isLoading.removeObservers(viewLifecycleOwner)

        viewModel.cartItems.observe(viewLifecycleOwner) { items ->
            if (items == null) {
                binding.cartRecycler.visibility = View.GONE
                binding.materialCardView.visibility = View.GONE
                binding.btnCheckOut.visibility = View.GONE
                return@observe
            }

            adapter.submitList(items)

            if (items.isEmpty()) {
                binding.cartRecycler.visibility = View.GONE
                binding.materialCardView.visibility = View.GONE
                binding.btnCheckOut.visibility = View.GONE
            } else {
                binding.cartRecycler.visibility = View.VISIBLE
                binding.materialCardView.visibility = View.VISIBLE
                binding.btnCheckOut.visibility = View.VISIBLE
            }
        }

        viewModel.itemsCount.observe(viewLifecycleOwner) { count ->
            binding.textItemsCount.text = count.toString()
        }
        viewModel.subtotal.observe(viewLifecycleOwner) {
            binding.textSubtotal.text = formatPrice(it)
        }
        viewModel.discount.observe(viewLifecycleOwner) {
            binding.textDiscount.text = if (it > 0) "-${formatPrice(it)}" else formatPrice(0.0)
        }
        viewModel.shipping.observe(viewLifecycleOwner) {
            binding.textShipping.text = formatPrice(it)
        }
        viewModel.total.observe(viewLifecycleOwner) { binding.textTotal.text = formatPrice(it) }
    }

    private fun setupNavigation() {
        binding.btnCheckOut.setOnClickListener {
            if ((viewModel.itemsCount.value ?: 0) > 0)
                findNavController().navigate(R.id.action_cartFragment_to_addNewAddressFragment)
        }
        binding.btnBack.setOnClickListener { findNavController().navigateUp() }
    }

    private fun formatPrice(value: Double) = "$${"%.2f".format(value)}"
}
