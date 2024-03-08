package com.example.beering.data.auth.dto

import com.example.beering.feature.auth.join.MemberErrors
import com.example.beering.feature.auth.login.LoginError
import com.example.beering.feature.auth.login.LoginResult
import com.example.beering.util.token.Jwt
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

data class JoinResponse(
    @SerializedName("errors") val errors: List<JoinError>,
    @SerializedName("message") val message: String,
    @SerializedName("objectName") val objectName: String
)

data class JoinError(
    @SerializedName("fieldName") val fieldName: String,
    @SerializedName("rejectValue") val rejectValue: String,
    @SerializedName("message") val message: String
)

data class LoginRequest(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)

data class LoginResponse(
    @SerializedName("memberId") val memberId: Int,
    @SerializedName("jwtInfo") val jwtInfo: JwtSet
)

