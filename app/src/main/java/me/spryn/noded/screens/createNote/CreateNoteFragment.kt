package me.spryn.noded.screens.createNote


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import me.spryn.noded.R
import me.spryn.noded.databinding.FragmentCreateNoteBinding

/**
 * A simple [Fragment] subclass.
 */
class CreateNoteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCreateNoteBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_create_note, container, false)

        binding.saveNoteButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_createNoteFragment_to_noteFragment)
        }

        return binding.root
    }


}
