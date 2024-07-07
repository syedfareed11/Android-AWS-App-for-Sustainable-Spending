package com.example.trial4.models

import com.google.gson.annotations.SerializedName

data class hisoryItem (
    @SerializedName("url") val imageUrl: String,
    @SerializedName("timestamp") val date : String,
    @SerializedName("result") val result: String
)

