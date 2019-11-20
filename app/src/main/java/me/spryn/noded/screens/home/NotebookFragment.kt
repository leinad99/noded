package me.spryn.noded.screens.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import me.spryn.noded.R
import me.spryn.noded.databinding.FragmentNotebookBinding

/**
 * A simple [Fragment] subclass.
 */
class NotebookFragment : Fragment() {

    lateinit var notebookRecyclerView: RecyclerView
    lateinit var notebookListAdapter: NotebookListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notebookListAdapter = NotebookListAdapter(context, inflater)

        val binding: FragmentNotebookBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_notebook, container, false)

        notebookRecyclerView = binding.notebookList

        notebookRecyclerView.adapter = notebookListAdapter
        notebookRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }
}
