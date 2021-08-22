package com.example.supplement_tracker

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.supplement_tracker.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)  }
    val shared_preference by lazy {PreferenceManager.getDefaultSharedPreferences(this)} /* 설정제어 변수 */
    val setting_value by lazy { Configuration() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    /* 프래그먼트 설정 */
        val fragment_List = listOf(list_Fragment(),report_Fragment(),setting_Fragment())
        val fragment_adapter = Fragment_Adapter(this)
        fragment_adapter.fragment_List = fragment_List
        binding.viewPager.adapter = fragment_adapter

    /* 메뉴 언어 설정 */
        var tab_Title: List<String> = listOf(
            resources.getString(R.string.Menu_1),
            resources.getString(R.string.Menu_2),
            resources.getString(R.string.Menu_3))
        TabLayoutMediator(binding.tabLayout, binding.viewPager){tabLayout, position
            -> tabLayout.text = tab_Title[position]}.attach()

    }

    /* 언어설정 로딩하기 */
//    fun setLanguage() {
//        val setting_language = shared_preference.getString("option_language",null) ?: "en"
//        Log.d("언어",setting_language)
//        if(setting_language == "ko"){
//            setting_value.setLocale(Locale("ko"))
////            startActivity(Intent(this,MainActivity::class.java))
//
//        } else {
//            setting_value.setLocale(Locale("en"))
//            recreate()
//        }
//    }
}









