package me.spryn.noded.screens.createNote


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import me.spryn.noded.R
import me.spryn.noded.database.saveNote
import me.spryn.noded.databinding.FragmentCreateNoteBinding
import me.spryn.noded.models.NoteModel

/**
 * A simple [Fragment] subclass.
 */
class CreateNoteFragment : Fragment() {
    lateinit var binding: FragmentCreateNoteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val notebook = getArguments()?.getString("notebookName");
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_create_note, container, false)

        var notebookName: String = notebook ?: " "
        binding.saveNoteButton.setOnClickListener { saveNoteInstance(it, notebookName) }

        return binding.root
    }

    private fun saveNoteInstance(view: View, notebook: String){
        view.findNavController().navigate(R.id.action_createNoteFragment_to_noteFragment)
        val noteInstance = NoteModel(title = binding.titleInput.text.toString(), text = binding.noteInput.text.toString(), lastModified = 1, notebookTitle = notebook)
        //saveNote(noteInstance)
    }


}
