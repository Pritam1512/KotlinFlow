package com.example.flowconcept.service

import com.example.flowconcept.model.CommentData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {
    @GET("/comments/{id}")
    suspend fun getComment(@Path("id") id:Int) : CommentData
}