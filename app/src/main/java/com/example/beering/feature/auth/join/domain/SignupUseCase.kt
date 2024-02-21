package com.example.beering.feature.auth.join.domain

import com.example.beering.data.ApiResult
import com.example.beering.data.auth.dto.JoinAgreements
import com.example.beering.data.auth.dto.JoinRequest
import com.example.beering.data.auth.dto.JoinResponse
import com.example.beering.util.base.BaseResponse

class SignupUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(username: String,
                                password: String,
                                nickname: String,
                                checkBoxList : ArrayList<Boolean>
    ) : ApiResult<BaseResponse<JoinResponse>>{
        val agreements = arrayListOf(
            JoinAgreements("SERVICE", checkBoxList[0]),
            JoinAgreements("PERSONAL", checkBoxList[1]),
            JoinAgreements("MARKETING", checkBoxList[2])
            )
        val apiRequest = JoinRequest(username, password, nickname, agreements)
        return repository.requestJoin(apiRequest)
    }

}