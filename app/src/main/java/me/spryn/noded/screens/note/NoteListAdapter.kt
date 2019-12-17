package me.spryn.noded.screens.note

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import me.spryn.noded.R
import me.spryn.noded.models.NoteModel
import me.spryn.noded.ui.colorBlendDark
import me.spryn.noded.ui.pickTextColorBasedOnBgColorSimple

class NoteListAdapter (
    private var notes: List<NoteModel>,
    context: Context?,
    private val inflater: LayoutInflater = LayoutInflater.from(context),
    private val color: String,
    private val notebookTitle: String
) : RecyclerView.Adapter<NoteViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
            val view = inflater.inflate(R.layout.note_item_list_view, parent, false)

            return NoteViewHolder(view)
        }

        override fun getItemCount() = notes.size

        override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
            val note = notes[position]

            holder.apply {
                bind(note)
            }

            val button = holder.itemView as Button
            val hex = String.format("#%06X", 0xFFFFFF and color.toInt())
            button.setTextColor(Color.parseColor(pickTextColorBasedOnBgColorSimple(hex)))

            holder.itemView.backgroundTintList = ColorStateList.valueOf(colorBlendDark(color.toInt()))

            holder.itemView.setOnClickListener{openNote(it, note) }
        }

    private fun openNote(view: View, note: NoteModel){
        val action = NoteFragmentDirections.actionNoteFragmentToCreateNoteFragment(notebookID = note.notebookID, noteID = note.ID, notebookColor = color, notebookName = notebookTitle)
        view.findNavController().navigate(action)
    }
}