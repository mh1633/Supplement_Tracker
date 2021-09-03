package com.example.supplement_tracker

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.supplement_tracker.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity(), View.OnClickListener {

    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}
    var initTime = 0L

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
            resources.getString(R.string.Menu_3)
        )
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tabLayout, position
            ->
            tabLayout.text = tab_Title[position]
        }.attach()

        /* 기능 구현 */
        with(binding) {
            imgSerachButton.setOnClickListener(this@MainActivity)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.imgSerachButton.id -> checkPermission()
        }
    }

    /* 권한 체크 */
    fun checkPermission() {
        val cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val storagePermission = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)

        if(cameraPermission == PackageManager.PERMISSION_GRANTED && storagePermission == PackageManager.PERMISSION_GRANTED){
            val intent = Intent(applicationContext, SearchIMG::class.java)
            startActivity(intent)
        }
        else requestPermission(this)
    }

    /* 권한 요청 */
    fun requestPermission(activity: Activity) {
        val arrayPermission = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE)
        ActivityCompat.requestPermissions(activity, arrayPermission,99)
    }

    /* 권한체크 응답 */
    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        var permission_enable = false
        when (requestCode) {
            99 -> {
                for (i in grantResults) {
                    permission_enable = i == PackageManager.PERMISSION_GRANTED
                }
                if (permission_enable == true) {
                    val intent = Intent(applicationContext, SearchIMG::class.java)
                    startActivity(intent)
                }
                else {
                    Toast.makeText(this,"Need Camera Permission",Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    /* 종료시 한번 더 눌러주세요 팝업 */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(System.currentTimeMillis() - initTime > 3000){
                Toast.makeText(this,R.string.Toast_App_finish,Toast.LENGTH_LONG).show()
                initTime = System.currentTimeMillis()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    /* 다국어 설정 처리 */
    override fun attachBaseContext(newBase: Context) {

        var language = MHCustom.Get_Pref(setting_Fragment.language_pref_key, "first", newBase)
        var context = newBase
        val config = context.resources.configuration

        val sysLocal = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.locales[0]
        } else {
            config.locale
        }
        if (language == "first") {
            language = sysLocal.language
            MHCustom.Put_Pref(setting_Fragment.language_pref_key, sysLocal.language, context)
        }
        context = setting_Fragment().chagnelang(newBase, language as String)
        super.attachBaseContext(context)

    }


}









