package com.example.vendoapp.ui.profile.myorders

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.findNavController
import com.example.vendoapp.R
import com.example.vendoapp.data.model.myorderstestmodel.MyOrdersTestModel
import com.example.vendoapp.databinding.FragmentMyOrdersBinding
import com.example.vendoapp.ui.adapter.MyOrdersAdapter
import com.example.vendoapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyOrdersFragment : BaseFragment<FragmentMyOrdersBinding>(
    FragmentMyOrdersBinding::inflate
) {

    private val myOrdersAdapter = MyOrdersAdapter()

    override fun onViewCreateFinish() {
        setupUi()
        binding.rvMyOrders.adapter = myOrdersAdapter

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        val testModel = ArrayList<MyOrdersTestModel>()

        testModel.add(MyOrdersTestModel("13 October 2025", "123.12$", R.drawable.testorder))
        testModel.add(MyOrdersTestModel("5 January 2025", "13.12$", R.drawable.testorder))
        testModel.add(MyOrdersTestModel("28 February 2025", "12.10$", R.drawable.testorder))
        testModel.add(MyOrdersTestModel("17 October 2025", "14.23$", R.drawable.testorder))
        testModel.add(MyOrdersTestModel("9 May 2025", "18.19$", R.drawable.testorder))
        testModel.add(MyOrdersTestModel("7 April 2025", "183.13$", R.drawable.testorder))
        testModel.add(MyOrdersTestModel("3 December 2025", "145.12$", R.drawable.testorder))
        testModel.add(MyOrdersTestModel("21 November 2025", "187.12$", R.drawable.testorder))

        myOrdersAdapter.updateList(testModel)
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