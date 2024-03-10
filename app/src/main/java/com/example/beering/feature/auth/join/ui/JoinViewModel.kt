package com.example.beering.feature.auth.join.ui

import SingleLiveEvent
import android.util.Log
import android.widget.Toast
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
import com.example.beering.feature.auth.join.model.NameValidations
import com.example.beering.feature.auth.join.model.PwValidations
import com.example.beering.feature.auth.join.domain.UserValidationUseCase
import kotlinx.coroutines.launch

class JoinViewModel(
    private val validation : UserValidationUseCase
) : ViewModel() {
    // UI state
    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String> = _userId
    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password
    private val _passwordAgain = MutableLiveData<String>()
    val passwordAgain: LiveData<String> = _passwordAgain
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name
    private val _nicknameValidation = MutableLiveData<NameValidations>()
    val nicknameValidation: LiveData<NameValidations> = _nicknameValidation
    private val _pwValidation = MutableLiveData<PwValidations>()
    val pwValidation: LiveData<PwValidations> = _pwValidation

    // 다음 버튼 활성화 조건
    private val _idCheck = MutableLiveData<DuplicationCheck>()   // 아이디 중복확인
    val idCheck: LiveData<DuplicationCheck> = _idCheck
    private val _nicknameCheck = MutableLiveData<DuplicationCheck>()     // 닉네임 중복확인
    val nicknameCheck: LiveData<DuplicationCheck> = _nicknameCheck
    private val _validNext = MutableLiveData<Boolean>()     // 최종 활성화 여부
    val validNext: LiveData<Boolean> = _validNext

    // SingleLiveEvent
    private val _snackBarEvent = MutableLiveData<SingleLiveEvent<String>>()
    val snackBarEvent : LiveData<SingleLiveEvent<String>> = _snackBarEvent

    init{
        _userId.value = ""
        _password.value = ""
        _passwordAgain.value = ""
        _name.value = ""
        _idCheck.value = DuplicationCheck.PROCEEDING
        _nicknameCheck.value = DuplicationCheck.PROCEEDING
    }
    fun setUserId(id : String){
        _userId.value = id
        _idCheck.value = DuplicationCheck.PROCEEDING
        validNext()
    }

    fun setPassword(pw : String){
        _password.value = pw
        _pwValidation.value = validation.validatePw(pw, passwordAgain.value!!)
        validNext()
    }

    fun setPasswordAgain(pwAgain : String){
        _passwordAgain.value = pwAgain
        _pwValidation.value = validation.validatePw(password.value!!, pwAgain)
        validNext()
    }

    fun setName(name : String){
        _name.value = name
        _nicknameValidation.value = validation.validateName(name)
        _nicknameCheck.value = DuplicationCheck.PROCEEDING
        validNext()
    }

    fun validNext(){
        if (pwValidation.value == null){
            return
        }
        Log.d("validLog", "${pwValidation.value!!.valid}\n" +
                "                && ${pwValidation.value!!.isConfirmed}\n" +
                "                && ${nicknameCheck.value}\n" +
                "                && ${idCheck.value}")
        _validNext.value = (pwValidation.value!!.valid
                && pwValidation.value!!.isConfirmed
                && nicknameCheck.value == DuplicationCheck.CHECKED
                && idCheck.value == DuplicationCheck.CHECKED)
    }

    fun checkId(){
        val cleanEmail = userId.value!!.trim()
        Log.d("userId", cleanEmail)
        viewModelScope.launch {
            val apiResult = validation.checkId(cleanEmail)
            Log.d("ususus", apiResult.toString())
            apiResult
                .onSuccess {
                    if (it.available){
                        _idCheck.value = DuplicationCheck.CHECKED
                    } else {
                        _idCheck.value = DuplicationCheck.UNCHECKED
                    }
                }
                .onFail {code, message ->
                    Log.d("Join CheckId-Fail", message)
                    when(code){
                        2010 -> _snackBarEvent.value = SingleLiveEvent("아이디 입력 형식을 다시 한 번 확인해 주세요.")
                    }
                }
            validNext()
        }
    }

    fun checkNickname(){
        viewModelScope.launch {
            validation.checkNickname(name.value!!)
                .onSuccess {
                    if (it.available){
                        _nicknameCheck.value = DuplicationCheck.CHECKED
                    } else {
                        _nicknameCheck.value = DuplicationCheck.UNCHECKED
                    }
                }
                .onFail {code, message ->
                    Log.d("Join CheckNickName-NetworkError", message)
                    when (code){
                        2010 -> _snackBarEvent.value = SingleLiveEvent("닉네임 입력 형식을 다시 한 번 확인해 주세요.")
                    }
                }
            validNext()
        }
    }

    // 뷰모델 의존성 주입을 위한 Factory
    companion object {
        enum class DuplicationCheck{
            PROCEEDING, UNCHECKED, CHECKED
        }

        @Suppress("UNCHECKED_CAST")
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
            ): T {
                val userDataSource = BeeringApplication.retrofit.create(UserApi::class.java)
                val userValidationUseCase = UserValidationUseCase(UserRepositoryImpl(userDataSource, TokenSpf()))

                return JoinViewModel(
                    userValidationUseCase
                ) as T
            }
        }
    }
}