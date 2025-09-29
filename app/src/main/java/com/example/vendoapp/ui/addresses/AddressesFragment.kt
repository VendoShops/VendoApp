package com.example.vendoapp.ui.addresses

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.findNavController
import com.example.vendoapp.ui.adapter.AddressesAdapter
import com.example.vendoapp.ui.base.BaseFragment
import com.example.vendoapp.databinding.FragmentAddressesBinding
import com.example.vendoapp.model.AddressesModelTest

class AddressesFragment : BaseFragment<FragmentAddressesBinding>(
    FragmentAddressesBinding::inflate
) {

    private val addressesAdapter = AddressesAdapter()

    override fun onViewCreateFinish() {
        setupUi()
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.rvAddresses.adapter = addressesAdapter

        val addresses = ArrayList<AddressesModelTest>()
        addresses.add(AddressesModelTest("Home", "8530 King Farhad dsdfdfdgdshasgfdjhhsdgshsdgdshgshdshsdf"))
        addresses.add(AddressesModelTest("Work", "8530 King Farhad dsdfdfdgdshasgfdjhhsdgshsdgdshgshdshsdf"))

        addressesAdapter.updateList(addresses)

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