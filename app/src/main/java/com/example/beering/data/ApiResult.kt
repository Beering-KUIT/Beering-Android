package com.example.beering.data

import com.example.beering.util.base.BaseResponse
import retrofit2.Response

sealed class ApiResult<T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Fail<T>(val code : Int, val message: String) : ApiResult<T>()

    companion object {
        // isSuccess 값에 따라 Success 또는 Fail 객체를 생성하는 정적 메서드
        fun <T> create(response : Response<BaseResponse<T>>): ApiResult<T> {
            return if (response.isSuccessful) {
                Success(response.body()!!.result!!)
            } else {
                Fail(response.body()!!.responseCode, response.body()!!.responseMessage)
            }
        }
    }
}

inline fun <T, R> ApiResult<T>.getResult(
    success: (ApiResult.Success<T>) -> R,
    fail: (ApiResult.Fail<T>) -> R
): R = when (this) {
    is ApiResult.Success -> success(this)
    is ApiResult.Fail -> fail(this)
}

inline fun <T> ApiResult<T>.onSuccess(
    action: (T) -> Unit
): ApiResult<T> {
    if (this is ApiResult.Success) action(data)
    return this
}

inline fun <T> ApiResult<T>.onFail(
    action: (Int, String) -> Unit
): ApiResult<T> {
    if (this is ApiResult.Fail) action(code, message)
    return this
}
