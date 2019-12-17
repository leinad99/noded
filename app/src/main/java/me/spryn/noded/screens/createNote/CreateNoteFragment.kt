package me.spryn.noded.screens.createNote


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import me.spryn.noded.MainActivity
import me.spryn.noded.R
import me.spryn.noded.database.DataManager
import me.spryn.noded.databinding.FragmentCreateNoteBinding
import me.spryn.noded.models.NoteModel
import me.spryn.noded.ui.updateToolbar


class CreateNoteFragment : Fragment() {
    lateinit var binding: FragmentCreateNoteBinding

    private val args: CreateNoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val note: NoteModel = DataManager.loadNote(args.noteName, args.notebookName, context)
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_create_note, container, false
        )

        if (note.title != "newNote1234") { //if not a new note, populate the fields
            binding.titleInput.setText(note.title, TextView.BufferType.EDITABLE)
            binding.noteInput.setText(note.text, TextView.BufferType.EDITABLE)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Log.i("CreateNoteFragment", "onResume")
        val mainActivity = activity as? MainActivity
        mainActivity?.let {
            val primaryColor = ContextCompat.getColor(it, R.color.colorPrimary)
            updateToolbar(
                mainActivity = it,
                toolbarColor = primaryColor,
                statusBarColor = primaryColor,
                toolbarElevation = 0F,
                checkButtonClick = ::saveNoteInstance,
                deleteButtonClick = ::deleteNote
            )
            it.window.navigationBarColor =
                ContextCompat.getColor(it, R.color.colorPrimaryDark)
        }
    }

    override fun onPause() {
        view?.let { hideKeyboard(it) }
        super.onPause()
    }

    private fun deleteNote() {
        //TODO @Mitchell write this for when the user taps the delete icon
    }

    private fun saveNoteInstance() {
        val action = CreateNoteFragmentDirections.actionCreateNoteFragmentToNoteFragment(
            notebookName = args.notebookName,
            notebookColor = args.notebookColor
        )
        view?.findNavController()?.navigate(action)
        val noteInstance = NoteModel(
            title = binding.titleInput.text.toString(),
            text = binding.noteInput.text.toString(),
            notebookTitle = args.notebookName
        )
        DataManager.saveNote(noteInstance, context)
    }

    private fun hideKeyboard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}
