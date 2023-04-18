package com.example.worldcinema.ui.screen.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcinema.domain.model.Token
import com.example.worldcinema.domain.usecase.network.GetUserProfileUseCase
import com.example.worldcinema.domain.usecase.storage.SaveFavouritesCollectionIdUseCase
import com.example.worldcinema.domain.usecase.storage.SaveTokenToLocalStorageUseCase
import com.example.worldcinema.ui.dialog.AlertType
import com.example.worldcinema.ui.model.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val saveTokenToLocalStorageUseCase: SaveTokenToLocalStorageUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val saveFavouritesCollectionIdUseCase: SaveFavouritesCollectionIdUseCase
) : ViewModel() {

    private val _shouldExit: MutableLiveData<Boolean> =
        MutableLiveData(false)
    val shouldExit: LiveData<Boolean> = _shouldExit

    private val _userProfile: MutableLiveData<UserProfile> =
        MutableLiveData()
    val userProfile: LiveData<UserProfile> = _userProfile

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private var dataLoadedCounter = 0
    private val requestsCount = 1


    // Alert
    private val _showAlertDialog = MutableLiveData(false)
    val showAlertDialog: LiveData<Boolean> = _showAlertDialog

    private val _alertType = MutableLiveData(AlertType.DEFAULT)
    val alertType: LiveData<AlertType> = _alertType

    init {
        loadProfileData()
    }

    private fun dataLoaded() {
        if (++dataLoadedCounter == requestsCount)
            _isLoading.postValue(false)
    }

    fun loadProfileData() {
        _isLoading.value = true
        dataLoadedCounter = 0
        viewModelScope.launch(Dispatchers.IO) {
            getUserProfileUseCase.execute().collect { result ->
                result.onSuccess {
                    dataLoaded()
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
                    showAlert(AlertType.DEFAULT)
                }
            }
        }
    }

    fun onExitBtnClick() {
        saveTokenToLocalStorageUseCase.execute(Token("", ""))
        saveFavouritesCollectionIdUseCase.execute("")
        _shouldExit.value = true
    }

    fun onExited() {
        _shouldExit.value = false
    }

    fun reload() {
        loadProfileData()
    }

    fun showAlert(alert: AlertType) {
        if (_showAlertDialog.value != true) {
            _alertType.postValue(alert)
            _showAlertDialog.postValue(true)
        }
    }

    fun alertShowed() {
        _showAlertDialog.value = false
        _alertType.value = AlertType.DEFAULT
    }
}