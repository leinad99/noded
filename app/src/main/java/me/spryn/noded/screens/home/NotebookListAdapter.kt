package me.spryn.noded.screens.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import me.spryn.noded.R
import me.spryn.noded.models.NotebookModel


class NotebookListAdapter(
    private var notebooks: List<NotebookModel>,
    context: Context?,
    private val inflater: LayoutInflater = LayoutInflater.from(context)
) : RecyclerView.Adapter<NotebookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotebookViewHolder {
        val view = inflater.inflate(R.layout.notebook_list_item_view, parent, false)
        val newNotebook: Button = view.findViewById(R.id.create_notebook_button) as Button
        newNotebook.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_notebookFragment_to_createNotebookFragment)
        }
        return NotebookViewHolder(view)
    }

    override fun getItemCount() = notebooks.size

    override fun onBindViewHolder(holder: NotebookViewHolder, position: Int) {
        val notebook = notebooks[position]

        holder.apply {
            bind(notebook)
        }
    }

}