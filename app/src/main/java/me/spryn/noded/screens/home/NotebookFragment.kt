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
import me.spryn.noded.database.DataManager.loadNotebooks
import me.spryn.noded.databinding.FragmentNotebookBinding
import me.spryn.noded.models.NotebookModel
import me.spryn.noded.screens.settings.SettingsActivity
import me.spryn.noded.ui.updateToolbar
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class NotebookFragment : Fragment() {

    lateinit var notebookRecyclerView: RecyclerView
    lateinit var notebookListAdapter: NotebookListAdapter
    lateinit var notebookList: LinkedList<NotebookModel>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentNotebookBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_notebook, container, false
        )
        context?.let {

            val notebooks = loadNotebooks(it)
            notebookList = LinkedList()
            for (notebook in notebooks) {
                notebookList.add(notebook)
            }
            // FOR TESTING
            val red = ContextCompat.getColor(it, R.color.red_500)
            notebookList.add(
                NotebookModel(
                    title = "Skool",
                    color = red.toString(),
                    lastModified = 1
                )
            )
            notebookList.add(
                NotebookModel(
                    title = "Personal",
                    color = "-10965321",
                    lastModified = 2
                )
            )
            notebookListAdapter = NotebookListAdapter(notebookList, context, inflater)
        }


        notebookRecyclerView = binding.notebookList

        notebookRecyclerView.adapter = notebookListAdapter
        notebookRecyclerView.layoutManager = GridLayoutManager(context, 2)

        binding.settingsButton.setOnClickListener { startActivity(Intent(context, SettingsActivity::class.java)) }
        binding.createNotebookFab.setOnClickListener { createNotebook(it) }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        if (FirebaseAuth.getInstance().currentUser == null) {
            view?.findNavController()?.navigate(R.id.action_notebookFragment_to_loginActivity)
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
