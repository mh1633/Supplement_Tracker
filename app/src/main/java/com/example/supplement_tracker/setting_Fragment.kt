package com.example.supplement_tracker

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat


class setting_Fragment : PreferenceFragmentCompat(), PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)
    }

    override fun onPreferenceStartFragment(caller: PreferenceFragmentCompat?, pref: Preference?): Boolean {
        /* 새 옵션 프래그먼트 구현 */
        return true
    }

}
