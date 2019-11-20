package me.spryn.noded.screens.home

import android.graphics.Color
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import me.spryn.noded.R
import me.spryn.noded.models.NotebookModel

class NotebookViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val notebook: Button = itemView.findViewById(R.id.notebook_name)

    fun bind(notebook: NotebookModel) {
        this.notebook.text = notebook.title
    }
}