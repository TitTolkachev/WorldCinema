package com.example.worldcinema.ui.screen.launch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcinema.domain.usecase.network.GetCoverUseCase
import com.example.worldcinema.domain.usecase.storage.GetFirstEnterInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LaunchViewModel(
    private val getCoverUseCase: GetCoverUseCase,
    private val getFirstEnterInfoUseCase: GetFirstEnterInfoUseCase
) : ViewModel() {

    private val _navigateToMainScreen: MutableLiveData<Boolean> =
        MutableLiveData(false)
    val navigateToMainScreen: LiveData<Boolean> = _navigateToMainScreen

    private val _navigateToSignInScreen: MutableLiveData<Boolean> =
        MutableLiveData(false)
    val navigateToSignInScreen: LiveData<Boolean> = _navigateToSignInScreen

    fun isEnterFirst(): Boolean {
        return getFirstEnterInfoUseCase.execute()
    }

    fun checkToken() {
        viewModelScope.launch(Dispatchers.IO) {
            getCoverUseCase.execute().collect { result ->
                result.onSuccess {
                    _navigateToMainScreen.postValue(true)
                }.onFailure {
                    _navigateToSignInScreen.postValue(true)
                }
            }
        }
    }
}