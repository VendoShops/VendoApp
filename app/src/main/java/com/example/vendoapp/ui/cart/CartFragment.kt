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
class CartFragment : BaseFragment<FragmentCartBinding>(
    FragmentCartBinding::inflate
) {
    private val viewModel: CartViewModel by activityViewModels()
    private lateinit var adapter: CartAdapter

    override fun onViewCreateFinish() {
        setUpNavigate()
        setupRecyclerView()
        observeCartItems()
    }

    private fun setupRecyclerView() {
        adapter = CartAdapter(viewModel) { item ->
            viewModel.toggleSelection(item.id)
        }

        binding.cartRecycler.apply {
            adapter = this@CartFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }


    private fun observeCartItems() {
        viewModel.cartItems.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
        viewModel.countMap.observe(viewLifecycleOwner) { count ->
            adapter.notifyDataSetChanged()
        }
    }

    private fun setUpNavigate() {
        binding.apply {
            btnCheckOut.setOnClickListener {
                findNavController().navigate(R.id.action_cartFragment_to_addNewAddressFragment)
            }
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }
}
