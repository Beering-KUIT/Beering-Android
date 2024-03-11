package com.example.beering.feature.auth.login.ui

import SingleLiveEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.beering.BeeringApplication
import com.example.beering.data.auth.api.TokenSpf
import com.example.beering.data.auth.api.UserApi
import com.example.beering.data.auth.repository.UserRepositoryImpl
import com.example.beering.data.onFail
import com.example.beering.data.onSuccess
import com.example.beering.feature.auth.join.domain.UserValidationUseCase
import com.example.beering.feature.auth.join.ui.JoinViewModel
import com.example.beering.feature.auth.login.domain.LoginUseCase
import com.example.beering.util.changeLogin
import kotlinx.coroutines.launch

class LoginViewModel(
    val login : LoginUseCase
) : ViewModel(){
    // UI State
    private val _id = MutableLiveData<String>()
    val id : LiveData<String> = _id
    private val _pw = MutableLiveData<String>()
    val pw : LiveData<String> = _pw

    // SingleLiveEvent
    private val _loginSuccess = MutableLiveData<SingleLiveEvent<Boolean>>()
    val loginSuccess : LiveData<SingleLiveEvent<Boolean>> = _loginSuccess
    private val _loginError = MutableLiveData<SingleLiveEvent<Int>>()
    val loginError : LiveData<SingleLiveEvent<Int>> = _loginError

    init{
        _id.value = ""
        _pw.value = ""
    }

    fun setId(id : String){
        _id.value = id
    }

    fun setPw(pw : String){
        _pw.value = pw
    }

    fun login(){
        viewModelScope.launch{
            login(id.value!!, pw.value!!)
                .onSuccess {
                    login.saveTokens(it.jwtInfo.accessToken, it.jwtInfo.refreshToken)
                    changeLogin(true)
                    _loginSuccess.value = SingleLiveEvent(true)
                }
                .onFail{code, msg ->
                    _loginError.value = SingleLiveEvent(code)
                }
        }
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
            ): T {
                val userDataSource = BeeringApplication.retrofit.create(UserApi::class.java)
                val loginUseCase = LoginUseCase(UserRepositoryImpl(userDataSource, TokenSpf()))

                return LoginViewModel(
                    loginUseCase
                ) as T
            }
        }
    }

}