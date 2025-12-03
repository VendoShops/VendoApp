package com.example.vendoapp.ui.profile.personalinformation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vendoapp.data.model.profile.ProfileModel
import com.example.vendoapp.data.model.profile.personalinformation.UpdateProfileRequest
import com.example.vendoapp.domain.repository.AuthRepository
import com.example.vendoapp.domain.usecase.UpdateProfileUseCase
import com.example.vendoapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalInfoViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {

    private val _updateState = MutableLiveData<Resource<ProfileModel>>()
    val updateState: LiveData<Resource<ProfileModel>> = _updateState

    fun updateProfile(userId: Int, fullName: String, avatarUrl: String?) {
        viewModelScope.launch {
            _updateState.value = Resource.Loading()
            val request = UpdateProfileRequest(
                avatarUrl = avatarUrl,
                avatar = null,
                fullName = fullName,
                termsAccepted = true
            )
            val result = updateProfileUseCase(userId, request)
            _updateState.value = result
        }
    }
}