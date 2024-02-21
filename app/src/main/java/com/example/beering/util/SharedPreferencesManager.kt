package com.example.beering.util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.beering.BeeringApplication.Companion.mSharedPreferences
import com.example.beering.util.token.Jwt

fun changeLogin(state : Boolean){
//    val spf = context.getSharedPreferences("login",  AppCompatActivity.MODE_PRIVATE)
    val editor = mSharedPreferences.edit()

    editor.putBoolean("isLogin", state)
    editor.apply()
}

fun stateLogin() : Boolean{
//    val spf = context.getSharedPreferences("login",  AppCompatActivity.MODE_PRIVATE)

    return mSharedPreferences.getBoolean("isLogin", false)
}

fun setToken(token: Jwt){
//    val spf = context.getSharedPreferences("token",  AppCompatActivity.MODE_PRIVATE)
    val editor = mSharedPreferences.edit()

    editor.putString("accessToken", token.accessToken)
    editor.putString("refreshToken", token.refreshToken)
    editor.apply()
}

fun getAccessToken() : String?{
//    val spf = context.getSharedPreferences("token",  AppCompatActivity.MODE_PRIVATE)

    return mSharedPreferences.getString("accessToken", "")

}



fun getRefreshToken(context: Context) : String? {
    val spf = context.getSharedPreferences("token",  AppCompatActivity.MODE_PRIVATE)

    return spf.getString("refreshToken", "")

}

//멤버 조회를 위한 id값 저장
fun setMemberId(context: Context, id :Int){
    val spf = context.getSharedPreferences("id", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putInt("memberId", id)
    editor.apply()
}

fun getMemberId(context: Context) : Int {
    val spf = context.getSharedPreferences("id", AppCompatActivity.MODE_PRIVATE)
    return spf.getInt("memberId", -1)
}



