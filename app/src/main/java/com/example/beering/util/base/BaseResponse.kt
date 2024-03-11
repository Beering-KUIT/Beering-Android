package com.example.beering.util.base

import com.google.gson.annotations.SerializedName

data class BaseResponse<T> (
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("responseCode") val responseCode: Int,
    @SerializedName("responseMessage") val responseMessage: String,
    @SerializedName("result") val  result: T?
)