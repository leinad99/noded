package me.spryn.noded.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import me.spryn.noded.models.NoteModel
import me.spryn.noded.models.NotebookModel

@Dao
interface LocalNotebookDao{

    @Insert
    fun insertNotebook(notebook: NotebookModel)

    @Update
    fun updateNotebook(notebook: NotebookModel)

    @Query("SELECT * FROM notebooks_table WHERE title = :title")
    fun getNotebook(title: String): NotebookModel?

    @Query("SELECT * FROM notebooks_table ORDER BY lastModified DESC")
    fun getAllNotebooks(): List<NotebookModel>

}