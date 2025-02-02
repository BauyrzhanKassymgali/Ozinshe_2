package com.example.ozinshe.data

import com.example.ozinshe.data.model.LoginRequest
import com.example.ozinshe.data.model.LoginResponse
import com.example.ozinshe.data.model.MainMoviesResponse
import com.example.ozinshe.data.model.MoviesByCategoryMainModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @GET("/core/V1/movies_main")
    suspend fun getMainMovies(@Header("Authorization") token: String): MainMoviesResponse

    @POST("/auth/V1/signup")
    suspend fun registrationUser(@Body loginRequest: LoginRequest): LoginResponse

    @POST("/auth/V1/signin")
    suspend fun loginUser(@Body loginRequest: LoginRequest): LoginResponse

    @GET("/core/V1/movies_main")
    suspend fun getMainMoviesByCategory(@Header("Authorization") token: String): MoviesByCategoryMainModel
}