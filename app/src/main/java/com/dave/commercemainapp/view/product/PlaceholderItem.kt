package com.dave.commercemainapp.view.product

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.dave.commercemainapp.util.Utils.SectionType
import com.dave.commercemainapp.util.Utils.getImageModifier
import com.dave.commercemainapp.util.Utils.getProductItemModifier
import com.dave.commercemainapp.view.theme.Gray
import com.dave.commercemainapp.view.theme.White
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize

@Composable

fun PlaceholderItem(sectionType: SectionType) {

    Card(
        modifier = getProductItemModifier(sectionType),
        shape = RoundedCornerShape(12.dp),
        colors = CardColors(containerColor = White, contentColor = White, disabledContainerColor = White, disabledContentColor = White),
        border = BorderStroke(width = 0.1.dp, color = Gray)
    ) {
        Column {
            Box(modifier = getImageModifier(sectionType).height(140.dp).shimmerEffect())
            when(sectionType) {
                SectionType.VERTICAL -> {
                    Box(modifier = Modifier.padding(start = 2.dp).fillMaxWidth().height(15.dp).shimmerEffect())
                }
                else -> {
                    Box(modifier = Modifier.padding(start = 2.dp, top = 2.dp).fillMaxWidth().height(15.dp).shimmerEffect())
                    Box(modifier = Modifier.padding(start = 2.dp, top = 2.dp).fillMaxWidth().height(15.dp).shimmerEffect())
                }
            }
        }
    }

}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        ),
        label = ""
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFB8B5B5),
                Color(0xFF8F8B8B),
                Color(0xFFB8B5B5),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    ).onGloballyPositioned {
        size = it.size
    }
}
