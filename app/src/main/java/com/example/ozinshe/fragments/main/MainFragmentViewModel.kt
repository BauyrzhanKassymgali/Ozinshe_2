package com.example.ozinshe.fragments.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ozinshe.data.ApiService
import com.example.ozinshe.data.model.LoginRequest
import com.example.ozinshe.data.model.LoginResponse
import com.example.ozinshe.data.model.MainMoviesResponse
import com.example.ozinshe.data.model.MoviesByCategoryMainModel
import com.example.ozinshe.objects.ServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragmentViewModel: ViewModel() {
    private var _mainMoviesResponse: MutableLiveData<MainMoviesResponse> = MutableLiveData()
    val mainMoviesResponse: LiveData<MainMoviesResponse> =_mainMoviesResponse

    private var _moviesByCategoryMainModel: MutableLiveData<MoviesByCategoryMainModel> = MutableLiveData()
    val moviesByCategoryMainModel: LiveData<MoviesByCategoryMainModel> =_moviesByCategoryMainModel

    private var _errorRespone: MutableLiveData<String> = MutableLiveData()
    val errorResponse: LiveData<String> =_errorRespone


    fun getMainMovies(token: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = ServiceBuilder.buildService(ApiService::class.java)

            runCatching {response.getMainMovies("Bearer $token")}
                .onSuccess {
                    _mainMoviesResponse.postValue(it)
                }
                .onFailure {
                    _errorRespone.postValue(it.message)
                }
        }
    }

    fun getMoviesByCategoryMain(token: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = ServiceBuilder.buildService(ApiService::class.java)

            runCatching {response.getMainMoviesByCategory("Bearer $token")}
                .onSuccess {
                    Log.d("AAA", "Серверный ответ: $it")
                    _moviesByCategoryMainModel.postValue(it)
                }
                .onFailure {
                    Log.d("AAA", "Ошибка: ${it.message}")
                    _errorRespone.postValue(it.message)
                }
        }
    }
}