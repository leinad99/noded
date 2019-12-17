package me.spryn.noded.screens.connectedNotes


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import me.spryn.noded.MainActivity
import me.spryn.noded.R
import me.spryn.noded.databinding.FragmentConnectedNotesBinding
import me.spryn.noded.ui.updateToolbar

/**
 * A simple [Fragment] subclass.
 */
class ConnectedNotesFragment : Fragment() {

    lateinit var binding: FragmentConnectedNotesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_connected_notes, container, false
        )

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Log.i("ConnectedNoteFragment", "onResume")
        val mainActivity = activity as? MainActivity
        mainActivity?.let {
            updateToolbar(
                mainActivity = it,
                addButtonClick = ::addNewNode
            )
            it.window.navigationBarColor = ContextCompat.getColor(it, R.color.colorPrimaryDark)
        }
    }

    private fun addNewNode() {
        //TODO do this
    }


}
