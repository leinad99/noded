package me.spryn.noded.screens.note


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import me.spryn.noded.R
import me.spryn.noded.databinding.FragmentNoteBinding
import me.spryn.noded.databinding.FragmentNotebookBinding

/**
 * A simple [Fragment] subclass.
 */
class NoteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentNoteBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_notebook, container, false)

        return binding.root
    }


}
