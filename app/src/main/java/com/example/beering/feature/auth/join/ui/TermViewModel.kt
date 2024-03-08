package com.example.beering.feature.auth.join.ui

import SingleLiveEvent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.beering.BeeringApplication
import com.example.beering.data.ApiResult
import com.example.beering.data.auth.api.UserApi
import com.example.beering.data.auth.dto.JoinResponse
import com.example.beering.data.auth.repository.UserRepositoryImpl
import com.example.beering.data.onFail
import com.example.beering.data.onSuccess
import com.example.beering.feature.auth.join.domain.SignupUseCase
import kotlinx.coroutines.launch

class TermViewModel(
    private val signUp : SignupUseCase
) : ViewModel() {
    // UI state
    private val checkBoxList = ArrayList<Boolean>()
    private val _checkBoxes = MutableLiveData<ArrayList<Boolean>>()
    val checkBoxes: LiveData<ArrayList<Boolean>> = _checkBoxes
    private val _curTermIndex = MutableLiveData<Int>()
    val curTermIndex : LiveData<Int> = _curTermIndex

    // SingleLiveEvent
    private val _intentFlag = MutableLiveData<SingleLiveEvent<String>>()
    val intentFlag : LiveData<SingleLiveEvent<String>> = _intentFlag

    init{
        for (i in 0 .. 2){
            checkBoxList.add(false)
        }
    }

    fun toggleCheckBox(index : Int){
        checkBoxList[index] = !checkBoxList[index]
        _checkBoxes.value = checkBoxList
    }

    fun agreeTerm(){
        checkBoxList[curTermIndex.value!!] = !checkBoxList[curTermIndex.value!!]
        _checkBoxes.value = checkBoxList
    }

    fun checkAll(checked : Boolean){
        for(i in 0..2){
            checkBoxList[i] = checked
        }
        _checkBoxes.value = checkBoxList
    }

    fun setTermIndex(index : Int){
        _curTermIndex.value = index
    }

    fun signUp(id : String, pw : String, name : String){
        viewModelScope.launch {
            signUp(id, pw, name, checkBoxList)
                .onSuccess {
                    _intentFlag.value = SingleLiveEvent("complete")
                }
                .onFail {code, msg ->
                    when(code){
                        2010 -> SingleLiveEvent("request-error")
                        2011 -> SingleLiveEvent("request-email-valid-error")
                    }
                }
        }
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        val Factory : ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(
                modelClass: Class<T>, extras: CreationExtras
            ): T {
                val userDataSource = BeeringApplication.retrofit.create(UserApi::class.java)
                val signupUseCase = SignupUseCase(UserRepositoryImpl(userDataSource))

                return TermViewModel(signupUseCase) as T
            }
        }
    }
}