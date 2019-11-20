package me.spryn.noded.database

import android.content.Context
import androidx.room.Room
import me.spryn.noded.models.NoteModel
import me.spryn.noded.models.NotebookModel

// Controls both public and local databases, is the interface for UI

fun saveNotebook(notebook: NotebookModel, context: Context) {

    val dao = LocalNotebookDatabase.getInstance(context).localNotebookDao

    if(dao.getNotebook(notebook.title) == null){
        dao.insertNotebook(notebook)
    }
    else {
        notebook.lastModified = System.currentTimeMillis()
        dao.insertNotebook(notebook)
    }

}

// Pass a note to be added to or updated in the database
fun saveNote(note: NoteModel) {

}