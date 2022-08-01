package com.tarapogancev.notesbycats

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
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

class MainActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener  {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView_home: SearchView
    private lateinit var noteListAdapter:NoteListAdapter
    private var notes: List<Note> = ArrayList<Note>()
    private lateinit var database: RoomDB
    private lateinit var floatingActionButton: FloatingActionButton
    private var selectedNote: Note = Note()
    private lateinit var imageView_menu: ImageView

    private val noteClickListener = object : NoteClickListener {
        override fun onClick(note: Note) {
            var intent: Intent = Intent(this@MainActivity, NotesTakerActivity::class.java)
            intent.putExtra("old_note", note)
            startActivityForResult(intent, 102)
        }

        override fun onLongClick(note: Note, cardView: CardView) {
            selectedNote = note
            showPopup(cardView)
        }
    }

    private fun showPopup(cardView: CardView) {
        var popupMenu: PopupMenu = PopupMenu(this, cardView)
        popupMenu.setOnMenuItemClickListener(this@MainActivity)
        popupMenu.inflate(R.menu.popup_menu)
        popupMenu.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_home)
        floatingActionButton = findViewById(R.id.fab_add)
        searchView_home = findViewById(R.id.searchView_home)
        searchView_home.setBackgroundResource(R.drawable.bg_white_rounded)
        imageView_menu = findViewById(R.id.imageView_menu)

        database = RoomDB.getInstance(this)
        notes = database.mainDAO().getAll()

        updateRecycler(notes)

        floatingActionButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent: Intent = Intent(this@MainActivity, NotesTakerActivity::class.java)
                startActivityForResult(intent, 101)

            }
        })

        searchView_home.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                filter(p0)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                filter(p0)
                return true
            }
        })

        imageView_menu.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var popupMenu: PopupMenu = PopupMenu(this@MainActivity, imageView_menu)
                popupMenu.setOnMenuItemClickListener(this@MainActivity)
                popupMenu.inflate(R.menu.main_menu)
                popupMenu.show()
            }

        })

    }

    private fun filter(p0: String?) {
        var filteredList: ArrayList<Note> = ArrayList()
        for (singleNote: Note in notes) {
            if (p0 != null) {
                if (singleNote.title.lowercase().contains(p0.lowercase()) || singleNote.text.lowercase().contains(p0.lowercase())) {
                    filteredList.add(singleNote)
                }
            }
        }
        updateRecycler(filteredList)
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
        } else if (requestCode == 102) {
            if (resultCode == Activity.RESULT_OK) {
                var newNote: Note = data?.getSerializableExtra("note") as Note
                database.mainDAO().update(newNote.id, newNote.title, newNote.text, newNote.timestamp)
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

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item != null) {
            when (item.itemId) {
                R.id.pin -> {
                    selectedNote.pinned =  !selectedNote.pinned
                    database.mainDAO().updatePinned(selectedNote.id, selectedNote.pinned)
                    if (selectedNote.pinned) {
                        Toast.makeText(this@MainActivity, "Note pinned!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@MainActivity, "Note unpinned!", Toast.LENGTH_SHORT).show()
                    }

                    notes = (database.mainDAO().getAll())
                    updateRecycler(notes)

                    return true
                }

                R.id.delete -> {
                    database.mainDAO().delete(selectedNote)
                    notes = (database.mainDAO().getAll())
                    updateRecycler(notes)
                    Toast.makeText(this@MainActivity, "Note deleted.", Toast.LENGTH_SHORT).show()
                    return true
                }

                R.id.menu_settings -> {
                    val intent: Intent = Intent(this@MainActivity, Settings::class.java)
                    startActivity(intent)
                    return true
                }

                R.id.menu_developer_info -> {
                    val intent: Intent = Intent(this@MainActivity, AboutDeveloper::class.java)
                    startActivity(intent)
                    return true
                }

            }
        }

        return true
    }
}

