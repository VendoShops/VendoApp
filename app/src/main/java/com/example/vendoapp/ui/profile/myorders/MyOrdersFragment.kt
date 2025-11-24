package com.example.vendoapp.ui.profile.myorders

import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.vendoapp.databinding.FragmentMyOrdersBinding
import com.example.vendoapp.ui.adapter.profile.morders.MyOrdersAdapter
import com.example.vendoapp.ui.base.BaseFragment
import com.example.vendoapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyOrdersFragment : BaseFragment<FragmentMyOrdersBinding>(
    FragmentMyOrdersBinding::inflate
) {

    private val myOrdersAdapter = MyOrdersAdapter()
    private val viewModel: MyOrdersViewModel by viewModels()

    override fun onViewCreateFinish() {
        setupUi()
        observes()
        binding.rvMyOrders.adapter = myOrdersAdapter

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        viewModel.fetchOrders()
    }

    private fun observes() {
        viewModel.orders.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is Resource.Idle -> {
                    binding.progressBar.isVisible = true
                }

                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    myOrdersAdapter.submitList(result.data?.content)
                }

                is Resource.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG).show()
                }
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