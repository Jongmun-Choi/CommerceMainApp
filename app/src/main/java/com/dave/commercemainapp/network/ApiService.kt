package com.dave.commercemainapp.network

import com.dave.commercemainapp.model.ProductResponse
import com.dave.commercemainapp.model.SectionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/sections")
    suspend fun getSectionList(@Query("page") page: Int): Result<SectionResponse>


    @GET("/section/products")
    suspend fun getProducts(@Query("sectionId") sectionId: Long): Result<ProductResponse>
}