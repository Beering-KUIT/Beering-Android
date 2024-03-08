package com.example.beering.feature.auth.join.domain

import com.example.beering.data.ApiResult
import com.example.beering.data.auth.dto.LoginResponse

class LoginUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(id : String, pw : String) : ApiResult<LoginResponse>{
        return repository.login(id, pw)
    }
}