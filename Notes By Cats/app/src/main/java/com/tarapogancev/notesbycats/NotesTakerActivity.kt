package com.tarapogancev.notesbycats

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.tarapogancev.notesbycats.model.Note
import java.text.SimpleDateFormat
import java.util.*

class NotesTakerActivity : AppCompatActivity() {

    private lateinit var editText_title: EditText
    private lateinit var editText_text: EditText
    private lateinit var imageView_save: ImageView
    private lateinit var note: Note


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_taker)
        setSupportActionBar(findViewById(R.id.toolbar_notes))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        imageView_save = findViewById(R.id.imageView_save)
        editText_text = findViewById(R.id.editText_text)
        editText_title = findViewById(R.id.editText_title)

        var isOldNote: Boolean = false
        var note: Note =  Note()
        try {
            note = intent.getSerializableExtra("old_note") as Note
            editText_text.setText(note.text)
            editText_title.setText(note.title)
            isOldNote = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        imageView_save.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var title: String = editText_title.text.toString()
                var text: String = editText_text.text.toString()

                if (title.isEmpty()) {
                    Toast.makeText(this@NotesTakerActivity, "Please enter note title.", Toast.LENGTH_SHORT)
                    return
                } else {
                    var formatter: SimpleDateFormat = SimpleDateFormat("EEE, dd/MM/yy")
                    var date: Date = Date()

                    if (!isOldNote) {
                        note = Note()
                    }
                    note.title = title
                    note.text = text
                    note.timestamp = "Edited: " + formatter.format(date)

                    val intent: Intent = Intent()
                    intent.putExtra("note", note)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}