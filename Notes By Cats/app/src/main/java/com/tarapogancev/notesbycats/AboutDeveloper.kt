package com.tarapogancev.notesbycats

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView


class AboutDeveloper : AppCompatActivity() {

    private lateinit var cardView_email: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_developer)

        cardView_email = findViewById(R.id.cardView_email)

        cardView_email.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent = Intent(
                    Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "tarapogancev@gmail.com", null
                    )
                )
                startActivity(Intent.createChooser(intent, "Choose an Email client:"))
            }
        })


    }

}