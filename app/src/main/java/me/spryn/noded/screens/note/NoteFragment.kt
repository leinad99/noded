package me.spryn.noded.screens.note

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.spryn.noded.MainActivity

import me.spryn.noded.R
import me.spryn.noded.ui.colorBlendDark
import me.spryn.noded.database.DataManager
import me.spryn.noded.databinding.FragmentNoteBinding
import me.spryn.noded.models.NoteModel
import me.spryn.noded.ui.updateToolbar
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class NoteFragment : Fragment() {

    lateinit var noteRecyclerView: RecyclerView
    lateinit var noteListAdapter: NoteListAdapter
    lateinit var noteList: LinkedList<NoteModel>

    private val args: NoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val mainActivity = activity as? MainActivity

        mainActivity?.let {
            val toolbarTitle: TextView? = it.findViewById(R.id.toolbar_title)
            toolbarTitle?.text = args.notebookName
        }

        val notes = DataManager.loadNotesInNotebookFromTitle(args.notebookName, context)

        noteList = LinkedList()
        for (note in notes) {
            noteList.add(note)
        }
        noteList.add(
            NoteModel(
                title = "Stack Overflow",
                text = "I've been reviewing the documentation and API for Laravel Collections, but don't seem to find what I am looking for:\n" +
                        "\n" +
                        "I would like to retrieve an array with model data from a collection, but only get specified attributes.\n" +
                        "\n" +
                        "I.e. something like Users::toArray('id','name','email'), where the collection in fact holds all attributes for the users, because they are used elsewhere, but in this specific place I need an array with userdata, and only the specified attributes.\n" +
                        "\n" +
                        "There does not seem to be a helper for this in Laravel? - How can I do this the easiest way?",
                lastModified = 100,
                notebookTitle = "Skool"
            )
        )
        noteList.add(
            NoteModel(
                title = "thoughts",
                text = "Anger is bad and the bible is good",
                lastModified = 2,
                notebookTitle = "Personal"
            )
        )
        noteListAdapter = NoteListAdapter(noteList, context, inflater, args.notebookColor)

        val binding: FragmentNoteBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_note, container, false
        )
        noteRecyclerView = binding.noteList

        binding.noteList.setBackgroundColor(args.notebookColor.toInt())

        noteRecyclerView.adapter = noteListAdapter
        noteRecyclerView.layoutManager = GridLayoutManager(context, 2)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Log.i("NoteFragment", "onResume")

        val mainActivity = activity as? MainActivity
        mainActivity?.let {
            updateToolbar(
                mainActivity = it,
                toolbarColor = args.notebookColor.toInt(),
                statusBarColor = colorBlendDark(args.notebookColor.toInt()),
                addButtonClick = ::createNote
            )
            it.window.navigationBarColor = colorBlendDark(args.notebookColor.toInt())
        }
    }

    private fun createNote() {
        val action = NoteFragmentDirections.actionNoteFragmentToCreateNoteFragment(
            notebookName = args.notebookName,
            noteName = "newNote1234",
            notebookColor = args.notebookColor
        )
        view?.findNavController()?.navigate(action)
    }
}

