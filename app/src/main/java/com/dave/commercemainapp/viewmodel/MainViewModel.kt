package com.dave.commercemainapp.viewmodel

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dave.commercemainapp.model.Product
import com.dave.commercemainapp.model.SectionInfo
import com.dave.commercemainapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(application: Application, private val repository: MainRepository, private val dataStore: DataStore<Preferences>): AndroidViewModel(application) {

    private val _sectionList = MutableStateFlow<PagingData<SectionInfo>>(PagingData.empty())
    val sectionList = _sectionList.asStateFlow()

    private val _productList = MutableStateFlow<MutableMap<Long, List<Product>>>(mutableMapOf())
    val productList = _productList.asStateFlow()

    private var favoriteList = mutableSetOf<Long>()


    fun getSectionList(isRefresh: Boolean = false) {
        if(isRefresh) {
            _sectionList.value = PagingData.empty()
            _productList.value = mutableMapOf()
        }
        viewModelScope.launch {
            try {
                repository.getSectionList()
                    .cachedIn(viewModelScope)
                    .collect { sectionList ->
                        _sectionList.value = sectionList
                    }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getProductList(sectionId: Long) {
        if(_productList.value.containsKey(sectionId)) return
        viewModelScope.launch {
            try {
                repository.getProductList(sectionId)
                    .onSuccess { response ->
                        var map = _productList.value.toMutableMap()
                        if(map.containsKey(sectionId)) {
                            map.replace(sectionId, response.products)
                        }else {
                            map.put(sectionId, response.products)
                        }
                        _productList.emit(map)
                    }
                    .onFailure {
                        it.printStackTrace()
                    }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getFavorite() {
        viewModelScope.launch {
            repository.getFavorite().collect {
                if(it.isNullOrEmpty()) return@collect
                favoriteList = it.split(",").map { it.toLong() }.toMutableSet()
            }
        }
    }

    fun isFavorite(productId: Long): Boolean {
        return favoriteList.contains(productId)
    }

    fun setFavorite(productId: Long, favorite: Boolean) {
        if(favorite) {
            favoriteList.add(productId)
        }else {
            favoriteList.remove(productId)
        }
        viewModelScope.launch {
            repository.setFavorite(productId, favoriteList.joinToString(","))
        }
    }

    companion object {
        val FAVORITE_KEY = stringPreferencesKey("favorite")
    }

}