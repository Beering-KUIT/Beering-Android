package com.example.beering.data.auth.dto

import com.google.gson.annotations.SerializedName

data class JwtSet(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String
)