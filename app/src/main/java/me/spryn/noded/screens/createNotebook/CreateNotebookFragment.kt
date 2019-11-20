package me.spryn.noded.screens.createNotebook


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import me.spryn.noded.R
import me.spryn.noded.databinding.FragmentCreateNotebookBinding

/**
 * A simple [Fragment] subclass.
 */
class CreateNotebookFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_create_notebook, container, false)
        val binding: FragmentCreateNotebookBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_create_notebook, container, false)

        binding.createNotebookButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_createNotebookFragment_to_noteFragment)
        }

        return binding.root
    }


}
