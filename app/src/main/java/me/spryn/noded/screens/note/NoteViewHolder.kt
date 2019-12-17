package me.spryn.noded.screens.note

import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import me.spryn.noded.R
import me.spryn.noded.models.NoteModel

class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val note: Button = itemView.findViewById(R.id.note_name)

    fun bind(note: NoteModel) {
        this.note.text = note.title
    }
}