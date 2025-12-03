package com.example.vendoapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vendoapp.data.model.profile.ProfileModel
import com.example.vendoapp.data.model.profile.UpdateAvatarModelX
import com.example.vendoapp.domain.usecase.ProfileUseCase
import com.example.vendoapp.domain.usecase.UpdateAvatarUseCase
import com.example.vendoapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private val updateAvatarUseCase: UpdateAvatarUseCase
) : ViewModel() {

    private val _profile = MutableLiveData<Resource<ProfileModel>>()
    val profile: LiveData<Resource<ProfileModel>> = _profile

    private val _avatar = MutableLiveData<Resource<UpdateAvatarModelX>>()
    val avatar: LiveData<Resource<UpdateAvatarModelX>> = _avatar

    fun loadUserProfile(userId: Int) {
        viewModelScope.launch {
            _profile.value = Resource.Loading()
            _profile.value = profileUseCase(userId)
        }
    }

    fun updateAvatar(userId: Int, fileBytes: ByteArray, mimeType: String) = viewModelScope.launch {
        val requestFile = fileBytes.toRequestBody(mimeType.toMediaType())
        val multipart = MultipartBody.Part.createFormData(
            name = "avatar",
            filename = "avatar.${mimeType.substringAfter("/")}",
            body = requestFile
        )
        _avatar.value = Resource.Loading()
        _avatar.value = updateAvatarUseCase(userId, multipart)
    }

}