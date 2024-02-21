package com.example.beering.data.auth.repository

import com.example.beering.data.ApiResult
import com.example.beering.data.auth.api.UserApi
import com.example.beering.data.auth.dto.CheckIdResult
import com.example.beering.data.auth.dto.CheckNameResult
import com.example.beering.data.auth.dto.JoinRequest
import com.example.beering.data.auth.dto.JoinResponse
import com.example.beering.feature.auth.join.domain.UserRepository
import com.example.beering.util.base.BaseResponse

class UserRepositoryImpl(private val userApi: UserApi) : UserRepository {
    override suspend fun checkId(id: String): ApiResult<BaseResponse<CheckIdResult>> {
        val response = userApi.checkUserId(id)
        if (response.isSuccessful) {
            response.body()?.let { it ->
                return ApiResult.Success(it)
            }
        }
        return ApiResult.Error(response.message())

    }

    override suspend fun checkNickName(name: String): ApiResult<BaseResponse<CheckNameResult>> {
        val response = userApi.checkNickname(name)
        if (response.isSuccessful) {
            response.body()?.let { it ->
                return ApiResult.Success(it)
            }
        }
        return ApiResult.Error(response.message())
    }

    override suspend fun requestJoin(joinRequest: JoinRequest): ApiResult<BaseResponse<JoinResponse>> {
        val response = userApi.signUp(joinRequest)
        if (response.isSuccessful) {
            response.body()?.let { it ->
                return ApiResult.Success(it)
            }
        }
        return ApiResult.Error(response.message())
    }
}