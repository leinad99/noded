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
    fun saveNote(note: NoteModel, context: Context?) {

        val requiredContext = context?: return
        val dao = LocalNotesDatabase.getInstance(requiredContext).localNotesDao

        if (dao.getNote(note.title, note.notebookTitle) == null){
            dao.insertNote(note)
        }
        else {
            note.lastModified = System.currentTimeMillis()
            dao.updateNote(note)
        }
    }

    // Load all notes in a notebook
    fun loadNotesInNotebookFromTitle(notebookTitle: String, context: Context?): List<NoteModel>{

        val requiredContext = context?: return emptyList<NoteModel>()
        val dao = LocalNotesDatabase.getInstance(requiredContext).localNotesDao

        return dao.getAllNotesFromNotebook(notebookTitle)
    }

    // Load a specific note
    fun loadNote(noteTitle: String, notebookTitle: String, context: Context?): NoteModel {

        val requiredContext = context?: return NoteModel(title = noteTitle, notebookTitle =  notebookTitle)
        val dao = LocalNotesDatabase.getInstance(requiredContext).localNotesDao

        return dao.getNote(noteTitle, notebookTitle)?: NoteModel(title = noteTitle, notebookTitle =  notebookTitle)
    }
}