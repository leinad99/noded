package me.spryn.noded.screens.note

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.spryn.noded.MainActivity
import me.spryn.noded.R
import me.spryn.noded.database.DataManager
import me.spryn.noded.databinding.FragmentNoteBinding
import me.spryn.noded.models.NoteModel
import me.spryn.noded.ui.colorBlendDark
import me.spryn.noded.ui.colorBlendDarker
import me.spryn.noded.ui.updateToolbar
import java.util.*
import kotlin.math.abs


/**
 * A simple [Fragment] subclass.
 */
class NoteFragment : Fragment() {

    lateinit var noteRecyclerView: RecyclerView

    private val args: NoteFragmentArgs by navArgs()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val mainActivity = activity as? MainActivity

        val binding: FragmentNoteBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_note, container, false
        )
        noteRecyclerView = binding.noteList

        binding.noteList.setBackgroundColor(args.notebookColor.toInt())

        noteRecyclerView.layoutManager = GridLayoutManager(context, 2)

        mainActivity?.let {
            val toolbarTitle: TextView? = it.findViewById(R.id.toolbar_title)
            toolbarTitle?.text = args.notebookName // TODO: Eh
            DataManager.addNotesToRecyclerViewFromNotebook(args.notebookID, args.notebookColor, args.notebookName, context, noteRecyclerView, inflater)
        }

        //calculate swipe 
        var x1 = 0.0F
        var x2 = 0.0F
        val MIN_DISTANCE = 150

        binding.noteList.setOnTouchListener(View.OnTouchListener { view, event: MotionEvent ->
            when (event.action) {
                MotionEvent.ACTION_DOWN-> {

                    x1 = event.x
                }
                MotionEvent.ACTION_UP -> {
                    x2 = event.x
                    val deltaX: Float = x2 - x1
                    if (deltaX > MIN_DISTANCE) { //swipe left
                        Log.i("TESTING", "swipe")
                        val mainActivity: MainActivity? = activity as? MainActivity
                        mainActivity?.onBackPressed()
                    }
                }
            }
            return@OnTouchListener true
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Log.i("NoteFragment", "onResume")

        val mainActivity = activity as? MainActivity
        val color = args.notebookColor.toInt()
        mainActivity?.let {
            updateToolbar(
                mainActivity = it,
                toolbarColor = colorBlendDark(color),
                statusBarColor = colorBlendDarker(color),
                addButtonClick = ::createNote
            )
            it.window.navigationBarColor = colorBlendDark(args.notebookColor.toInt())
        }
    }

    private fun createNote() {
        val action = NoteFragmentDirections.actionNoteFragmentToCreateNoteFragment(
            notebookID = args.notebookID,
            noteID = UUID.randomUUID().toString(),
            notebookColor = args.notebookColor,
            notebookName = args.notebookName
        )
        view?.findNavController()?.navigate(action)
    }
}

