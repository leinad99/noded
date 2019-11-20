package me.spryn.noded.screens.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import me.spryn.noded.R
import me.spryn.noded.databinding.FragmentNotebookBinding
import me.spryn.noded.models.NotebookModel


class NotebookListAdapter(
    private var notebooks: List<NotebookModel>,
    context: Context?,
    private val inflater: LayoutInflater = LayoutInflater.from(context)
) : RecyclerView.Adapter<NotebookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotebookViewHolder {
        val view = inflater.inflate(R.layout.notebook_list_item_view, parent, false)

        val binding: FragmentNotebookBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_notebook, parent, false)
        binding.createNotebookButton.setOnClickListener {
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