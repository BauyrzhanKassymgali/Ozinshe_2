package com.example.ozinshe.data.model


import android.content.Context
import android.content.SharedPreferences



class SharedPref(private val context: Context) {
    private val sharedToken = "SAVE_TOKEN"

    private val preference: SharedPreferences
        get() = context.getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE)
    fun saveToken(token: String){
        preference.edit().putString(sharedToken, token).apply()
    }
    fun getToken(): String{
        return preference.getString(sharedToken, "without_token").toString()
    }

    fun clearShared(){
        preference.edit().clear().apply()
    }
}