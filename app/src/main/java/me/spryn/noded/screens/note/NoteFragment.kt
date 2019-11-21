package me.spryn.noded.screens.note


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import me.spryn.noded.R
import me.spryn.noded.databinding.FragmentNoteBinding
import me.spryn.noded.models.NoteModel



/**
 * A simple [Fragment] subclass.
 */
class NoteFragment : Fragment() {

    lateinit var noteRecyclerView: RecyclerView
    lateinit var noteListAdapter: NoteListAdapter
    lateinit var noteList: ArrayList<NoteModel>

    private val args: NoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        noteList = ArrayList()
        noteList.add(NoteModel(title = "Stack Overflow", text = "I've been reviewing the documentation and API for Laravel Collections, but don't seem to find what I am looking for:\n" +
                "\n" +
                "I would like to retrieve an array with model data from a collection, but only get specified attributes.\n" +
                "\n" +
                "I.e. something like Users::toArray('id','name','email'), where the collection in fact holds all attributes for the users, because they are used elsewhere, but in this specific place I need an array with userdata, and only the specified attributes.\n" +
                "\n" +
                "There does not seem to be a helper for this in Laravel? - How can I do this the easiest way?", lastModified = 100, notebookTitle = "Skool"))
        noteList.add(NoteModel(title = "thoughts", text = "Anger is bad and the bible is good", lastModified = 2, notebookTitle = "Personal"))
        noteListAdapter = NoteListAdapter(noteList, context, inflater)

        val binding: FragmentNoteBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_note, container, false)
        noteRecyclerView = binding.noteList

        binding.notebookName.text = args.notebookName

        noteRecyclerView.adapter = noteListAdapter
        noteRecyclerView.layoutManager = LinearLayoutManager(context)

        binding.createNoteButton.setOnClickListener{createNote(it)}
        return binding.root
    }

    private fun createNote(view: View){

        val action = NoteFragmentDirections.actionNoteFragmentToCreateNoteFragment(args.notebookName)
        view.findNavController().navigate(action)

    }


}
