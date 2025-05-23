package com.dave.commercemainapp.view.product

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dave.commercemainapp.R
import com.dave.commercemainapp.model.Product
import com.dave.commercemainapp.view.theme.Black
import com.dave.commercemainapp.view.theme.DiscountPercent
import com.dave.commercemainapp.view.theme.Gray
import com.dave.commercemainapp.view.theme.White
import com.dave.commercemainapp.util.Utils.SectionType
import com.dave.commercemainapp.util.Utils.SectionType.*
import com.dave.commercemainapp.util.Utils.getImageModifier
import com.dave.commercemainapp.util.Utils.getProductItemModifier
import com.dave.commercemainapp.util.Utils.toPrice
import com.dave.commercemainapp.viewmodel.MainViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductItem(product: Product, sectionType: SectionType, viewModel: MainViewModel) {

    var isFavorite by remember { mutableStateOf(viewModel.isFavorite(product.id)) }

    LaunchedEffect(isFavorite) {
        viewModel.setFavorite(product.id, isFavorite)
    }

    Card(
        modifier = getProductItemModifier(sectionType),
        shape = RoundedCornerShape(12.dp),
        colors = CardColors(containerColor = White, contentColor = White, disabledContainerColor = White, disabledContentColor = White),
        border = BorderStroke(width = 0.1.dp, color = Gray)
    ) {
        Column(modifier = Modifier.background(color = White)) {
            Box {
                GlideImage(
                    model = product.image,
                    modifier = getImageModifier(sectionType),
                    contentDescription = "",
                    contentScale = if (sectionType == VERTICAL) ContentScale.FillBounds else ContentScale.Fit
                )

                Image(
                    modifier = Modifier.padding(top = 4.dp, end = 4.dp).align(Alignment.TopEnd).clickable(enabled = true,
                        onClick = {
                            isFavorite = !isFavorite
                        }),
                    contentDescription = "", painter = if(isFavorite) painterResource(id = R.drawable.ic_favorite) else painterResource(id = R.drawable.ic_favorite_border),
                )
            }

            Text(
                modifier = Modifier.padding(start = 2.dp),
                text = product.name,
                maxLines = if (sectionType == VERTICAL) 1 else 2,
                overflow = TextOverflow.Ellipsis,
                color = Black,
                fontSize = 15.sp,
                lineHeight = 14.sp

            )
            if (product.discountedPrice < 0) {
                Text(modifier = Modifier.padding(start = 2.dp), text = product.originalPrice.toPrice(), color = Black, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            } else {

                when (sectionType) {
                    HORIZONTAL, GRID -> {
                        // 할인률
                        Column(modifier = Modifier.padding(start = 2.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${product.getDiscountPercent()}%",
                                    fontWeight = FontWeight.Bold,
                                    color = DiscountPercent,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = product.discountedPrice.toPrice(),
                                    fontWeight = FontWeight.Bold,
                                    color = Black,
                                    fontSize = 15.sp
                                )
                            }
                            Text(
                                text = product.originalPrice.toPrice(),
                                color = Gray,
                                fontSize = 15.sp,
                                style = TextStyle(textDecoration = TextDecoration.LineThrough)
                            )
                        }

                    }

                    else -> {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(start = 2.dp),
                            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${product.getDiscountPercent()}%",
                                fontWeight = FontWeight.Bold,
                                color = DiscountPercent,
                                fontSize = 15.sp
                            )
                            Text(
                                text = product.discountedPrice.toPrice(),
                                fontWeight = FontWeight.Bold,
                                color = Black,
                                fontSize = 15.sp
                            )
                            Text(
                                text = product.originalPrice.toPrice(),
                                color = Gray,
                                fontSize = 15.sp,
                                style = TextStyle(textDecoration = TextDecoration.LineThrough)
                            )
                        }

                    }
                }
            }

        }

    }
}

