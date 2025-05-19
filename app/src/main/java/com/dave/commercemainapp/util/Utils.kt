package com.dave.commercemainapp.util

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dave.commercemainapp.util.Utils.SectionType.VERTICAL

object Utils {

    fun Long.toPrice(): String {
        return String.format("%,d원", this)
    }

    fun getProductItemModifier(sectionType: SectionType): Modifier {
        return if(sectionType == VERTICAL) {
            Modifier.fillMaxWidth()
        }else {
            Modifier.widthIn(max = 100.dp).height(220.dp)
        }
    }

    fun getImageModifier(sectionType: SectionType): Modifier {
        return if(sectionType == VERTICAL) {
            Modifier.aspectRatio(1.5f)
        }else {
            Modifier.fillMaxWidth()
        }
    }

    inline fun <reified T : Enum<T>> safeValueOf(type: String, default: T): T {
        return try {
            java.lang.Enum.valueOf(T::class.java, type)
        } catch (e: IllegalArgumentException) {
            default
        }
    }

    enum class SectionType(typeString: String) {
        HORIZONTAL("horizontal"),
        VERTICAL("vertical"),
        GRID("grid")
    }
}