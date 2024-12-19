package com.example.flowconcept.di

import com.example.flowconcept.DatabaseManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    private val BASE_URL = "https://jsonplaceholder.typicode.com/"
    @Provides
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    fun provideDBManager():DatabaseManager{
        return DatabaseManager()
    }
}