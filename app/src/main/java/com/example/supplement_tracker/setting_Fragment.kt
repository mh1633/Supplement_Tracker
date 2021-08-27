package com.example.supplement_tracker

import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.preference.*
import java.util.*

class setting_Fragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    companion object {
        val language_pref_key = "option_language"
        val alarm_enable_key = "switch_alarm_enable"
        val alarm_time_key = "option_timer"
    }

    val language_preference: ListPreference? by lazy { findPreference(language_pref_key) }
    val alarm_enable: SwitchPreference? by lazy { findPreference(alarm_enable_key) }
    val alarm_time_value: ListPreference? by lazy { findPreference(alarm_time_key) }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)

        val sharedPreferences: SharedPreferences? =
            PreferenceManager.getDefaultSharedPreferences(activity)
        sharedPreferences?.registerOnSharedPreferenceChangeListener(this)

        /* 셋업 선택정보 summary 표시 */
            view_preference_summary(language_preference, language_pref_key)
            view_preference_summary(alarm_enable, alarm_enable_key)
            view_preference_summary(alarm_time_value, alarm_time_key)

        /* 언어 설정 변경 */

    }

    /*  셋업 선택정보 summary 표시 함수  */
    fun <T> view_preference_summary(preference_Type: T, findPreferenceKey: String) {

        when (preference_Type) {
            is ListPreference -> {
                language_preference?.summaryProvider =
                    Preference.SummaryProvider<ListPreference> { preference ->
                        val text = preference.entry
                        if (TextUtils.isEmpty(text.toString())) {
                            "Not Set"
                        } else {
                            text.toString()
                        }
                    }
            }
            is SwitchPreference -> {
                language_preference?.summaryProvider =
                    Preference.SummaryProvider<SwitchPreference> { preference ->
                        val text = preference.isChecked
                        if (TextUtils.isEmpty(text.toString())) {
                            "Not Set"
                        } else {
                            text.toString()
                        }
                    }
            }
        }
    }

    /* 설정 변경 캐치 */
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (activity != null) {
            when (key) {
                language_pref_key -> {
                    Toast.makeText(activity, language_preference?.entry, Toast.LENGTH_LONG).show()
                    activity?.let { chagnelang(it, language_preference?.value.toString()) }
                    activity?.recreate()
                }
            }
        }
    }

    /* 언어 변경처리 함수 */
    fun chagnelang(context: Context, lang_code: String): ContextWrapper {

        var context = context
        lateinit var sysLocale: Locale
        val config = context.resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sysLocale = config.locales[0]
        } else {
            sysLocale = config.locale
        }
        if (lang_code != null || lang_code != sysLocale.language) {
            val locale = Locale(lang_code)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                config.setLocale(locale)
            } else {
                config.locale = locale
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                context = context.createConfigurationContext(config)
            } else {
                context.resources.updateConfiguration(config, context.resources.displayMetrics)
            }
        }
        return ContextWrapper(context)
    }
}
