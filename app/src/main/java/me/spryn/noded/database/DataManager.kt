package me.spryn.noded.database

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import me.spryn.noded.databinding.FragmentCreateNoteBinding
import me.spryn.noded.models.NoteModel
import me.spryn.noded.models.NotebookModel
import me.spryn.noded.screens.home.NotebookListAdapter
import me.spryn.noded.screens.note.NoteListAdapter
import java.util.*

// Controls both public and local databases, is the interface for UI

object DataManager {

    // Gives a notebook for the database to update or create
    fun saveNotebook(notebook: NotebookModel, context: Context?) {

        /* OLD SQL
        val requiredContext = context?: return
        val dao = LocalNotebookDatabase.getInstance(requiredContext).localNotebookDao

        if (dao.getNotebook(notebook.title) == null) {
            dao.insertNotebook(notebook)
        } else {
            notebook.lastModified = System.currentTimeMillis()
            dao.updateNotebook(notebook)
        }
        */

        // NEW Firebase

        val db = FirebaseFirestore.getInstance()

        db.document("users/" + FirebaseAuth.getInstance().uid!! + "/notebooks/" + notebook.ID).set(
            hashMapOf(
                "title" to notebook.title,
                "color" to notebook.color,
                "lastModified" to System.currentTimeMillis()
            )
        ).addOnSuccessListener { Log.i("FirebaseListener", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.i("FirebaseListener", "Error writing document", e) }

    }

    // Get a list of all the notebooks, sorta by the most recently modified first
    fun addNotebooksToRecyclerView(context: Context?, view: RecyclerView, inflater: LayoutInflater){

        /* OLD SQL
        val requiredContext = context?: return emptyList<NotebookModel>()
        val dao = LocalNotebookDatabase.getInstance(requiredContext).localNotebookDao

        return dao.getAllNotebooks()

         */

        // NEW Firebase

        val db = FirebaseFirestore.getInstance()
        val notebooks = LinkedList<NotebookModel>()

        db.collection("users/" + FirebaseAuth.getInstance().uid!! + "/notebooks")
            .get().addOnSuccessListener { result ->
                for (document in result) {

                    val modified = document.getLong("lastModified") ?: 1

                    notebooks.add(NotebookModel(ID = document.id,
                        title = document.getString("title") ?: "error",
                        color = document.getString("color") ?: "000000", //TODO: Better default color
                        lastModified = modified
                    ))
                }

                view.adapter = NotebookListAdapter(notebooks, context, inflater)
                view.adapter!!.notifyDataSetChanged()
            } .addOnFailureListener { e -> Log.i("FirebaseListener", "Error writing document", e) }

    }

    // TODO: do it
    fun loadNotebookTitleFromID(ID: String): String{
        return ID
    }

    // Pass a note to be added to or updated in the database
    fun saveNote(note: NoteModel, context: Context?) {

        /* OLD SQL
        val requiredContext = context?: return
        val dao = LocalNotesDatabase.getInstance(requiredContext).localNotesDao

        if (dao.getNote(note.title, note.notebookTitle) == null){
            dao.insertNote(note)
        }
        else {
            note.lastModified = System.currentTimeMillis()
            dao.updateNote(note)
        }

         */

        // NEW Firebase

        val db = FirebaseFirestore.getInstance()

        db.document("users/" + FirebaseAuth.getInstance().uid!! + "/notebooks/" + note.notebookID + "/notes/" + note.ID).set(
            hashMapOf(
                "title" to note.title,
                "text" to note.text,
                "lastModified" to System.currentTimeMillis()
            )
        ).addOnSuccessListener { Log.i("FirebaseListener", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.i("FirebaseListener", "Error writing document", e) }
    }

    // Load all notes in a notebook
    fun addNotesToRecyclerViewFromNotebook(notebookID: String, notebookColor: String, notebookTitle: String, context: Context?, view: RecyclerView, inflater: LayoutInflater){

        /*
        val requiredContext = context?: return emptyList<NoteModel>()
        val dao = LocalNotesDatabase.getInstance(requiredContext).localNotesDao

        return dao.getAllNotesFromNotebook(notebookTitle)

         */

        // NEW Firebase

        val db = FirebaseFirestore.getInstance()
        val notes = LinkedList<NoteModel>()

        db.collection("users/" + FirebaseAuth.getInstance().uid!! + "/notebooks/" + notebookID + "/notes")
            .get().addOnSuccessListener { result ->
                for (document in result) {

                    val modified = document.getLong("lastModified") ?: 1

                    notes.add(NoteModel(ID = document.id,
                        title = document.getString("title") ?: "error",
                        text = document.getString("text") ?: "000000", //TODO: Better default color
                        lastModified = modified.toLong(),
                        notebookID = document.getString("notebookID") ?: notebookID))
                }

                view.adapter = NoteListAdapter(notes, context, inflater, notebookColor, notebookTitle)
                view.adapter!!.notifyDataSetChanged()
            } .addOnFailureListener { e -> Log.i("FirebaseListener", "Error writing document", e) }
    }

    // Load a specific note
    fun loadNoteIntoBinding(binding: FragmentCreateNoteBinding, noteID: String, notebookID: String): NoteModel {

        /* OLD SQL
        val requiredContext = context?: return NoteModel(title = noteTitle, notebookTitle =  notebookTitle)
        val dao = LocalNotesDatabase.getInstance(requiredContext).localNotesDao

        return dao.getNote(noteTitle, notebookTitle)?: NoteModel(title = noteTitle, notebookTitle =  notebookTitle)

         */

        // New Firebase

        val db = FirebaseFirestore.getInstance()

        val note = NoteModel(noteID, notebookID = notebookID)

        db.document("users/" + FirebaseAuth.getInstance().uid!! + "/notebooks/" + notebookID + "/notes/" + noteID)
            .get().addOnSuccessListener { document ->
                if (document != null) {
                    binding.titleInput.setText(document.getString("title") ?: "untitled", TextView.BufferType.EDITABLE) // TODO: This is not a title, it's actually the ID
                    binding.editor.setHtml(document.getString("text") ?: "")
                    binding.wikiBtn.isEnabled = false
                }
            }

        return note // TODO: Could be this
    }
}