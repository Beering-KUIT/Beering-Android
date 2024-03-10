package com.example.beering.data.auth.api

import com.example.beering.BeeringApplication

class TokenSpf {
    fun saveAccessToken(token: String) {
        val editor = BeeringApplication.mSharedPreferences.edit()
        editor.putString("accessToken", token).apply()
    }

    fun saveRefreshToken(token: String) {
        val editor = BeeringApplication.mSharedPreferences.edit()
        editor.putString("refreshToken", token).apply()
    }

    fun getAccessToken(): String? {
        return BeeringApplication.mSharedPreferences.getString("accessToken", null)
    }

    fun getRefreshToken(): String? {
        return BeeringApplication.mSharedPreferences.getString("refreshToken", null)
    }

}