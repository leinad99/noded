package me.spryn.noded.screens.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import me.spryn.noded.R
import me.spryn.noded.databinding.FragmentNotebookBinding
import me.spryn.noded.models.NotebookModel
import me.spryn.noded.screens.createNotebook.CreateNotebookFragmentDirections


class NotebookListAdapter(
    private var notebooks: List<NotebookModel>,
    context: Context?,
    private val inflater: LayoutInflater = LayoutInflater.from(context)
) : RecyclerView.Adapter<NotebookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotebookViewHolder {
        val view = inflater.inflate(R.layout.notebook_list_item_view, parent, false)

        return NotebookViewHolder(view)
    }

    override fun getItemCount() = notebooks.size

    override fun onBindViewHolder(holder: NotebookViewHolder, position: Int) {
        val notebook = notebooks[position]

        holder.apply {
            bind(notebook)
        }
        holder.itemView.setBackgroundColor(notebook.color.toInt())
        holder.itemView.setOnClickListener{openNotebook(it, notebook) }
    }

    private fun openNotebook(view: View, notebook: NotebookModel){
        val action = NotebookFragmentDirections.actionNotebookFragmentToNoteFragment(notebookColor = notebook.color, notebookID = notebook.ID)
        view.findNavController().navigate(action)
    }

}