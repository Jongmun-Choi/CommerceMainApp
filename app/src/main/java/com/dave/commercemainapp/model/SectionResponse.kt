package com.dave.commercemainapp.model


import com.dave.commercemainapp.util.Utils.SectionType
import com.dave.commercemainapp.util.Utils.safeValueOf
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SectionResponse(
    @field:Json(name = "data")
    val list: List<SectionInfo> = listOf<SectionInfo>(),
)

@JsonClass(generateAdapter = true)
data class SectionInfo(
    @field:Json(name = "title")
    val title : String,
    @field:Json(name = "id")
    val id : Long,
    @field:Json(name = "type")
    val type : String,
    @field:Json(name = "url")
    val url : String
) {
    fun getSectionType() : SectionType {
        return safeValueOf(type.uppercase(), SectionType.VERTICAL)
    }
}