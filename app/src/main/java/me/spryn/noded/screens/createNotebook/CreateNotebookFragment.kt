package me.spryn.noded.screens.createNotebook


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import me.spryn.noded.R
import me.spryn.noded.database.DataManager.saveNotebook
import me.spryn.noded.databinding.FragmentCreateNotebookBinding
import me.spryn.noded.models.NotebookModel


/**
 * A simple [Fragment] subclass.
 */
class CreateNotebookFragment : Fragment() {
    lateinit var binding: FragmentCreateNotebookBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_create_notebook, container, false)

        binding.createNotebookButton.setOnClickListener { saveThisNotebook(it) }

        return binding.root
    }
    private fun saveThisNotebook(view: View){
        val action = CreateNotebookFragmentDirections.actionCreateNotebookFragmentToNoteFragment(binding.titleInput.text.toString())
        view.findNavController().navigate(action)
        val notebookInstance = NotebookModel(title = binding.titleInput.text.toString(), color = "Red", lastModified = 1)

        saveNotebook(notebookInstance, context)
    }


}
