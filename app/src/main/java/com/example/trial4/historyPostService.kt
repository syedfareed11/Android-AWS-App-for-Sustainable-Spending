package com.example.trial4

import com.example.trial4.models.historyPostItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface HistoryPostService {
    @POST("/test/uploadreceipthistory")
    fun uploadResult(@Body request:historyPostItem) : Call<Any>
}