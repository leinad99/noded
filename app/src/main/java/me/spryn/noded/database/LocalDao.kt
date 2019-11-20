package me.spryn.noded.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import me.spryn.noded.models.NotebookModel

@Dao
interface SleepDatabaseDao{

    @Insert
    fun insert(notebook: NotebookModel)

    @Update
    fun update(notebook: NotebookModel)

    @Query("SELECT * FROM notebook_table ORDER BY lastModified DESC")
    fun getAllNights(): List<NotebookModel>

}