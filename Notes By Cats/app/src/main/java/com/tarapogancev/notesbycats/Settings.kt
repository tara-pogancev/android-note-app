package com.tarapogancev.notesbycats

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.CheckBoxPreference
import android.preference.Preference
import android.preference.PreferenceActivity
import android.preference.PreferenceManager
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate

class Settings : PreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.prefs)
        loadSettings()
    }

    fun loadSettings() {
        var checkBox_nigth: CheckBoxPreference = findPreference("DarkTheme") as CheckBoxPreference
        if (checkBox_nigth.isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        checkBox_nigth.setOnPreferenceChangeListener(object: Preference.OnPreferenceChangeListener {
            override fun onPreferenceChange(p0: Preference?, p1: Any?): Boolean {

                var darkMode: Boolean = p1 as Boolean
                if (darkMode) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }

                recreate()
                return true
            }

        })
    }

    override fun onResume() {
        loadSettings()
        super.onResume()
    }
}