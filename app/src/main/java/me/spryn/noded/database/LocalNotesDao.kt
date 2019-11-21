package me.spryn.noded.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import me.spryn.noded.models.NoteModel

@Dao
interface LocalNotesDao{

    @Insert
    fun insertNote(note: NoteModel)

    @Update
    fun updateNote(note: NoteModel)

    @Query("SELECT * FROM notes_table WHERE notebookTitle = :notebookTitle ORDER BY lastModified DESC")
    fun getAllNotesFromNotebook(notebookTitle: String): List<NoteModel>

    @Query("SELECT * FROM notes_table WHERE title = :noteTitle AND notebookTitle = :notebookTitle ORDER BY lastModified DESC")
    fun getNote(noteTitle: String, notebookTitle: String): NoteModel?

}