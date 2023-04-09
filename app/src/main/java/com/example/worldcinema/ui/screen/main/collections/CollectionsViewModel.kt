package com.example.worldcinema.ui.screen.main.collections

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcinema.domain.usecase.network.GetCollectionsUseCase
import com.example.worldcinema.ui.helper.CollectionMapper
import com.example.worldcinema.ui.model.UsersCollection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollectionsViewModel(
    private val getCollectionsUseCase: GetCollectionsUseCase
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

    fun onItemClicked(collection: UsersCollection) {
        _selectedCollection.value = collection
        _showCollection.value = true
    }

    fun collectionShowed() {
        _showCollection.value = false
    }

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            getCollectionsUseCase.execute().collect { result ->
                result.onSuccess {
                    _collections.postValue(CollectionMapper.mapCollections(it))
                }.onFailure {

                }
            }
        }
    }
}