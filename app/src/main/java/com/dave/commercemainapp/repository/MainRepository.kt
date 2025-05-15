package com.dave.commercemainapp.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dave.commercemainapp.model.SectionInfo
import com.dave.commercemainapp.network.ApiService
import com.dave.commercemainapp.pager.MainSectionPagerSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(private val apiService:ApiService, private val dataStore: DataStore<Preferences>) {
    val FAVORITE_KEY = stringPreferencesKey("favorite")

    fun getSectionList(): Flow<PagingData<SectionInfo>> =
        Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                MainSectionPagerSource(apiService)
            }).flow

    suspend fun getProductList(id: Long) = apiService.getProducts(id)


    suspend fun setFavorite(productId: Long, favoriteList: String) {
        dataStore.edit {
            it[FAVORITE_KEY] = favoriteList
        }
    }

    fun getFavorite(): Flow<String> =
        dataStore.data.map { preferences ->
            preferences[FAVORITE_KEY].let { favorite ->
                if(favorite.isNullOrEmpty()) "" else favorite
            }
        }

}