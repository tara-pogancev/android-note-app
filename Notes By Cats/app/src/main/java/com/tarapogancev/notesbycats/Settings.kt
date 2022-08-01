package com.tarapogancev.notesbycats

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceActivity
import android.view.View
import android.widget.ImageView
import android.widget.Toast

class Settings : PreferenceActivity() {

    private lateinit var imageView_back: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.prefs)
/*
        imageView_back = findViewById(R.id.imageView_back)
        imageView_back.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                val intent: Intent = Intent(this@Settings, MainActivity::class.java)
                startActivity(intent)
            }

        })
 */

    }
}