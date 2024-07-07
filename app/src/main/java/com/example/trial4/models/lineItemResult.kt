package com.example.trial4.models

import com.google.gson.annotations.SerializedName

data class lineItemResult (
    @SerializedName("lineItems") val lineItems : ArrayList<String>,
    @SerializedName("s3Url") val s3Url : String
)