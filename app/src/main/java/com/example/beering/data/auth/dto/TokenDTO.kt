package com.example.beering.data.auth.dto

import com.google.gson.annotations.SerializedName

data class JwtSet(
    @SerializedName("jwtInfo") val accessToken: String,
    @SerializedName("jwtInfo") val refreshToken: String
)