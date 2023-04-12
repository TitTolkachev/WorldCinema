package com.example.worldcinema.ui.screen.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcinema.domain.model.Token
import com.example.worldcinema.domain.usecase.network.GetUserProfileUseCase
import com.example.worldcinema.domain.usecase.network.SaveUserAvatarUseCase
import com.example.worldcinema.domain.usecase.storage.SaveTokenToLocalStorageUseCase
import com.example.worldcinema.ui.model.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val saveTokenToLocalStorageUseCase: SaveTokenToLocalStorageUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val saveUserAvatarUseCase: SaveUserAvatarUseCase
) : ViewModel() {

    private val _shouldExit: MutableLiveData<Boolean> =
        MutableLiveData(false)
    val shouldExit: LiveData<Boolean> = _shouldExit


    private val _userProfile: MutableLiveData<UserProfile> =
        MutableLiveData()
    val userProfile: LiveData<UserProfile> = _userProfile

    init {
        loadProfileData()
    }

    private fun loadProfileData() {
        viewModelScope.launch(Dispatchers.IO) {
            getUserProfileUseCase.execute().collect { result ->
                result.onSuccess {
                    _userProfile.postValue(
                        UserProfile(
                            it.userId,
                            it.firstName,
                            it.lastName,
                            it.email,
                            it.avatar
                        )
                    )
                }.onFailure {
                    // TODO(Показать ошибку)
                }
            }
        }
    }

    fun onExitBtnClick() {
        saveTokenToLocalStorageUseCase.execute(Token("", ""))
        _shouldExit.value = true
    }

    fun onExited() {
        _shouldExit.value = false
    }
}