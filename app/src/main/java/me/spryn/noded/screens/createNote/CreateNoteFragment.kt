package me.spryn.noded.screens.createNote


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs

import me.spryn.noded.R
import me.spryn.noded.database.DataManager.saveNote
import me.spryn.noded.databinding.FragmentCreateNoteBinding
import me.spryn.noded.models.NoteModel
import me.spryn.noded.screens.note.NoteFragmentArgs

/**
 * A simple [Fragment] subclass.
 */
class CreateNoteFragment : Fragment() {
    lateinit var binding: FragmentCreateNoteBinding

    private val args: NoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_create_note, container, false)


        binding.saveNoteButton.setOnClickListener { saveNoteInstance(it) }

        return binding.root
    }

    private fun saveNoteInstance(view: View){
        val action = CreateNoteFragmentDirections.actionCreateNoteFragmentToNoteFragment(args.notebookName)
        view.findNavController().navigate(action)
        val noteInstance = NoteModel(title = binding.titleInput.text.toString(), text = binding.noteInput.text.toString(), lastModified = 1, notebookTitle = args.notebookName)
        saveNote(noteInstance, context)
    }


}
