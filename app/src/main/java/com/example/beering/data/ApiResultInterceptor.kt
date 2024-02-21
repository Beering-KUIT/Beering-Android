package com.example.beering.data

import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody

class ApiResultInterceptor : Interceptor {
    private val gson = Gson()

    // TODO : 나중에 다시 적용
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        // 응답의 MediaType이 application/json인지 확인
        val contentType: MediaType? = response.body?.contentType()
        val isJson = contentType?.type == "application" && contentType.subtype == "json"

        // 응답이 JSON 형식일 경우에만 가공
        if (isJson) {
            val json = response.body?.string()
            val jsonObject: JsonObject = gson.fromJson(json, JsonObject::class.java)

            // 응답을 JsonObject로 변환하여 반환
            val modifiedResponse = response.newBuilder()
                .body(ResponseBody.create(contentType, jsonObject.toString()))
                .build()

            return modifiedResponse
        }

        // JSON 형식이 아니면 그대로 반환
        return response
    }
}