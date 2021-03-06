package me.spryn.noded.screens.home


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import me.spryn.noded.MainActivity
import me.spryn.noded.R
import me.spryn.noded.database.DataManager.addNotebooksToRecyclerView
import me.spryn.noded.databinding.FragmentNotebookBinding
import me.spryn.noded.navigation.clearBackStackAndNavigateTo
import me.spryn.noded.screens.settings.SettingsActivity
import me.spryn.noded.ui.updateToolbar

/**
 * A simple [Fragment] subclass.
 */
class NotebookFragment : Fragment() {

    lateinit var notebookRecyclerView: RecyclerView

    private lateinit var binding: FragmentNotebookBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_notebook, container, false
        )

        notebookRecyclerView = binding.notebookList

        notebookRecyclerView.layoutManager = GridLayoutManager(context, 2)

        binding.settingsButton.setOnClickListener {
            it.isEnabled = false
            startActivity(Intent(context, SettingsActivity::class.java))
        }
        binding.createNotebookFab.setOnClickListener { createNotebook(it) }

        context?.let {
            addNotebooksToRecyclerView(context, notebookRecyclerView, inflater)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        binding.settingsButton.isEnabled = true

        if (FirebaseAuth.getInstance().currentUser == null) {
            view?.findNavController()
                ?.clearBackStackAndNavigateTo(R.id.action_notebookFragment_to_loginActivity)
        }

        val mainActivity = activity as? MainActivity
        mainActivity?.let {

            updateToolbar(mainActivity)
            it.window.navigationBarColor = ContextCompat.getColor(it, R.color.colorPrimaryDark)
        }
    }

    private fun createNotebook(view: View) {
        view.findNavController()
            .navigate(R.id.action_notebookFragment_to_createNotebookFragment)

    }
}
