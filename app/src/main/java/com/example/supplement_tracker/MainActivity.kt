package com.example.supplement_tracker

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.supplement_tracker.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        /* 프래그먼트 설정 */
        val fragment_List = listOf(list_Fragment(), report_Fragment(), setting_Fragment())
        val fragment_adapter = Fragment_Adapter(this)
        fragment_adapter.fragment_List = fragment_List
        binding.viewPager.adapter = fragment_adapter

        /* 메뉴 언어 설정 */
        var tab_Title: List<String> = listOf(
            resources.getString(R.string.Menu_1),
            resources.getString(R.string.Menu_2),
            resources.getString(R.string.Menu_3))
        TabLayoutMediator(binding.tabLayout, binding.viewPager) {
                tabLayout, position
            ->
            tabLayout.text = tab_Title[position]
        }.attach()
    }

    /* 다국어 설정 처리 */
    override fun attachBaseContext(newBase: Context) {

        var language = MHCustom.Get_Pref(setting_Fragment.language_pref_key,"first",newBase)
        var context = newBase
        val config = context.resources.configuration

        val sysLocal = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.locales[0]
        } else {
            config.locale
        }
        if(language == "first"){
            language = sysLocal.language
            MHCustom.Put_Pref(setting_Fragment.language_pref_key,sysLocal.language,context)
        }
        context = setting_Fragment().chagnelang(newBase, language as String)
        super.attachBaseContext(context)

    }
}








