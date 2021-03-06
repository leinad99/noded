package me.spryn.noded.screens.createNotebook


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.GridLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import me.spryn.noded.MainActivity
import me.spryn.noded.R
import me.spryn.noded.database.DataManager.saveNotebook
import me.spryn.noded.databinding.FragmentCreateNotebookBinding
import me.spryn.noded.models.NotebookModel
import me.spryn.noded.ui.colorBlendDark
import me.spryn.noded.ui.colorBlendDarker
import me.spryn.noded.ui.updateToolbar
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class CreateNotebookFragment : Fragment() {

    lateinit var binding: FragmentCreateNotebookBinding
    private lateinit var viewModel: CreateNotebookViewModel

    var notebookColor = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_create_notebook, container, false
        )
        binding.colorButton.setOnClickListener { showColors() }

        viewModel = ViewModelProviders.of(this).get(CreateNotebookViewModel::class.java)

        binding.notebookInfoLayout.setBackgroundColor(viewModel.color)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val mainActivity = activity as? MainActivity

        mainActivity?.let {
            val color = viewModel.color
            notebookColor = color //set default notebook color in case user doesn't select one
            val darkColor = colorBlendDark(color)
            val darkerColor = colorBlendDarker(color)
            updateToolbar(
                mainActivity,
                checkButtonClick = ::saveThisNotebook,
                toolbarColor = darkColor,
                statusBarColor = darkerColor
            )
        }
    }

    override fun onPause() {
        view?.let { hideKeyboard(it) }
        super.onPause()
    }

    private fun showColors() {
        val colorsGrid = layoutInflater.inflate(R.layout.color_picker_grid, null)

        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setView(colorsGrid)

        val gridItems: GridLayout = colorsGrid.findViewById(R.id.color_picker_grid)

        for (i in 0 until gridItems.childCount) {
            val item = gridItems.getChildAt(i)
            item?.setOnClickListener {
                Log.i("Color Picker", "Picked item $i")
                updateColor(item)
            }
        }
        alertDialog.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }

        // create alert dialog and show it
        alertDialog.create().show()
    }

    private fun updateColor(item: View) {
        val color = item.backgroundTintList!!.defaultColor
        viewModel.color = color
        binding.notebookInfoLayout.setBackgroundColor(color)
        notebookColor = color

        //set the status bar and navbar colors
        val mainActivity = activity as? MainActivity
        val darkColor = colorBlendDark(color)
        val darkerColor = colorBlendDarker(color)
        mainActivity?.let {
            updateToolbar(
                mainActivity,
                checkButtonClick = ::saveThisNotebook,
                toolbarColor = darkColor,
                statusBarColor = darkerColor
            )
            it.window.navigationBarColor = darkColor
        }
    }

    private fun saveThisNotebook() {
        val id = UUID.randomUUID().toString()

        if (binding.titleInput.text.toString().isEmpty()) {
            Toast.makeText(context, "Please enter a title!", Toast.LENGTH_SHORT).show()
            return
        }
        val action = CreateNotebookFragmentDirections.actionCreateNotebookFragmentToNoteFragment(
            notebookColor = notebookColor.toString(),
            notebookName = binding.titleInput.text.toString(),
            notebookID = id
        )
        view?.findNavController()?.navigate(action)
        val notebookInstance = NotebookModel(
            ID = id,
            title = binding.titleInput.text.toString(),
            color = notebookColor.toString(),
            lastModified = 1
        )
        saveNotebook(notebookInstance, context)
    }

    private fun hideKeyboard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
