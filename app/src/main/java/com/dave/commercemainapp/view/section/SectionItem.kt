package com.dave.commercemainapp.view.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dave.commercemainapp.model.SectionInfo
import com.dave.commercemainapp.view.product.ProductItem
import com.dave.commercemainapp.viewmodel.MainViewModel
import com.dave.commercemainapp.util.Utils.SectionType.*
import com.dave.commercemainapp.view.product.PlaceholderItem

@Composable
fun SectionItem(viewModel : MainViewModel, section: SectionInfo) {

    val productList = viewModel.productList.collectAsStateWithLifecycle()
    val lazyListState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {

        Text(text = section.title)
        Spacer(modifier = Modifier.height(8.dp))
        when(section.getSectionType()) {
            VERTICAL -> {
                Column(modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    if(productList.value.contains(section.id)) {
                        productList.value[section.id]?.forEach { product ->
                            ProductItem(product = product, section.getSectionType(), viewModel)
                        }
                    }else {
                        PlaceholderItem(section.getSectionType())
                    }
                }
            }
            HORIZONTAL -> {
                LazyRow(state = lazyListState,
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if(productList.value.contains(section.id)) {
                        items(productList.value[section.id]?.size ?: 0) { index ->
                            ProductItem(
                                product = productList.value[section.id]!![index],
                                section.getSectionType(),
                                viewModel
                            )
                        }
                    }else {
                        items(4) {
                            PlaceholderItem(section.getSectionType())
                        }
                    }
                }
            }
            GRID -> {
                LazyVerticalGrid(modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight()
                                            .heightIn(max = 700.dp),
                    userScrollEnabled = false,
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ){
                    if(productList.value.contains(section.id)) {
                        items(productList.value[section.id]?.size ?: 0) { index ->
                            ProductItem(
                                product = productList.value[section.id]!![index],
                                section.getSectionType(),
                                viewModel
                            )
                        }
                    }else {
                        items(6) {
                            PlaceholderItem(section.getSectionType())
                        }
                    }
                }
            }

        }

    }

}