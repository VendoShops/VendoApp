package com.example.vendoapp.ui.profile.myreturns

import android.net.Uri
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.vendoapp.R
import com.example.vendoapp.data.model.profile.myreturns.MyReturnsPhotosModel
import com.example.vendoapp.databinding.FragmentMyReturnsBinding
import com.example.vendoapp.ui.adapter.profile.myreturns.MyReturnsPhotosAdapter
import com.example.vendoapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyReturnsFragment : BaseFragment<FragmentMyReturnsBinding>(
    FragmentMyReturnsBinding::inflate
) {

    private val myReturnsPhotosAdapter by lazy { MyReturnsPhotosAdapter(requireContext()) }

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            myReturnsPhotosAdapter.addPhoto(MyReturnsPhotosModel(it))
            binding.rvMyReturnsPhotosRecycler.isVisible = true
        }
    }


    override fun onViewCreateFinish() {
        setupUi()
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.tvAddPhoto.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        val nedenler = listOf(
            getString(R.string.reason_damaged),
            getString(R.string.reason_no_need),
            getString(R.string.reason_bad_condition),
            getString(R.string.reason_duplicate)

        )

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, nedenler)
        binding.iadeNedeniDropdown.setAdapter(adapter)

        binding.rvMyReturnsPhotosRecycler.adapter = myReturnsPhotosAdapter
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