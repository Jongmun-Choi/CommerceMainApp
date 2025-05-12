package com.dave.commercemainapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dave.commercemainapp.model.SectionResponse
import com.dave.commercemainapp.network.ApiService
import com.dave.commercemainapp.pager.MainSectionPagerSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService:ApiService) {
    fun getSections(): Flow<PagingData<SectionResponse.SectionInfo>> =
        Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                MainSectionPagerSource(apiService)
            }).flow
    suspend fun getProductList(id: Long) = apiService.getProducts(id)
}