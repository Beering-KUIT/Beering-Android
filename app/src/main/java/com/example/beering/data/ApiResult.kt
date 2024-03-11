package com.example.beering.data

import android.util.Log
import com.example.beering.BeeringApplication
import com.example.beering.util.base.ApiErrorResponse
import com.example.beering.util.base.BaseResponse
import okhttp3.ResponseBody
import retrofit2.Response

sealed class ApiResult<T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Fail<T>(val code: Int, val message: String) : ApiResult<T>()

    companion object {
        // isSuccess 값에 따라 Success 또는 Fail 객체를 생성하는 정적 메서드
        inline fun <reified T> create(response: Response<BaseResponse<T>>): ApiResult<T> {
            return if (response.isSuccessful) {
                if (T::class == Unit::class){
                    Success(Unit) as ApiResult<T>
                } else {
                    Success(response.body()!!.result!!)
                }

            } else {
                val errorResponse = parseErrorResponse<ApiErrorResponse>(response.errorBody()!!)!!
                Fail(errorResponse.responseCode, errorResponse.responseMessage)
            }
        }

        inline fun <reified T> parseErrorResponse(errorBody: ResponseBody): T? {
            return BeeringApplication.retrofit.responseBodyConverter<T>(
                T::class.java,
                T::class.java.annotations
            ).convert(errorBody)
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
