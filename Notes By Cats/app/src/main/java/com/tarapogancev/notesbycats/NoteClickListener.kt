package com.tarapogancev.notesbycats;

import androidx.cardview.widget.CardView;

import com.tarapogancev.notesbycats.model.Note;

interface NoteClickListener {

    fun onClick(note: Note);
    fun onLongClick(note: Note, cardView: CardView);

}
