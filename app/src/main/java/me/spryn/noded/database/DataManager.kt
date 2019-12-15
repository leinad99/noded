package me.spryn.noded.database

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import me.spryn.noded.models.NoteModel
import me.spryn.noded.models.NotebookModel

// Controls both public and local databases, is the interface for UI

object DataManager {

    // Gives a notebook for the database to update or create
    fun saveNotebook(notebook: NotebookModel, context: Context?) {

        /* OLD
        val requiredContext = context?: return
        val dao = LocalNotebookDatabase.getInstance(requiredContext).localNotebookDao

        if (dao.getNotebook(notebook.title) == null) {
            dao.insertNotebook(notebook)
        } else {
            notebook.lastModified = System.currentTimeMillis()
            dao.updateNotebook(notebook)
        }
        */

        val db = FirebaseFirestore.getInstance()
        FirebaseFirestore.setLoggingEnabled(true)

        /* WORKING CODE

        db.collection("users").add(hashMapOf(
            "name" to "Tokyo",
            "country" to "Japan"))
            .addOnSuccessListener { Log.i("avideobobuae", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.i("avideobobuae", "Error writing document", e) }
        */

        // NEEDS TO BE WORKING CODE

        db.document("users/" + FirebaseAuth.getInstance().uid!! + "/notebooks/" + notebook.ID).set(
            hashMapOf(
                "title" to notebook.title,
                "color" to notebook.color,
                "lastModified" to System.currentTimeMillis()
            )
        ).addOnSuccessListener { Log.i("avideobobuae", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.i("avideobobuae", "Error writing document", e) }

        Log.i("avideobobuae",FirebaseAuth.getInstance().uid)

    }

    // Get a list of all the notebooks, sorta by the most recently modified first
    fun loadNotebooks(context: Context?): List<NotebookModel> {

        /*
        val requiredContext = context?: return emptyList<NotebookModel>()
        val dao = LocalNotebookDatabase.getInstance(requiredContext).localNotebookDao

        return dao.getAllNotebooks()

         */

        return emptyList()
    }

    // TODO: do it
    fun loadNotebookTitleFromID(ID: String): String{
        return ID
    }

    // Pass a note to be added to or updated in the database
    fun saveNote(note: NoteModel, context: Context?) {

        /*
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
    }

    // Load all notes in a notebook
    fun loadNotesInNotebookFromTitle(notebookTitle: String, context: Context?): List<NoteModel>{

        /*
        val requiredContext = context?: return emptyList<NoteModel>()
        val dao = LocalNotesDatabase.getInstance(requiredContext).localNotesDao

        return dao.getAllNotesFromNotebook(notebookTitle)

         */

        return emptyList()
    }

    // Load a specific note
    fun loadNote(noteTitle: String, notebookTitle: String, context: Context?): NoteModel {

        /*
        val requiredContext = context?: return NoteModel(title = noteTitle, notebookTitle =  notebookTitle)
        val dao = LocalNotesDatabase.getInstance(requiredContext).localNotesDao

        return dao.getNote(noteTitle, notebookTitle)?: NoteModel(title = noteTitle, notebookTitle =  notebookTitle)

         */

        return NoteModel("no", notebookID = "no")
    }
}