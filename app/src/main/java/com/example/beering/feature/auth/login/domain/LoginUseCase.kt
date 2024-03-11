package com.example.beering.feature.auth.login.domain

import com.example.beering.data.ApiResult
import com.example.beering.data.auth.dto.LoginResponse
import com.example.beering.feature.auth.join.domain.UserRepository

class LoginUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(id : String, pw : String) : ApiResult<LoginResponse>{
        return repository.login(id, pw)
    }

    fun saveTokens(accessToken : String, refreshToken : String){
        repository.saveAccessToken(accessToken)
        repository.saveRefreshToken(refreshToken)
    }
}