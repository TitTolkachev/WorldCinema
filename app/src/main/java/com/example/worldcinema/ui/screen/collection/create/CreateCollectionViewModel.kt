package com.example.worldcinema.ui.screen.collection.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcinema.domain.usecase.network.CreateCollectionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateCollectionViewModel(
    private val createCollectionUseCase: CreateCollectionUseCase
) : ViewModel() {

    private val _isCollectionCreated: MutableLiveData<Boolean> =
        MutableLiveData(false)
    val isCollectionCreated: LiveData<Boolean> = _isCollectionCreated

    fun createCollection(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            createCollectionUseCase.execute(name).collect { result ->
                result.onSuccess {
                    _isCollectionCreated.postValue(true)
                }.onFailure {
                    // TODO(Показать ошибку)
                }
            }
        }
    }
}