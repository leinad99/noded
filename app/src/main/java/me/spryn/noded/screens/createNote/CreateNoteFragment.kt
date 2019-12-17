package me.spryn.noded.screens.createNote


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
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
import java.util.*


class CreateNoteFragment : Fragment() {
    lateinit var binding: FragmentCreateNoteBinding

    private val args: CreateNoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val note: NoteModel = DataManager.loadNote(args.noteID, args.notebookID, context)
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_create_note, container, false
        )

        binding.titleInput.setText(note.ID, TextView.BufferType.EDITABLE) // TODO: This is not a title, it's actually the ID
        binding.noteInput.setText(note.text, TextView.BufferType.EDITABLE)

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

    private fun deleteNote() {
        //TODO @Mitchell write this for when the user taps the delete icon
    }

    private fun saveNoteInstance() {
        val action = CreateNoteFragmentDirections.actionCreateNoteFragmentToNoteFragment(
            notebookID = args.notebookID,
            notebookColor = args.notebookColor
        )
        view?.findNavController()?.navigate(action)
        val noteInstance = NoteModel(
            ID = UUID.randomUUID().toString(),
            title = binding.titleInput.text.toString(),
            text = binding.noteInput.text.toString(),
            notebookID = args.notebookID
        )
        DataManager.saveNote(noteInstance, context)
    }
}
