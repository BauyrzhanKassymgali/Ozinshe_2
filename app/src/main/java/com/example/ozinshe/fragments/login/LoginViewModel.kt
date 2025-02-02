package com.example.ozinshe.fragments.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ozinshe.data.ApiService
import com.example.ozinshe.data.model.LoginRequest
import com.example.ozinshe.data.model.LoginResponse
import com.example.ozinshe.objects.ServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    private var _loginResponse: MutableLiveData<LoginResponse> = MutableLiveData()
    val loginResponse: LiveData<LoginResponse> =_loginResponse

    private var _errorRespone: MutableLiveData<String> = MutableLiveData()
    val errorResponse: LiveData<String> =_errorRespone


    fun loginUser(email: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = ServiceBuilder.buildService(ApiService::class.java)

            runCatching {response.loginUser(LoginRequest(email, password))}
                .onSuccess {
                    _loginResponse.postValue(it)
                }
                .onFailure {
                    _errorRespone.postValue(it.message)
                }

        }
    }
}