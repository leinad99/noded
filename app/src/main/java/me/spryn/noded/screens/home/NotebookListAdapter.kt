package me.spryn.noded.screens.home

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import me.spryn.noded.R
import me.spryn.noded.models.NotebookModel
import me.spryn.noded.ui.pickTextColorBasedOnBgColorSimple
import android.graphics.Color


class NotebookListAdapter(
    private var notebooks: List<NotebookModel>,
    context: Context?,
    private val inflater: LayoutInflater = LayoutInflater.from(context)
) : RecyclerView.Adapter<NotebookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotebookViewHolder {
        val view = inflater.inflate(R.layout.note_item_list_view, parent, false)

        return NotebookViewHolder(view)
    }

    override fun getItemCount() = notebooks.size

    override fun onBindViewHolder(holder: NotebookViewHolder, position: Int) {
        val notebook = notebooks[position]

        holder.apply {
            bind(notebook)
        }
        val button = holder.itemView as Button
        val hex = String.format("#%06X", 0xFFFFFF and notebook.color.toInt())
        button.setTextColor(Color.parseColor(pickTextColorBasedOnBgColorSimple(hex)))

        holder.itemView.backgroundTintList = ColorStateList.valueOf(notebook.color.toInt())
        holder.itemView.setOnClickListener{openNotebook(it, notebook) }
    }

    private fun openNotebook(view: View, notebook: NotebookModel){
        val action = NotebookFragmentDirections.actionNotebookFragmentToNoteFragment(notebookColor = notebook.color, notebookName = notebook.title)
        view.findNavController().navigate(action)
    }

}