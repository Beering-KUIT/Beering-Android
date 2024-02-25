package com.example.beering.data

import android.util.Log
import com.example.beering.util.getAccessToken
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AccessTokenInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()

        val jwtToken: String? = getAccessToken()
        var request : Request? = null

        // jwtToken이 없는 경우 : 로그인
        // jwtToken이 있는 경우 : 로그인 이외의 모든 경우
        // 위의 두가지 케이스를 모두 처리하기 위해 jwtToken을 nullable로 선언
        jwtToken?.let{
            request = builder.putTokenHeader(jwtToken)
        } ?: run {
            request = builder.build()
        }

        val response : Response = chain.proceed(request!!)

        return response
    }

    private fun Request.Builder.putTokenHeader(token: String): Request {
        return this.addHeader("Authorization", token)
            .build()
    }
}