package com.example.trial4

import com.example.trial4.models.hisoryItem
import retrofit2.Call
import retrofit2.http.GET

interface HistoryService {
    @GET("/test/retrievereceipthistory")
    fun getHistory() : Call<List<hisoryItem>>
}
