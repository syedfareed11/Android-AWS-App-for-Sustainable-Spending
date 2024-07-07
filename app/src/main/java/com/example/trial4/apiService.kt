package com.example.trial4

import com.example.trial4.models.lineItemResult
import retrofit2.Call
import retrofit2.http.*

import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/test/uploadimage")
    fun uploadImage(@Body request: String): Call<lineItemResult>
}