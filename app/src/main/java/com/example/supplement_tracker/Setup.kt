package com.example.supplement_tracker

import android.content.Context
import android.os.Build
import android.view.ContextThemeWrapper
import java.util.*

class Utilization {

}

class language_change(base: Context) : ContextThemeWrapper(base, R.style.AppTheme) {
    companion object {

        fun wrap(context: Context, language: String) : ContextThemeWrapper {
            var ctx = context
            val config = context.resources.configuration

            if (language != "" || language != "en") {

            } else {
                val locale = Locale(language)
                Locale.setDefault(locale)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    config.setLocale(locale)
                    config.setLayoutDirection(locale)
                    ctx = context.createConfigurationContext(config)
                }
                /*
                Save the selected language in shared Preference,
                context.putString("my_lang", language)
                */
            }
            return language_change(ctx)
        }
    }
}