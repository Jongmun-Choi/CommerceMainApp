package com.dave.commercemainapp.pager

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dave.commercemainapp.model.SectionResponse
import com.dave.commercemainapp.model.SectionResponse.SectionInfo
import com.dave.commercemainapp.network.ApiService

class MainSectionPagerSource(private val service: ApiService): PagingSource<Int, SectionInfo>() {
    override fun getRefreshKey(state: PagingState<Int, SectionInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SectionInfo> {
        val page = params.key ?: 1
        return try {
            val response = service.getSections(page)
            LoadResult.Page(
                data = response.getOrDefault(SectionResponse(list = emptyList())).list,
                prevKey = if(page == 1) null else page - 1,
                nextKey = page + 1
            )
        }catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}