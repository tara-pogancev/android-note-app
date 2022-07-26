package com.tarapogancev.notesbycats

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.tarapogancev.notesbycats.adapter.NoteListAdapter
import com.tarapogancev.notesbycats.database.RoomDB
import com.tarapogancev.notesbycats.databinding.ActivityMainBinding
import com.tarapogancev.notesbycats.model.Note
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var noteListAdapter:NoteListAdapter
    private var notes: List<Note> = ArrayList<Note>()
    private lateinit var database: RoomDB
    private lateinit var floatingActionButton: FloatingActionButton

    private val noteClickListener = object : NoteClickListener {
        override fun onClick(note: Note): Void {
            TODO("Not yet implemented")
        }

        override fun onLongClick(note: Note, cardView: CardView): Void {
            TODO("Not yet implemented")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_home)
        floatingActionButton = findViewById(R.id.fab_add)

        database = RoomDB.getInstance(this)
        notes = database.mainDAO().getAll()

        updateRecycler(notes)

        floatingActionButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent: Intent = Intent(this@MainActivity, NotesTakerActivity::class.java)
                startActivityForResult(intent, 101)

            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                var newNote: Note = data?.getSerializableExtra("note") as Note
                database.mainDAO().insert(newNote)
                notes = (database.mainDAO().getAll())
                noteListAdapter.notifyDataSetChanged()
                updateRecycler(notes)

            }
        }
    }

    private fun updateRecycler(notes: List<Note>) {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        noteListAdapter = NoteListAdapter(this@MainActivity, notes, noteClickListener)
        recyclerView.adapter = noteListAdapter
    }

}