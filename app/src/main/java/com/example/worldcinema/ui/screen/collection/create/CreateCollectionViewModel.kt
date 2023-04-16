package com.example.worldcinema.ui.screen.collection.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcinema.domain.model.CollectionIcon
import com.example.worldcinema.domain.usecase.network.CreateCollectionUseCase
import com.example.worldcinema.domain.usecase.storage.SaveCollectionIconUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateCollectionViewModel(
    private val favouritesCollectionName: String,
    private val createCollectionUseCase: CreateCollectionUseCase,
    private val saveCollectionIconUseCase: SaveCollectionIconUseCase
) : ViewModel() {

    private val _exit: MutableLiveData<Boolean> =
        MutableLiveData(false)
    val exit: LiveData<Boolean> = _exit

    private val _iconIndex = MutableLiveData(0)
    val iconIndex: LiveData<Int> = _iconIndex

    fun setIconIndex(index: Int) {
        _iconIndex.value = index
    }

    fun createCollection(name: String) {

        if (name == favouritesCollectionName) {
            // TODO(Показать ошибку)
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            createCollectionUseCase.execute(name).collect { result ->
                result.onSuccess {
                    saveCollectionIconUseCase.execute(CollectionIcon(it, _iconIndex.value ?: 0))
                    _exit.postValue(true)
                }.onFailure {
                    // TODO(Показать ошибку)
                }
            }
        }
    }
}