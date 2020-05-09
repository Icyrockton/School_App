package com.icyrockton.school_app.fragment.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProfileViewModel (private val repository: ProfileRepository): ViewModel() {
    private var _profileInfo=MutableLiveData<ProfileInfo>()
    val profileInfo=_profileInfo
    private var _guardianInfo=MutableLiveData<GuardianInfo>()
    val guardianInfo=_guardianInfo
    init {
        viewModelScope.launch {
            _profileInfo.postValue(repository.getProfileInfo())
            _guardianInfo.postValue(repository.getGuardianInfo())
        }
    }
}