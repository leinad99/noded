package me.spryn.noded.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import me.spryn.noded.models.NoteModel
import me.spryn.noded.models.NotebookModel

@Dao
interface SleepDatabaseDao{

    @Insert
    fun insertNotebook(notebook: NotebookModel)

    @Update
    fun updateNotebook(notebook: NotebookModel)

    @Query("SELECT * FROM notebooks_table WHERE title = :title")
    fun getNotebook(title: String): NotebookModel

    @Query("SELECT * FROM notebooks_table ORDER BY lastModified DESC")
    fun getAllNotebooks(): List<NotebookModel>

    @Insert
    fun insertNote(note: NoteModel)

    @Update
    fun updateNote(note: NoteModel)

    @Query("SELECT * FROM notes_table WHERE notebookTitle = :notebookTitle ORDER BY lastModified DESC")
    fun getAllNotesFromNotebook(notebookTitle: String): List<NoteModel>

}