package com.example.ozinshe.data.model


import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email")
    val email: String, // string12@mail.com
    @SerializedName("password")
    val password: String // string12
)