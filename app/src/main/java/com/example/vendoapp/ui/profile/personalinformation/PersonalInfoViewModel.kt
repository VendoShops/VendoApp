package com.example.vendoapp.ui.profile.personalinformation

import androidx.lifecycle.ViewModel
import com.example.vendoapp.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonalInfoViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel(){

}