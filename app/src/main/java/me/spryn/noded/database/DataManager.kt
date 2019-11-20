package me.spryn.noded.database

import me.spryn.noded.models.NoteModel
import me.spryn.noded.models.NotebookModel

// Controls both public and local databases, is the interface for UI

// Pass a notebook to be added to or updated in the database
fun saveNotebook(notebook: NotebookModel){

    // Check if exists
        // Insert if not
        // Update otherwise
            // Make sure to set new last modified

}

// Pass a note to be added to or updated in the database
fun saveNote(note: NoteModel){

}