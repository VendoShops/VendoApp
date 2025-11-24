package com.example.vendoapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vendoapp.R
import com.example.vendoapp.databinding.BottomSheetScannerBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScannerBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetScannerBinding? = null
    private val binding get() = _binding!!

    // Callback interface for handling option clicks
    interface ScannerOptionListener {
        fun onTakePhotoClicked()
        fun onChooseFromAlbumClicked()
        fun onSearchHistoryClicked()
    }

    private var listener: ScannerOptionListener? = null

    fun setListener(listener: ScannerOptionListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = BottomSheetScannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Take a photo
        binding.layoutTakePhoto.setOnClickListener {
            listener?.onTakePhotoClicked()
            dismiss()
        }

        // Choose from album
        binding.layoutChooseFromAlbum.setOnClickListener {
            listener?.onChooseFromAlbumClicked()
            dismiss()
        }

        // Search history
        binding.layoutSearchHistory.setOnClickListener {
            listener?.onSearchHistoryClicked()
            dismiss()
        }

        // Cancel button
        binding.tvCancel.setOnClickListener {
            dismiss()
        }

        // Close encryption notice
        binding.ivCloseNotice.setOnClickListener {
            // Hide the encryption notice
            binding.root.findViewById<View>(R.id.ivCloseNotice)?.parent?.let { parent ->
                (parent as? View)?.visibility = View.GONE
            }
        }
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "ScannerBottomSheet"

        fun newInstance(): ScannerBottomSheetFragment {
            return ScannerBottomSheetFragment()
        }
    }
}