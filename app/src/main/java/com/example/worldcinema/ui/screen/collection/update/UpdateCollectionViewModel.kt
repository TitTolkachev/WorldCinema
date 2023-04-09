package com.example.worldcinema.ui.screen.collection.update

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcinema.domain.usecase.network.DeleteCollectionUseCase
import com.example.worldcinema.ui.model.UsersCollection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateCollectionViewModel(
    private val collection: UsersCollection,
    private val deleteCollectionUseCase: DeleteCollectionUseCase
) : ViewModel() {

    private val _isCollectionDeleted: MutableLiveData<Boolean> =
        MutableLiveData(false)
    val isCollectionDeleted: LiveData<Boolean> = _isCollectionDeleted

    fun deleteCollection() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteCollectionUseCase.execute(collection.collectionId).collect { result ->
                result.onSuccess {
                    _isCollectionDeleted.postValue(true)
                }.onFailure {
                    // TODO(Показать ошибку)
                    _isCollectionDeleted.postValue(true)
                }
            }
        }
    }
}