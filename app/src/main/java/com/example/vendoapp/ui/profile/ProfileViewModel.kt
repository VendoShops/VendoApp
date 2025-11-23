package com.example.vendoapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vendoapp.data.model.profile.ProfileModel
import com.example.vendoapp.domain.usecase.ProfileUseCase
import com.example.vendoapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {

    private val _profile = MutableLiveData<Resource<ProfileModel>>()
    val profile: LiveData<Resource<ProfileModel>> = _profile

    fun loadUserProfile(userId: Int) {
        viewModelScope.launch {
            _profile.value = Resource.Loading()
            _profile.value = profileUseCase(userId)
        }
    }
}