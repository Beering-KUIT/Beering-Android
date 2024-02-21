package com.example.beering.feature.auth.join.domain

import com.example.beering.data.ApiResult
import com.example.beering.data.auth.dto.CheckIdResult
import com.example.beering.data.auth.dto.CheckNameResult
import com.example.beering.data.auth.dto.JoinAgreements
import com.example.beering.data.auth.dto.JoinRequest
import com.example.beering.data.auth.dto.JoinResponse
import com.example.beering.util.base.BaseResponse

interface UserRepository {
    suspend fun checkId(id : String) : ApiResult<BaseResponse<CheckIdResult>>
    suspend fun checkNickName(name : String) : ApiResult<BaseResponse<CheckNameResult>>
    suspend fun requestJoin(joinRequest: JoinRequest) : ApiResult<BaseResponse<JoinResponse>>
}