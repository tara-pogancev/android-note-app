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

class Settings : PreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.prefs)
        loadSettings()
    }

    fun loadSettings() {
        var sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        var darkMode: Boolean = sharedPreferences.getBoolean("DarkTheme", false)
        if (darkMode) {
            setTheme(R.style.Theme_NotesByCatsDark)
        } else {
            setTheme(R.style.Theme_NotesByCats)
        }

        var checkBox_nigth: CheckBoxPreference = findPreference("DarkTheme") as CheckBoxPreference
        checkBox_nigth.setOnPreferenceChangeListener(object: Preference.OnPreferenceChangeListener {
            override fun onPreferenceChange(p0: Preference?, p1: Any?): Boolean {

                var darkMode: Boolean = p1 as Boolean
                if (darkMode) {
                    setTheme(R.style.Theme_NotesByCatsDark)
                } else {
                    setTheme(R.style.Theme_NotesByCats)
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