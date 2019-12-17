package me.spryn.noded.screens.createNote

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
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
import me.spryn.noded.screens.wikipedia.WikipediaActivity
import me.spryn.noded.ui.updateToolbar
import net.dankito.utils.android.permissions.PermissionsService
import java.util.*


class CreateNoteFragment : Fragment() {
    lateinit var binding: FragmentCreateNoteBinding

    private val args: CreateNoteFragmentArgs by navArgs()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_create_note, container, false
        )

        binding.wikiBtn.isEnabled = false // So then the user can't search wikipedia with an empty string
        DataManager.loadNoteIntoBinding(binding, args.noteID, args.notebookID)

        configureEditor()

        binding.wikiBtn.setOnClickListener { openWikipediaPage() }

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
//                nodeButtonClick =  ::connectedNotes
            )
            it.window.navigationBarColor =
                ContextCompat.getColor(it, R.color.colorPrimaryDark)
        }
    }

    override fun onPause() {
        view?.let { hideKeyboard(it) }
        super.onPause()
    }

//    private fun connectedNotes() {
//        view?.findNavController()
//            ?.navigate(R.id.action_createNoteFragment_to_connectedNotesFragment)
//    }


    private fun deleteNote() {
        val action = CreateNoteFragmentDirections.actionCreateNoteFragmentToNoteFragment(
            notebookID = args.notebookID,
            notebookColor = args.notebookColor,
            notebookName = args.notebookName
        )
        DataManager.deleteNoteAndExit(view, args.noteID, args.notebookID, action)
    }

    private fun saveNoteInstance() {
        val action = CreateNoteFragmentDirections.actionCreateNoteFragmentToNoteFragment(
            notebookID = args.notebookID,
            notebookColor = args.notebookColor,
            notebookName = args.notebookName
        )
        view?.findNavController()?.navigate(action)
        val noteInstance = NoteModel(
            ID = args.noteID,
            title = binding.titleInput.text.toString(),
            notebookID = args.notebookID,
            text = getBodyText()
        )
        DataManager.saveNote(noteInstance, context)
    }

    private fun openWikipediaPage() {
        val intent = Intent(context, WikipediaActivity::class.java)
        intent.putExtra("title", binding.titleInput.text.toString())
        startActivity(intent)
    }

    private fun configureEditor() {
        val mainActivity = activity as? MainActivity

        mainActivity?.let { binding.editor.permissionsService = PermissionsService(mainActivity) }
        binding.editorToolbar.editor = binding.editor
        context?.let {
            binding.editor.setEditorBackgroundColor(
                ContextCompat.getColor(it, R.color.colorPrimary)
            )
        }
        binding.editor.setEditorFontColor(Color.WHITE)
        binding.editor.setEditorFontFamily("Roboto")
    }


    // only needed if you like to insert images from local device so the user gets asked for permission to access external storage if needed
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        val mainActivity = activity as? MainActivity
        mainActivity?.let {
            PermissionsService(it).onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
            )
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun getBodyText(): String { //TODO change back?
//        var htmlText = ""
//        binding.editor.getCurrentHtmlAsync (object : GetCurrentHtmlCallback {
//            override fun htmlRetrieved(html: String) {
//                htmlText = html
//            }
//        })
//        return htmlText
        return binding.editor.getCachedHtml()
    }
    
    private fun hideKeyboard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}
