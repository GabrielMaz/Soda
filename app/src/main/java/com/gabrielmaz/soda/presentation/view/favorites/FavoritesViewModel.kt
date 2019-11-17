package com.gabrielmaz.soda.presentation.view.favorites

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gabrielmaz.soda.data.models.Favorite
import com.gabrielmaz.soda.data.models.Movie
import com.gabrielmaz.soda.data.sources.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FavoritesViewModel : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val localFavorites = MutableLiveData<List<Favorite>>()
    private val localIsEmptyList = MutableLiveData<Boolean>()
    private val localIsLoading = MutableLiveData<Boolean>()

    val favorites: LiveData<List<Favorite>>
        get() = localFavorites
    val isEmptyList: LiveData<Boolean>
        get() = localIsEmptyList
    val isLoading: LiveData<Boolean>
        get() = localIsLoading


    fun loadFavorites(context: Context) {
        localIsLoading.postValue(true)
        launch(Dispatchers.IO) {
            val favorites = AppDatabase.getInstance(context).favoriteDao().getFavorites()
            localIsLoading.postValue(false)
            localFavorites.postValue(favorites)
            localIsEmptyList.postValue(favorites.isEmpty())
        }

    }
}