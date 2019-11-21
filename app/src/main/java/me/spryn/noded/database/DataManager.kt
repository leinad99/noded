package me.spryn.noded.database

import android.content.Context
import me.spryn.noded.models.NoteModel
import me.spryn.noded.models.NotebookModel

// Controls both public and local databases, is the interface for UI

object DataManager {

    // Gives a notebook for the database to update or create
    fun saveNotebook(notebook: NotebookModel, context: Context?) {

        val requiredContext = context?: return

        val dao = LocalNotebookDatabase.getInstance(requiredContext).localNotebookDao

        if (dao.getNotebook(notebook.title) == null) {
            dao.insertNotebook(notebook)
        } else {
            notebook.lastModified = System.currentTimeMillis()
            dao.updateNotebook(notebook)
        }

    }

    // Get a list of all the notebooks, sorta by the most recently modified first
    fun loadNotebooks(context: Context?): List<NotebookModel> {

        val requiredContext = context?: return emptyList<NotebookModel>()

        val dao = LocalNotebookDatabase.getInstance(requiredContext).localNotebookDao

        return dao.getAllNotebooks()
    }

    // Pass a note to be added to or updated in the database
//    fun saveNote(note: NoteModel, context: Context?) {
//
//        val requiredContext = context?: return
//
//        val dao = LocalNotebookDatabase.getInstance(requiredContext).localNotebookDao
//
//    }
}