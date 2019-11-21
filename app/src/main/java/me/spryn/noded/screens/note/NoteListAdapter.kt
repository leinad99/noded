package me.spryn.noded.screens.note

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import me.spryn.noded.R
import me.spryn.noded.models.NoteModel

class NoteListAdapter (
    private var notes: List<NoteModel>,
    context: Context?,
    private val inflater: LayoutInflater = LayoutInflater.from(context),
    private val color: String
) : RecyclerView.Adapter<NoteViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
            val view = inflater.inflate(R.layout.note_list_item_view, parent, false)

            return NoteViewHolder(view)
        }

        override fun getItemCount() = notes.size

        override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
            val note = notes[position]

            holder.apply {
                bind(note)
            }

            holder.itemView.setOnClickListener{openNote(it, note) }
        }

    private fun openNote(view: View, note: NoteModel){
        val action = NoteFragmentDirections.actionNoteFragmentToCreateNoteFragment(notebookName = note.notebookTitle, noteName = note.title, notebookColor = color)
        view.findNavController().navigate(action)
    }
}