package com.example.supplement_tracker

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.preference.PreferenceManager

/* 자주쓰는 함수 재정의 */
class MHCustom{

    companion object{
        /* 설정정보 가져오기 */
        fun <T> Get_Pref(key: String, default: T, contex: Context): Any {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(contex)
            val FalseReturn = false
            return when (default) {
                is String -> sharedPreferences.getString(key, default)!!
                is Int -> sharedPreferences.getInt(key, default)!!
                is Float -> sharedPreferences.getFloat(key, default)!!
                is Boolean -> sharedPreferences.getBoolean(key, default)!!
                else -> return FalseReturn
            }
        }
        /* 설정정보 업데이트 하기 */
        fun <T> Put_Pref(key: String, putValue: T, contex: Context) {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(contex)
            val edit = sharedPreferences.edit()
            when(putValue){
                is String -> edit.putString(key,putValue)
                is Int -> edit.putInt(key,putValue)
                is Float -> edit.putFloat(key,putValue)
                is Boolean -> edit.putBoolean(key,putValue)
            }
            edit.apply()
        }

    }
}