package com.example.vendoapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB: ViewBinding> (
    private val bindingInflate: (inflater: LayoutInflater) -> VB
): Fragment() {

    private var _binding : VB? = null
    val binding: VB get() = _binding as VB

    protected abstract fun onViewCreateFinish()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflate.invoke(layoutInflater)

        if (_binding == null) {
            throw IllegalArgumentException("binding is null")
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreateFinish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}