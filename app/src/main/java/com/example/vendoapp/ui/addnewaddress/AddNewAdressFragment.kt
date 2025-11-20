package com.example.vendoapp.ui.addnewaddress

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vendoapp.R
import com.example.vendoapp.databinding.FragmentAddNewAdressBinding
import com.example.vendoapp.ui.adapter.addaddress.AddAddressAdapter
import com.example.vendoapp.ui.adapter.addaddress.AddressItem
import com.example.vendoapp.ui.base.BaseFragment


class AddNewAddressFragment : BaseFragment<FragmentAddNewAdressBinding>(
    FragmentAddNewAdressBinding::inflate
) {

    private lateinit var adapter: AddAddressAdapter


    override fun onViewCreateFinish() {
        setupRecyclerView()
        loadFakeData()
        setUpButtons()
    }

    private fun setupRecyclerView() {
        adapter = AddAddressAdapter(
            onItemClick = { item, position ->
                // click event
            },
            onRemoveClick = { item, position ->
                adapter.removeAt(position)
            }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = this@AddNewAddressFragment.adapter
        }
    }

    private fun loadFakeData() {
        val fakeAddresses = listOf(
            AddressItem(
                id = "1",
                title = "Home",
                address = "28 May küçəsi 12, Mənzil 45\nBakı, Azərbaycan",
                iconRes = R.drawable.add_address
            ),
            AddressItem(
                id = "2",
                title = "Work",
                address = "Nəsimi rayonu, Azadlıq prospekti 89\nBakı, Azərbaycan",
                iconRes = R.drawable.add_address
            ),
            AddressItem(
                id = "3",
                title = "Parents House",
                address = "Yasamal rayonu, S.Vurğun küçəsi 23\nBakı, Azərbaycan",
                iconRes = R.drawable.add_address
            )
        )

        adapter.submitList(fakeAddresses)
    }

    private fun setUpButtons() {
        binding.apply {
            icBack.setOnClickListener {
                findNavController().navigateUp()
            }
            btnSaveAdress.setOnClickListener {
                findNavController().navigate(R.id.paymentSuccessful)
            }
        }
    }
}