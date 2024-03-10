package com.example.beering.feature.auth.login

import com.example.beering.util.token.Jwt

// 카카오 로그인 DTO

data class KakaoLoginRequest(
    val id_token: String,
    val access_token: String,
    val refresh_token: String
)


data class KakaoLoginResponse(
    val isSuccess: Boolean,
    val responseCode: Int,
    val responseMessage: String,
    val result: KakaoLoginResult
)


data class KakaoLoginResult(
    val memberId: Int,
    val jwtInfo: Jwt,
    val isLoginCompleted: Boolean,
    val sub : Int,
    val oauthType: String
)

