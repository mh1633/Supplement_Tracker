package com.example.supplement_tracker

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.preference.PreferenceManager
import java.io.IOException

const val logmsg = "테스트"

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

/* 사진찍은거 비트맵으로 만들기 */
fun loadBitmap(photoURI: Uri, contex: Context): Bitmap? {
    var image: Bitmap? = null
    try {
        image = if (Build.VERSION.SDK_INT > 27) {
            val source: ImageDecoder.Source = ImageDecoder.createSource(contex.contentResolver, photoURI)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(contex.contentResolver, photoURI)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    /* 테서렉트는 ARGB_8888만 인식함 */
    return image?.copy(Bitmap.Config.ARGB_8888, true)
}


