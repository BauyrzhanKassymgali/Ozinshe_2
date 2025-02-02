package com.example.ozinshe.fragments.registration

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

class RegistrationViewModel: ViewModel() {
    private var _registrationResponse: MutableLiveData<LoginResponse> = MutableLiveData()
    val registrationResponse: LiveData<LoginResponse> =_registrationResponse

    private var _errorRespone: MutableLiveData<String> = MutableLiveData()
    val errorResponse: LiveData<String> =_errorRespone


    fun registrationUser(email: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = ServiceBuilder.buildService(ApiService::class.java)

            runCatching {response.registrationUser(LoginRequest(email, password))}
                .onSuccess {
                    _registrationResponse.postValue(it)
                }
                .onFailure {
                    _errorRespone.postValue(it.message)
                }

        }
    }
}