package com.example.ozinshe.data.model


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("id")
    val id: Int, // 25800
    @SerializedName("username")
    val username: String, // string12@mail.com
    @SerializedName("email")
    val email: String, // string12@mail.com
    @SerializedName("roles")
    val roles: List<String>,
    @SerializedName("accessToken")
    val accessToken: String, // eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdHJpbmcxMkBtYWlsLmNvbSIsImlhdCI6MTcyNTczODE0OCwiZXhwIjoxNzU3Mjc0MTQ4fQ.4e8UPnD3YDIiSlTpE8tfFCjRZihYp08d8Ie7_dD3ypfzAecB-f8hCVAx6HcdULrJABWhFgBj49WP3hpHuFj1kA
    @SerializedName("tokenType")
    val tokenType: String // Bearer
)