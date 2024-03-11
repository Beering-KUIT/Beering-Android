package com.example.beering.feature.auth.join.domain

import android.util.Log
import com.example.beering.data.ApiResult

class SignupUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(username: String,
                                password: String,
                                nickname: String,
                                checkBoxList : ArrayList<Boolean>
    ) : ApiResult<Unit>{
        return repository.requestJoin(username, password, nickname, checkBoxList)
    }

}