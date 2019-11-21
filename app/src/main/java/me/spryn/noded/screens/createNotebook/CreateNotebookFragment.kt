package me.spryn.noded.screens.createNotebook


import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import me.spryn.noded.R
import me.spryn.noded.database.DataManager.saveNotebook
import me.spryn.noded.databinding.FragmentCreateNotebookBinding
import me.spryn.noded.models.NotebookModel
import petrov.kristiyan.colorpicker.ColorPicker
import android.graphics.Color.parseColor
import android.widget.Toast



/**
 * A simple [Fragment] subclass.
 */
class CreateNotebookFragment : Fragment() {

    lateinit var binding: FragmentCreateNotebookBinding
    lateinit var colorPicker: ColorPicker
    private lateinit var colorButton: Button

    var notebookColor = -1 //default to white

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_create_notebook, container, false
        )

        colorPicker = ColorPicker(activity)

        colorButton = binding.colorButton
        colorButton.setOnClickListener { showColors() }

        binding.createNotebookButton.setOnClickListener { saveThisNotebook(it) }


        return binding.root


    }

    private fun showColors() {
        colorPicker.setOnChooseColorListener(object : ColorPicker.OnChooseColorListener {

            override fun onChooseColor(position: Int, color: Int) {
                //Toast.makeText(context, color.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onCancel() {
                binding.notebookInfoLayout.setBackgroundColor(parseColor("37474F"))
            }
        })
            .addListenerButton("Accept") { v, position, color ->
                binding.notebookInfoLayout.setBackgroundColor(color)
                Toast.makeText(context, color.toString(), Toast.LENGTH_SHORT).show()
                notebookColor = color
                colorPicker.dismissDialog()
            }
            .disableDefaultButtons(true)
            .show()


    }
    private fun saveThisNotebook(view: View){
        val action = CreateNotebookFragmentDirections.actionCreateNotebookFragmentToNoteFragment(notebookColor = notebookColor.toString(), notebookName = binding.titleInput.text.toString())
        view.findNavController().navigate(action)
        val notebookInstance = NotebookModel(title = binding.titleInput.text.toString(), color = notebookColor.toString(), lastModified = 1)

        saveNotebook(notebookInstance, context)
    }


}
