package com.example.beering.util.base

import com.google.gson.annotations.SerializedName

data class ApiErrorResponse(
    @SerializedName("responseCode") val responseCode : Int,
    @SerializedName("responseMessage") val responseMessage : String
)
