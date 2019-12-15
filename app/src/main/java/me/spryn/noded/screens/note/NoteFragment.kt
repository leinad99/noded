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
            toolbarTitle?.text = args.notebookID
        }

        val notes = DataManager.loadNotesInNotebookFromTitle(args.notebookID, context)

        noteList = LinkedList()
        for (note in notes) {
            noteList.add(note)
        }

        noteListAdapter = NoteListAdapter(noteList, context, inflater, args.notebookColor)

        val binding: FragmentNoteBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_note, container, false
        )
        noteRecyclerView = binding.noteList

        binding.noteList.setBackgroundColor(args.notebookColor.toInt())

        noteRecyclerView.adapter = noteListAdapter
        noteRecyclerView.layoutManager = LinearLayoutManager(context)

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
            notebookID = args.notebookID,
            noteName = "newNote1234",
            notebookColor = args.notebookColor
        )
        view?.findNavController()?.navigate(action)
    }
}

