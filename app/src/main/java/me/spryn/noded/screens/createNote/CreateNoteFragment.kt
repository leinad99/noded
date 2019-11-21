package me.spryn.noded.screens.createNote


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs

import me.spryn.noded.R
import me.spryn.noded.database.DataManager
import me.spryn.noded.databinding.FragmentCreateNoteBinding
import me.spryn.noded.models.NoteModel
import me.spryn.noded.screens.note.NoteFragmentArgs


class CreateNoteFragment : Fragment() {
    lateinit var binding: FragmentCreateNoteBinding

    private val args: CreateNoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var note: NoteModel = DataManager.loadNote(args.noteName, args.notebookName, context)
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_create_note, container, false
        )

        if (note.title != "newNote1234") { //if not a new note, populate the fields
            binding.titleInput.setText(note.title, TextView.BufferType.EDITABLE)
            binding.noteInput.setText(note.text, TextView.BufferType.EDITABLE)
        }


        binding.saveNoteButton.setOnClickListener { saveNoteInstance(it) }

        return binding.root
    }

    private fun saveNoteInstance(view: View) {
        val action = CreateNoteFragmentDirections.actionCreateNoteFragmentToNoteFragment(
            notebookName = args.notebookName,
            notebookColor = args.notebookColor
        )
        view.findNavController().navigate(action)
//        view.findNavController().navigate(R.id.action_createNoteFragment_to_noteFragment)
        val noteInstance = NoteModel(
            title = binding.titleInput.text.toString(),
            text = binding.noteInput.text.toString(),
            notebookTitle = args.notebookName
        )
        DataManager.saveNote(noteInstance, context)
    }


}
