package com.example.worldcinema.ui.screen.collection.update

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcinema.domain.model.CollectionIcon
import com.example.worldcinema.domain.usecase.network.DeleteCollectionUseCase
import com.example.worldcinema.domain.usecase.storage.DeleteCollectionIconUseCase
import com.example.worldcinema.domain.usecase.storage.GetCollectionsIconsUseCase
import com.example.worldcinema.domain.usecase.storage.SaveCollectionIconUseCase
import com.example.worldcinema.ui.model.UsersCollection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateCollectionViewModel(
    private val collection: UsersCollection,
    private val deleteCollectionUseCase: DeleteCollectionUseCase,
    private val deleteCollectionIconUseCase: DeleteCollectionIconUseCase,
    private val getCollectionsIconsUseCase: GetCollectionsIconsUseCase,
    private val saveCollectionIconUseCase: SaveCollectionIconUseCase
) : ViewModel() {

    private val _exit: MutableLiveData<Boolean> =
        MutableLiveData(false)
    val exit: LiveData<Boolean> = _exit

    private val _iconIndex = MutableLiveData(0)
    val iconIndex: LiveData<Int> = _iconIndex

    init {
        loadIconIndex()
    }

    fun setIconIndex(index: Int) {
        _iconIndex.value = index
    }

    fun deleteCollection() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteCollectionUseCase.execute(collection.collectionId).collect { result ->
                result.onSuccess {
                    _exit.postValue(true)
                    deleteCollectionIconUseCase.execute(collection.collectionId)
                }.onFailure {
                    // TODO(Показать ошибку)
                    _exit.postValue(true)
                }
            }
        }
    }

    fun saveIcon() {
        viewModelScope.launch(Dispatchers.IO) {

            deleteCollectionIconUseCase.execute(collection.collectionId)

            saveCollectionIconUseCase.execute(
                CollectionIcon(
                    collection.collectionId,
                    _iconIndex.value ?: 0
                )
            ).onSuccess {
                _exit.postValue(true)
            }.onFailure {
                // TODO(Показать ошибку)
            }
        }
    }

    private fun loadIconIndex() {
        viewModelScope.launch(Dispatchers.IO) {
            getCollectionsIconsUseCase.execute().onSuccess {
                val icon =
                    it.singleOrNull { c -> c.collectionId == collection.collectionId }?.iconIndex
                        ?: 0
                _iconIndex.postValue(icon)
            }
        }
    }
}