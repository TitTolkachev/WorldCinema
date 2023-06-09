package com.example.worldcinema.ui.screen.main.collections

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcinema.domain.model.CollectionIcon
import com.example.worldcinema.domain.usecase.network.GetCollectionsUseCase
import com.example.worldcinema.domain.usecase.storage.GetCollectionsIconsUseCase
import com.example.worldcinema.ui.dialog.AlertType
import com.example.worldcinema.ui.helper.CollectionMapper
import com.example.worldcinema.ui.model.UsersCollection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollectionsViewModel(
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val getCollectionsIconsUseCase: GetCollectionsIconsUseCase
) : ViewModel() {

    private val _collections: MutableLiveData<MutableList<UsersCollection>> =
        MutableLiveData(mutableListOf())
    val collections: LiveData<MutableList<UsersCollection>> = _collections

    private val _showCollection: MutableLiveData<Boolean> =
        MutableLiveData(false)
    val showCollection: LiveData<Boolean> = _showCollection

    private val _selectedCollection: MutableLiveData<UsersCollection> =
        MutableLiveData()
    val selectedCollection: LiveData<UsersCollection> = _selectedCollection

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
        _isLoading.value = true
        dataLoadedCounter = 0
    }

    fun onItemClicked(collection: UsersCollection) {
        _selectedCollection.value = collection
        _showCollection.value = true
    }

    fun collectionShowed() {
        _showCollection.value = false
    }

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {

            var icons = listOf<CollectionIcon>()
            getCollectionsIconsUseCase.execute().onSuccess {
                icons = it
            }

            getCollectionsUseCase.execute().collect { result ->
                result.onSuccess {
                    dataLoaded()
                    _collections.postValue(CollectionMapper.mapCollections(it, icons))
                }.onFailure {
                    showAlert(AlertType.DEFAULT)
                }
            }
        }
    }

    private fun dataLoaded() {
        if(++dataLoadedCounter == requestsCount)
            _isLoading.postValue(false)
    }

    fun reload() {
        _isLoading.value = true
        dataLoadedCounter = 0
        loadData()
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