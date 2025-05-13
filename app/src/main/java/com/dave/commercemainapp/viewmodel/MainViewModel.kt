package com.dave.commercemainapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.map
import com.dave.commercemainapp.model.Product
import com.dave.commercemainapp.model.SectionInfo
import com.dave.commercemainapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(application: Application, private val repository: MainRepository): AndroidViewModel(application) {

    private val _sectionList = MutableStateFlow<PagingData<SectionInfo>>(PagingData.empty())
    val sectionList = _sectionList.asStateFlow()

    private val _productList = MutableStateFlow<MutableMap<Long, List<Product>>>(mutableMapOf())
    val productList = _productList.asStateFlow()



    fun getSectionList() {
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

}