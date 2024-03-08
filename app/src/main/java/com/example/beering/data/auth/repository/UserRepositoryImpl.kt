package com.example.beering.data.auth.repository

import com.example.beering.data.ApiResult
import com.example.beering.data.auth.api.UserApi
import com.example.beering.data.auth.dto.CheckIdResult
import com.example.beering.data.auth.dto.CheckNameResult
import com.example.beering.data.auth.dto.JoinAgreements
import com.example.beering.data.auth.dto.JoinRequest
import com.example.beering.data.auth.dto.JoinResponse
import com.example.beering.data.auth.dto.LoginRequest
import com.example.beering.data.auth.dto.LoginResponse
import com.example.beering.feature.auth.join.domain.UserRepository

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

    override suspend fun requestJoin(username: String,
                                     password: String,
                                     nickname: String,
                                     checkBoxList : ArrayList<Boolean>): ApiResult<JoinResponse> {
        val agreements = arrayListOf(
            JoinAgreements("SERVICE", checkBoxList[0]),
            JoinAgreements("PERSONAL", checkBoxList[1]),
            JoinAgreements("MARKETING", checkBoxList[2])
        )
        val apiRequest = JoinRequest(username, password, nickname, agreements)
        val response = userApi.signUp(apiRequest)
        return ApiResult.create(response)
    }

    override suspend fun login(id : String, pw : String): ApiResult<LoginResponse> {
        val request = LoginRequest(id, pw)
        val response = userApi.login(request)
        return ApiResult.create(response)
    }
}