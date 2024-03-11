package com.example.beering.data.auth.dto

import com.google.gson.annotations.SerializedName

data class CheckIdResult(
    @SerializedName("available") val available: Boolean,
)

data class CheckNameResult(
    @SerializedName("available") val available: Boolean,
)

data class JoinRequest(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("agreements") val agreements: List<JoinAgreements>,
)

data class JoinAgreements(
    @SerializedName("name") val name: String,
    @SerializedName("isAgreed") val isAgreed: Boolean,
)

data class LoginRequest(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)

data class LoginResponse(
    @SerializedName("memberId") val memberId: Int,
    @SerializedName("jwtInfo") val jwtInfo: JwtSet
)

