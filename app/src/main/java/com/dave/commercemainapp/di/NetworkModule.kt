package com.dave.commercemainapp.di

import android.content.Context
import com.dave.commercemainapp.BuildConfig
import com.dave.commercemainapp.network.ApiService
import com.dave.commercemainapp.network.ResultFactory
import com.kurly.android.mockserver.MockInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val resultFactory = ResultFactory()
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level =
            HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideMockInterceptor(@ApplicationContext context: Context): MockInterceptor {
        return MockInterceptor(context)
    }

    @Singleton
    @Provides
    fun provideOkhttpClient(interceptor: MockInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder().build()
        return Retrofit.Builder()
            .baseUrl("https://kurly.com") // 실제 API Base URL (Mock 사용 시에는 중요도 낮음)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(resultFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)


}