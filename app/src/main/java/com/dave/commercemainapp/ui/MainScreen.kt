package com.dave.commercemainapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.*
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.dave.commercemainapp.ui.section.SectionItem
import com.dave.commercemainapp.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val isRefreshing = remember { mutableStateOf(false) }
    val sectionList = viewModel.sectionList.collectAsLazyPagingItems()
    val lazyListState = rememberLazyListState()
    val refreshState = rememberPullToRefreshState()

    fun refreshItems() = run {
        isRefreshing.value = true
    }

    LaunchedEffect(isRefreshing.value) {
        if(isRefreshing.value || sectionList.itemCount == 0) viewModel.getSectionList(isRefreshing.value)
        isRefreshing.value = false
        if(isRefreshing.value) sectionList.refresh()
    }

    LaunchedEffect(sectionList.itemCount) {
        for(i in 0 until sectionList.itemCount) {
            viewModel.getProductList(sectionList[i]!!.id)
        }
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing.value,
        onRefresh = { refreshItems() },
        state = refreshState,
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = isRefreshing.value,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                state = refreshState
            )
        }
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize(),
            state = lazyListState,
            verticalArrangement = Arrangement.Top) {
            if(sectionList.itemCount>0) {
                items(sectionList.itemCount) { index ->
                    SectionItem(viewModel = viewModel, section = sectionList[index]!!)
                }
            }
        }
    }

}