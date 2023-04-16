package com.example.worldcinema.ui.screen.collection.update

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.worldcinema.data.network.requests.auth.AuthRefreshRepository
import com.example.worldcinema.data.network.requests.collections.CollectionsRepository
import com.example.worldcinema.data.storage.db.CollectionIconRepository
import com.example.worldcinema.data.storage.db.CollectionsDatabase
import com.example.worldcinema.data.storage.shared_prefs.token.SharedPrefTokenStorage
import com.example.worldcinema.data.storage.shared_prefs.token.TokenStorageRepository
import com.example.worldcinema.domain.usecase.model.AuthNetworkUseCases
import com.example.worldcinema.domain.usecase.network.DeleteCollectionUseCase
import com.example.worldcinema.domain.usecase.network.RefreshTokenUseCase
import com.example.worldcinema.domain.usecase.storage.*
import com.example.worldcinema.ui.model.UsersCollection

class UpdateCollectionViewModelFactory(context: Context, private val collection: UsersCollection) :
    ViewModelProvider.Factory {

    private val tokenRepository by lazy {
        TokenStorageRepository(SharedPrefTokenStorage(context))
    }

    private val getTokenFromLocalStorageUseCase by lazy {
        GetTokenFromLocalStorageUseCase(tokenRepository)
    }
    private val saveTokenToLocalStorageUseCase by lazy {
        SaveTokenToLocalStorageUseCase(tokenRepository)
    }

    private val deleteCollectionUseCase by lazy {
        DeleteCollectionUseCase(
            CollectionsRepository(
                AuthNetworkUseCases(
                    getTokenFromLocalStorageUseCase,
                    saveTokenToLocalStorageUseCase,
                    RefreshTokenUseCase(AuthRefreshRepository(getTokenFromLocalStorageUseCase))
                )
            )
        )
    }

    private val collectionIconRepository by lazy {
        CollectionIconRepository(
            Room.databaseBuilder(
                context,
                CollectionsDatabase::class.java,
                CollectionsDatabase.DB_NAME
            ).build()
        )
    }

    private val deleteCollectionIconUseCase by lazy {
        DeleteCollectionIconUseCase(collectionIconRepository)
    }

    private val getCollectionsIconsUseCase by lazy {
        GetCollectionsIconsUseCase(collectionIconRepository)
    }

    private val saveCollectionIconUseCase by lazy {
        SaveCollectionIconUseCase(collectionIconRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UpdateCollectionViewModel(
            collection,
            deleteCollectionUseCase,
            deleteCollectionIconUseCase,
            getCollectionsIconsUseCase,
            saveCollectionIconUseCase
        ) as T
    }
}