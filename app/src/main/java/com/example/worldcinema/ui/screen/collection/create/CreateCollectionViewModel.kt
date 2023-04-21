package com.example.worldcinema.ui.screen.collection.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcinema.domain.model.CollectionIcon
import com.example.worldcinema.domain.usecase.network.CreateCollectionUseCase
import com.example.worldcinema.domain.usecase.storage.SaveCollectionIconUseCase
import com.example.worldcinema.ui.dialog.AlertType
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

    // Alert
    private val _showAlertDialog = MutableLiveData(false)
    val showAlertDialog: LiveData<Boolean> = _showAlertDialog

    private val _alertType = MutableLiveData(AlertType.DEFAULT)
    val alertType: LiveData<AlertType> = _alertType

    fun setIconIndex(index: Int) {
        _iconIndex.value = index
    }

    fun createCollection(name: String) {

        if (name.trim() == favouritesCollectionName) {
            _alertType.value = AlertType.COLLECTION_WITH_FAVOURITE_NAME
            _showAlertDialog.value = true
            return
        }
        if(name.isBlank()) {
            _alertType.value = AlertType.EMPTY_COLLECTION_NAME
            _showAlertDialog.value = true
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            createCollectionUseCase.execute(name).collect { result ->
                result.onSuccess {
                    saveCollectionIconUseCase.execute(CollectionIcon(it, _iconIndex.value ?: 0))
                    _exit.postValue(true)
                }.onFailure {
                    _alertType.postValue(AlertType.DEFAULT)
                    _showAlertDialog.postValue(true)
                }
            }
        }
    }
}