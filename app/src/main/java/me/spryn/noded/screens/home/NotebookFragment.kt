package me.spryn.noded.screens.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

import me.spryn.noded.R
import me.spryn.noded.database.DataManager.loadNotebooks
import me.spryn.noded.databinding.FragmentNotebookBinding
import me.spryn.noded.models.NotebookModel
import me.spryn.noded.navigation.clearBackStackAndNavigateTo
import java.util.*
import kotlin.collections.ArrayList

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
        context?.let {

            val notebooks = loadNotebooks(it)
            notebookList = LinkedList()
            for(notebook in notebooks){
                notebookList.add(notebook)
            }
            // FOR TESTING
            notebookList.add(NotebookModel(title = "Skool", color = "-2277816", lastModified = 1))
            notebookList.add(NotebookModel(title = "Personal", color = "-10965321", lastModified = 2))
            notebookListAdapter = NotebookListAdapter(notebookList, context, inflater)
        }

        val binding: FragmentNotebookBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_notebook, container, false)

        notebookRecyclerView = binding.notebookList

        notebookRecyclerView.adapter = notebookListAdapter
        notebookRecyclerView.layoutManager = LinearLayoutManager(context)

        binding.createNotebookButton.setOnClickListener{ createNotebook(it) }
        binding.logoutButton.setOnClickListener { logout() }



        return binding.root
    }

    private fun createNotebook(view: View){
        view.findNavController()
            .navigate(R.id.action_notebookFragment_to_createNotebookFragment)

    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        view?.findNavController()?.clearBackStackAndNavigateTo(R.id.action_notebookFragment_to_loginActivity)
    }
}
