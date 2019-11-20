package me.spryn.noded.screens.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
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