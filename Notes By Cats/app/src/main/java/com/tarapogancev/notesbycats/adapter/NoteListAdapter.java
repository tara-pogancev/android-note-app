package com.tarapogancev.notesbycats.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.tarapogancev.notesbycats.NoteClickListener;
import com.tarapogancev.notesbycats.R;
import com.tarapogancev.notesbycats.model.Note;

import java.util.List;


public class NoteListAdapter extends RecyclerView.Adapter<NotesViewHolder> {

    Context context;
    List<Note> list;
    NoteClickListener listener;

    public NoteListAdapter(Context context, List<Note> list, NoteClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.note_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.note_title.setText(list.get(position).getTitle());
        holder.note_title.setSelected(true);

        holder.textView_note.setText(list.get(position).getText());

        holder.textView_date.setText(list.get(position).getTimestamp());
        holder.textView_date.setSelected(true);

        if (list.get(position).getPinned()) {
            holder.imageView_pin.setImageResource(R.drawable.ic_baseline_push_pin_24);
        } else {
            holder.imageView_pin.setImageResource(0);
        }

        holder.note_card.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                listener.onClick(list.get(holder.getAdapterPosition()));
            }
        });

        holder.note_card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClick(list.get(holder.getAdapterPosition()), holder.note_card);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

class NotesViewHolder extends RecyclerView.ViewHolder {

    CardView note_card;
    TextView note_title;
    TextView textView_note;
    TextView textView_date;
    ImageView imageView_pin;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        note_card = itemView.findViewById(R.id.note_card);
        note_title = itemView.findViewById(R.id.note_title);
        textView_note = itemView.findViewById(R.id.textView_note);
        textView_date = itemView.findViewById(R.id.textView_date);
        imageView_pin = itemView.findViewById(R.id.imageView_pin);
    }
}
