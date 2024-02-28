package com.example.beering.data.auth.repository

import android.util.Log
import com.example.beering.data.ApiResult
import com.example.beering.data.auth.api.UserApi
import com.example.beering.data.auth.dto.CheckIdResult
import com.example.beering.data.auth.dto.CheckNameResult
import com.example.beering.data.auth.dto.JoinRequest
import com.example.beering.data.auth.dto.JoinResponse
import com.example.beering.feature.auth.join.domain.UserRepository
import com.example.beering.util.base.BaseResponse

class UserRepositoryImpl(private val userApi: UserApi) : UserRepository {
    override suspend fun checkId(id: String): ApiResult<CheckIdResult> {
        val response = userApi.checkUserId(id)

        return ApiResult.create(response)
//        Log.d("responseeeiei", response.body().toString())
//        if (response.isSuccessful) {
//            response.body()?.let { it ->
//                return ApiResult.Success(it.result)
//            }
//        }
//        return ApiResult.Fail(response.code(), response.message())

    }

    override suspend fun checkNickName(name: String): ApiResult<CheckNameResult> {
        val response = userApi.checkNickname(name)

        return ApiResult.create(response)
//        Log.d("responseee", response.toString())
//        if (response.isSuccessful) {
//            response.body()?.let { it ->
//                return ApiResult.Success(it)
//            }
//        }
//        return ApiResult.Fail(response.code(), response.message())
    }

    override suspend fun requestJoin(joinRequest: JoinRequest): ApiResult<JoinResponse> {
        val response = userApi.signUp(joinRequest)
        return ApiResult.create(response)
//        if (response.isSuccessful) {
//            response.body()?.let { it ->
//                return ApiResult.Success(it)
//            }
//        }
//        return ApiResult.Fail(response.code(), response.message())
    }
}