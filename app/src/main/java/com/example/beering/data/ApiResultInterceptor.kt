package com.example.beering.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.beering.util.base.BaseResponse
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject

class ApiResultInterceptor(val context : Context) : Interceptor {

    // TODO : 나중에 다시 적용
override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

//        val responseData = extractResult(response)
//        Log.d("responseee", responseData.toString())
//
//        return response.newBuilder()
//            .message(response.message)
//            .body(responseData.toString().toResponseBody())
//            .build()
        // 응답 코드를 확인하여 ApiResult를 생성합니다.
        return when (response.code) {
            // 200 OK
            200 -> {
//                val responseData = extractResult(response)
//                Log.d("responseee", responseData.toString())
//                // 새로운 응답 객체를 만들어서 성공 결과를 포함시킵니다.
//                response.newBuilder()
//                    .body(responseData.toString().toResponseBody())
//                    .build()
                response
            }
            // 400 Bad Request, 404 Not Found
            400, 404 -> {
                val responseError = extractError(response)
                Log.d("responseee", responseError.toString())
                // 새로운 응답 객체를 만들어서 실패 결과를 포함시킵니다.
                response.newBuilder()
                    .body(responseError.toString().toResponseBody())
                    .build()
            }
            else -> {
//                Toast.makeText(context, "서버로부터 응답이 없습니다.", Toast.LENGTH_SHORT).show()
                Log.d("Network Error", response.toString())
                response
            }
        }
    }

    private fun extractResult(response : Response) : JSONObject {
        return if (null == response.body) {
            JSONObject(EMPTY_JSON)
        } else {
            try {
                val responseBody = JSONObject(response.body!!.string())[KEY_RESULT].toString()
                JSONObject(responseBody)
            } catch (e : Exception){
                JSONObject(EMPTY_JSON)
            }
        }
    }
    private fun extractError(response : Response) : JSONObject {
        return if (null == response.body) {
            JSONObject(EMPTY_JSON)
        } else {
            try {
                val responseBody = JSONObject(response.body!!.string())
                val errorCode = responseBody.optString(KEY_CODE)
                val errorMessage = responseBody.optString(KEY_MSG)

                JSONObject().apply {
                    put(KEY_CODE, errorCode)
                    put(KEY_MSG, errorMessage)
                }
            } catch (e : Exception){
                JSONObject(EMPTY_JSON)
            }
        }
    }

    companion object {
        private const val EMPTY_JSON = "{}"
        private const val EMPTY_LIST = "[]"
        private const val KEY_RESULT = "result"
        private const val KEY_CODE = "responseCode"
        private const val KEY_MSG = "responseMessage"
    }
}