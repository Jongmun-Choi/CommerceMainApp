package com.dave.commercemainapp.ui.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.dave.commercemainapp.ui.theme.Black
import com.dave.commercemainapp.ui.theme.DiscountPercent
import com.dave.commercemainapp.ui.theme.Gray
import com.dave.commercemainapp.ui.theme.White
import com.dave.commercemainapp.util.Utils.toPrice

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductItem(product: Product, sectionType: String) {

    Card(
        modifier = getModifier(sectionType),
        shape = RoundedCornerShape(12.dp),
        colors = CardColors(containerColor = White, contentColor = White, disabledContainerColor = White, disabledContentColor = White)
    ) {
        Column(modifier = Modifier.background(color = White)) {
            Box {
                GlideImage(
                    model = product.image,
                    modifier = getImageModifier(sectionType),
                    contentDescription = "",
                    contentScale = if (sectionType == "vertical") ContentScale.FillBounds else ContentScale.Fit
                )

                Image(
                    modifier = Modifier.padding(top = 4.dp, end = 4.dp).align(Alignment.TopEnd),
                    contentDescription = "", painter = if(product.isFavorite()) painterResource(id = com.dave.commercemainapp.R.drawable.ic_favorite) else painterResource(id = R.drawable.ic_favorite_border)
                )
            }

            Text(
                modifier = Modifier.padding(start = 2.dp),
                text = product.name,
                maxLines = if (sectionType == "vertical") 1 else 2,
                overflow = TextOverflow.Ellipsis,
                color = Black,
                fontSize = 15.sp,
                lineHeight = 14.sp

            )
            if (product.discountedPrice < 0) {
                Text(modifier = Modifier.padding(start = 2.dp), text = product.originalPrice.toPrice(), color = Black, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            } else {

                when (sectionType) {
                    "horizontal", "grid" -> {
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

fun getModifier(sectionType: String): Modifier {
    return if(sectionType == "vertical") {
        Modifier.fillMaxWidth()
    }else {
        Modifier.widthIn(max = 100.dp).height(220.dp)
    }
}

fun getImageModifier(sectionType: String): Modifier {
    return if(sectionType == "vertical") {
        Modifier.aspectRatio(1.5f)
    }else {
        Modifier.fillMaxWidth()
    }
}